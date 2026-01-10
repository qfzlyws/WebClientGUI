package com.dgys.app;

import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class RequestsPanel extends JPanel {
	public RequestsPanel(List<RequestData> requestData, MouseListener listener) {
		DefaultListModel<RequestData> listModel = new DefaultListModel<>();
		requestData.forEach(listModel::addElement);
		
		JList<RequestData> requestList = new JList<>(listModel);
		requestList.addMouseListener(listener);
		this.add(new JScrollPane(requestList));
	}
}
