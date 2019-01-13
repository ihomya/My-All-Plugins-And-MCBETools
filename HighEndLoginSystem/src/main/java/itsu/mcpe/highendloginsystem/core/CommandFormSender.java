package itsu.mcpe.highendloginsystem.core;

import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.form.base.CustomForm;
import itsu.mcbe.form.base.ModalForm;
import itsu.mcbe.form.base.SimpleForm;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.element.Button;
import itsu.mcbe.form.element.Input;
import itsu.mcbe.form.element.Label;

public class CommandFormSender {
	
	public static final int FORM_MENU = 8326;
	public static final int FORM_LOGIN = 8327;
	public static final int FORM_ACCOUNT_INFO = 8328;
	public static final int FORM_AUTH_MAIL = 8329;
	public static final int FORM_CHANGE_MAILADDRESS = 8330;
	public static final int FORM_CHANGE_PASSWORD = 8331;
	
	private static final int STATE_ACCOUNT_INFO = 0;
	private static final int STATE_CHANGE_MAILADDRESS = 1;
	private static final int STATE_CHANGE_PASSWORD = 2;
	
	private int state = 0;
	
	public SimpleForm getMenuForm(Player player) {
		SimpleForm form = new SimpleForm() {
			@Override
			public void onEnter(Player player, int index) {
				switch(index) {
					case 0:
						state = STATE_ACCOUNT_INFO;
						NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, false));
						break;
						
					case 1:
						NukkitFormAPI.sendFormToPlayer(player, getAuthForm(player, false));
						break;
						
					case 2:
						state = STATE_CHANGE_MAILADDRESS;
						NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, false));
						break;
						
