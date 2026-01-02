package com.dgys.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.dgys.app.AuthPanel.AuthOption;

@SuppressWarnings("serial")
public class WebClientFrame extends JFrame implements ActionListener{
	private URLPanel urlPanel;
	private HeaderPanel headerPanel;
	private AuthPanel authPanel;
	private BodyPanel bodyPanel;
	private JTextArea resultTextArea;
	private WebClient webClient;
	
	public WebClientFrame(WebClient webClient) {
		super("WebClientGUI");
		setPreferredSize(new Dimension(600,500));
		
		urlPanel = new URLPanel(this);
		headerPanel = new HeaderPanel();
		authPanel = new AuthPanel();
		bodyPanel = new BodyPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Header", headerPanel);
		tabbedPane.addTab("Authrozation", authPanel);
		tabbedPane.addTab("Body", bodyPanel);
		
		resultTextArea = new JTextArea(10, 20);
		resultTextArea.setEditable(false);
		resultTextArea.setLineWrap(true);
		
		add(urlPanel,BorderLayout.NORTH);
		add(tabbedPane,BorderLayout.CENTER);
		add(new JScrollPane(resultTextArea),BorderLayout.SOUTH);
		
		this.webClient = webClient;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String result = "";

		try {
			String method = urlPanel.getHttpMethod();
			String url = urlPanel.getHttpURL();
			
			if(url.trim().equals(""))
				return;

			Map<String, String> headerParams = headerPanel.getHeaderParams();
			
			AuthOption authType = authPanel.getAuthType();
			
			String jsonBody = bodyPanel.getJsonBody();
			byte bodyOption = bodyPanel.getSelectedOption();
			
			if(method.equals("POST") && bodyOption == 2) {
				result = webClient.postWithUrlEncodedParam(url, headerParams, bodyPanel.getUrlEncodedParams());
			} else if(method.equals("POST") && bodyOption == 3) {
				result = webClient.postWithJSON(url, headerParams, jsonBody);
			} else if(method.equals("GET") && authType == AuthOption.BEARERTOKEN) {
				result = webClient.getWithBearerToken(url, headerParams, authPanel.getTokenText());
			}
		} catch (Exception excep) {
			result = excep.getMessage();
		}
		
		if(result == null)
			result = "";

		resultTextArea.setText(result);
	}
}
