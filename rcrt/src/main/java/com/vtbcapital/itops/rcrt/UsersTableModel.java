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
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query q = session.createQuery("from Users");
		
		UsersList = new ArrayList<Users>(q.list());
		
		session.close();
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

}
