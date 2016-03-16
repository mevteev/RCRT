package com.vtbcapital.itops.rcrt;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.hibernate.Query;
import org.hibernate.Session;

public class UsersTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Users> UsersList;
	
	private String[] columnNames = new String[] {"id", "Name", "EMail", "DomainAccount"};
	
	final private int[] columnWidth = new int[] {20, 150, 200, 150};

	@SuppressWarnings("unchecked")
	public UsersTableModel() {
		super();
		
		reloadData();

	}
	
	@Override
	public int getRowCount() {
		return UsersList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Users u = UsersList.get(rowIndex);
		Object[] values = new Object[] { u.getId(), u.getName(), u.getEmail(), u.getDomainAccount()};
		
		return values[columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnWidth(int column) {
		return columnWidth[column];
	}
	
	public Users get(int index) {
		if (index < UsersList.size() && index >= 0) {
			return UsersList.get(index); 
		}
		else return null;
	}
	
	public int index(Users user) {
		for (int i = 0; i < UsersList.size(); i++) {
			if (UsersList.get(i).equals(user)) {
				return i;
			}
		}
		return -1;
	}
	
	private void reloadData() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query q = session.createQuery("from Users");
		
		UsersList = new ArrayList<Users>(q.list());
		
		session.close();		
	}
	
	public Users findByEMail(String email) {
		for(Users u : UsersList) {
			if (u.getEmail().equalsIgnoreCase(email)) {
				return u;
			}
		}
		return null;
	}
	
	public Users findByName(String name) {
		for(Users u : UsersList) {
			if (u.getName().equalsIgnoreCase(name)) {
				return u;
			}
		}
		return null;
	}

	
	public void add(Users user) {
		HibernateUtil.saveElement(user);	
		
		reloadData();
	}

}