					case 3:
						state = STATE_CHANGE_PASSWORD;
						NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, false));
						break;
						
					case 4:
						break;
				}
			}
		}
			.setId(FORM_MENU)
			.setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " アカウントメニュー")
			.setContent("行う操作を選んでください。")
			
			.addButton(new Button("アカウント情報"))
			.addButton(new Button("メールアドレス認証"))
			.addButton(new Button("メールアドレス変更"))
			.addButton(new Button("パスワード変更"));
			
		if(player.isOp()) form.addButton(new Button("BAN / BAN解除"));
			
		return form;
	}
	
	public CustomForm getLoginForm(Player player, boolean reSend) {
		String text = "アカウント情報を見るために再度ログインしてください。";
		
		if(reSend) text += TextFormat.RED + "\nログインに失敗しました。";
		
		CustomForm form = new CustomForm() {
			@Override
			public void onEnter(Player player, List<Object> result) {
				if(Server.getSQLSystem().getPassword(player.getName()).equals((String) result.get(1))) {
					if(state == STATE_ACCOUNT_INFO) NukkitFormAPI.sendFormToPlayer(player, getAccountInfo(player));
					else if(state == STATE_CHANGE_MAILADDRESS) NukkitFormAPI.sendFormToPlayer(player, getChangeMailAddressForm(player, false));
					else if(state == STATE_CHANGE_PASSWORD) NukkitFormAPI.sendFormToPlayer(player, getChangePasswordForm(player, false));
					return;
					
				} else {
					NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, true));
					return;
				}
			}
		}
		
			.setId(FORM_LOGIN)
			.setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " ログイン")
			
			.addFormElement(new Label(text))
			.addFormElement(new Input("パスワード", "パスワード", ""));
			
		return form;
	}
	
	public ModalForm getAccountInfo(Player player) {
		Map<String, Object> data = Server.getSQLSystem().getAccount(player.getName());
		
		StringBuffer bf = new StringBuffer();
		bf.append(player.getName() + "さんのアカウント情報です。\n\n");
		bf.append(TextFormat.AQUA + "ユーザー名: " + TextFormat.WHITE + data.get("name") + "\n");
		
		String mail = (String) data.get("mailAddress");
		if(((String) data.get("mailAddress")).startsWith("NONE:")) {
			mail = mail.replaceAll("NONE:", "") + TextFormat.RED + " [未認証]";
		}
		bf.append(TextFormat.AQUA + "メールアドレス: " + TextFormat.WHITE + mail.replaceAll("NONE:", "") + "\n");
		
		String pass = "";
		for(int i = 0;i < ((String) data.get("password")).length();i++) {
			pass += "*";
		}
		bf.append(TextFormat.AQUA + "パスワード: " + TextFormat.WHITE + pass + "\n");
		
		ModalForm form = new ModalForm() {
			@Override
			public void onButton1Click(Player player) {
				NukkitFormAPI.sendFormToPlayer(player, getMenuForm(player));
				return;
			}
		}
				.setId(FORM_ACCOUNT_INFO)
				.setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " アカウント情報")
				.setButton1Text("メニューへ戻る")
				.setButton2Text("閉じる")
				.setContent(bf.toString());
		
		return form;
	}
	
	public CustomForm getAuthForm(Player player, boolean reAuth) {
    	String text = TextFormat.AQUA + HighEndLoginSystem.instance.getServer().getMotd() + " メール認証\n" + TextFormat.WHITE + "フォームは閉じないでください。五桁の認証コードを入力してください。";
        if(reAuth) text += "\n\n" + TextFormat.RED + "不正な認証コードが入力されたか、コードが違います。";
        
    	CustomForm form = new CustomForm() {
            @Override
            public void onEnter(Player player, List<Object> result) {
            	int code = 0;
            	try {
            		code = Integer.parseInt((String) result.get(1));
            		
            	} catch(NumberFormatException ex) {
            		NukkitFormAPI.sendFormToPlayer(player, getAuthForm(player, true));
            		return;
            	}
            	
            	if(code < 10000 || code > 99999) {
            		NukkitFormAPI.sendFormToPlayer(player, getAuthForm(player, true));
            		return;
            	}
            	
            	if(Server.getSQLSystem().getMailPath(player.getName()).equals(String.valueOf(code))) {
            		Server.getSQLSystem().updateMailAddress(player.getName(), Server.getSQLSystem().getMailAddress(player.getName()).replaceAll("NONE:", ""));
            		Server.getSQLSystem().updateMailPath(player.getName(), "nonvalue");
            		player.sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] 認証されました。");
            		return;
            		
            	} else {
            		NukkitFormAPI.sendFormToPlayer(player, getAuthForm(player, true));
            		return;
                }
            }
        }

            .setId(FORM_AUTH_MAIL)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " メール認証")

            .addFormElement(new Label()
                    .setText(text)
                    )

            .addFormElement(new Input()
                    .setText("認証コード")
                    .setDefaultText("")
                    .setPlaceHolder("認証コード")
                    );

        return form;
    }
	
	public CustomForm getChangeMailAddressForm(Player player, boolean reSend) {
		String mailAddress = Server.getSQLSystem().getMailAddress(player.getName());
		if(mailAddress.startsWith("NONE:")) {
			mailAddress = mailAddress.replaceAll("NONE:", "") + TextFormat.RED + " [未認証]";
		} else {
			mailAddress.replaceAll("NONE:", "");
		}
		
		String text = "メールアドレスを変更します。\n\n現在のメールアドレス: \n" + Server.getSQLSystem().getMailAddress(player.getName());
		
		if(reSend) text = "メールアドレスを変更します。\n" + TextFormat.RED + "不正なメールアドレスです。" + "\n\n現在のメールアドレス: \n" + mailAddress;
		
		CustomForm form = new CustomForm() {
			@Override
			public void onEnter(Player player, List<Object> result) {
				String mail = (String) result.get(1);
				
				if(!(mail.contains("@"))) {
					NukkitFormAPI.sendFormToPlayer(player, getChangeMailAddressForm(player, true));
					return;
				}
				
				String mailPath = mail.equals("") ? "nonvalue" : HighEndLoginSystem.getMailPath();
				
				if(mail.equals("")) {
					Server.getSQLSystem().updateMailAddress(player.getAddress(), "nonvalue");
					Server.getSQLSystem().updateMailPath(player.getName(), mailPath);
					player.sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] メールアドレスを削除しました。");
					return;
				}
				
				Server.getSQLSystem().updateMailAddress(player.getName(), "NONE:" + mail);
				Server.getSQLSystem().updateMailPath(player.getName(), mailPath);
				
				MailSender.sendMail("アカウント登録完了のお知らせ", "", mail, "NONE", player.getName(), HighEndLoginSystem.getMailPath(), new String[]{}, MailSender.TYPE_MAIL_PATH);
				
				player.sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] メールアドレスが変更されました。: " + mail);
				player.sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] 再度認証を行ってください。");
				return;
			}
		}
		
			.setId(FORM_CHANGE_MAILADDRESS)
			.setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " メール変更")
			.addFormElement(new Label(text))
			.addFormElement(new Input("新しいメールアドレス", "メールアドレス", ""));
			
		return form;
		
	}
	
	public CustomForm getChangePasswordForm(Player player, boolean reSend) {
		String text = "お使いのアカウントのパスワードを変更します。";
		
		if(reSend) text += TextFormat.RED + "\nパスワードが一致しないか、パスワードが変わっていません。";
		
		CustomForm form = new CustomForm() {
			@Override
			public void onEnter(Player player, List<Object> result) {
				String pass = (String) result.get(1);
				String passTwice = (String) result.get(2);
				
				if(!pass.equals(passTwice)) {
					NukkitFormAPI.sendFormToPlayer(player, getChangePasswordForm(player, true));
					return;
					
				} else if(!(pass.equals(passTwice)) || pass.equals("") || passTwice.equals("") || pass.contains("\\s")) {
					NukkitFormAPI.sendFormToPlayer(player, getChangePasswordForm(player, true));
					return;
					
				} else if(Server.getSQLSystem().getPassword(player.getName()).equals(pass)) {
					NukkitFormAPI.sendFormToPlayer(player, getChangePasswordForm(player, true));
					return;
				}
				
				Server.getSQLSystem().updatePassword(player.getName(), pass);
				
				player.sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] パスワードが変更されました。");
				return;
			}
		}
			.setId(FORM_CHANGE_PASSWORD)
			.setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " パスワード変更")
			
			.addFormElement(new Label(text))
			.addFormElement(new Input("新しいパスワード", "新しいパスワード", ""))
			.addFormElement(new Input("新しいパスワード（再入力）", "新しいパスワード（再入力）", ""));
		
		return form;
	}

}
