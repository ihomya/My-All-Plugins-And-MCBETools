package help.on.mail.helponmail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener {

	String Title,Main,From,To,Smtp;
	File f = new File(".\\help.on.mail.data\\");

	public void onEnable() {
        getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
        if(!(f.exists())){
        	if(!(f.mkdirs())){
        		getLogger().critical("ファイル作成失敗");
        	}
        }
        if(f.mkdirs()){
    		try{
				FileWriter fw1 = new FileWriter(".\\help.on.mail.data\\Title.txt" , false);  //※1
				PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
				FileWriter fw2 = new FileWriter(".\\help.on.mail.data\\Main.txt" , false);  //※1
				PrintWriter pw2 = new PrintWriter(new BufferedWriter(fw2));
				FileWriter fw3 = new FileWriter(".\\help.on.mail.data\\From.txt" , false);  //※1
				PrintWriter pw3 = new PrintWriter(new BufferedWriter(fw3));
				FileWriter fw4 = new FileWriter(".\\help.on.mail.data\\To.txt" , false);  //※1
				PrintWriter pw4 = new PrintWriter(new BufferedWriter(fw4));
				FileWriter fw5 = new FileWriter(".\\help.on.mail.data\\Smtp.txt" , false);  //※1
				PrintWriter pw5 = new PrintWriter(new BufferedWriter(fw5));
				FileWriter fw6 = new FileWriter(".\\help.on.mail.data\\Account.txt" , false);  //※1
				PrintWriter pw6 = new PrintWriter(new BufferedWriter(fw6));
				FileWriter fw7 = new FileWriter(".\\help.on.mail.data\\Password.txt" , false);  //※1
				PrintWriter pw7 = new PrintWriter(new BufferedWriter(fw7));

				pw1.println("");
				pw2.println("");
				pw3.println("");
				pw4.println("");
				pw5.println("");
				pw6.println("");
				pw7.println("");

				pw1.close();
				fw1.close();
				pw2.close();
				fw2.close();
				pw3.close();
				fw3.close();
				pw4.close();
				fw4.close();
				pw5.close();
				pw5.close();
				pw6.close();
				fw6.close();
				pw7.close();
				pw7.close();
				
			}catch(IOException e){
			}
        }
    }

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		Player player = (Player) sender;
		switch(command.getName()){
		case "alert":
			try {
	            //ファイルを読み込む
	        FileReader fr1 = new FileReader(".\\help.on.mail.data\\Title.txt");
	        BufferedReader br1 = new BufferedReader(fr1);
	        FileReader fr2 = new FileReader(".\\help.on.mail.data\\Main.txt");
	        BufferedReader br2 = new BufferedReader(fr2);
	        FileReader fr3 = new FileReader(".\\help.on.mail.data\\From.txt");
	        BufferedReader br3 = new BufferedReader(fr3);
	        FileReader fr4 = new FileReader(".\\help.on.mail.data\\To.txt");
	        BufferedReader br4 = new BufferedReader(fr4);
	        FileReader fr5 = new FileReader(".\\help.on.mail.data\\Smtp.txt");
	        BufferedReader br5 = new BufferedReader(fr5);

	        Title = br1.readLine();
	        Main = br2.readLine();
	        From = br3.readLine();
	        To = br4.readLine();
	        Smtp = br4.readLine();

	        fr1.close();
	        fr2.close();
	        fr3.close();
	        fr4.close();
	        fr5.close();
	        br1.close();
	        br2.close();
	        br3.close();
	        br4.close();
	        br5.close();
			}catch (IOException ex1) {
	            //例外発生時処理
	            sender.sendMessage(TextFormat.RED + "メール送信に失敗しました。:ファイルが見つかりません。");
	        return false;
			}

			try {
			      // メール送信準備
			      Properties props = new Properties();
			      Authenticator auth = new MailAuthenticator();
			      props.setProperty("mail.smtp.connectiontimeout", "50000");
			      props.setProperty("mail.smtp.timeout", "50000");
			      props.put("mail.smtp.host", Smtp );
			      props.put("mail.smtp.auth", "true" );// ▲承認用コード①
			      Session session = Session.getInstance( props, auth); // ▲承認用コード②
			      session.setDebug( true );  // デバッグモードにする
			      // メール作成
			      MimeMessage mime = new MimeMessage( session );
			      mime.addFrom( InternetAddress.parse("From") );
			      mime.setRecipients( Message.RecipientType.TO, InternetAddress.parse("To"));
			      mime.setSubject(Title, "iso-2022-jp" );
			      mime.setText("<" + player.getName() + "から>" + Main,"iso-2022-jp");
			      mime.setSentDate( new Date() );
			      // 送信
			      Transport.send( mime );
			      sender.sendMessage(TextFormat.GREEN + "鯖主への荒らし報告が完了しました。");
			    } catch( Exception ex ){
			      sender.sendMessage(TextFormat.RED + "メールが正しく送信されませんでした。");
			    return false;
			    }
			return true;
		}
		return false;
	}
	
	public class MailAuthenticator extends Authenticator {
		String pass,account;
	    protected PasswordAuthentication getPasswordAuthentication() {
	    	try {
	            //ファイルを読み込む
	        FileReader fr1 = new FileReader(".\\help.on.mail.data\\Password.txt");
	        BufferedReader br1 = new BufferedReader(fr1);
	        FileReader fr2 = new FileReader(".\\help.on.mail.data\\Account.txt");
	        BufferedReader br2 = new BufferedReader(fr2);

	        pass = br1.readLine();
	        account = br2.readLine();

	        fr1.close();
	        fr2.close();
	        br1.close();
	        br2.close();

			}catch (IOException ex1) {
				getLogger().critical("メール送信失敗");
			}
	        return new PasswordAuthentication(account, pass);
	    }
	}
}

