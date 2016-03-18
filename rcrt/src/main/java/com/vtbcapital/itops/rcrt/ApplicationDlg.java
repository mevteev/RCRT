package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;

import javax.swing.JRadioButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLayeredPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.ObjectProperty;

import java.awt.Color;
import java.awt.GridLayout;

public class ApplicationDlg extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JTextField tfServer;
	private JTextField tfDatabase;
	private JTextField tfUser;
	private JPasswordField pfPassword;
	
	private JSpinner spInterval;
	private UtilDateModel dateModel;
	private JComboBox<Applications> cbApplication;
	private JTextArea taNotes;
	final private JTextArea taStatement;
	
	DefaultComboBoxModel<Applications> applicationModel;
	
	private boolean isModified;
	
	private Applications currentApp;
	
	
	private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void removeUpdate(DocumentEvent e) {
        	isModified = true;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
        	isModified = true;
        }

        @Override
        public void changedUpdate(DocumentEvent arg0) {
        	isModified = true;
        }			
	};
	
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	private String[] requiredFields = {"Login", "UserName", "EMail" };
	private String[] additionalFields = {"Field1", "Field2", "Field3", "Field4", "Field5" };
	private JTextField[] tFields;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ApplicationDlg dialog = new ApplicationDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the dialog.
	 */
	public ApplicationDlg() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Applications settings");
		setBounds(100, 100, 623, 589);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 518, 607, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Close");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (isModified) {
							if (JOptionPane.showConfirmDialog(null, "Data was changed. Do you want to save?", "Data was changed", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								saveChanges();
							}
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		JLabel lblApplication = new JLabel("Application");
		lblApplication.setBounds(10, 11, 123, 14);
		getContentPane().add(lblApplication);
		
		cbApplication = new JComboBox<Applications>();
		cbApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isModified) {
					if (JOptionPane.showConfirmDialog(null, "Data was changed. Do you want to save?", "Data was changed", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						saveChanges();
					}
				}
				currentApp = (Applications) cbApplication.getSelectedItem();
				fillFormData((Applications)cbApplication.getSelectedItem());
			}
		});
		cbApplication.setBounds(76, 8, 199, 20);
		getContentPane().add(cbApplication);
		
		
		applicationModel = new DefaultComboBoxModel(Applications.getApplications().toArray());
		cbApplication.setModel(applicationModel);
		
		JButton btnAdd = new JButton("Add");

		btnAdd.setBounds(285, 7, 89, 23);
		getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(384, 7, 89, 23);
		getContentPane().add(btnDelete);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 36, 587, 475);
		getContentPane().add(tabbedPane);
		
		dateModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
		
		JPanel panelCommon = new JPanel();
		tabbedPane.addTab("Common", null, panelCommon, null);
		GridBagLayout gbl_panelCommon = new GridBagLayout();
		gbl_panelCommon.columnWidths = new int[]{0, 245, 120, 164, 0};
		gbl_panelCommon.rowHeights = new int[]{23, 14, 20, 0, 0, 0};
		gbl_panelCommon.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCommon.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelCommon.setLayout(gbl_panelCommon);
		

		
		
		JCheckBox chckbxActive = new JCheckBox("Active");
		chckbxActive.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				isModified = true;
			}
		});
		chckbxActive.setSelected(true);
		GridBagConstraints gbc_chckbxActive = new GridBagConstraints();
		gbc_chckbxActive.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxActive.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxActive.gridx = 1;
		gbc_chckbxActive.gridy = 0;
		panelCommon.add(chckbxActive, gbc_chckbxActive);
		
		JLabel lblNextRecertificationDate = new JLabel("Next re-certification date:");
		GridBagConstraints gbc_lblNextRecertificationDate = new GridBagConstraints();
		gbc_lblNextRecertificationDate.anchor = GridBagConstraints.NORTH;
		gbc_lblNextRecertificationDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNextRecertificationDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblNextRecertificationDate.gridx = 1;
		gbc_lblNextRecertificationDate.gridy = 1;
		panelCommon.add(lblNextRecertificationDate, gbc_lblNextRecertificationDate);
		JDatePickerImpl dpDate = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		dpDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isModified = true;
			}
		});
		
		
		
				
		GridBagConstraints gbc_dpDate = new GridBagConstraints();
		gbc_dpDate.anchor = GridBagConstraints.SOUTH;
		gbc_dpDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_dpDate.insets = new Insets(0, 0, 5, 5);
		gbc_dpDate.gridx = 2;
		gbc_dpDate.gridy = 1;
		panelCommon.add(dpDate, gbc_dpDate);
		

		
		
		JLabel lblIntervalBetweenRecertifications = new JLabel("Interval between re-certifications (in months):");
		GridBagConstraints gbc_lblIntervalBetweenRecertifications = new GridBagConstraints();
		gbc_lblIntervalBetweenRecertifications.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIntervalBetweenRecertifications.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntervalBetweenRecertifications.gridx = 1;
		gbc_lblIntervalBetweenRecertifications.gridy = 2;
		panelCommon.add(lblIntervalBetweenRecertifications, gbc_lblIntervalBetweenRecertifications);
		
		spInterval = new JSpinner();
		spInterval.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				isModified = true;
			}
		});
		spInterval.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		GridBagConstraints gbc_spInterval = new GridBagConstraints();
		gbc_spInterval.insets = new Insets(0, 0, 5, 5);
		gbc_spInterval.anchor = GridBagConstraints.NORTHWEST;
		gbc_spInterval.gridx = 2;
		gbc_spInterval.gridy = 2;
		panelCommon.add(spInterval, gbc_spInterval);
		
		JLabel lblNotes = new JLabel("Notes");
		GridBagConstraints gbc_lblNotes = new GridBagConstraints();
		gbc_lblNotes.anchor = GridBagConstraints.WEST;
		gbc_lblNotes.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotes.gridx = 1;
		gbc_lblNotes.gridy = 3;
		panelCommon.add(lblNotes, gbc_lblNotes);
		
		taNotes = new JTextArea();
		GridBagConstraints gbc_taNotes = new GridBagConstraints();
		gbc_taNotes.gridwidth = 3;
		gbc_taNotes.fill = GridBagConstraints.BOTH;
		gbc_taNotes.gridx = 1;
		gbc_taNotes.gridy = 4;
		panelCommon.add(taNotes, gbc_taNotes);
		
		taNotes.getDocument().addDocumentListener(documentListener);
		
		
		
		JPanel panelApprovers = new JPanel();
		tabbedPane.addTab("Approvers", null, panelApprovers, null);
		panelApprovers.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 11, 562, 197);
		panelApprovers.add(table);
		
		JButton btnAdd_1 = new JButton("Add");
		btnAdd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserPicker up = new UserPicker();
				Users selectedUser = up.showDialog();
				if (selectedUser != null) {
					System.out.println(selectedUser);
				} else {
					System.out.println("null");
				}
			}
		});
		btnAdd_1.setBounds(10, 219, 89, 23);
		panelApprovers.add(btnAdd_1);
		
		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.setBounds(109, 219, 89, 23);
		panelApprovers.add(btnDelete_1);
		
		JPanel panelDataSource = new JPanel();
		tabbedPane.addTab("Data Source", null, panelDataSource, null);
		GridBagLayout gbl_panelDataSource = new GridBagLayout();
		gbl_panelDataSource.columnWidths = new int[]{111, 100, 369, 0};
		gbl_panelDataSource.rowHeights = new int[]{0, 0, 0};
		gbl_panelDataSource.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelDataSource.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelDataSource.setLayout(gbl_panelDataSource);
		
		JRadioButton rdbtnDatabase = new JRadioButton("DataBase");
		GridBagConstraints gbc_rdbtnDatabase = new GridBagConstraints();
		gbc_rdbtnDatabase.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnDatabase.gridx = 0;
		gbc_rdbtnDatabase.gridy = 0;
		panelDataSource.add(rdbtnDatabase, gbc_rdbtnDatabase);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnDatabase);
		
		JRadioButton rdbtnCsvFile = new JRadioButton("CSV file");
		GridBagConstraints gbc_rdbtnCsvFile = new GridBagConstraints();
		gbc_rdbtnCsvFile.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCsvFile.gridx = 1;
		gbc_rdbtnCsvFile.gridy = 0;
		panelDataSource.add(rdbtnCsvFile, gbc_rdbtnCsvFile);
		buttonGroup.add(rdbtnCsvFile);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane_1 = new GridBagConstraints();
		gbc_tabbedPane_1.gridwidth = 3;
		gbc_tabbedPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_tabbedPane_1.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane_1.gridx = 0;
		gbc_tabbedPane_1.gridy = 1;
		panelDataSource.add(tabbedPane_1, gbc_tabbedPane_1);
		
		JPanel panelDatabase = new JPanel();
		tabbedPane_1.addTab("Database", null, panelDatabase, null);
		GridBagLayout gbl_panelDatabase = new GridBagLayout();
		gbl_panelDatabase.columnWidths = new int[]{16, 58, 139, 50, 87, 143, 6, 0};
		gbl_panelDatabase.rowHeights = new int[]{8, 20, 23, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelDatabase.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelDatabase.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelDatabase.setLayout(gbl_panelDatabase);
		
		JLabel lblSqlServer = new JLabel("SQL Server:");
		GridBagConstraints gbc_lblSqlServer = new GridBagConstraints();
		gbc_lblSqlServer.anchor = GridBagConstraints.WEST;
		gbc_lblSqlServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblSqlServer.gridx = 1;
		gbc_lblSqlServer.gridy = 1;
		panelDatabase.add(lblSqlServer, gbc_lblSqlServer);
		
		tfServer = new JTextField();
		tfServer.getDocument().addDocumentListener(documentListener);
		
		GridBagConstraints gbc_tfServer = new GridBagConstraints();
		gbc_tfServer.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfServer.anchor = GridBagConstraints.NORTH;
		gbc_tfServer.insets = new Insets(0, 0, 5, 5);
		gbc_tfServer.gridx = 2;
		gbc_tfServer.gridy = 1;
		panelDatabase.add(tfServer, gbc_tfServer);
		tfServer.setColumns(10);
		
		JLabel lblUser = new JLabel("User:");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.anchor = GridBagConstraints.WEST;
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 4;
		gbc_lblUser.gridy = 1;
		panelDatabase.add(lblUser, gbc_lblUser);
		
		tfUser = new JTextField();
		tfUser.getDocument().addDocumentListener(documentListener);
		
		GridBagConstraints gbc_tfUser = new GridBagConstraints();
		gbc_tfUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfUser.anchor = GridBagConstraints.NORTH;
		gbc_tfUser.insets = new Insets(0, 0, 5, 5);
		gbc_tfUser.gridx = 5;
		gbc_tfUser.gridy = 1;
		panelDatabase.add(tfUser, gbc_tfUser);
		tfUser.setColumns(10);
		
		JLabel lblDatabase = new JLabel("Database:");
		GridBagConstraints gbc_lblDatabase = new GridBagConstraints();
		gbc_lblDatabase.anchor = GridBagConstraints.WEST;
		gbc_lblDatabase.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatabase.gridx = 1;
		gbc_lblDatabase.gridy = 2;
		panelDatabase.add(lblDatabase, gbc_lblDatabase);
		
		tfDatabase = new JTextField();
		tfDatabase.getDocument().addDocumentListener(documentListener);
		
		GridBagConstraints gbc_tfDatabase = new GridBagConstraints();
		gbc_tfDatabase.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfDatabase.anchor = GridBagConstraints.NORTH;
		gbc_tfDatabase.insets = new Insets(0, 0, 5, 5);
		gbc_tfDatabase.gridx = 2;
		gbc_tfDatabase.gridy = 2;
		panelDatabase.add(tfDatabase, gbc_tfDatabase);
		tfDatabase.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.WEST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 4;
		gbc_lblPassword.gridy = 2;
		panelDatabase.add(lblPassword, gbc_lblPassword);
		
		pfPassword = new JPasswordField();
		pfPassword.getDocument().addDocumentListener(documentListener);
		
		GridBagConstraints gbc_pfPassword = new GridBagConstraints();
		gbc_pfPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pfPassword.anchor = GridBagConstraints.NORTH;
		gbc_pfPassword.insets = new Insets(0, 0, 5, 5);
		gbc_pfPassword.gridx = 5;
		gbc_pfPassword.gridy = 2;
		panelDatabase.add(pfPassword, gbc_pfPassword);
		
		JButton btnTestConnection = new JButton("Test connection");
		GridBagConstraints gbc_btnTestConnection = new GridBagConstraints();
		gbc_btnTestConnection.anchor = GridBagConstraints.NORTH;
		gbc_btnTestConnection.insets = new Insets(0, 0, 5, 5);
		gbc_btnTestConnection.gridwidth = 2;
		gbc_btnTestConnection.gridx = 1;
		gbc_btnTestConnection.gridy = 3;
		panelDatabase.add(btnTestConnection, gbc_btnTestConnection);
		
		JLabel lblSqlStatement = new JLabel("SQL Statement:");
		GridBagConstraints gbc_lblSqlStatement = new GridBagConstraints();
		gbc_lblSqlStatement.insets = new Insets(0, 0, 5, 5);
		gbc_lblSqlStatement.gridx = 1;
		gbc_lblSqlStatement.gridy = 4;
		panelDatabase.add(lblSqlStatement, gbc_lblSqlStatement);
		
		taStatement = new JTextArea();
		taStatement.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_taStatement = new GridBagConstraints();
		gbc_taStatement.gridwidth = 5;
		gbc_taStatement.insets = new Insets(0, 0, 5, 5);
		gbc_taStatement.fill = GridBagConstraints.BOTH;
		gbc_taStatement.gridx = 1;
		gbc_taStatement.gridy = 5;
		panelDatabase.add(taStatement, gbc_taStatement);
		
		taStatement.getDocument().addDocumentListener(documentListener);
		
		
		JButton btnNewButton = new JButton("Test statement");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testStatement(tfServer.getText(), tfDatabase.getText(), taStatement.getText());
			}
		});
		
		JLabel lblRequiredFields = new JLabel("Required fields: Login, UserName, EMail");
		lblRequiredFields.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblRequiredFields = new GridBagConstraints();
		gbc_lblRequiredFields.anchor = GridBagConstraints.WEST;
		gbc_lblRequiredFields.gridwidth = 2;
		gbc_lblRequiredFields.insets = new Insets(0, 0, 5, 5);
		gbc_lblRequiredFields.gridx = 1;
		gbc_lblRequiredFields.gridy = 6;
		panelDatabase.add(lblRequiredFields, gbc_lblRequiredFields);
		
		JLabel lblAdditionalFieldsField = new JLabel("Additional fields: Field1, Field2, Field3, Field4, Field5");
		lblAdditionalFieldsField.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblAdditionalFieldsField = new GridBagConstraints();
		gbc_lblAdditionalFieldsField.anchor = GridBagConstraints.WEST;
		gbc_lblAdditionalFieldsField.gridwidth = 5;
		gbc_lblAdditionalFieldsField.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdditionalFieldsField.gridx = 1;
		gbc_lblAdditionalFieldsField.gridy = 7;
		panelDatabase.add(lblAdditionalFieldsField, gbc_lblAdditionalFieldsField);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 8;
		panelDatabase.add(btnNewButton, gbc_btnNewButton);
		btnTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testConnection(tfServer.getText(), tfDatabase.getText());
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("CSV file", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 178, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panelFields = new JPanel();
		tabbedPane_1.addTab("Additional fields", null, panelFields, null);
		GridBagLayout gbl_panelFields = new GridBagLayout();
		gbl_panelFields.columnWidths = new int[]{0, 65, 299, 0};
		gbl_panelFields.rowHeights = new int[]{0, 13, 25, 25, 25, 25, 25, 0};
		gbl_panelFields.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelFields.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelFields.setLayout(gbl_panelFields);
		
		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 2;
		gbc_lblTitle.gridy = 1;
		panelFields.add(lblTitle, gbc_lblTitle);
		
		JLabel lblNewLabel = new JLabel("Field 1:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		panelFields.add(lblNewLabel, gbc_lblNewLabel);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 2;
		panelFields.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblField = new JLabel("Field 2:");
		GridBagConstraints gbc_lblField = new GridBagConstraints();
		gbc_lblField.insets = new Insets(0, 0, 5, 5);
		gbc_lblField.anchor = GridBagConstraints.EAST;
		gbc_lblField.gridx = 1;
		gbc_lblField.gridy = 3;
		panelFields.add(lblField, gbc_lblField);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 3;
		panelFields.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblField_1 = new JLabel("Field 3:");
		GridBagConstraints gbc_lblField_1 = new GridBagConstraints();
		gbc_lblField_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblField_1.anchor = GridBagConstraints.EAST;
		gbc_lblField_1.gridx = 1;
		gbc_lblField_1.gridy = 4;
		panelFields.add(lblField_1, gbc_lblField_1);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 4;
		panelFields.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel lblField_2 = new JLabel("Field 4:");
		GridBagConstraints gbc_lblField_2 = new GridBagConstraints();
		gbc_lblField_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblField_2.anchor = GridBagConstraints.EAST;
		gbc_lblField_2.gridx = 1;
		gbc_lblField_2.gridy = 5;
		panelFields.add(lblField_2, gbc_lblField_2);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 5;
		panelFields.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblField_3 = new JLabel("Field 5:");
		GridBagConstraints gbc_lblField_3 = new GridBagConstraints();
		gbc_lblField_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblField_3.anchor = GridBagConstraints.EAST;
		gbc_lblField_3.gridx = 1;
		gbc_lblField_3.gridy = 6;
		panelFields.add(lblField_3, gbc_lblField_3);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 2;
		gbc_textField_5.gridy = 6;
		panelFields.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		

		tFields = new JTextField[] {textField_1, textField_2, textField_3, textField_4, textField_5};
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		// initialization
		fillFormData((Applications)cbApplication.getSelectedItem());
		
		
		currentApp = (Applications)cbApplication.getSelectedItem();
		
		
		
	}
	
	private void testConnection(String server, String database) {
		String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";integratedSecurity=true";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url);
			System.out.println("connected");
			
			JOptionPane.showMessageDialog(this, "Connected");
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this,"Error " + e.getMessage());

			e.printStackTrace();
		}
	}
	
	private void testStatement(String server, String database, String sql) {
		String url = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";integratedSecurity=true";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url);
			
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(sql);
			
			int size = 0;
			try {
				rs.last();
			    size = rs.getRow();
			    rs.beforeFirst();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			String errors = "";
			boolean validated = true;
			
			//Check for required fields
			for (String col : requiredFields) {
				try {
					rs.findColumn(col);
				}
				catch (SQLException e) {
					validated = false;
					errors += "Required column ["+col+"] is absent\n";
				}
			}
			
			//Check for additional fields
			for (int i = 0; i < additionalFields.length; i++) {
				String col = additionalFields[i];
				if (!currentApp.getField(i).isEmpty()) {
					try {
						rs.findColumn(col);
					}
					catch (SQLException e) {
						validated = false;
						errors += "Additional column ["+col+"] is absent\n";
					}
				}
			}

			
			if (validated) {
				JOptionPane.showMessageDialog(this,"Statement is valid. Count rows: " + size);
			} else {
				JOptionPane.showMessageDialog(this,errors, "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this,"Error " + e.getMessage());
			
			e.printStackTrace();
		}
	}
	
	private void fillFormData(Applications app) {
		if (app != null) {
			dateModel.setValue(app.getDate());
			spInterval.setValue(app.getInterval());
			taNotes.setText(app.getNotes());
			
			tfServer.setText(app.getServer());
			tfDatabase.setText(app.getDatabaseName());
			tfUser.setText(app.getUserName());
			pfPassword.setText(app.getPassword());
			taStatement.setText(app.getStatement());
			
			textField_1.setText(app.getField1());
			textField_2.setText(app.getField2());
			textField_3.setText(app.getField3());
			textField_4.setText(app.getField4());
			textField_5.setText(app.getField5());
			
			isModified = false;
			
		}
	}
	
	private void saveChanges() {
		if (isModified) {
			currentApp.setDate(dateModel.getValue());
			currentApp.setInterval((Integer)spInterval.getValue());
			currentApp.setNotes(taNotes.getText());
			currentApp.setServer(tfServer.getText());
			currentApp.setDatabaseName(tfDatabase.getText());
			currentApp.setPassword(pfPassword.getPassword().toString());
			currentApp.setStatement(taStatement.getText());
			
			currentApp.setField1(textField_1.getText());
			currentApp.setField2(textField_2.getText());
			currentApp.setField3(textField_3.getText());
			currentApp.setField4(textField_4.getText());
			currentApp.setField5(textField_5.getText());

			HibernateUtil.saveElement(currentApp);
			
			isModified = false;
			
		}
	}
}
