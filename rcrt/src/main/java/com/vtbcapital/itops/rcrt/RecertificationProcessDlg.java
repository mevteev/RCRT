package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

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
import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;

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
	
	private String[] titles;
	
	private RecertificationDetailTableModel rdModel;

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
					tableDetails = new JTable();
					scrollPane_1.setViewportView(tableDetails);
					
					//tableDetails.setDefaultEditor(Users.class,
                    //        new UsersCellEditor(tableDetails.getDefaultEditor(String.class)));
					

				}
			}
		}
		{
			panelConfirm = new JPanel();
			contentPanelWizard.add(panelConfirm, "name_202922565411318");
			{
				JButton btnConfirm = new JButton("Confirm");
				panelConfirm.add(btnConfirm);
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
		 
		 titles = new String[] {"Select application", "Reading user list from database", 
				 "Match users", "Set line managers", "Confirm"};
		 
		 
		
		currentPage = 0;
		currentPanel = wizard[currentPage];
		this.setTitle(titles[currentPage]);
	}
	
	
	private void nextButtonPress() {
		switch (currentPage) {
			case 0:
				selectedApp = (Applications)listApplication.getSelectedValue();
				break;
			case 1:
				rdModel = new RecertificationDetailTableModel(lstRd, true);
				table_users.setModel(rdModel);

				table_users.getColumnModel().getColumn(3).setCellEditor(new UsersCellEditor(table_users.getDefaultEditor(Users.class)));
				
				table_users.getModel().addTableModelListener(this);
				
				TableColumnModel tcm = table_users.getColumnModel();
				tcm.removeColumn(tcm.getColumn(5));
				tcm.removeColumn(tcm.getColumn(4));
				break;
				
			case 2:
				//RecertificationDetailTableModel rdModel = new RecertificationDetailTableModel(lstRd, true);
				tableDetails.setModel(rdModel);

				//tableDetails.getColumnModel().getColumn(3).setCellRenderer(new UsersCellRenderer(tableDetails));
				tableDetails.getColumnModel().getColumn(3).setCellEditor(new UsersCellEditor(tableDetails.getDefaultEditor(Users.class)));
				
				

				break;
		}
		
		
		if (currentPage < wizard.length - 1) {
			currentPage ++;
			
			this.setTitle(titles[currentPage]);
			
			wizard[currentPage - 1].setVisible(false);
			wizard[currentPage].setVisible(true);
			
			btBack.setEnabled(true);
			btNext.setEnabled(false);
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

	@Override
	public void tableChanged(TableModelEvent e) {
		if (currentPage == 2) {	// Matching users
			btNext.setEnabled(rdModel.isAllUsersMatched());
		}
		
	}
	


}
