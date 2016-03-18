package com.vtbcapital.itops.rcrt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;








import javax.swing.JOptionPane;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;



@Entity(name="Applications")
public class Applications {
	
	final static int numberAdditionalFields = 5;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private
	int id;
	
	@Column(name="Name")
	private
	String name;
	
	@Column(name="Date")
	private
	Date date;
	
	@Column(name="Interval")
	private
	int interval;
	
	@Column(name="Notes")
	private
	String notes;
	
	@Column(name="Server")
	private
	String server;
	
	@Column(name="DatabaseName")
	private 
	String databaseName;
	
	@Column(name="UserName")
	private 
	String userName;
	
	@Column(name="Password")
	private
	String password;
	
	@Column(name="Statement")
	private String statement; 
	
	@Column(name="Field1")
	private String field1;
	
	@Column(name="Field2")
	private String field2;
	
	@Column(name="Field3")
	private String field3;
	
	@Column(name="Field4")
	private String field4;
	
	@Column(name="Field5")
	private String field5;
	
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
	private Set<Approvers> approvers = new HashSet<Approvers>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "application")
	private Set<Recertifications> recertifications = new HashSet<Recertifications>(0);	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<Approvers> getApprovers() {
		return approvers;
	}

	public void setApprovers(Set<Approvers> approvers) {
		this.approvers = approvers;
	}
	
	
	public String toString() {
		return getName();
	}
	
	public static ArrayList<Applications> getApplications() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query q = session.createQuery("from Applications");
		
		@SuppressWarnings("unchecked")
		ArrayList<Applications> result = new ArrayList<Applications>(q.list());
		
		session.close();

		return result;
		
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	
	public List<RecertificationDetail> getUsersList() {
		List<RecertificationDetail> ll = new LinkedList<RecertificationDetail>();
		
		Connection conn;
		String url = "jdbc:sqlserver://" + getServer() + ";databaseName=" + getDatabaseName() + ";integratedSecurity=true";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url);
			System.out.println("connected");
			
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(getStatement());
			
			
			while(rs.next()) {
				String login = rs.getString("Login");
				String userName = rs.getString("UserName");
				String email = rs.getString("EMail");
				
				
				RecertificationDetail rd = new RecertificationDetail();
				rd.setLogin(login);
				rd.setUserName(userName);
				rd.setEmail(email);
				
				for (int i = 0; i < numberAdditionalFields; i++) {
					if (!this.getField(i).trim().isEmpty()) {
						String fld = rs.getString("Field" + (i+1));
						rd.setField(i, fld);
					}
				}
				
				
				ll.add(rd);
			}
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		return ll;
		
	}

	
	public Set<Recertifications> getRecertifications() {
		return recertifications;
	}

	public void setRecertifications(Set<Recertifications> recertifications) {
		this.recertifications = recertifications;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}
	
	public String getField(int index) {
		switch (index) {
			case 0: return getField1();
			case 1: return getField2();
			case 2: return getField3();
			case 3: return getField4();
			case 4: return getField5();
			default: return "";
		}
	}
	
	

}
