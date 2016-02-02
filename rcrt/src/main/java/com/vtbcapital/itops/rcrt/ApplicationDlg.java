package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ApplicationDlg extends JDialog {
	private JTextField textField;
	private JTable table;

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
		setTitle("Applications settings");
		setBounds(100, 100, 623, 589);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 518, 607, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
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
		
		JLabel lblApplication = new JLabel("Application");
		lblApplication.setBounds(10, 11, 123, 14);
		getContentPane().add(lblApplication);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(76, 8, 199, 20);
		getContentPane().add(comboBox);
		
		JButton btnAdd = new JButton("Add");

		btnAdd.setBounds(285, 7, 89, 23);
		getContentPane().add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(384, 7, 89, 23);
		getContentPane().add(btnDelete);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 36, 587, 475);
		getContentPane().add(tabbedPane);
		
		JPanel panelCommon = new JPanel();
		tabbedPane.addTab("Common", null, panelCommon, null);
		panelCommon.setLayout(null);
		
		JLabel lblNextRecertificationDate = new JLabel("Next re-certification date:");
		lblNextRecertificationDate.setBounds(10, 37, 233, 14);
		panelCommon.add(lblNextRecertificationDate);
		

		
		
		JLabel lblIntervalBetweenRecertifications = new JLabel("Interval between re-certifications (in months):");
		lblIntervalBetweenRecertifications.setBounds(10, 65, 233, 14);
		panelCommon.add(lblIntervalBetweenRecertifications);
		
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(253, 28, 164, 23);
		
				
		panelCommon.add(datePicker);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.setBounds(253, 62, 47, 20);
		panelCommon.add(spinner);
		
		JCheckBox chckbxActive = new JCheckBox("Active");
		chckbxActive.setSelected(true);
		chckbxActive.setBounds(6, 7, 97, 23);
		panelCommon.add(chckbxActive);
		
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
