package com.Itsu.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.nukkit.Player;

public class InformationWindow extends JFrame{
	
	private Graphics g;
	
	public InformationWindow(Player player){
		JLabel info = new JLabel("<html>"
				+ "名前: " + player.getName() + "<br>"
				+ "IPアドレス: " + player.getAddress() + "<br>"
				+ "ゲームモード: " + player.getGamemode() + "<br>"
				+ "OP: " + player.isOp() + "<br>"
				+ "ホワイトリスト: " + player.isWhitelisted() + "<br><br>"
				+ "X座標: " + player.getX() + "<br>"
				+ "Y座標: " + player.getY() + "<br>"
				+ "Z座標: " + player.getZ() + "<br>"
				+ "ワールド: " + player.getLevel().getName() + "<br>");
		
		JPanel panel = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.GRAY);
				g2.fillRect(0, 0, 10, 200);
			}
		};
		
		info.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 15));
		info.setHorizontalAlignment(JLabel.CENTER);
		
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout());
		panel.add(info, BorderLayout.CENTER);
		panel.setMaximumSize(new Dimension(300, 200));
		
		this.setTitle(player.getName() + "さんの情報");
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.setBackground(Color.WHITE);
		this.setBounds(0, 0, 300, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

}
