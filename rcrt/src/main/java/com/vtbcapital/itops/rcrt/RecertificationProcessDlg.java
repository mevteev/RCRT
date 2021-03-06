package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;

import org.hibernate.exception.spi.ConversionContext;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class RecertificationProcessDlg extends JDialog implements TableModelListener {

	
	private final JPanel contentPanelWizard = new JPanel();
	private JTable table_users;
	private JTable tableDetails;
	
	private JButton btBack;
	private JButton btNext;
	
	private JList listApplication;
	
	private JPanel panelSelectApplication;
	private JPanel panelReadUsersList;
	private JPanel panelMatchUsers;
	private JPanel panelLineManagers;
	private JPanel panelConfirm;
	
	private int currentPage; 
	private JPanel currentPanel;
	private JPanel[] wizard;
	
	private JTextArea taLog;
	
	private Applications selectedApp;
	
	private List<RecertificationDetail> lstRd;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	private String[] titles = new String[] {"Select application", "Reading user list from database", 
			 "Match users", "Set line managers", "Review and confirm"};
	
	private RecertificationDetailTableModel rdModel;
	private JLabel lblApplication;
	private JTextField tfApplication;
	private JLabel lblRecertificationDate;
	private JFormattedTextField tfRecertificationDate;
	private JTable tableConfirm;
	private JScrollPane scrollPane_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RecertificationProcessDlg dialog = new RecertificationProcessDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RecertificationProcessDlg() {
		
		setBounds(100, 100, 565, 401);
		getContentPane().setLayout(new BorderLayout());
		contentPanelWizard.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanelWizard, BorderLayout.CENTER);
		contentPanelWizard.setLayout(new CardLayout(0, 0));
		{
			panelSelectApplication = new JPanel();
			contentPanelWizard.add(panelSelectApplication, "name_201259768106540");
			GridBagLayout gbl_panelSelectApplication = new GridBagLayout();
			gbl_panelSelectApplication.columnWidths = new int[]{226, 87, 0};
			gbl_panelSelectApplication.rowHeights = new int[]{14, 0, 0};
			gbl_panelSelectApplication.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_panelSelectApplication.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			panelSelectApplication.setLayout(gbl_panelSelectApplication);
			{
				JLabel lblSelectApplication = new JLabel("Select application:");
				GridBagConstraints gbc_lblSelectApplication = new GridBagConstraints();
				gbc_lblSelectApplication.insets = new Insets(0, 0, 5, 5);
				gbc_lblSelectApplication.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblSelectApplication.gridx = 0;
				gbc_lblSelectApplication.gridy = 0;
				panelSelectApplication.add(lblSelectApplication, gbc_lblSelectApplication);
			}
			{
				listApplication = new JList(Applications.getApplications().toArray());
				listApplication.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						btNext.setEnabled(!listApplication.isSelectionEmpty());
						
					}
				});
				GridBagConstraints gbc_listApplication = new GridBagConstraints();
				gbc_listApplication.insets = new Insets(0, 0, 0, 5);
				gbc_listApplication.fill = GridBagConstraints.BOTH;
				gbc_listApplication.gridx = 0;
				gbc_listApplication.gridy = 1;
				panelSelectApplication.add(listApplication, gbc_listApplication);
			}
		}
		{
			panelReadUsersList = new JPanel();
			contentPanelWizard.add(panelReadUsersList, "name_202630300808156");
			GridBagLayout gbl_panelReadUsersList = new GridBagLayout();
			gbl_panelReadUsersList.columnWidths = new int[]{0, 0};
			gbl_panelReadUsersList.rowHeights = new int[]{0, 0, 0};
			gbl_panelReadUsersList.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelReadUsersList.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			panelReadUsersList.setLayout(gbl_panelReadUsersList);
			{
				JButton btnReadUsersList = new JButton("Read users list");
				btnReadUsersList.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						lstRd = readUsersList(selectedApp);
					}
				});
				GridBagConstraints gbc_btnReadUsersList = new GridBagConstraints();
				gbc_btnReadUsersList.insets = new Insets(0, 0, 5, 0);
				gbc_btnReadUsersList.gridx = 0;
				gbc_btnReadUsersList.gridy = 0;
				panelReadUsersList.add(btnReadUsersList, gbc_btnReadUsersList);
			}
			{
				taLog = new JTextArea();
				GridBagConstraints gbc_taLog = new GridBagConstraints();
				gbc_taLog.fill = GridBagConstraints.BOTH;
				gbc_taLog.gridx = 0;
				gbc_taLog.gridy = 1;
				panelReadUsersList.add(taLog, gbc_taLog);
			}
		}
		{
			panelMatchUsers = new JPanel();
			contentPanelWizard.add(panelMatchUsers, "name_202742481011784");
			GridBagLayout gbl_panelMatchUsers = new GridBagLayout();
			gbl_panelMatchUsers.columnWidths = new int[]{0, 0};
			gbl_panelMatchUsers.rowHeights = new int[]{0, 0};
			gbl_panelMatchUsers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelMatchUsers.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panelMatchUsers.setLayout(gbl_panelMatchUsers);
			{
				scrollPane = new JScrollPane();
				GridBagConstraints gbc_scrollPane = new GridBagConstraints();
				gbc_scrollPane.fill = GridBagConstraints.BOTH;
				gbc_scrollPane.gridx = 0;
				gbc_scrollPane.gridy = 0;
				panelMatchUsers.add(scrollPane, gbc_scrollPane);
				{
					table_users = new JTable();
					scrollPane.setViewportView(table_users);
				}
			}
		}
		{
			panelLineManagers = new JPanel();
			contentPanelWizard.add(panelLineManagers, "name_202823326922861");
			GridBagLayout gbl_panelLineManagers = new GridBagLayout();
			gbl_panelLineManagers.columnWidths = new int[]{0, 0};
			gbl_panelLineManagers.rowHeights = new int[]{0, 0};
			gbl_panelLineManagers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelLineManagers.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panelLineManagers.setLayout(gbl_panelLineManagers);
			{
				scrollPane_1 = new JScrollPane();
				GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
				gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
				gbc_scrollPane_1.gridx = 0;
				gbc_scrollPane_1.gridy = 0;
				panelLineManagers.add(scrollPane_1, gbc_scrollPane_1);
				{
					tableDetails = new JTable() {
						public TableCellEditor getCellEditor(int row, int column) {
							int modelColumn = convertColumnIndexToModel(column);
							if (modelColumn != rdModel.TABLE_DETAILS_COLUMN_LINE_MANAGER) {
								return super.getCellEditor(row, column);
							} else {
								int index = convertRowIndexToModel(row);
								RecertificationDetail rdCurr = rdModel.getRdList().get(index);
								//JComboBox<Users> cb = new JComboBox<Users>(rdCurr.getProposedLineManagers().toArray());
								
								return new ProposedManagerComboBoxEditor(rdCurr.getProposedLineManagers());
							}
						}
						
					};
					scrollPane_1.setViewportView(tableDetails);
					
					//tableDetails.setDefaultEditor(Users.class,
                    //        new UsersCellEditor(tableDetails.getDefaultEditor(String.class)));
					

				}
			}
		}
		{
			panelConfirm = new JPanel();
			contentPanelWizard.add(panelConfirm, "name_202922565411318");
			GridBagLayout gbl_panelConfirm = new GridBagLayout();
			gbl_panelConfirm.columnWidths = new int[]{157, 69, 0};
			gbl_panelConfirm.rowHeights = new int[]{23, 0, 0, 0, 0};
			gbl_panelConfirm.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panelConfirm.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			panelConfirm.setLayout(gbl_panelConfirm);
			{
				lblApplication = new JLabel("Application:");
				GridBagConstraints gbc_lblApplication = new GridBagConstraints();
				gbc_lblApplication.anchor = GridBagConstraints.WEST;
				gbc_lblApplication.insets = new Insets(0, 0, 5, 5);
				gbc_lblApplication.gridx = 0;
				gbc_lblApplication.gridy = 0;
				panelConfirm.add(lblApplication, gbc_lblApplication);
			}
			{
				tfApplication = new JTextField();
				tfApplication.setEditable(false);
				GridBagConstraints gbc_tfApplication = new GridBagConstraints();
				gbc_tfApplication.insets = new Insets(0, 0, 5, 0);
				gbc_tfApplication.fill = GridBagConstraints.HORIZONTAL;
				gbc_tfApplication.gridx = 1;
				gbc_tfApplication.gridy = 0;
				panelConfirm.add(tfApplication, gbc_tfApplication);
				tfApplication.setColumns(10);
			}
			{
				lblRecertificationDate = new JLabel("Recertification date:");
				GridBagConstraints gbc_lblRecertificationDate = new GridBagConstraints();
				gbc_lblRecertificationDate.anchor = GridBagConstraints.WEST;
				gbc_lblRecertificationDate.insets = new Insets(0, 0, 5, 5);
				gbc_lblRecertificationDate.gridx = 0;
				gbc_lblRecertificationDate.gridy = 1;
				panelConfirm.add(lblRecertificationDate, gbc_lblRecertificationDate);
			}
			{
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				format.setLenient(false);

				tfRecertificationDate = new JFormattedTextField(format);
				tfRecertificationDate.setColumns(10);
				GridBagConstraints gbc_formattedTextField = new GridBagConstraints();
				gbc_formattedTextField.insets = new Insets(0, 0, 5, 0);
				gbc_formattedTextField.fill = GridBagConstraints.HORIZONTAL;
				gbc_formattedTextField.gridx = 1;
				gbc_formattedTextField.gridy = 1;
				panelConfirm.add(tfRecertificationDate, gbc_formattedTextField);
			}
			{
				scrollPane_2 = new JScrollPane();
				GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
				gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
				gbc_scrollPane_2.gridwidth = 2;
				gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
				gbc_scrollPane_2.gridx = 0;
				gbc_scrollPane_2.gridy = 2;
				panelConfirm.add(scrollPane_2, gbc_scrollPane_2);
				{
					tableConfirm = new JTable();
					scrollPane_2.setViewportView(tableConfirm);
				}
			}
			{
				JButton btnConfirm = new JButton("Confirm");
				btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						startRecertification();
					}
				});
				GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
				gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
				gbc_btnConfirm.anchor = GridBagConstraints.NORTHWEST;
				gbc_btnConfirm.gridx = 0;
				gbc_btnConfirm.gridy = 3;
				panelConfirm.add(btnConfirm, gbc_btnConfirm);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btBack = new JButton("Back");
				btBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						backButtonPress();
					}
				});
				btBack.setEnabled(false);
				btBack.setActionCommand("OK");
				buttonPane.add(btBack);
				getRootPane().setDefaultButton(btBack);
			}
			{
				btNext = new JButton("Next");
				btNext.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						nextButtonPress();
					}
				});
				btNext.setEnabled(false);
				btNext.setActionCommand("Cancel");
				buttonPane.add(btNext);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		
		 wizard = new JPanel[] { panelSelectApplication,panelReadUsersList,
				 panelMatchUsers, panelLineManagers, panelConfirm};
		 
		 
		 
		
		currentPage = 0;
		currentPanel = wizard[currentPage];
		this.setTitle(titles[currentPage]);
		
		
	}
	
	
	private void nextButtonPress() {
		TableColumnModel tcm;
		switch (currentPage) {
			case 0:
				selectedApp = (Applications)listApplication.getSelectedValue();
				break;
			case 1:
				rdModel = new RecertificationDetailTableModel(lstRd, selectedApp, true);
				table_users.setModel(rdModel);
				table_users.getColumnModel().getColumn(rdModel.TABLE_USERS_COLUMN_USER).setCellEditor(new UsersCellEditor(table_users.getDefaultEditor(Users.class)));
				table_users.getModel().addTableModelListener(this);
				tcm = table_users.getColumnModel();
				tcm.removeColumn(tcm.getColumn(5));
				tcm.removeColumn(tcm.getColumn(4));
				for (int i = Applications.numberAdditionalFields - 1 ; i >= 0 ; i--) {
					if (selectedApp.getField(i).isEmpty()) {
						tcm.removeColumn(tcm.getColumn(4 + i));
					}
				}
				break;
				
			case 2:
				//RecertificationDetailTableModel rdModel = new RecertificationDetailTableModel(lstRd, true);
				rdModel.fillPreviousLineManagers(selectedApp);
				rdModel.proposeLineManagers();
				tableDetails.setModel(rdModel);
				rdModel.addTableModelListener(this);
				//tableDetails.getColumnModel().getColumn(3).setCellEditor(new UsersCellEditor(tableDetails.getDefaultEditor(Users.class)));
				
				tcm = tableDetails.getColumnModel();
				tcm.removeColumn(tcm.getColumn(10));
				tcm.removeColumn(tcm.getColumn(9));
				tcm.removeColumn(tcm.getColumn(8));
				tcm.removeColumn(tcm.getColumn(7));
				tcm.removeColumn(tcm.getColumn(6));
				
				break;
			case 3:
				Date date = new Date();
				tfRecertificationDate.setValue(date);
				tfApplication.setText(selectedApp.getName());
				
				tableConfirm.setModel(rdModel);
				
				tcm = tableConfirm.getColumnModel();
				tcm.removeColumn(tcm.getColumn(5));
				for (int i = Applications.numberAdditionalFields - 1 ; i >= 0 ; i--) {
					if (selectedApp.getField(i).isEmpty()) {
						tcm.removeColumn(tcm.getColumn(5 + i));
					}
				}
				
				break;
		}
		
		
		if (currentPage < wizard.length - 1) {
			currentPage ++;
			
			this.setTitle(titles[currentPage]);
			
			wizard[currentPage - 1].setVisible(false);
			wizard[currentPage].setVisible(true);
			
			btBack.setEnabled(true);
			
			if (currentPage == 3) btNext.setEnabled(rdModel.isAllLineManagersSet());
			else if (currentPage == 2) btNext.setEnabled(rdModel.isAllUsersMatched());
			else btNext.setEnabled(false);
		}
	}
	
	private void backButtonPress() {
		if (currentPage > 0) {
			currentPage--;
			wizard[currentPage + 1].setVisible(false);
			wizard[currentPage].setVisible(true);
			btNext.setEnabled(true);
			btBack.setEnabled(currentPage>0);
			
			this.setTitle(titles[currentPage]);
		}
	}
	
	private List<RecertificationDetail> readUsersList(Applications app) {
		taLog.setText("Reading applications users\n");
		List<RecertificationDetail> ll = app.getUsersList();
		if (ll.isEmpty()) {
			taLog.setText(taLog.getText() + "Returned empty list");
			btNext.setEnabled(false);
		} else {
			taLog.setText(taLog.getText() + "Returned " + ll.size() + " records");
			btNext.setEnabled(true);
		}
		
		return ll;
	}

	
	private void startRecertification() {
		Recertifications r = new Recertifications();
		r.setApplication(selectedApp);
		r.setStartDate((Date)tfRecertificationDate.getValue());
		
		HibernateUtil.saveElement(r);
		
		rdModel.save(r);
		
		System.out.println("Saved");
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		switch (currentPage) {
		case 2:
			btNext.setEnabled(rdModel.isAllUsersMatched());
			break;
		case 3:
			btNext.setEnabled(rdModel.isAllLineManagersSet());
			break;
		}
		
	}

	


}
