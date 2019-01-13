package mcserver.gui.mcservergui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultStyledDocument;

import mcserver.gui.console.MainConsole;
import mcserver.gui.console.SubConsole;
import mcserver.gui.core.Excute;
import mcserver.gui.core.FileChecker;
import mcserver.gui.core.MatiPanel;
import mcserver.gui.core.MessageDialog;
import mcserver.gui.core.Setting;
import mcserver.gui.core.SimpleTabbedPaneUI;
import mcserver.gui.exception.FileException;
import mcserver.gui.plugin.PluginLoader;
import mcserver.gui.plugin.Utils;
import mcserver.gui.plugin.event.CloseServerEvent;
import mcserver.gui.plugin.event.EventExecutor;
import mcserver.gui.plugin.event.StartServerEvent;


public class Main extends JFrame{

  	private OutputStream out = null;

  	private static Process process = null;

  	private Thread stdRun  = null;

  	private Thread errRun  = null;

  	private static Thread Time  = null;

  	static DefaultStyledDocument document = new DefaultStyledDocument();
    static JTextPane area2 = new JTextPane(document);
    private JScrollPane scrollpane1 = new JScrollPane(area2);
    static JTextArea area3 = new JTextArea();
    private JScrollPane scrollpane2 = new JScrollPane(area3);
    static JTextArea area4 = new JTextArea();
    private JScrollPane scrollpane3 = new JScrollPane(area4);
    static JTextArea area5 = new JTextArea();
    private JScrollPane scrollpane5 = new JScrollPane(area5);
    private JLabel label1 = new JLabel("");
    private JLabel label2 = new JLabel("サブコンソール");
    private JTextField text = new JTextField();
    private JTextField backg = new JTextField();
    private JButton b = new JButton("送る");
    private JButton b1 = new JButton("終了");
    private JButton b2 = new JButton("開始");
    private JButton b3 = new JButton("更新");
    private JButton b4 = new JButton("保存");
    private JButton li = new JButton("ライセンス/使用承諾書");
    private JButton howto = new JButton("使用方法");
    static JTabbedPane tabbedpane = new JTabbedPane();
    private String com;
    private String nn;
    private String name1;
    private String bun;
    private String port1;
    private String ip1;
    private String max1;
    private String wl1;
    private String gm1;

    private JCheckBox cb1 = new JCheckBox("20分おきに再読み込み");
    private BlackMessageBox bmb = new BlackMessageBox();

    private boolean net = true;

    private JLabel name = new JLabel("");
    private JLabel port = new JLabel("");
    private JLabel ip = new JLabel("");
    private JLabel max = new JLabel("");
    private JLabel wl = new JLabel("");
    private JLabel gm = new JLabel("");
    private JLabel plugin = new JLabel("プラグイン");
    private JLabel op = new JLabel("OP");
    private JLabel title = new JLabel("クレジット");
    private JLabel autor = new JLabel("開発:Itsu");
    private JLabel eclipse = new JLabel("開発環境:Windows10 Home, Eclipse 4.6 Neon, JavaSE-1.8(JDK8)");
    private JLabel subautor = new JLabel("開発補助/技術提供:Ogiwara");
    private JLabel license = new JLabel("ライセンス");
    private JLabel len1 = new JLabel("詳しくは右のボタンからご確認ください。");
    private JLabel len2 = new JLabel("MCServerGUI 開発:Itsu/技術提供:Ogiwara");
    private JLabel len3 = new JLabel("HP:http://itsuplugin.web.fc2.com/soft.html");
    private JLabel len4 = new JLabel("(c)2015-2017 Itsu All rights Reserved");
    private JLabel how = new JLabel("仕様");
    private JLabel window = new JLabel("ウィンドウサイズ:1000x550");
    private JLabel nukkit = new JLabel("起動可能bat名:/MCServerGUI/start.txtにより定義");
    private JLabel option = new JLabel("設定");
    private JLabel back = new JLabel("壁紙:(相対/絶対)");
    private JLabel j = new JLabel("設定を反映するには再起動が必要です。");

    private String pas;

    static MainConsole m = new MainConsole();
    static SubConsole sc = new SubConsole();
    private Excute exc = new Excute();
    private FileChecker fc = new FileChecker();
    private static Setting sett = new Setting();

    private int day = 0,hou = 0,min = 0,sec = 0;
    private int setter = 1;
    private int set = 0;
    private int tt = 0;

    private JLabel timer = new JLabel("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");

    static PluginLoader loader;

    private EventExecutor executor = new EventExecutor();



  public Main() {
  }

