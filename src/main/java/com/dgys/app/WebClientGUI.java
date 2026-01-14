package com.dgys.app;

import javax.swing.JFrame;

import com.dgys.app.model.WebClient;
import com.dgys.app.ui.WebClientFrame;
import com.dgys.app.utilities.GUITools;

public class WebClientGUI {
	public static void main(String[] args) {
		WebClient webClient = new WebClient();
		WebClientFrame mainFrame = new WebClientFrame(webClient);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.pack();
		mainFrame.setVisible(true);
		GUITools.setFrameCenter(mainFrame);
	}
}
