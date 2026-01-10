package com.dgys.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import com.dgys.app.AuthPanel.AuthOption;

@SuppressWarnings("serial")
public class WebClientFrame extends JFrame implements ActionListener, MouseListener {
	private URLPanel urlPanel;
	private HeaderPanel headerPanel;
	private AuthPanel authPanel;
	private BodyPanel bodyPanel;
	private JTextArea resultTextArea;
	private WebClient webClient;
	private RequestData requestData;
	private RequestsPanel requestsPanel;
	
	public WebClientFrame(WebClient webClient) {
		super("WebClientGUI");
		setPreferredSize(new Dimension(600,500));
		
		urlPanel = new URLPanel(this);
		headerPanel = new HeaderPanel();
		authPanel = new AuthPanel();
		bodyPanel = new BodyPanel();
		requestsPanel = new RequestsPanel(webClient.getRequestDataList(), this);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Header", headerPanel);
		tabbedPane.addTab("Authrozation", authPanel);
		tabbedPane.addTab("Body", bodyPanel);
		
		resultTextArea = new JTextArea(10, 20);
		resultTextArea.setEditable(false);
		resultTextArea.setLineWrap(true);
		
		add(requestsPanel, BorderLayout.WEST);
		add(urlPanel,BorderLayout.NORTH);
		add(tabbedPane,BorderLayout.CENTER);
		add(new JScrollPane(resultTextArea),BorderLayout.SOUTH);
		
		this.webClient = webClient;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if (actionCommand.equals("Send"))
			sendEventHandler();
		else
			saveEventHandler();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JList<RequestData> requestsList = (JList<RequestData>) e.getComponent();
			
			if(requestsList.getSelectedValue() != null) {
				requestData = requestsList.getSelectedValue();
			}
		}
	}
	
	private void sendEventHandler() {
		String result = "";

		try {
			getRequestData();
			
			if(requestData == null)
				return;

			if(requestData.getMethod().equals("POST") && requestData.getBodyOption() == 2) {
				result = webClient.postWithUrlEncodedParam(requestData);
			} else if(requestData.getMethod().equals("POST") && requestData.getBodyOption() == 3) {
				result = webClient.postWithJSON(requestData);
			} else if(requestData.getMethod().equals("GET") && requestData.getAuthType() == AuthOption.BEARERTOKEN) {
				result = webClient.getWithBearerToken(requestData);
			}
		} catch (Exception excep) {
			result = excep.getMessage();
		}
		
		if(result == null)
			result = "";

		resultTextArea.setText(result);
	}
	
	private void saveEventHandler() {
		String result = "";

		try {
			getRequestData();
			
			if(requestData == null)
				return;
			
			String name = "";
			if (name.trim().equals(""))
				name = JOptionPane.showInputDialog("Input a name for this request");
			
			if (name.trim().equals(""))
				return;
			
			requestData.setName(name);
			
			webClient.saveRequestData(requestData);
		} catch (Exception excep) {
			result = excep.getMessage();
		}
		
		if(result == null)
			result = "";

		resultTextArea.setText(result);
	}
	
	private RequestData getRequestData() {	
		String method = urlPanel.getHttpMethod();
		String url = urlPanel.getHttpURL();
		
		if(url.trim().equals(""))
			return null;
		
		if (requestData == null)
			requestData = new RequestData();
		
		requestData.setUrl(url);
		requestData.setMethod(method);

		Map<String, String> headerParams = headerPanel.getHeaderParams();
		requestData.setHeaderParams(headerParams);
		
		AuthOption authType = authPanel.getAuthType();
		requestData.setAuthType(authType);
		requestData.setTokenText(authPanel.getTokenText());
		requestData.setUrlEncodedParams(bodyPanel.getUrlEncodedParams());
		
		String jsonBody = bodyPanel.getJsonBody();
		byte bodyOption = bodyPanel.getSelectedOption();
		requestData.setBodyOption(bodyOption);
		requestData.setJsonBody(jsonBody);
		
		return requestData;
	}
	
	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
