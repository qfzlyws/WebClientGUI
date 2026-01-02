package com.dgys.app;

import javax.swing.JFrame;

public class WebClientGUI {
	public static void main(String[] args) {
		WebClient webClient = new WebClient();
		WebClientFrame mainFrame = new WebClientFrame(webClient);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
