package com.dgys.app.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.dgys.app.model.RequestData;

@SuppressWarnings("serial")
public class BodyPanel extends JPanel {
	private DefaultTableModel tableModel;
	private JTable bodyTable;
	private JTextArea jsonBody;
	private byte selectedOption;
	private JRadioButton option1;
	private JRadioButton option2;
	private JRadioButton option3;
	private List<NameValuePair> urlEncodedParams;
	
	public BodyPanel() {
		this.setLayout(new BorderLayout());
		initComponent();
	}
	
	private void initComponent() {
		tableModel = new DefaultTableModel();
		tableModel.addColumn("KEY");
		tableModel.addColumn("VALUE");
		bodyTable = new JTable(tableModel);
		bodyTable.setRowHeight(25);
		bodyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		bodyTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == '\n' && bodyTable.getSelectedRow() == bodyTable.getRowCount() - 1) {
					tableModel.addRow(new Object[] {"",""});
				}
			}
		});
		
		JScrollPane tableScrollPane = new JScrollPane(bodyTable);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		option1 = new JRadioButton("none");
		option2 = new JRadioButton("x-www-form-urlencoded");
		option3 = new JRadioButton("json");
		buttonGroup.add(option1);
		buttonGroup.add(option2);
		buttonGroup.add(option3);
		
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.add(option1);
		radioButtonPanel.add(option2);
		radioButtonPanel.add(option3);
		
		jsonBody= new JTextArea(5,10);
		
		JScrollPane jsonBodyScrollPane = new JScrollPane(jsonBody);
		
		JPanel bodyDataPanel = new JPanel(new BorderLayout());
		
		option1.addItemListener(e -> {
			bodyDataPanel.removeAll();
			selectedOption = 1;
			});
		option2.addItemListener(e -> {
			bodyDataPanel.removeAll();
			bodyDataPanel.add(tableScrollPane);
			this.revalidate();
			this.repaint();
			selectedOption = 2;
		});
		option3.addItemListener(e -> {
			bodyDataPanel.removeAll();
			bodyDataPanel.add(jsonBodyScrollPane);
			
			this.revalidate();
			this.repaint();
			selectedOption = 3;
		});
		
		this.add(radioButtonPanel, BorderLayout.NORTH);
		this.add(bodyDataPanel, BorderLayout.CENTER);
	}

	public byte getSelectedOption() {
		return selectedOption;
	}
	
	public List<NameValuePair> getUrlEncodedParams() {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			String key = (String) tableModel.getValueAt(i, 0);
			String value = (String) tableModel.getValueAt(i, 1);
			if(key == null || value == null || key.trim().equals("") || value.trim().equals(""))
				continue;
			
			urlEncodedParams.add(new BasicNameValuePair(key, value));
		}
		
		return urlEncodedParams;
	}
	
	public String getJsonBody() {
		return jsonBody.getText();
	}
	
	public void setComponentEnable(boolean enabled) {
		this.option1.setEnabled(enabled);
		this.option2.setEnabled(enabled);
		this.option3.setEnabled(enabled);
		this.jsonBody.setEnabled(enabled);
		this.bodyTable.setEnabled(enabled);
	}
	
	public void setRequestData(RequestData requestData) {
		setSelectedOption(requestData.getBodyOption());
		setJsonBody(requestData.getJsonBody());
		setUrlEncodedParams(requestData.getUrlEncodedParams());
	}

	private void setJsonBody(String json) {
		this.jsonBody.setText(json);
	}

	private void setSelectedOption(byte selectedOption) {
		switch (selectedOption) {
		case 1:
			option1.setSelected(true);
			break;
		case 2:
			option2.setSelected(true);
			break;
		case 3:
			option3.setSelected(true);
			break;
		default:
			option1.setSelected(true);
		}
	}

	public void setUrlEncodedParams(List<NameValuePair> urlEncodedParams) {
		this.urlEncodedParams = urlEncodedParams;
		this.tableModel.setRowCount(0);
		
		if (urlEncodedParams == null || urlEncodedParams.isEmpty())
			return;
		
		urlEncodedParams.forEach(obj -> {
			Object[] rowData = {obj.getName(), obj.getValue()};
			tableModel.addRow(rowData);
		});
		
		if (tableModel.getRowCount() == 0)
			this.tableModel.addRow(new Object[] {"",""});
	}
}
