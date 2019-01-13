package com.Itsu.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ControlPanel extends JPanel{
	
	String[] wData = {"晴れ", "雨", "雷", "雪"};
	JComboBox<?> weather;
	JLabel label;
	JPanel combo;
	
	public ControlPanel(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		weather = new JComboBox<>(wData);
		label = new JLabel("天気", JLabel.LEFT);
		combo = new JPanel();
		
		this.setBackground(Color.GRAY);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(250, 480));
		
		label.setFont(new Font("メイリオ", Font.PLAIN, 15));
		label.setForeground(Color.WHITE);
		label.setAlignmentX(0.0f);
		
		weather.setFont(new Font("メイリオ", Font.PLAIN, 12));
		weather.setBackground(Color.WHITE);
		
		combo.setMaximumSize(new Dimension(150, 30));
		combo.setLayout(new BorderLayout());
		combo.setAlignmentX(0.5f);
		combo.add(weather, BorderLayout.CENTER);
		
		this.add(label);
		this.add(Box.createRigidArea(new Dimension(10,10)));
		this.add(combo);
		this.add(Box.createRigidArea(new Dimension(30,30)));
	}
}
