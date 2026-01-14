package com.dgys.app.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dgys.app.model.RequestData;

@SuppressWarnings("serial")
public class RequestListPanel extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	public RequestListPanel(List<RequestData> requestDataList, MouseListener mouseListener, ActionListener actionListener) {
		this.setLayout(new BorderLayout());
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("請求名稱");
		requestDataList.forEach(obj -> tableModel.addRow(new Object[] {obj}));
		
		table = new JTable(tableModel);
		table.setRowHeight(25);
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(mouseListener);
		
		JButton addButton = new JButton("新增請求");
		JButton deleteButton = new JButton("刪除請求");
		addButton.addActionListener(actionListener);
		deleteButton.addActionListener(actionListener);
		JPanel northComponent = new JPanel();
		northComponent.add(addButton);
		northComponent.add(deleteButton);
		
		this.add(northComponent, BorderLayout.NORTH);
		this.add(new JScrollPane(table));
		this.setPreferredSize(new Dimension(250,30));
	}
	
	public void newRequest(RequestData requestData) {
		Object[] rowData = {requestData};
		tableModel.addRow(rowData);
		int newRowIndex = tableModel.getRowCount() - 1;
		table.setRowSelectionInterval(newRowIndex, newRowIndex);
	}
	
	public RequestData getCurrentSelectedData() {
		int selectedRow = table.getSelectedRow(); 
		
		if(selectedRow == -1)
			return null;
			
		return (RequestData) table.getValueAt(selectedRow, 0);
	}
	
	public void deleteCurrentSelectedRow() {
		int selectedRow = table.getSelectedRow(); 
		
		if(selectedRow == -1)
			return;
			
		tableModel.removeRow(selectedRow);
	}
	
	public void fireTableDataChanged() {
		tableModel.fireTableDataChanged();
	}
}