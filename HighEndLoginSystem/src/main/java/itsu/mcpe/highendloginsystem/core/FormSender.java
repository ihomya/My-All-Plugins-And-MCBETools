package itsu.mcpe.highendloginsystem.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import itsu.mcbe.form.base.CustomForm;
import itsu.mcbe.form.base.ModalForm;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.element.Input;
import itsu.mcbe.form.element.Label;
import itsu.mcbe.form.element.Toggle;

public class FormSender {

    public static final int FORM_WELCOME = 9132;
    public static final int FORM_EULA = 9133;
    public static final int FORM_REGISTER = 9134;
    public static final int FORM_REGISTER_CHECK = 9135;
    public static final int FORM_REGISTER_SUCCESS = 9136;
    public static final int FORM_HOW_TO_USE = 9137;
    public static final int FORM_FORGET_PASSWORD = 9138;
    public static final int FORM_AUTH = 9139;
    
    private final static int DEVICE_OS_ANDROID = 1;
    private final static int DEVICE_OS_IOS = 2;
    private final static int DEVICE_OS_OSX = 3;
    private final static int DEVICE_OS_FIREOS = 4;
    private final static int DEVICE_OS_GEARVR = 5;
    private final static int DEVICE_OS_HOLOLENS = 6;
    private final static int DEVICE_OS_WINDOWS10 = 7;
    private final static int DEVICE_OS_WINDOWS32 = 8;
    private final static int DEVICE_OS_DEDICATED = 9;

    private String mailAddress = "";
    private String password = "";
    private boolean autoLogin = false;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E曜日 a hh:mm:ss");

    public ModalForm getWelcomeForm(Player player) {
        ModalForm welcome = new ModalForm() {
            @Override
            public void onButton1Click(Player player) {
                NukkitFormAPI.sendFormToPlayer(player, getEulaForm());
            }

            @Override
            public void onButton2Click(Player player) {
                NukkitFormAPI.sendFormToPlayer(player, getEulaForm());
            }
        }

            .setId(FORM_WELCOME)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " 1 / 4")
            .setButton1Text("次へ")
            .setButton2Text("Next")
            .setContent((String) HighEndLoginSystem.configData.get("Form-1"));

