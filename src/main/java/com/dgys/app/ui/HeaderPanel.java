package com.dgys.app.ui;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.dgys.app.model.RequestData;

@SuppressWarnings("serial")
public class HeaderPanel extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	private JButton addButton;
	private Map<String,String> headerParams;
	public HeaderPanel() {
		this.setLayout(new BorderLayout());
		
		addButton = new JButton("Add");
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("KEY");
		tableModel.addColumn("VALUE");
		
		table = new JTable(tableModel);
		table.setRowHeight(25);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		addButton.addActionListener(event -> tableModel.addRow(new Object[] {"",""}));
		
		JScrollPane dataScrollPane = new JScrollPane(table);
		
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
	
	public void setComponentEnable(boolean enabled) {
		this.table.setEnabled(enabled);
		this.addButton.setEnabled(enabled);
	}
	
	public void setRequestData(RequestData requestData) {
		setHeaderParams(requestData.getHeaderParams());
	}

	private void setHeaderParams(Map<String, String> headerParams) {
		this.headerParams = headerParams;
		this.tableModel.setRowCount(0);
		
		if (headerParams == null || headerParams.isEmpty())
			return;
		
		headerParams.forEach((key, value) -> {
			Object[] rowData = {key, value};
			tableModel.addRow(rowData);
		});
	}
}
