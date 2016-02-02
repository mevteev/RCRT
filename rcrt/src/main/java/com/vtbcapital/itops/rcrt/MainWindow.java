package com.vtbcapital.itops.rcrt;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



public class MainWindow {

	private JFrame frmRecertificationTool;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmRecertificationTool.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRecertificationTool = new JFrame();
		frmRecertificationTool.setTitle("Recertification tool");
		frmRecertificationTool.setBounds(100, 100, 620, 420);
		frmRecertificationTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmRecertificationTool.setJMenuBar(menuBar);
		
		JMenu mnReferences = new JMenu("References");
		menuBar.add(mnReferences);
		
		JMenuItem mntmApplications = new JMenuItem("Applications");
		mnReferences.add(mntmApplications);
		
		
	}

}
