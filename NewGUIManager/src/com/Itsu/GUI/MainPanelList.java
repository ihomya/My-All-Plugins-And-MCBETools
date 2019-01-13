package com.Itsu.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.Itsu.NewGUIManager;

import cn.nukkit.Player;

public class MainPanelList extends JScrollPane{
	
	JPanel base = new JPanel();
	JPopupMenu popup = new JPopupMenu();
	JMenuItem ban = new JMenuItem("BANする");
	JMenuItem ipban = new JMenuItem("IP-BANする");
	JMenuItem kick = new JMenuItem("kickする");
	JMenuItem white = new JMenuItem("ホワイトリストに追加する");
	JMenuItem info = new JMenuItem("プレイヤー情報...");
	JTextField text = new JTextField();
	JLabel status = new JLabel();
	String name1;
	int y = 50;
	
	public MainPanelList(){
		base.setBackground(new Color(240, 240, 230));
		base.setPreferredSize(new Dimension(750, 480));
		base.setLayout(null);
		
		status.setBackground(Color.LIGHT_GRAY);
		status.setFont(new Font("メイリオ", Font.PLAIN, 12));
		
		text.setText("Enterで送信");
		text.setForeground(Color.BLACK);
		text.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					try{
						NewGUIManager.getRawServer().getPlayer(name1).sendMessage("鯖主からの個人メッセージ: " + text.getText());
						text.setText("");
					}catch(NullPointerException ex){
						text.setText("エラー:送れませんでした");
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		
		ban.setFont(new Font("メイリオ", Font.PLAIN, 12));
		ban.setBackground(Color.WHITE);
		ban.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGUIManager.getRawServer().getNameBans().addBan(name1);
			}
			
		});
		ipban.setFont(new Font("メイリオ", Font.PLAIN, 12));
		ipban.setBackground(Color.WHITE);
		ipban.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGUIManager.getRawServer().getIPBans().addBan(name1);
			}
			
		});
		kick.setFont(new Font("メイリオ", Font.PLAIN, 12));
		kick.setBackground(Color.WHITE);
		kick.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGUIManager.getRawServer().getPlayer(name1).kick();
			}
			
		});
		white.setFont(new Font("メイリオ", Font.PLAIN, 12));
		white.setBackground(Color.WHITE);
		white.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NewGUIManager.getRawServer().addWhitelist(name1);
			}
			
		});
		info.setFont(new Font("メイリオ", Font.PLAIN, 12));
		info.setBackground(Color.WHITE);
		info.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Player player = NewGUIManager.getRawServer().getPlayer(name1);
				new InformationWindow(player);
			}
			
		});
		
		popup.add(status);
		popup.addSeparator();
		popup.add(ban);
		popup.add(ipban);
		popup.add(kick);
		popup.add(white);
		popup.add(info);
		popup.addSeparator();
		popup.add(text);
		
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.getVerticalScrollBar().setUI(new BlackScrollBarUI());
		this.getVerticalScrollBar().setUnitIncrement(15);
		this.getViewport().add(base);
	}
	
	public void add(BufferedImage img, String name, String ip, String reason, int act) throws IOException, URISyntaxException{
		JPanel cell = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.GRAY);
				g2.fillRect(0, 0, 10, 80);
			}
		};
		
		cell.setBounds(50, y, 650, 80);
		cell.setLayout(null);
		cell.setBackground(Color.WHITE);
		cell.setName(act + name);
		cell.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3 && e.getComponent().getName().startsWith("1")){
					
					name1 = e.getComponent().getName().replaceFirst("1", "");
					
					ban.setEnabled(true);
					ipban.setEnabled(true);
					kick.setEnabled(true);
					white.setEnabled(true);
					text.setEnabled(true);
					info.setEnabled(true);
					
					status.setText("オンライン");
					status.setForeground(Color.GREEN);
					ban.setText("\"" + name1 + "\"をBANする");
					ipban.setText("\"" + name1 + "\"をIP-BANする");
					kick.setText("\"" + name1 + "\"をkickする");
					white.setText("\"" + name1 + "\"をホワイトリストに追加する");
					
					try{
						NewGUIManager.getRawServer().getPlayer(name1).isOnline();
					}catch(NullPointerException ex){
						status.setText("オフライン");
						status.setForeground(Color.RED);
						ban.setEnabled(false);
						ipban.setEnabled(false);
						kick.setEnabled(false);
						white.setEnabled(false);
						text.setEnabled(false);
						info.setEnabled(false);
					}
					
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
				
				if(e.getButton() == MouseEvent.BUTTON3 && e.getComponent().getName().startsWith("2")){

					name1 = e.getComponent().getName().replaceFirst("2", "");
					
					ban.setEnabled(false);
					ipban.setEnabled(false);
					kick.setEnabled(false);
					white.setEnabled(false);
					text.setEnabled(false);
					info.setEnabled(false);
					
					status.setText("オフライン");
					status.setForeground(Color.RED);
					ban.setText("\"" + name1 + "\"をBANする");
					ipban.setText("\"" + name1 + "\"をIP-BANする");
					kick.setText("\"" + name1 + "\"をkickする");
					white.setText("\"" + name1 + "\"をホワイトリストに追加する");
					
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().setBackground(new Color(220, 220, 220));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.getComponent().setBackground(Color.WHITE);
			}
		});
		
		JLabel skin = new JLabel(new ImageIcon(img));
		JLabel n = new JLabel(name);
		JLabel i = new JLabel(ip);
		JLabel a = new JLabel();
		
		String action;
		if(act == 1){
			action = "参加";
			a.setText(action);
			a.setForeground(Color.GREEN);
		}else{
			action = "退出";
			a.setText(action + "(" + reason + ")");
			a.setForeground(Color.RED);
		}
		
		skin.setBounds(20, 8, 64, 64);
		cell.add(skin);
		
		n.setFont(new Font("Arial", Font.PLAIN, 25));
		n.setBounds(92, 8, 400, 30);
		cell.add(n);
		
		i.setFont(new Font("Arial", Font.PLAIN, 15));
		i.setBounds(92, 52, 400, 20);
		cell.add(i);
		
		a.setFont(new Font("ＭＳ Ｐゴシック", Font.PLAIN, 15));
		a.setBounds(500, 8, 150, 64);
		a.setBackground(new Color(230, 230, 230));
		cell.add(a);
		
		base.add(cell);
		
		y = y + 110;
		
		base.setPreferredSize(new Dimension(750, 500 + y));
		
		base.repaint();
		
		return;
	}

}
