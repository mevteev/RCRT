package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;

public class RecertificationProcessDlg extends JDialog {

	private final JPanel contentPanelWizard = new JPanel();
	private JTable table;
	private JTable table_1;
	private JTable table_2;

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
			JPanel panelSelectApplication = new JPanel();
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
				table = new JTable();
				GridBagConstraints gbc_table = new GridBagConstraints();
				gbc_table.insets = new Insets(0, 0, 0, 5);
				gbc_table.fill = GridBagConstraints.BOTH;
				gbc_table.gridx = 0;
				gbc_table.gridy = 1;
				panelSelectApplication.add(table, gbc_table);
			}
		}
		{
			JPanel panelReadUsersList = new JPanel();
			contentPanelWizard.add(panelReadUsersList, "name_202630300808156");
			GridBagLayout gbl_panelReadUsersList = new GridBagLayout();
			gbl_panelReadUsersList.columnWidths = new int[]{0, 0};
			gbl_panelReadUsersList.rowHeights = new int[]{0, 0, 0};
			gbl_panelReadUsersList.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelReadUsersList.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			panelReadUsersList.setLayout(gbl_panelReadUsersList);
			{
				JButton btnReadUsersList = new JButton("Read users list");
				GridBagConstraints gbc_btnReadUsersList = new GridBagConstraints();
				gbc_btnReadUsersList.insets = new Insets(0, 0, 5, 0);
				gbc_btnReadUsersList.gridx = 0;
				gbc_btnReadUsersList.gridy = 0;
				panelReadUsersList.add(btnReadUsersList, gbc_btnReadUsersList);
			}
			{
				JTextArea textArea = new JTextArea();
				GridBagConstraints gbc_textArea = new GridBagConstraints();
				gbc_textArea.fill = GridBagConstraints.BOTH;
				gbc_textArea.gridx = 0;
				gbc_textArea.gridy = 1;
				panelReadUsersList.add(textArea, gbc_textArea);
			}
		}
		{
			JPanel panelMatchUsers = new JPanel();
			contentPanelWizard.add(panelMatchUsers, "name_202742481011784");
			GridBagLayout gbl_panelMatchUsers = new GridBagLayout();
			gbl_panelMatchUsers.columnWidths = new int[]{0, 0};
			gbl_panelMatchUsers.rowHeights = new int[]{0, 0};
			gbl_panelMatchUsers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelMatchUsers.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panelMatchUsers.setLayout(gbl_panelMatchUsers);
			{
				table_1 = new JTable();
				GridBagConstraints gbc_table_1 = new GridBagConstraints();
				gbc_table_1.fill = GridBagConstraints.BOTH;
				gbc_table_1.gridx = 0;
				gbc_table_1.gridy = 0;
				panelMatchUsers.add(table_1, gbc_table_1);
			}
		}
		{
			JPanel panelLineManagers = new JPanel();
			contentPanelWizard.add(panelLineManagers, "name_202823326922861");
			GridBagLayout gbl_panelLineManagers = new GridBagLayout();
			gbl_panelLineManagers.columnWidths = new int[]{0, 0};
			gbl_panelLineManagers.rowHeights = new int[]{0, 0};
			gbl_panelLineManagers.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panelLineManagers.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panelLineManagers.setLayout(gbl_panelLineManagers);
			{
				table_2 = new JTable();
				GridBagConstraints gbc_table_2 = new GridBagConstraints();
				gbc_table_2.fill = GridBagConstraints.BOTH;
				gbc_table_2.gridx = 0;
				gbc_table_2.gridy = 0;
				panelLineManagers.add(table_2, gbc_table_2);
			}
		}
		{
			JPanel panelConfirm = new JPanel();
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
				JButton okButton = new JButton("Back");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Next");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}

}
