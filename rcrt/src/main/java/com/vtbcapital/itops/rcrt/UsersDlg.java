package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsersDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfName;
	private JTextField tfEMail;
	private JTextField tfDomain;
	
	private Users result;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UsersDlg dialog = new UsersDlg(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public UsersDlg(Users user) {
		result = user;
		
		setTitle("User");
		setBounds(100, 100, 368, 167);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 96, 14);
		contentPanel.add(lblName);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setBounds(10, 36, 96, 14);
		contentPanel.add(lblEmail);
		
		JLabel lblDomainAccount = new JLabel("Domain account:");
		lblDomainAccount.setBounds(10, 61, 96, 14);
		contentPanel.add(lblDomainAccount);
		
		tfName = new JTextField();
		tfName.setBounds(116, 8, 226, 20);
		contentPanel.add(tfName);
		tfName.setColumns(10);
		
		tfEMail = new JTextField();
		tfEMail.setBounds(116, 33, 226, 20);
		contentPanel.add(tfEMail);
		tfEMail.setColumns(10);
		
		tfDomain = new JTextField();
		tfDomain.setBounds(116, 58, 226, 20);
		contentPanel.add(tfDomain);
		tfDomain.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (result == null) {
							result = new Users();
						}
						result.setName(tfName.getText());
						result.setEmail(tfEMail.getText());
						result.setDomainAccount(tfDomain.getText());
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
		
		if (user != null) {
			tfName.setText(user.getName());
			tfEMail.setText(user.getEmail());
			tfDomain.setText(user.getDomainAccount());
		}
	}
	
	public Users showDialog() {
		setModal(true);
		setVisible(true);
		return result;
	}
}
