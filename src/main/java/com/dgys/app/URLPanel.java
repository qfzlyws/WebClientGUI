package com.dgys.app;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class URLPanel extends JPanel {
	private JComboBox<String> methods;
	private JTextField urlTextField;

	public URLPanel(ActionListener sendRequestListener) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel urlTextLabel = new JLabel("URL:");
		methods = new JComboBox<>();
		methods.addItem("GET");
		methods.addItem("POST");
		urlTextField = new JTextField(30);
		JButton sendButton = new JButton("Send");

		sendButton.addActionListener(sendRequestListener);

		this.add(urlTextLabel);
		this.add(methods);
		this.add(new JScrollPane(urlTextField));
		this.add(sendButton);
	}

	public String getHttpMethod() {
		return (String) methods.getSelectedItem();
	}

	public String getHttpURL() {
		return urlTextField.getText();
	}
}
