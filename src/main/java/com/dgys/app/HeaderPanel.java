package com.dgys.app;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel {
	private DefaultTableModel tableModel;
	private Map<String,String> headerParams;
	public HeaderPanel() {
		this.setLayout(new BorderLayout());
		headerParams = new HashMap<>();
		
		JButton addButton = new JButton("Add");
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("KEY");
		tableModel.addColumn("VALUE");
		
		JTable headerData = new JTable(tableModel);
		headerData.setRowHeight(25);
		
		addButton.addActionListener(event -> tableModel.addRow(new Object[] {"",""}));
		
		JScrollPane dataScrollPane = new JScrollPane(headerData);
		
		this.add(addButton, BorderLayout.NORTH);
		this.add(dataScrollPane, BorderLayout.CENTER);
	}
	
	public Map<String,String> getHeaderParams() {
		for (byte i = 0; i < tableModel.getRowCount(); i++) {
			String key = (String) tableModel.getValueAt(i, 0);
			String value = (String) tableModel.getValueAt(i, 1);
			if(key == null || value == null || key.equals("") || value.equals(""))
				continue;
			
			headerParams.put(key, value);
		}
		
		return headerParams;
	}
}
