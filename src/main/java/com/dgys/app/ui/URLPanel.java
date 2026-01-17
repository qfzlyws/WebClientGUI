package com.dgys.app.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.dgys.app.model.RequestData;

@SuppressWarnings("serial")
public class URLPanel extends JPanel {
	private JComboBox<String> httpMethods;
	private JTextField urlTextField;
	private JButton sendButton;
	private JButton saveButton;

	public URLPanel(ActionListener actionListener) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel urlTextLabel = new JLabel("URL:");
		httpMethods = new JComboBox<>();
		httpMethods.addItem("GET");
		httpMethods.addItem("POST");
		urlTextField = new JTextField(30);
		sendButton = new JButton("Send");
		saveButton = new JButton("Save");
		sendButton.addActionListener(actionListener);
		saveButton.addActionListener(actionListener);

		this.add(urlTextLabel);
		this.add(httpMethods);
		this.add(new JScrollPane(urlTextField));
		this.add(sendButton);
		this.add(saveButton);
		this.setBorder(BorderFactory.createEtchedBorder());
	}
	
	private void setMethods(String method) {
		this.httpMethods.setSelectedItem(method);
	}

	private void setUrlTextField(String url) {
		this.urlTextField.setText(url);
	}

	public String getHttpMethod() {
		return (String) httpMethods.getSelectedItem();
	}

	public String getHttpURL() {
		return urlTextField.getText();
	}
	
	public void setComponentEnable(boolean enabled) {
		this.httpMethods.setEnabled(enabled);
		this.urlTextField.setEnabled(enabled);
		this.sendButton.setEnabled(enabled);
		this.saveButton.setEnabled(enabled);
	}
	
	public void setRequestData(RequestData requestData) {	
		setMethods(requestData.getMethod());
		setUrlTextField(requestData.getUrl());
	}
}
