package com.vtbcapital.itops.rcrt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class UsersCellEditor extends AbstractCellEditor implements
		TableCellEditor , ActionListener {
	
	Users currentUser;
	JButton button;
	UserPicker dialog;
	private TableCellEditor editor; 
	private JButton customEditorButton = new JButton("..."); 

    protected JTable table; 
    protected int row, column; 
	
	
	protected static final String EDIT = "edit";
	
	public UsersCellEditor(TableCellEditor editor) {
		this.editor = editor;
		customEditorButton.addActionListener(this); 
		
		// ui-tweaking 
        customEditorButton.setFocusable(false); 
        customEditorButton.setFocusPainted(false); 
        customEditorButton.setMargin(new Insets(0, 0, 0, 0)); 
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel label = new JLabel();
		
		if (value != null) {
			label.setText(value.toString());
		}

		panel.add(label);
        panel.add(customEditorButton, BorderLayout.EAST); 
        this.table = table; 
        this.row = row; 
        this.column = column; 
        return panel; 
	}

	@Override
	public Object getCellEditorValue() {
		return editor.getCellEditorValue();
	}
	
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return editor.isCellEditable(anEvent);
	}
	
	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return editor.shouldSelectCell(anEvent);
	}

	@Override
	public boolean stopCellEditing() {
		return editor.stopCellEditing();
	}

	@Override
	public void cancelCellEditing() {
		editor.cancelCellEditing();
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		editor.addCellEditorListener(l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		editor.removeCellEditorListener(l);
	}
	
		
	@Override
	public void actionPerformed(ActionEvent e) {
		editor.cancelCellEditing();
		editCell(table, row, column);
	}
	
	
	 protected void editCell(JTable table, int row, int column) {
		dialog = new UserPicker((Users) table.getValueAt(row, column));
		dialog.strUserName = table.getValueAt(row, column - 2).toString();
		dialog.strEMail = table.getValueAt(row, column - 1).toString();
		dialog.setModal(true);
		dialog.setVisible(true);
		if (dialog.result != null) {
			currentUser = dialog.result;
			System.out.println(currentUser);
			table.setValueAt(currentUser, row, column);
		}
	 }

}
