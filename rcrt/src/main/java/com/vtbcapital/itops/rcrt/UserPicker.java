package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.UIManager;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;

public class UserPicker extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfFilter;
	private JTable table;
	private UsersTableModel model;
	private TableRowSorter<UsersTableModel> sorter;
	
	private JButton okButton;
	
	public Users result;
	
	//pass this params to form if 'add new' pressed these values will appear in form
	public String strUserName = null;
	public String strEMail = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UserPicker dialog = new UserPicker();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public UserPicker() {
		this(null);
	}
	/**
	 * Create the dialog.
	 */
	public UserPicker(Users user) {
		setTitle("Select user:");
		setBounds(100, 100, 516, 475);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{72, 156, 56, 115, 0};
		gbl_contentPanel.rowHeights = new int[]{23, 23, 351, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblInputName = new JLabel("Input name:");
			GridBagConstraints gbc_lblInputName = new GridBagConstraints();
			gbc_lblInputName.anchor = GridBagConstraints.WEST;
			gbc_lblInputName.insets = new Insets(0, 0, 5, 5);
			gbc_lblInputName.gridx = 0;
			gbc_lblInputName.gridy = 0;
			contentPanel.add(lblInputName, gbc_lblInputName);
		}
		{
			tfFilter = new JTextField();
			GridBagConstraints gbc_tfFilter = new GridBagConstraints();
			gbc_tfFilter.gridwidth = 2;
			gbc_tfFilter.fill = GridBagConstraints.HORIZONTAL;
			gbc_tfFilter.insets = new Insets(0, 0, 5, 5);
			gbc_tfFilter.gridx = 1;
			gbc_tfFilter.gridy = 0;
			contentPanel.add(tfFilter, gbc_tfFilter);
			tfFilter.setColumns(10);
		}
		{
			JButton btnFind = new JButton("Find");
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newFilter();
				}
			});
			GridBagConstraints gbc_btnFind = new GridBagConstraints();
			gbc_btnFind.anchor = GridBagConstraints.NORTH;
			gbc_btnFind.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnFind.insets = new Insets(0, 0, 5, 0);
			gbc_btnFind.gridx = 3;
			gbc_btnFind.gridy = 0;
			contentPanel.add(btnFind, gbc_btnFind);
		}
		{
			JButton btnAddNew = new JButton("Add new");
			btnAddNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UsersDlg ud = new UsersDlg(null, strUserName, strEMail);
					Users newUser = ud.showDialog();
					if (newUser != null) {
						addNewUser(newUser);
					}
				}
			});
			{
				JButton btnEdit = new JButton("Edit");
				btnEdit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Users user = ((UsersTableModel)table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
						if (user != null) {
							UsersDlg ud = new UsersDlg(user);
							Users edited = ud.showDialog();
							if (edited != null) {
								editUser(edited);
							}
						}
						
					}
				});
				GridBagConstraints gbc_btnEdit = new GridBagConstraints();
				gbc_btnEdit.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnEdit.anchor = GridBagConstraints.NORTH;
				gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
				gbc_btnEdit.gridx = 2;
				gbc_btnEdit.gridy = 1;
				contentPanel.add(btnEdit, gbc_btnEdit);
			}
			GridBagConstraints gbc_btnAddNew = new GridBagConstraints();
			gbc_btnAddNew.anchor = GridBagConstraints.NORTH;
			gbc_btnAddNew.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnAddNew.insets = new Insets(0, 0, 5, 0);
			gbc_btnAddNew.gridx = 3;
			gbc_btnAddNew.gridy = 1;
			contentPanel.add(btnAddNew, gbc_btnAddNew);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPanel.add(scrollPane, gbc_scrollPane);
		
		
		model = new UsersTableModel();
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		

		ListSelectionModel tableSelectionModel = table.getSelectionModel();
		tableSelectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				result = ((UsersTableModel)table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
			}
		});
		
		sorter = new TableRowSorter<UsersTableModel>(model);
		table.setRowSorter(sorter);
		
		if (user != null) {
			int index = model.index(user);
			if (index >= 0) {
				table.setRowSelectionInterval(sorter.convertRowIndexToView(index), sorter.convertRowIndexToView(index));
				
			}
		}
		
		
		TableColumn column = null;
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(model.getColumnWidth(i));
		}
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		
		
		scrollPane.setViewportView(table);
		table.setFillsViewportHeight(true);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						result = ((UsersTableModel)table.getModel()).get(table.convertRowIndexToModel(table.getSelectedRow()));
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						result = null;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	

	
	private void newFilter() {
		RowFilter<UsersTableModel, Object> rf = null;
		
		try {
			rf = RowFilter.regexFilter("(?i)" + tfFilter.getText(), 1);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		
		sorter.setRowFilter(rf);
	}
	
	private void addNewUser(Users user) {
		model.add(user);
		model.fireTableDataChanged();
		
		int numberOfRows = model.getRowCount();
		int lastRow = table.convertRowIndexToView(numberOfRows - 1);
		table.changeSelection(lastRow, 0, false, false);
	}
	
	private void editUser(Users user) {
		int selected = table.getSelectedRow();
		model.add(user);
		model.fireTableDataChanged();	
		table.changeSelection(selected, 0, false, false);
	}
	
	public Users showDialog() {
		setModal(true);
		setVisible(true);
		return result;
	}
	
	public void addConfirmListener(ActionListener listener) {
		okButton.addActionListener(listener);
	}
}
