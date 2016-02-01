package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class ApplicationDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JTable table_1;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
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
		setTitle("Applications settings");
		setBounds(100, 100, 623, 589);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		table_1 = new JTable();
		table_1.setBounds(433, 384, 141, -121);
		contentPanel.add(table_1);
		
		JLabel lblApplication = new JLabel("Application");
		lblApplication.setBounds(10, 11, 65, 14);
		contentPanel.add(lblApplication);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(73, 8, 209, 20);
		contentPanel.add(comboBox);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(292, 7, 89, 23);
		contentPanel.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(386, 7, 89, 23);
		contentPanel.add(btnRemove);
		
		JLabel lblApprovers = new JLabel("Approvers");
		lblApprovers.setBounds(10, 123, 81, 14);
		contentPanel.add(lblApprovers);
		
		table = new JTable();
		table.setBounds(10, 148, 464, 84);
		contentPanel.add(table);
		
		JButton btnAdd_1 = new JButton("Add");
		btnAdd_1.setBounds(73, 119, 89, 23);
		contentPanel.add(btnAdd_1);
		
		JButton btnRemove_1 = new JButton("Remove");
		btnRemove_1.setBounds(172, 119, 89, 23);
		contentPanel.add(btnRemove_1);
		
		JLabel lblSqlScript = new JLabel("SQL Script:");
		lblSqlScript.setBounds(10, 243, 81, 14);
		contentPanel.add(lblSqlScript);
		
		textField = new JTextField();
		textField.setBounds(10, 262, 371, 68);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setBounds(10, 341, 46, 14);
		contentPanel.add(lblServer);
		
		textField_1 = new JTextField();
		textField_1.setBounds(73, 338, 121, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDatabase = new JLabel("Database:");
		lblDatabase.setBounds(10, 371, 65, 14);
		contentPanel.add(lblDatabase);
		
		textField_2 = new JTextField();
		textField_2.setBounds(73, 368, 121, 20);
		contentPanel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(212, 344, 46, 14);
		contentPanel.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(212, 370, 65, 14);
		contentPanel.add(lblPassword);
		
		textField_3 = new JTextField();
		textField_3.setBounds(275, 341, 118, 20);
		contentPanel.add(textField_3);
		textField_3.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(275, 367, 121, 20);
		contentPanel.add(passwordField);
		
		JLabel lblAdditionalColumns = new JLabel("Additional columns:");
		lblAdditionalColumns.setBounds(423, 243, 118, 14);
		contentPanel.add(lblAdditionalColumns);
		
		JLabel lblNextRecertificationDate = new JLabel("Next recertification date:");
		lblNextRecertificationDate.setBounds(10, 47, 147, 14);
		contentPanel.add(lblNextRecertificationDate);
		
		textField_4 = new JTextField();
		textField_4.setBounds(196, 44, 86, 20);
		contentPanel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblIntervalBetweenRecertifications = new JLabel("Interval between recertifications:");
		lblIntervalBetweenRecertifications.setBounds(10, 73, 184, 14);
		contentPanel.add(lblIntervalBetweenRecertifications);
		
		textField_5 = new JTextField();
		textField_5.setBounds(196, 70, 86, 20);
		contentPanel.add(textField_5);
		textField_5.setColumns(10);
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
	}
}