  private void execCmd() throws IOException, InterruptedException{

	pas = fc.checkFile();

	String[] cmd = {"cmd","/c" + pas};
               final long start = System.nanoTime();

	process = Runtime.getRuntime().exec(cmd);
	
	System.out.println(pas + "を起動しました。");

	Runnable inputStreamThread = new Runnable(){
	  public void run(){
		  m.sendMainConsole(process, document);
	  }
	};

	Runnable errStreamThread = new Runnable(){
	  public void run(){
	  	m.sendError(process, document);
	  }
	};

	Runnable time = new Runnable(){
		  @SuppressWarnings("static-access")
		public void run(){
			  while(true){
				  if(set == 2){
					  break;
				  }
		  	sec++;
		  	timer.setText("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");
		  	if(sec == 60){
		  		sec = 0;
		  		min++;
		  		timer.setText("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");
		  		sc.sendSubConsole(area4, start);
		  	}
		  	if(min == 60){
		  		min = 0;
		  		hou++;
		  		timer.setText("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");
		  	}
		  	if(min == 20 || min == 40 || min == 60 && sett.getAutoReloadCheck() == 1){
		  		exc.excuteCommand(process, "reload", text);
		  	}
		  	if(hou == 24){
		  		hou = 0;
		  		day++;
		  		timer.setText("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");
		  	}
		  	try {
				Time.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  	if(setter == 0){
		  		break;
		  	}
			  }
		  }
		};

	stdRun = new Thread(inputStreamThread);
	System.out.println("メインストリームのスレッドが開始しました。");
	errRun = new Thread(errStreamThread);
	System.out.println("エラーストリームのスレッドが開始しました。");
	Time = new Thread(time);

	stdRun.start();
	errRun.start();
	Time.start();
  }


  public void setStatus(String w){
	  if(w.indexOf("motd=") != -1){
  		name1 = w.replaceAll("motd=","");
  		name.setText(name1);
  		System.out.println("サーバー名の読み込みが完了しました。: " + name1);
  	}
  	if(w.indexOf("server-port=") != -1){
  		port1 = w.replaceAll("server-port=","");
  		port.setText("ポート:" + port1);
  		System.out.println("ポートの読み込みが完了しました。: " + port1);
  	}
  	if(w.indexOf("server-ip=") != -1){
  		ip1 = w.replaceAll("server-ip=","");
  		ip.setText("IP:" + ip1);
  		System.out.println("IPの読み込みが完了しました。: " + ip1);
  	}
  	if(w.indexOf("max-players=") != -1){
  		max1 = w.replaceAll("max-players=","");
  		max.setText("最大人数:" + max1);
  		System.out.println("最大人数の読み込みが完了しました。: " + max1);
  	}
  	if(w.indexOf("white-list=") != -1){
  		wl1 = w.replaceAll("white-list=","");
  		if(wl1.equals("0")){
  			wl.setText("ホワイトリスト:無効");
  			System.out.println("ホワイトリストの読み込みが完了しました。: 無効");
  		}
  		if(wl1.equals("1")){
			wl.setText("ホワイトリスト:有効");
			System.out.println("ホワイトリストの読み込みが完了しました。: 有効");
		}
  	}
  	if(w.indexOf("gamemode=") != -1){
  		gm1 = w.replaceAll("gamemode=","");
  		if(gm1.equals("2")){
  			gm.setText("ゲームモード:アドベンチャー");
  			System.out.println("ゲームモードの読み込みが完了しました。: アドベンチャー");
  		}
  		if(gm1.equals("1")){
  			gm.setText("ゲームモード:クリエイティブ");
  			System.out.println("ゲームモードの読み込みが完了しました。: クリエイティブ");
  		}
  		if(gm1.equals("0")){
  			gm.setText("ゲームモード:サバイバル");
  			System.out.println("ゲームモードの読み込みが完了しました。: サバイバル");
  		}
  		if(gm1.equals("3")){
  			gm.setText("ゲームモード:スペクテイター");
  			System.out.println("ゲームモードの読み込みが完了しました。: スペクテイター");
  		}
  	}
  }

  public void gui(){

	    bmb.setTitle("MCServerGUI");
	    bmb.setFrameBounds(0,0,1000, 550);
	    bmb.setVisible(true);
	    bmb.setResizable(false);
	    bmb.setButtonAction(BlackMessageBox.SYSTEM_EXIT);
	    bmb.setLocationRelativeTo(null);

	    JPanel p = new MatiPanel();
	    JPanel p1 = new MatiPanel();
	    JPanel p2 = new MatiPanel();
	    JPanel p3 = new MatiPanel();
	    JPanel p4 = new MatiPanel();

	    area2.setEditable(false);
	    area3.setLineWrap(true);
	    area2.setBackground(Color.BLACK);
	    area3.setEditable(false);
	    area4.setLineWrap(true);
	    area4.setEditable(false);
	    area5.setLineWrap(true);
	    area5.setEditable(false);

	    try {
	    	URL url = new URL("http://google.com");
	    	URLConnection con = url.openConnection();
	    	con.getInputStream();
	    	} catch (IOException e) {
	    	net = false;
	    	}
	    if(net == false){
	    	MessageDialog.NoInternetDialog();
	    	sc.NotConnectionConsole(area4);
	    }


	//--------------------------------ファイル処理
	    File cdirectory = new File("./plugins/");
	    if(!(cdirectory.exists())){
	    	MessageDialog.freeDialog("pluginsディレクトリが見つかりません。");
	    	throw new FileException("pluginsディレクトリが見つかりません。");
	    }
	    String filelist[] = cdirectory.list();
	    for (int i = 0 ; i<filelist.length; i++){
	    	if(filelist[i].endsWith(".jar")){
	    		area3.append(filelist[i].replaceAll(".jar", "") + "\n");
	    	}
	    }
	//-----------------------------------------

	    scrollpane1.setBounds(0,20,995,280);
	    scrollpane1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    scrollpane2.setBounds(460,190,265,300);
	    scrollpane2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    scrollpane3.setBounds(0,20,995,280);
	    scrollpane3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    scrollpane5.setBounds(725,190,265,300);
	    scrollpane5.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

	    text.setBounds(30,400,200,20);
	    label1.setBounds(5,305,1000,15);
	    label1.setText("メインコンソール ");
	    label1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    label2.setBounds(5,305,1000,15);;
	    label2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    b.setBounds(250,400,70,20);
	    b.addActionListener(
	    new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	    	Excute.excuteCommand(process, text.getText(), text);
	        System.out.println("コマンドを送信しました。: " + text.getText());
	    	if(text.getText().equals("stop")){
	    		setter = 0;
	    		set = 2;
		        sec = 0;
		        min = 0;
		        hou = 0;
		        day = 0;
		        timer.setText("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");
		        System.out.println("サーバーを終了しました。");
	    	}
	    }
	    }
	    );
	    b1.setBounds(400,400,70,20);
	    b1.addActionListener(
	    new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	        out = process.getOutputStream();
	        PrintWriter pw1 = new PrintWriter(out);
	        pw1.println("stop");
	        pw1.flush();
	        pw1.close();
	        setter = 0;
	        set = 2;
	        sec = 0;
	        min = 0;
	        hou = 0;
	        day = 0;
	        timer.setText("起動から" + day + "日" + hou + "時間" + min + "分" + sec + "秒");
	        
	        try {
				out.close();
				System.out.println("サーバーを終了しました。");
				executeCloseServerEvent();
				System.out.println("CloseServerEventが発生しました。(onCloseServer()を呼び出しました。)");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    }
	    }
	    );

	    b2.setBounds(500,400,70,20);
	    b2.addActionListener(
	    new ActionListener(){
	    public void actionPerformed(ActionEvent e){
	    	try {
	    		if(set == 1){
	    			  MessageDialog.alreadyRunDialog();
	    			  return;
	    		}
	    		setter = 1;
	    		set = 1;
	    		executeStartServerEvent();
	    		execCmd();	//コマンド実行
	    		System.out.println("サーバーを開始しました。");
	    		System.out.println("StartServerEventが発生しました。(onStartServer()を呼び出しました。)");
	    	} catch (Exception e3) {
	    		e3.printStackTrace();
	    	}
	    }
	    }
	    );

	    b3.setBounds(850,0,100,20);
	    b3.addActionListener(
	    new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		    try {
				 FileReader fr12 = new FileReader("server.properties");
			     BufferedReader br12 = new BufferedReader(fr12);

			     while((bun = br12.readLine()) != null){
				    setStatus(bun);
				 }

			   	try {
			   		 area5.setText("");
			   		 FileReader fr121 = new FileReader("ops.txt");
			   	     BufferedReader br121 = new BufferedReader(fr121);


			   	     String ops = "";
			   	     while((bun = br121.readLine()) != null){
			   		    area5.append(bun + "\n");
			   		    ops = ops + bun + "  ";
			   		 }
			   	     
			   	     System.out.println("ops.txtの読み込みが完了しました。: " + ops);

			   	     fr121.close();
			   	     br121.close();
			   		}catch(IOException ex){
			   			MessageDialog.freeDialog("ops.txtが見つかりません。");
			   			throw new FileException("ops.txtが見つかりません。");
			   		}
			     
			     System.out.println("server.propertiesを読みこみました。");

			     fr12.close();
			     br12.close();
				}catch(IOException ex){
					MessageDialog.freeDialog("server.propertiesが見つかりません。");
					throw new FileException("server.propertiesが見つかりません。");
				}
	    }
	    }
	    );

	    plugin.setBounds(460,175,100,20);
	    plugin.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    op.setBounds(725,175,100,20);
	    op.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    name.setBounds(0,5,500,40);
	    name.setFont(Utils.getFont());
	    port.setBounds(30,60,500,20);
	    port.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    ip.setBounds(30,85,500,20);
	    ip.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    max.setBounds(30,110,500,20);
	    max.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    wl.setBounds(30,135,500,20);
	    wl.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    gm.setBounds(30,160,500,20);
	    gm.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));

	    title.setBounds(0,5,1000,20);
	    title.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    autor.setBounds(30,30,1000,20);
	    autor.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    subautor.setBounds(30,50,1000,20);
	    subautor.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    eclipse.setBounds(30,70,1000,20);
	    eclipse.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    license.setBounds(0,95,500,20);
	    license.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    len1.setBounds(30,115,1000,20);
	    len1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    len2.setBounds(30,135,1000,20);
	    len2.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    len3.setBounds(30,155,1000,20);
	    len3.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    len4.setBounds(30,175,1000,20);
	    len4.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    how.setBounds(0,195,1000,20);
	    how.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    window.setBounds(30,220,1000,20);
	    window.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));
	    nukkit.setBounds(30,240,1000,20);
	    nukkit.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

	    li.setBounds(500,97,300,16);
	    li.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
	    li.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog.Licence();
				System.out.println("ライセンスウィンドウを表示しました。");
			}
		});

	    howto.setBounds(850,97,100,16);
	    howto.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
	    howto.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MessageDialog.howtoDialog();
				System.out.println("使用方法ウィンドウを表示しました。");
			}
		});

	    timer.setBounds(0,330,1000,20);
	    timer.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 15));

	    option.setBounds(0,5,200,40);
	    option.setFont(Utils.getFont());
	    back.setBounds(30,60,200,20);
	    back.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    backg.setBounds(230,60,200,20);
	    backg.setText(sett.getBackGround());
	    j.setBounds(50,450,700,20);
	    j.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    cb1.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 20));
	    cb1.setBounds(30, 85, 350, 20);
	    cb1.setOpaque(false);
	    if(sett.getAutoReloadCheck() == 0){
	    	cb1.setSelected(false);
	    }else{
	    	cb1.setSelected(true);
	    }

	    b4.setBounds(850, 450, 100, 20);

	    b4.addActionListener(
	    	    new ActionListener(){
	    		    public void actionPerformed(ActionEvent e){
	    			    sett.setBackGround(backg.getText());
	    			    System.out.println("壁紙を設定しました。: " + backg.getText());
	    			    if(cb1.isSelected() == false){
	    			    	sett.setAutoReloadCheck(0);
	    			    	System.out.println("20分おきに再起動を無効にしました。");
	    			    }else{
	    			    	sett.setAutoReloadCheck(1);
	    			    	System.out.println("20分おきに再起動を有効にしました。");
	    			    }
	    		    }
	    		    }
	    		    );


	    p.setLayout(null);

	    p.setBackground(Color.WHITE);

	    p.setBounds(0,0,1000,550);

	    p1.setLayout(null);

	    p1.setBackground(Color.WHITE);

	    p1.setBounds(0,0,1000,550);

	    p2.setLayout(null);

	    p2.setBackground(Color.WHITE);

	    p2.setBounds(0,0,1000,550);

	    p3.setLayout(null);

	    p3.setBackground(Color.WHITE);

	    p3.setBounds(0,0,1000,550);

	    p4.setLayout(null);

	    p4.setBackground(Color.WHITE);

	    p4.setBounds(0,0,1000,550);


	    p.add(scrollpane1);
	    p.add(label1);
	    p.add(text);
	    p.add(b);
	    p.add(b1);
	    p.add(b2);
	    p.add(timer);

	    p1.add(scrollpane3);
	    p1.add(label2);

	    p2.add(scrollpane2);
	    p2.add(scrollpane5);
	    p2.add(plugin);
	    p2.add(op);
	    p2.add(name);
	    p2.add(port);
	    p2.add(ip);
	    p2.add(max);
	    p2.add(wl);
	    p2.add(gm);
	    p2.add(b3);

	    p3.add(option);
	    p3.add(back);
	    p3.add(backg);
	    p3.add(b4);
	    p3.add(cb1);
	    p3.add(j);

	    p4.add(autor);
	    p4.add(subautor);
	    p4.add(eclipse);
	    p4.add(title);
	    p4.add(license);
	    p4.add(len1);
	    p4.add(len2);
	    p4.add(len3);
	    p4.add(len4);
	    p4.add(how);
	    p4.add(window);
	    p4.add(nukkit);
	    p4.add(li);
	    p4.add(howto);

	    tabbedpane.setUI(new SimpleTabbedPaneUI());
	    tabbedpane.setForeground(Color.WHITE);

	    tabbedpane.addTab(" メインコンソール", p);
	    tabbedpane.addTab(" サブコンソール", p1);
	    tabbedpane.addTab(" サーバーステータス", p2);
	    tabbedpane.addTab(" 設定", p3);
	    tabbedpane.addTab(" クレジット/ライセンス", p4);
	    tabbedpane.setBounds(0, 20, 1000, 530);

	    bmb.add(tabbedpane);

	    try {
			 FileReader fr12 = new FileReader("server.properties");
		     BufferedReader br12 = new BufferedReader(fr12);

		     while((bun = br12.readLine()) != null){
			    setStatus(bun);
			 }
		     

		   	try {
		   		 area5.setText("");
		   		 FileReader fr121 = new FileReader("ops.txt");
		   	     BufferedReader br121 = new BufferedReader(fr121);


		   	     String ops = "";
		   	     while((bun = br121.readLine()) != null){
		   		    area5.append(bun + "\n");
		   		    ops = ops + bun + "  ";
		   		 }
		   	     
		   	     System.out.println("ops.txtの読み込みが完了しました。: " + ops);

		   	     fr121.close();
		   	     br121.close();
		   		}catch(IOException ex){
		   			MessageDialog.freeDialog("ops.txtが見つかりません。");
		   			throw new FileException("ops.txtが見つかりません。");
		   		}
		     
		     System.out.println("server.propertiesを読みこみました。");

		     fr12.close();
		     br12.close();
		     
			 System.out.println("GUIの描画が完了しました。");

			return;
			}catch(IOException ex){
				MessageDialog.freeDialog("server.propertiesが見つかりません。");
				throw new FileException("server.propertiesが見つかりません。");
			}
  }

  public static void main(String[] args) {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e1) {
		e1.printStackTrace();
	}

	Main main = new Main();

	main.fc.makeFile();

	if(sett.getRun() == 0){
		MessageDialog.firstStart();
		sett.setRun();
		sett.setBackGround("./MCServerGUI/images/Background.PNG");
	}

	main.gui();

	main.loadPlugins();
	
    System.out.println("プラグインの読み込みが完了しました。");

	return;
  }

  public void loadPlugins(){
	  PluginLoader loader = new PluginLoader();
	  File pluginFolder = new File("./MCServerGUI/plugins/");
	  File[] plugins = pluginFolder.listFiles();
	  int count = 0;
	  Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), "==========プラグインを読み込んでいます...==========\n");
	  System.out.println("プラグインの読み込みを開始しました。");
	  for(int i=0;i < plugins.length;i++){
		try {
			if(plugins[i].toURL().toString().endsWith(".jar")){
				loader.loadPlugin(plugins[i]);
				count++;
			}
		} catch (Exception e) {
			String[] txt;
			try {
				txt = plugins[i].toURL().toString().split("/");
				String src = txt[txt.length - 1];
				System.err.println(src.replaceAll(".jar", "") + "プラグインを読み込めませんでした。");
				MessageDialog.freeDialog(src.replaceAll(".jar", "") + "プラグインを読み込めませんでした。");
				e.printStackTrace();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			return;
		}
	  }
	  Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), "==========プラグインを読み込みました。(" + count + ")==========\n");
	  return;
  }

  public static Process gerProcess(){
	  return process;
  }

  public static MainConsole getMainConsole(){
	  return m;
  }

  public static SubConsole getSubConsole(){
	  return sc;
  }

  public static JTextPane getMainConsoleArea(){
	  return area2;
  }

  public static JTextArea getSubConsoleArea(){
	  return area4;
  }

  public static DefaultStyledDocument getDocument(){
	  return document;
  }

  public static JTabbedPane getTabPane(){
	  return tabbedpane;
  }

  public static JTextArea getSubConsoleAd(){
	  return area4;
  }


  public void executeStartServerEvent(){
	  executor.callEvent(new StartServerEvent(this));
  }

  public void executeCloseServerEvent(){
	  executor.callEvent(new CloseServerEvent(this));
  }

}