        return welcome;
    }

    public ModalForm getEulaForm() {
        try {
            ModalForm eula = new ModalForm() {
                @Override
                public void onButton1Click(Player player) {
                    NukkitFormAPI.sendFormToPlayer(player, getRegisterForm(false));
                }

                @Override
                public void onButton2Click(Player player) {
                    NukkitFormAPI.sendFormToPlayer(player, getWelcomeForm(player));
                }
            }

                .setId(FORM_EULA)
                .setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " 2 / 4")
                .setButton1Text("次へ")
                .setButton2Text("前へ")
                .setContent(Utils.readFile("./plugins/HighEndLoginSystem/" + (String) HighEndLoginSystem.configData.get("Eula")));

                return eula;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CustomForm getRegisterForm(boolean reRegister) {
        String text = TextFormat.AQUA + HighEndLoginSystem.instance.getServer().getMotd() + " アカウント登録\n" + TextFormat.WHITE + "フォームは閉じないでください。入力項目に空白文字は使えません。";
        if(reRegister) text += "\n\n" + TextFormat.RED + "入力されたパスワードが違うか、パスワードが入力されていない、または不正なメールアドレスやパスワードが入力されました。";

        CustomForm login = new CustomForm() {
            @Override
            public void onEnter(Player player, List<Object> result) {
                mailAddress = ((String) result.get(2)) != null ? ((String) result.get(2)) : "";
                password = ((String) result.get(3)) != null ? ((String) result.get(3)) : "";
                String rePassword = ((String) result.get(4)) != null ? ((String) result.get(4)) : "";
                autoLogin = (boolean) result.get(5);

                if(!(password.equals(rePassword)) || password.equals("") || rePassword.equals("") || password.contains("\\s") || (mailAddress.length() > 0 && !(mailAddress.contains("@")))) {
                    mailAddress = "";
                    password = "";
                    autoLogin = false;

                    NukkitFormAPI.sendFormToPlayer(player, getRegisterForm(true));
                    return;
                }

                if(password.length() == 1 || password.equals("1234")) {
                    NukkitFormAPI.sendFormToPlayer(player, getRegisterCheckForm(TextFormat.WHITE + "入力されたパスワードの安全性は低く、不正ログインされる恐れがあります。それでも続けますか?\n\n" + TextFormat.AQUA + "設定されたパスワード: " + TextFormat.WHITE + TextFormat.ITALIC + password));
                    return;
                }

                if(mailAddress.equals("")) {
                    NukkitFormAPI.sendFormToPlayer(player, getRegisterCheckForm(TextFormat.WHITE + "メールアドレスが設定されませんでした。その場合、パスワード復旧などのサービスが受けられませんがよろしいですか?"));
                    return;
                }

                NukkitFormAPI.sendFormToPlayer(player, getRegisterSuccessForm(player, mailAddress, password, autoLogin));
            }
        }

            .setId(FORM_REGISTER)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " 3 / 4")

            .addFormElement(new Label()
                    .setText(text)
                    )

            .addFormElement(new Label()
                    .setText(TextFormat.YELLOW + "メールアドレスを入力する理由\n" + TextFormat.WHITE + "あなたのメールアドレスを入力することで、パスワードを忘れた際などに再発行などが可能になります。GMailやYahoo!メールなどネットメールが好ましいです。")
                    )

            .addFormElement(new Input()
                    .setText("メールアドレス（任意）")
                    .setDefaultText("")
                    .setPlaceHolder("メールアドレス")
                    )

            .addFormElement(new Input()
                    .setText("パスワード")
                    .setDefaultText("")
                    .setPlaceHolder("パスワード")
                    )

            .addFormElement(new Input()
                    .setText("パスワード（再度）")
                    .setDefaultText("")
                    .setPlaceHolder("パスワード（再度）")
                    )

            .addFormElement(new Toggle()
                    .setText("ログインしたままにする")
                    .setValue(false)
                    );

        return login;
    }

    public ModalForm getRegisterCheckForm(String text) {
        ModalForm eula = new ModalForm() {
            @Override
            public void onButton1Click(Player player) {
                NukkitFormAPI.sendFormToPlayer(player, getRegisterSuccessForm(player, mailAddress, password, autoLogin));
            }

            @Override
            public void onButton2Click(Player player) {
                mailAddress = "";
                password = "";
                autoLogin = false;

                NukkitFormAPI.sendFormToPlayer(player, getRegisterForm(false));
            }
        }

            .setId(FORM_REGISTER_CHECK)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd())
            .setButton1Text("続ける")
            .setButton2Text("登録しなおす")
            .setContent(text);

        return eula;
    }

    public ModalForm getRegisterSuccessForm(Player player, String mailAddress, String password, boolean autoLogin) {
        if(!Server.getSQLSystem().existsAccount(player.getName())) {
            String mailPath = mailAddress.equals("") ? "nonvalue" : HighEndLoginSystem.getMailPath();
            String mail = mailAddress.equals("") ? "nonvalue" : "NONE:" + mailAddress;
            
            Server.getSQLSystem().createAccount(player, password, mail, mailPath, autoLogin, false);
            MailSender.sendMail("アカウント登録完了のお知らせ", "", mailAddress, password, player.getName(), mailPath, new String[]{}, MailSender.TYPE_MAIL_PATH);
            Server.setLoggedInPlayer(player, true);
            player.sendMessage(
            		TextFormat.GREEN + "アカウントを登録しました。\n" + 
            		TextFormat.WHITE + "メールアドレスを登録した場合はまだ仮登録状態であり、有効にはなっておりませんので登録完了メールより認証を行ってください。");
        }

        ModalForm rsf = new ModalForm()
            .setId(FORM_REGISTER_SUCCESS)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd() + " 4 / 4")
            .setButton1Text("閉じる")
            .setButton2Text("End")
            .setContent(TextFormat.GREEN + "アカウントの登録が完了しました。\n" + TextFormat.WHITE + "メールアドレスを入力した場合、確認メールが届きます。この画面をスクリーンショットするなどして忘れないようにしておきましょう。\n\n" + TextFormat.YELLOW + "メールアドレス: " + TextFormat.WHITE + TextFormat.ITALIC + mailAddress + TextFormat.YELLOW + "\nパスワード: " + TextFormat.WHITE + TextFormat.ITALIC + password);

        return rsf;
    }
    
    public CustomForm getAuthForm(Player player, boolean reAuth) {
    	String text = TextFormat.AQUA + HighEndLoginSystem.instance.getServer().getMotd() + " メール認証\n" + TextFormat.WHITE + "フォームは閉じないでください。五桁の認証コードを入力してください。";
        if(reAuth) text += "\n\n" + TextFormat.RED + "不正な認証コードが入力されたか、コードが違います。";
        
    	CustomForm form = new CustomForm() {
            @SuppressWarnings("deprecation")
			@Override
            public void onEnter(Player player, List<Object> result) {
            	
            	if((boolean) result.get(2)) {
            		NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, false));
            		return;
            	}
            	
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
            		
            		if(Server.getSQLSystem().getAutoLogin(player.getName())) {
	            		if(!(Server.getSQLSystem().getClientId(player.getName()).equals(player.getClientId())) ||
	    						!(Server.getSQLSystem().getIpAddress(player.getName()).equals(player.getName()))) {
	    					NukkitFormAPI.sendFormToPlayer(player, new FormSender().getLoginForm(player, false));
	    					return;
	            		}
	            		
            		} else {
            			NukkitFormAPI.sendFormToPlayer(player, new FormSender().getLoginForm(player, false));
    					return;
            		}
            		
            	} else {
            		NukkitFormAPI.sendFormToPlayer(player, getAuthForm(player, true));
            		return;
                }
            }
        }

            .setId(FORM_AUTH)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd())

            .addFormElement(new Label()
                    .setText(text)
                    )

            .addFormElement(new Input()
                    .setText("認証コード")
                    .setDefaultText("")
                    .setPlaceHolder("認証コード")
                    )
            
            .addFormElement(new Toggle()
                    .setText("スキップ")
                    .setValue(false)
                    );

        return form;
    }
    
    public CustomForm getLoginForm(Player player, boolean reRegister) {
        String text = TextFormat.AQUA + HighEndLoginSystem.instance.getServer().getMotd() + " ログイン\n" + TextFormat.WHITE + "フォームは閉じないでください。入力項目に空白文字は使えません。";
        if(reRegister) text += "\n\n" + TextFormat.RED + "入力されたパスワードが違うか、パスワードが入力されていない、または不正なメールアドレスやパスワードが入力されました。";

        CustomForm login = new CustomForm() {
            @SuppressWarnings("deprecation")
			@Override
            public void onEnter(Player player, List<Object> result) {
                password = ((String) result.get(1)) != null ? ((String) result.get(1)) : "";
                autoLogin = (boolean) result.get(2);
                
                boolean forgetPassword = (boolean) result.get(3);
                
                if(forgetPassword) {
                	NukkitFormAPI.sendFormToPlayer(player, getForgetPasswordForm(player));
                	return;
                }

                if(password.equals("") || password.contains("\\s")) {
                    password = "";
                    autoLogin = false;

                    NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, true));
                    return;
                }
                
                if(Server.getSQLSystem().getPassword(player.getName()).equals(password)) {
                	player.sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] ログインが完了しました。");
                	Server.setLoggedInPlayer(player, true);
                	Server.getSQLSystem().updateAutoLogin(player.getName(), autoLogin);
                	
                	if(!Server.getSQLSystem().getIpAddress(player.getName()).equals(player.getAddress()) ||
                			!Server.getSQLSystem().getClientId(player.getName()).equals(player.getClientId())) {
                		MailSender.sendMail(HighEndLoginSystem.instance.getServer().getMotd(), "", Server.getSQLSystem().getMailAddress(player.getName()), "", "", "", new String[]{getOSById(player.getLoginChainData().getDeviceOS()), player.getLoginChainData().getDeviceModel(), sdf.format(Calendar.getInstance().getTime())}, MailSender.TYPE_ANOTHER_LOGIN);
                	}
                	
                	return;
                	
                } else {
                	NukkitFormAPI.sendFormToPlayer(player, getLoginForm(player, true));
                    return;
                }
            }
        }

            .setId(FORM_REGISTER)
            .setTitle(HighEndLoginSystem.instance.getServer().getMotd())

            .addFormElement(new Label()
                    .setText(text)
                    )

            .addFormElement(new Input()
                    .setText("パスワード")
                    .setDefaultText("")
                    .setPlaceHolder("パスワード")
                    )

            .addFormElement(new Toggle()
                    .setText("ログインしたままにする")
                    .setValue(false)
                    )
            
            .addFormElement(new Toggle()
                    .setText("パスワードを忘れた")
                    .setValue(false)
                    );

        return login;
    }
    
    public ModalForm getForgetPasswordForm(Player player) {
    	String text = "メールアドレスが登録されていません。アカウントを削除しますか?";
    	String mail = Server.getSQLSystem().getMailAddress(player.getName());
    	
    	if(!mail.equals("nonvalue")) {
    		if(!mail.startsWith("NONE:")) {
    			text = "メールアドレスが登録されています。\nメールにてあなたのパスワードを通知しますか?";
    		}
    	}
    	
    	ModalForm form = new ModalForm() {
            @Override
            public void onButton1Click(Player player) {
                if(this.getContent().startsWith("メールアドレスが登録されています。")) {
                	player.kick("メールを送信しました。再度ログインしてください。");
                	MailSender.sendMail(HighEndLoginSystem.instance.getServer().getMotd(), "", Server.getSQLSystem().getMailAddress(player.getName()), mail, player.getName(), "", new String[]{}, MailSender.TYPE_FORGET_PASSWORD);
                	return;
                	
                } else {
                	Server.getSQLSystem().deleteAccount(player.getName());
                	player.kick("アカウントが削除されました。再度アカウント登録をしてください。");
                	return;
                }
            }

            @Override
            public void onButton2Click(Player player) {
            	player.kick(HighEndLoginSystem.instance.getServer().getMotd() + "によってキックされました。");
            	return;
            }
        }
    		.setId(FormSender.FORM_FORGET_PASSWORD)
    		.setButton1Text("はい")
    		.setButton2Text("いいえ")
    		.setContent(text)
    		.setTitle(HighEndLoginSystem.instance.getServer().getMotd());
        
        return form;
    }
    
    private String getOSById(int id) {
    	switch(id){
	        case DEVICE_OS_ANDROID:
	            return "Android";
	
	        case DEVICE_OS_IOS:
	            return "iOS";
	
	        case DEVICE_OS_OSX:
	            return "OSX";
	
	        case DEVICE_OS_FIREOS:
	            return "FireOS";
	
	        case DEVICE_OS_GEARVR:
	            return "Gear VR";
	
	        case DEVICE_OS_HOLOLENS:
	            return "HoloLens";
	
	        case DEVICE_OS_WINDOWS10:
	            return "Windows 10";
	
	        case DEVICE_OS_WINDOWS32:
	            return "Windows 32";
	
	        case DEVICE_OS_DEDICATED:
	            return "Dedicated";
	            
	        default:
	        	return "Unknown";
    	}
    }

}
