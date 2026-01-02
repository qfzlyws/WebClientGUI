package com.dgys.app;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

@SuppressWarnings("serial")
public class BodyPanel extends JPanel {
	private DefaultTableModel tableModel;
	private JTextArea jsonBody;
	private byte selectedOption;
	private List<NameValuePair> urlEncodedParams;
	
	public BodyPanel() {
		this.setLayout(new BorderLayout());
		urlEncodedParams = new ArrayList<>();
		initComponent();
	}
	
	private void initComponent() {
		tableModel = new DefaultTableModel();
		tableModel.addColumn("KEY");
		tableModel.addColumn("VALUE");
		tableModel.addRow(new Object[] {"",""});
		JTable bodyTable = new JTable(tableModel);
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
		JRadioButton option1 = new JRadioButton("none");
		JRadioButton option2 = new JRadioButton("x-www-form-urlencoded");
		JRadioButton option3 = new JRadioButton("json");
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
}
