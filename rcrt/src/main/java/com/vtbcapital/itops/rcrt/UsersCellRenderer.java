package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class UsersCellRenderer extends JLabel implements TableCellRenderer {

	Border unselectedBorder = null;
    Border selectedBorder = null;
    //boolean isBordered = true;
    
    TableCellRenderer originalRenderer;
    
    JTable table;
 
    public UsersCellRenderer(JTable table) {
        this.table = table;
        
    	originalRenderer = table
				.getDefaultRenderer(Users.class);
        //setOpaque(true); //MUST do this for background to show up.
    }
 
    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
        
    	

    	
    	Component component = originalRenderer
				.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);  

    	// Just check for sanity, this is overkill.
		if (!(component instanceof JLabel))
			throw new RuntimeException(
					"Programmer error, wrong type");

		// The component is a label
		JLabel label = (JLabel) component;

    	
    	setIcon(null);
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new BorderLayout());
    	
    	if (isSelected) {
    		panel.setForeground(table.getSelectionForeground());
    		panel.setBackground(table.getSelectionBackground());
    	} else {
    		panel.setForeground(table.getForeground());
    		panel.setBackground(table.getBackground());
    	}
    	
    	// Create and add a button with the icon;
		// this button has no listener
		panel.add(new JButton("..."), BorderLayout.WEST);

		// Add the original JLabel renderer
		panel.add(label, BorderLayout.CENTER);

		// The panel should be displayed
		return panel;
    }
}
