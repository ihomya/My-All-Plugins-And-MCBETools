package com.Itsu;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.Itsu.GUI.MainPanelList;

public class GUIManagerWindow extends JFrame{
	
	MainPanelList list = new MainPanelList();
	
	public GUIManagerWindow(){
		this.setSize(800, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setTitle("NewGUIManager");
		this.getContentPane().add(list, BorderLayout.CENTER);
	}

}
