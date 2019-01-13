package mcserver.gui.console;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MainConsole {

	String line;
	String errLine;
	SubConsole sc = new SubConsole();
	Thread Run;
	String source = "";

	public MainConsole()
	{
	}

	public void sendMainConsole(Process process, DefaultStyledDocument document){

		InputStream in = process.getInputStream();
		try {
			  BufferedReader br =new BufferedReader(new InputStreamReader(in));

		  		while ((line = br.readLine()) != null) {
		  			if(line.indexOf("[ALERT]") != -1){
		  		//属性情報を作成
			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

			  	//属性情報の文字色に赤を設定
			  			attribute1.addAttribute(StyleConstants.Foreground, Color.RED);
			  			
					  	//ドキュメントにその属性情報つきの文字列を挿入
			  			document.insertString(source.length(), line + "\n", attribute1);
			  			
			  			source = source + line + "\n";
		  			}
		  			else if(line.indexOf("[CRITICAL]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.RED);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[ERROR]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.RED);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[EMERGENCY]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.RED);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[NOTICE]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.CYAN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[WARNING]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.YELLOW);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[DEBUG]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GRAY);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] 起動完了(") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] Loading") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] [Server]") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.MAGENTA);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] Stopping other threads") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] 未知のコマンドです。/helpでコマンドの一覧を確認してください") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.RED);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] Minecraft: PEサーバー(") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.CYAN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] nukkit.ymlを読み込んでいます") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] jupiter.ymlを読み込んでいます") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("[INFO] server.propertiesを読み込んでいます") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
					  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  			else if(line.indexOf("Starting Nukkit") != -1){
		  		  		//属性情報を作成
		  			  			SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  			  	//属性情報の文字色に赤を設定
		  			  			attribute1.addAttribute(StyleConstants.Foreground, Color.GREEN);
		  			  			
							  	//ドキュメントにその属性情報つきの文字列を挿入
					  			document.insertString(source.length(), line + "\n", attribute1);
					  			
					  			source = source + line + "\n";
		  		  			}
		  	//属性情報を作成
		  			else{
		  				SimpleAttributeSet attribute1 = new SimpleAttributeSet();

		  	//属性情報の文字色に赤を設定
		  			attribute1.addAttribute(StyleConstants.Foreground, Color.WHITE);

			  	//ドキュメントにその属性情報つきの文字列を挿入
			  			document.insertString(source.length(), line + "\n", attribute1);
			  			
			  			source = source + line + "\n";
		  			}
		  		}
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			return;
		  		}
		return;
	}
	
	public void sendError(Process process, DefaultStyledDocument document){
		InputStream ein = process.getErrorStream();
		try {
		  	BufferedReader ebr = new BufferedReader(new InputStreamReader(ein));
		  	while ((errLine = ebr.readLine()) != null) {
		  		SimpleAttributeSet attribute1 = new SimpleAttributeSet();

			  	//属性情報の文字色に赤を設定
			  			attribute1.addAttribute(StyleConstants.Foreground, Color.RED);

			  	//ドキュメントにその属性情報つきの文字列を挿入
			  			document.insertString(source.length(), errLine + "\n", attribute1);
			  			
			  			source = source + line + "\n";
		  	}
		  	} catch (Exception e) {
		  	  e.printStackTrace();
		  	  return;
		  	}
		return;
	}

}
