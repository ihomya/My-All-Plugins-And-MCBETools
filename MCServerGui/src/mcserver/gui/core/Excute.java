package mcserver.gui.core;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextField;

public class Excute {

	private static OutputStream out = null;

	public Excute()
	{
	}

	  public static void excuteCommand(Process process, String com, JTextField text){
		  if(com == "")return;
	  	if(process == null)return;
	  		out = process.getOutputStream();
	  		BufferedOutputStream osp = new BufferedOutputStream(out);
	  		try {
					osp.write((new StringBuilder()).append(com).append(System.getProperty("line.separator")).toString().getBytes());
					osp.flush();
	  		} catch (IOException e2) {
					e2.printStackTrace();
					return;
				}
	  		text.setText("");
	  		return;
	  }

}
