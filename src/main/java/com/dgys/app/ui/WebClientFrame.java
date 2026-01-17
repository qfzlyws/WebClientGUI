package com.dgys.app.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.dgys.app.model.RequestData;
import com.dgys.app.model.WebClient;
import com.dgys.app.ui.AuthPanel.AuthOption;

@SuppressWarnings("serial")
public class WebClientFrame extends JFrame implements ActionListener, MouseListener {
	private URLPanel urlPanel;
	private HeaderPanel headerPanel;
	private AuthPanel authPanel;
	private BodyPanel bodyPanel;
	private JTextArea resultTextArea;
	private WebClient webClient;
	private RequestData requestData;
	private RequestListPanel requestListPanel;
	
	public WebClientFrame(WebClient webClient) {
		super("WebClientGUI");
		setPreferredSize(new Dimension(850,500));
		
		urlPanel = new URLPanel(this);
		headerPanel = new HeaderPanel();
		authPanel = new AuthPanel();
		bodyPanel = new BodyPanel();
		requestListPanel = new RequestListPanel(webClient.getRequestDataList(), this, this);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Header", headerPanel);
		tabbedPane.addTab("Authrozation", authPanel);
		tabbedPane.addTab("Body", bodyPanel);
		
		resultTextArea = new JTextArea(10, 20);
		resultTextArea.setEditable(false);
		resultTextArea.setLineWrap(true);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(urlPanel,BorderLayout.NORTH);
		centerPanel.add(tabbedPane,BorderLayout.CENTER);
		centerPanel.add(new JScrollPane(resultTextArea),BorderLayout.SOUTH);
		
		this.add(requestListPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		
		this.webClient = webClient;
		
		this.setComponentEnable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if (actionCommand.equals("Send"))
			sendEventHandler();
		else if (actionCommand.equals("Save"))
			saveEventHandler();
		else if (actionCommand.equals("新增請求"))
			newRequestHandler();
		else
			deleteRequestHandler();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JTable requestsList = (JTable) e.getComponent();
			
			int clickedRow = requestsList.rowAtPoint(e.getPoint()); 
			
			if(clickedRow != -1) {
				requestData = (RequestData) requestsList.getValueAt(clickedRow, 0);
				setRequestData(requestData);
				this.setComponentEnable(true);
			}
		}
	}
	
	private void sendEventHandler() {
		String result = "";

		try {
			getRequestData();
			
			if(requestData == null)
				return;

			result = webClient.sendRequest(requestData);
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
			
			if (requestData.getName().trim().equals("")) {
				String name = JOptionPane.showInputDialog("Input a name for this request");
				if (name.trim().equals(""))
					return;
				requestData.setName(name);
			}
			
			webClient.saveRequestData(requestData);
			requestListPanel.fireTableDataChanged();
		} catch (Exception excep) {
			result = excep.getMessage();
		}
		
		if(result == null)
			result = "";

		resultTextArea.setText(result);
	}
	
	private void newRequestHandler() {
		requestData = webClient.newRequestData();
		requestListPanel.newRequest(requestData);
		setRequestData(requestData);
		this.setComponentEnable(true);
	}
	
	private void deleteRequestHandler() {
		RequestData deleteRequestData = requestListPanel.getCurrentSelectedData();
		if (deleteRequestData == null)
			return;
		
		try {
			webClient.deleteRequestData(deleteRequestData);
			requestListPanel.deleteCurrentSelectedRow();
			this.setComponentEnable(false);
		} catch (Exception e) {
			resultTextArea.setText(e.getMessage());
		}
	}
	
	private RequestData getRequestData() {	
		String method = urlPanel.getHttpMethod();
		String url = urlPanel.getHttpURL();
		
		if(url.trim().equals(""))
			return null;
		
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
	
	private void setRequestData(RequestData requestData) {
		this.requestData = requestData;
		
		urlPanel.setRequestData(requestData);
		headerPanel.setRequestData(requestData);
		authPanel.setRequestData(requestData);
		bodyPanel.setRequestData(requestData);
		resultTextArea.setText("");
	}
	
	private void setComponentEnable(boolean enabled) {
		urlPanel.setComponentEnable(enabled);
		headerPanel.setComponentEnable(enabled);
		authPanel.setComponentEnable(enabled);
		bodyPanel.setComponentEnable(enabled);
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
