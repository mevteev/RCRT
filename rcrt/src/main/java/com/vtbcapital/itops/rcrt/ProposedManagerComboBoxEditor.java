package com.vtbcapital.itops.rcrt;


import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

public class ProposedManagerComboBoxEditor extends DefaultCellEditor {
	public ProposedManagerComboBoxEditor(Vector<Users> items) {
	    super(new JComboBox<Users>(items));
	  }

}
