package itsu.mcpe.highendloginsystem.core;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.nukkit.utils.Utils;

public class MailSender {

    public static final int TYPE_MAIL_PATH = 0;
    public static final int TYPE_FORGET_PASSWORD = 1;
    public static final int TYPE_ANOTHER_LOGIN = 2;

        public static void sendMail(String title, String text, String toAddress, String password, String userName, String mailPath, String data[], int type) {
         try {
             Properties props = System.getProperties();

             props.setProperty("mail.debug", 						((Boolean) HighEndLoginSystem.configData.get("Mail-Debug")) ? "true" : "false");
             props.setProperty("mail.smtp.host", 					(String) HighEndLoginSystem.configData.get("Smtp-Host"));
             props.setProperty("mail.smtp.port", 					String.valueOf(HighEndLoginSystem.configData.get("Smtp-Port")));
             props.setProperty("mail.smtp.auth", 					((Boolean) HighEndLoginSystem.configData.get("Smtp-Auth")) ? "true" : "false");
             props.setProperty("mail.smtp.user", 					(String) HighEndLoginSystem.configData.get("Address"));
             props.setProperty("mail.smtp.password", 				String.valueOf(HighEndLoginSystem.configData.get("Password")));
             props.setProperty("mail.smtp.connectiontimeout", 		String.valueOf(HighEndLoginSystem.configData.get("Smtp-ConnectionTimeout")));
             props.setProperty("mail.smtp.timeout", 				String.valueOf(HighEndLoginSystem.configData.get("Smtp-Timeout")));
             props.setProperty("mail.smtp.socketFactory.class", 	(String) HighEndLoginSystem.configData.get("Smtp-SocketFactory-Class"));
             props.setProperty("mail.smtp.socketFactory.fallback", 	((Boolean) HighEndLoginSystem.configData.get("Smtp-SocketFactory-Fallback")) ? "true" : "false");
             props.setProperty("mail.smtp.socketFactory.port", 		String.valueOf(HighEndLoginSystem.configData.get("Smtp-SocketFactory-Port")));
             props.setProperty("mail.smtps.localhost", 				(String) HighEndLoginSystem.configData.get("Smtp-Localhost"));
             props.setProperty("mail.transport.protocol", 			(String) HighEndLoginSystem.configData.get("Session-Transport"));

             Session session = Session.getInstance(props);

             MimeMessage msg = new MimeMessage(session);

             String str;

             switch(type) {
                 case TYPE_MAIL_PATH:
                     str = Utils.readFile("./plugins/HighEndLoginSystem/" + HighEndLoginSystem.configData.get("Account-Mail"));
                     msg.setContent(new String(str.replaceAll("#_ADDRESS_#", toAddress).replaceAll("#_PASSWORD_#", password).replaceAll("#_MAILPATH_#", mailPath).replaceAll("#_USERNAME_#", userName).getBytes("MS932"), "SHIFT_JIS"), "text/html;charset=SHIFT_JIS");
                     break;

                 case TYPE_FORGET_PASSWORD:
                     str = Utils.readFile("./plugins/HighEndLoginSystem/" + HighEndLoginSystem.configData.get("Password-Mail"));
                     msg.setContent(new String(str.replaceAll("#_PASSWORD_#", password).replaceAll("#_USERNAME_#", userName).getBytes("MS932"), "SHIFT_JIS"), "text/html;charset=SHIFT_JIS");
                     break;

                 case TYPE_ANOTHER_LOGIN:
                     str = Utils.readFile("./plugins/HighEndLoginSystem/" + HighEndLoginSystem.configData.get("AnotherLogin-Mail"));
                     msg.setContent(new String(str.replaceAll("#_OS_#", data[0]).replaceAll("#_MODEL_#", data[1]).replaceAll("#_TIME_#", data[2]).getBytes("MS932"), "SHIFT_JIS"), "text/html;charset=SHIFT_JIS");
                     break;
             }

             msg.setFrom(new InternetAddress((String) HighEndLoginSystem.configData.get("Address"), HighEndLoginSystem.instance.getServer().getMotd()));
             msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
             msg.setSubject(title);
             msg.setSentDate(new Date());

             Transport trans = null;

             try {
                 trans = session.getTransport("smtps");

                 trans.connect(
                     props.getProperty("mail.smtp.host"),
                     Integer.parseInt(props.getProperty("mail.smtp.port")),
                     props.getProperty("mail.smtp.user"),
                     props.getProperty("mail.smtp.password"));

                 trans.sendMessage(msg, msg.getAllRecipients());
             } finally {
                 if (trans != null) {
                     trans.close();
                 }
             }

       } catch (Exception e) {
    	   HighEndLoginSystem.instance.getLogger().alert("メールアドレスまたはパスワードが違う可能性があります。");
           e.printStackTrace();
       }
    }
}
