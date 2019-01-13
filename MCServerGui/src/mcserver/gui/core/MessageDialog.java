package mcserver.gui.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import mcserver.gui.mcservergui.BlackMessageBox;

public class MessageDialog {

	public MessageDialog()
	{
	}

	public static void alertDialog(){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 400, 100);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel("プログラムに重大なエラーが発生しました。");
		label.setSize(400, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);
		return;
	}

	public static void alreadyRunDialog(){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 400, 100);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel("すでにサーバーは開いています。");
		label.setSize(300, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
	    label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
		label.setForeground(Color.WHITE);

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);
		return;
	}

	public static void NoInternetDialog(){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 400, 100);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel("インターネット接続がありません。");
		label.setSize(300, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);

		return;
	}

	public static void firstStart(){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 400, 200);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel("<html>このたびはMCServerGUIを導入していただき、ありがとうございます。");
		label.setSize(300, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);
		return;
	}

	public static void freeDialog(String message){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 400, 100);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel(message);
		label.setSize(300, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);
		return;
	}
	
	public static void Licence(){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 800, 500);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel("<html>MCServerGUI 4.0  ライセンス/使用承諾書<br><br>"
				+ "1.使用について<br>"
				+ "(1)本ソフトウェアはフリーソフトです。<br>"
				+ "(2)本ソフトウェア使用による損害については開発者(Itsu)は責任を負いません。<br>"
				+ "(3)使用方法についてはクレジット/ライセンスタブの使用方法ボタンからご確認ください。<br><br>"
				+ "2.管理について<br>"
				+ "(1)いかなる理由があっても二次配布と解凍は禁止です。<br>"
				+ "(2)自サイト等での紹介は可能とします。<br>"
				+ "(3)商用利用は固くお断りします。<br><br>"
				+ "3.ライセンス<br>"
				+ "開発: Itsu<br>"
				+ "技術提供: Ogiwara<br>"
				+ "フォント: 超極細ゴシック (フリーフォント/二次配布可)<br>"
				+ "HP: http://itsuplugin.web.fc2.com/soft.html<br>"
				+ "Blog: http://itsumemo.blog.fc2.com<br>"
				+ "Twitter: @itsu_dev<br>"
				+ "(c)2015-2017 Itsu All rights Reserved<br>");
		
		label.setSize(800, 500);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);
	}
	
	public static void howtoDialog(){
		BlackMessageBox bx = new BlackMessageBox();
		bx.setFrameBounds(0, 0, 800, 500);
		bx.setButtonAction(BlackMessageBox.HIDE_WINDOW);
		bx.setAlwaysOnTop(true);
		bx.setLocationRelativeTo(null);
		bx.setResizable(false);
		bx.setLayout(new BorderLayout());

		JLabel label = new JLabel("<html>"
				+ "MCServerGUI 4.0  使用方法<br><br>"
				+ "・起動<br>"
				+ "メインコンソールタブから開始ボタンを押します。<br><br>"
				+ "・終了<br>"
				+ "メインコンソールタブの終了ボタンを押すかコマンドボックスにstopと入力して送ります。<br><br>"
				+ "・壁紙の変更<br>"
				+ "設定タブのボックスに壁紙として使う画像のパスを入力します。<br><br>"
				+ "・コマンドの送信<br>"
				+ "メインコンソールタブのコマンドボックスにコマンドを入力し送るボタンを押します。<br><br>"
				+ "・サブコンソール<br>"
				+ "ここには状況が表示されます。<br><br>"
				+ "・起動するスクリプトの名前を変える<br>"
				+ "MCServerGUI/startFile.txtの中身を書き換えます。<br><br>"
				+ "・プラグインの導入<br>"
				+ "MCServerGUI/plugins/の中にJarプラグインを入れます。<br><br><br>"
				+ "その他はTwitterまで");
		label.setSize(300, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(70, 15));
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bx.setFrameVisible(false);
			}
		});

		bx.getContentPane().add(label, BorderLayout.CENTER);
		bx.getContentPane().add(ok, BorderLayout.SOUTH);

		bx.setFrameVisible(true);
		return;
	}


}
