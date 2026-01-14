package com.dgys.app.ui;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dgys.app.model.RequestData;

@SuppressWarnings("serial")
public class AuthPanel extends JPanel {
	private AuthOption authType = AuthOption.NONE;
	private JComboBox<String> typeComboBox;
	private JTextField tokenTextField;
	public enum AuthOption {
		NONE,BEARERTOKEN,BASICAUTH;
		
		@Override
		public String toString() {
			String authType = "";
			switch(this) {
			case NONE:
				authType =  "<空>";
				break;
			case BEARERTOKEN:
				authType =  "Bearer Token";
				break;
			case BASICAUTH:
				authType =  "Basic Auth";
				break;
			}
			
			return authType;
		}
	}
	
	public AuthPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel typeLabel = new JLabel("Type");
		typeComboBox = new JComboBox<>();
		for(AuthOption option : AuthOption.values()) {
			typeComboBox.addItem(option.toString());
		}
				
		JPanel authDataPanel = new JPanel();
		JLabel tokenLabel = new JLabel("Token");
		tokenTextField = new JTextField(30);
		
		typeComboBox.addActionListener(e -> {
			String selectedItem = (String)typeComboBox.getSelectedItem();
			
			switch (selectedItem) {
			case "<空>":
				authDataPanel.removeAll();
				authType = AuthOption.NONE;
				break;
			case "Bearer Token":
				authDataPanel.removeAll();
				authDataPanel.add(tokenLabel);
				authDataPanel.add(tokenTextField);
				authType = AuthOption.BEARERTOKEN;
				break;
			default:
				
			}
			authDataPanel.revalidate();
			authDataPanel.repaint();
		});
		
		this.add(typeLabel);
		this.add(typeComboBox);
		this.add(authDataPanel);
	}
	
	public AuthOption getAuthType() {
		return authType;
	}
	
	public String getTokenText() {
		return tokenTextField.getText();
	}
	
	public void setComponentEnable(boolean enabled) {
		this.typeComboBox.setEnabled(enabled);
		this.tokenTextField.setEnabled(enabled);
	}
	
	public void setRequestData(RequestData requestData) {
		setAuthType(requestData.getAuthType());
		setTokenTextField(requestData.getTokenText());
	}

	private void setAuthType(AuthOption authType) {
		typeComboBox.setSelectedItem(authType.toString());
	}

	private void setTokenTextField(String token) {
		this.tokenTextField.setText(token);
	}
}
