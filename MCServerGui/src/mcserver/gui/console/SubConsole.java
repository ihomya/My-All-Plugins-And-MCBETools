package mcserver.gui.console;

import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.JTextArea;

public class SubConsole {

	public SubConsole()
	{
	}

	public void sendSubConsole(JTextArea area4,Long start){
	    Calendar now = Calendar.getInstance();
	    int h = now.get(Calendar.HOUR_OF_DAY);//時を取得
	    int m = now.get(Calendar.MINUTE);     //分を取得
	    int s = now.get(Calendar.SECOND);      //秒を取得
	    DecimalFormat f1 = new DecimalFormat("#,###KB");
	    DecimalFormat f2 = new DecimalFormat("##.#");
	    long free = Runtime.getRuntime().freeMemory() / 1024;
	    long total = Runtime.getRuntime().totalMemory() / 1024;
	    long max = Runtime.getRuntime().maxMemory() / 1024;
	    long used = total - free;
	    double ratio = (used * 100 / (double)total);
	    area4.append(">" + h + ":" + m + ":" + s + Pattern.INFO + "メモリ使用量:\n");
	    area4.append("          合計:" + f1.format(total) + "\n");
	    area4.append("          使用量:" + f1.format(used) + "(" + f2.format(ratio) + "%)\n");
	    area4.append("          最大使用可能:" + f1.format(max) + "\n\n");
	    long end = System.nanoTime();
	    area4.append(">" + h + ":" + m + ":" + s + Pattern.INFO + "起動から" + (end - start) / 1000000000 + "秒\n\n");
		  	return;
	}
	
	public void NotConnectionConsole(JTextArea area){
	    Calendar now = Calendar.getInstance();
	    int h = now.get(Calendar.HOUR_OF_DAY);//時を取得
	    int m = now.get(Calendar.MINUTE);     //分を取得
	    int s = now.get(Calendar.SECOND);      //秒を取得
	    area.append(">" + h + ":" + m + ":" + s + Pattern.ALERT + "インターネットに接続されていないことが検出されました。\n");
	    return;
	}
	
	public void sendConsole(JTextArea area, String value){
	    Calendar now = Calendar.getInstance();
	    int h = now.get(Calendar.HOUR_OF_DAY);//時を取得
	    int m = now.get(Calendar.MINUTE);     //分を取得
	    int s = now.get(Calendar.SECOND);      //秒を取得
	    area.append(">" + h + ":" + m + ":" + s + value);
	    return;
	}
	
	public static class Pattern{
		public static String ALERT = " [ALERT]";
		public static String INFO = " [INFO]";
		public static String CRITICAL = " [CRITICAL]";
	}

}
