package com.vtbcapital.itops.rcrt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.transaction.Transactional;

public class RecertificationDetailTableModel extends AbstractTableModel {
	
	public static Users fakeUser = new Users("...", "", "");
	
	public static final int TABLE_DETAILS_COLUMN_LINE_MANAGER = 4; 
	public static final int TABLE_USERS_COLUMN_USER = 3; 
	
	
	private final int managerDepth = 3;
	
	private List<RecertificationDetail> rdList;
	
	private String[] columnNames = new String[] {"Login", "UserName", "EMail", "User", "LineManager", "PrevLineManager", 
			"Field1", "Field2", "Field3", "Field4", "Field5"};
	
	private Applications app;
	
	public RecertificationDetailTableModel(List<RecertificationDetail> rdList, Applications app, boolean mapByMail) {
		this.setRdList(rdList);
		this.setApp(app);
		
		if (mapByMail) {
			mapByEmail();
		}
		
		
	}
	
	@Override
	public int getRowCount() {
		return getRdList().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column < 6) {
			return columnNames[column];
		}
		
		return getApp().getField(column - 6);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		RecertificationDetail rd = getRdList().get(rowIndex);
		Object[] values = new Object[] {rd.getLogin(), rd.getUserName(), rd.getEmail(), rd.getUser(),
				rd.getLineManager(), rd.getPrevLineManager(), 
				rd.getField1(), rd.getField2(), rd.getField3(), rd.getField4(), rd.getField5()};
		return values[columnIndex];
	}
	
	public Class getColumnClass(int c) {
        Class[] values = new Class[] {String.class, String.class, String.class, Users.class, Users.class, Users.class,
        		String.class, String.class, String.class, String.class, String.class};
        return values[c];
    }
	
	public boolean isCellEditable(int row, int col) {
		boolean[] values = new boolean[] {false, false, false, true, true, false, 
				false, false, false, false, false};
		return values[col];
	}
	
	public void setValueAt(Object value, int row, int col) {
		//set user
		if (col == TABLE_USERS_COLUMN_USER) {
			getRdList().get(row).setUser((Users)value);
			fireTableCellUpdated(row, col);
		}
		if (col == TABLE_DETAILS_COLUMN_LINE_MANAGER) {
			if (value != null && ((Users)value).equals(fakeUser)) {
				UserPicker dialog = new UserPicker();
				dialog.setModal(true);
				dialog.setVisible(true);
				if (dialog.result != null) {
					value = dialog.result;
				}				
			}
			
			getRdList().get(row).setLineManager((Users)value);
			fireTableCellUpdated(row, col);
		}
	}
	
	public void mapByEmail() {
		UsersTableModel utm = new UsersTableModel();

		for (RecertificationDetail rd : getRdList()) {
			if (!rd.getEmail().isEmpty()) {
				rd.setUser(utm.findByEMail(rd.getEmail()));
			}
		}
	}
	
	
	
	public void fillPreviousLineManagers(Applications app) {
		HibernateUtil.initialize(app.getRecertifications());
		
		Set<Recertifications> recert = app.getRecertifications();
		System.out.println(recert.size());
		for (RecertificationDetail rd : getRdList()) {
			Users user = rd.getUser();
			if (user != null) {
				rd.setPrevLineManager(user.getPreviousLineManager(app));
			}
		}
	}
	
	public void proposeLineManagers() {
		
		for (RecertificationDetail rd : getRdList()) {
			Users user = rd.getUser();
			if (user != null) {
				Vector<Users> managersList = new Vector<Users>();
				
				if (rd.getPrevLineManager() != null)
					managersList.add(rd.getPrevLineManager());

				String domainAcc = user.getDomainAccount();
				if (!domainAcc.isEmpty()) {
					UsersLdap manager = ActiveDirectoryUserInfo.getManager(domainAcc);
					int i = 0;
					while (true) {
						if (manager != null) {
							if (!managersList.contains(manager)) {
								managersList.add(manager);
							}
						} else {
							break;
						}
							
						i++;
						if (i >= managerDepth) {
							break;
						}
						
						manager = ActiveDirectoryUserInfo.getManager(manager.getCn());
					} 
						
				}
				managersList.add(fakeUser);
				rd.setProposedLineManagers(managersList);
				
				if (!managersList.isEmpty()) {
					rd.setLineManager(managersList.get(0));
				}
			}
		}
		
	}
	
	public boolean isAllUsersMatched() {
		for (RecertificationDetail rd : getRdList()) {
			if (rd.getUser() == null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isAllLineManagersSet() {
		for (RecertificationDetail rd : getRdList()) {
			if (rd.getLineManager() == null || rd.getLineManager().equals(fakeUser)) {
				return false;
			}
		}
		return true;
	}	

	public List<RecertificationDetail> getRdList() {
		return rdList;
	}

	public void setRdList(List<RecertificationDetail> rdList) {
		this.rdList = rdList;
	}
	
	public void save(Recertifications r) {
		UsersTableModel utm = new UsersTableModel();
		
		for (RecertificationDetail rd : rdList) {
			//save user if not saved
			Users usr = utm.findByName(rd.getLineManager().getName());
			if (usr == null) {
				HibernateUtil.saveElement("com.vtbcapital.itops.rcrt.Users", rd.getLineManager());
			} else {
				rd.setLineManager(usr);
			}
			
			rd.setRecertification(r);
			
			HibernateUtil.saveElement(rd);
		}
	}

	public Applications getApp() {
		return app;
	}

	public void setApp(Applications app) {
		this.app = app;
	}

}
