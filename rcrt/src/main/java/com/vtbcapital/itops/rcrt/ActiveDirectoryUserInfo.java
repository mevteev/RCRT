package com.vtbcapital.itops.rcrt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;






import com4j.COM4J;
import com4j.Com4jObject;
import com4j.ComException;
import com4j.Variant;
import com4j.typelibs.activeDirectory.IADs;
import com4j.typelibs.ado20.ClassFactory;
import com4j.typelibs.ado20.Fields;
import com4j.typelibs.ado20._Command;
import com4j.typelibs.ado20._Connection;
import com4j.typelibs.ado20._Recordset;

public class ActiveDirectoryUserInfo {
	

	static String defaultNamingContext = null;
	static String alterNamingContext = null;
	
	private final Map<String, String> infoMap = new HashMap<String, String>();
	
	static HashMap<String, ActiveDirectoryUserInfo> knownUsers = new HashMap<String, ActiveDirectoryUserInfo>();
	
	synchronized void initNamingContext() 
	{
		if (defaultNamingContext == null) 
		{
			IADs rootDSE = COM4J.getObject(IADs.class, "LDAP://RootDSE", null);
			defaultNamingContext = (String)rootDSE.get("defaultNamingContext");
			
			//*****  HARDCODE ********
			alterNamingContext = "uk.vtbcapital.internal/DC=uk,DC=vtbcapital,DC=internal";
			//defaultNamingContext = "corp.vtbcapital.internal/DC=corp,DC=vtbcapital,DC=internal";
			//*************************
			
	    	System.err.println("defaultNamingContext= " + defaultNamingContext);

			
			

		}
	}

	synchronized public static ActiveDirectoryUserInfo getInstance(String username, String requestedInfo) 
	{
		ActiveDirectoryUserInfo found = knownUsers.get(username);
		if (found != null) 
		{
			return found;
		}
		return getInstanceNoCache(username, requestedInfo);
	}

	synchronized public static ActiveDirectoryUserInfo getInstanceNoCache(String username, String requestedInfo) 
	{
		ActiveDirectoryUserInfo found = new ActiveDirectoryUserInfo(username, requestedInfo);
		if (found.infoMap.isEmpty()) 
		{
			return null;
		}
		knownUsers.put(username, found);
		return found;
	}

	private ActiveDirectoryUserInfo (String username, String requestedInfo) 
	{
		System.err.println("* ActiveDirectoryUserInfo ctor *");

		infoMap.clear();
		
		initNamingContext();
		if (defaultNamingContext == null) {
			return;
		}

		// Searching LDAP requires ADO [8], so it's good to create a connection upfront for reuse. 

		_Connection con = ClassFactory.createConnection();
		con.provider("ADsDSOObject");
		con.open("Active Directory Provider",""/*default*/,""/*default*/,-1/*default*/);

		// query LDAP to find out the LDAP DN and other info for the given user from the login ID 

		_Command cmd = ClassFactory.createCommand();
		cmd.activeConnection(con);

		String searchField = "userPrincipalName";
		
		int pSlash = username.indexOf('\\');
		if (!username.startsWith("CN") && (pSlash > 0)) 
		{
			searchField = "sAMAccountName";
			username = username.substring(pSlash+1);
		} else {
			searchField = "distinguishedName";
		}

		System.err.println("sending command: "+"<LDAP://"+defaultNamingContext+">;("+searchField+"="+username+");"+requestedInfo+";subTree");

		cmd.commandText("<LDAP://"+defaultNamingContext+">;("+searchField+"="+username+");"+requestedInfo+";subTree");
		_Recordset rs = cmd.execute(null, Variant.getMissing(), -1/*default*/);
		
		if(rs.eof()) 
		{ // User not found!
			System.err.println("sending command: "+"<LDAP://"+alterNamingContext+">;("+searchField+"="+username+");"+requestedInfo+";subTree");

			cmd.commandText("<LDAP://"+alterNamingContext+">;("+searchField+"="+username+");"+requestedInfo+";subTree");
			rs = cmd.execute(null, Variant.getMissing(), -1/*default*/);
			
		}
		
		if(rs.eof()) {
			System.err.println(username+" not found.");
		}
		else 
		{
			System.err.println("got someting in RS !!");
			Fields userData = rs.fields();
			if (userData != null)
			{
/*				Iterator<Com4jObject> itCom = userData.iterator();
				int i=0;
				while (itCom.hasNext()) 
				{
					Com4jObject comObj = itCom.next();
					_log.info(i++ +":"+comObj.toString());
				} 
*/				
				
				buildInfoMap(requestedInfo, userData);

				
			}
			else
			{
				System.err.println("User "+username+" information is empty.");
			}
		}
		
		
		if(infoMap.isEmpty())
		{
			System.err.println("user-info map is empty - no data was written to it.");
		}

		
		rs.close();
		con.close();
	}

	private void buildInfoMap(String requestedInfo, Fields userData) 
	{
		StringTokenizer tokenizer = new StringTokenizer(requestedInfo, ",");
		String detail ;
		String value = null;
		while( tokenizer.hasMoreTokens() )
		{
			detail = tokenizer.nextToken();

			try
			{
				Object o = userData.item(detail).value();
				if (o != null)
				{
					value = o.toString();
					System.err.println(detail + " = " + value);
					infoMap.put(detail, value);
				}
			}
			catch (ComException ecom ) 
			{
				System.err.println(detail + " not returned: "+ecom.getMessage());
			}
		}
	}

	public static String getDefaultNamingContext() {
		return defaultNamingContext;
	}

	public Map<String, String> getInfoMap()
	{
		return infoMap;
	}
	
	public static UsersLdap getManager(String fqn) {
		String requestedFields= "cn,mail,manager,samAccountName,distinguishedName";
		ActiveDirectoryUserInfo userInfo = ActiveDirectoryUserInfo.getInstance(fqn, requestedFields);
		if (userInfo != null) {
			Map<String, String> infoMap = userInfo.getInfoMap();
			
			String manager = infoMap.get("manager");
			
			ActiveDirectoryUserInfo userInfoManager = ActiveDirectoryUserInfo.getInstance(manager, requestedFields);
			
			if (userInfoManager != null) {
				Map<String, String> infoMapMan = userInfoManager.getInfoMap();
				
				String domain = infoMapMan.get("distinguishedName").split(",DC=")[1];
				UsersLdap user = new UsersLdap(infoMapMan.get("cn"),
						infoMapMan.get("mail"),domain.toUpperCase() + "\\" + infoMapMan.get("samAccountName"),
						infoMapMan.get("distinguishedName"));
				return user;
			}
			
			//System.out.println(domain.toUpperCase() + "\\" + infoMap.get("cn"));			
		}
		
		return null;
	}
	
	
	public static void main(String[] args) {
		//UsersLdap man = getManager("CORP\\APankov");
		UsersLdap man = getManager("uk\\zoisg");
		System.out.println(man);
		UsersLdap man2 = getManager(man.getCn());
		System.out.println(man2);
		UsersLdap man3 = getManager(man2.getCn());
		System.out.println(man3);

	}
	
}
