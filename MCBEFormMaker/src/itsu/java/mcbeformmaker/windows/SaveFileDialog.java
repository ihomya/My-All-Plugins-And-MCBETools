package itsu.java.mcbeformmaker.windows;

import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.utils.Colors;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class SaveFileDialog {
	
	private static JFileChooser fileChooser;
	private static FileNameExtensionFilter filter;
	
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		fileChooser = new JFileChooser();
		fileChooser.setBackground(Colors.BACKGROUND);
		fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	public static File show(JComponent component, String type) {
		fileChooser.setDialogTitle("MCBEFormMaker 保存 - " + Controller.getNowTab().getForm().getTitle());
		if(filter != null) fileChooser.removeChoosableFileFilter(filter);
		fileChooser.addChoosableFileFilter(filter = new FileNameExtensionFilter(type + "ファイル", type));
		
		int selected = fileChooser.showSaveDialog(component);
		File file = null;
		
		if (selected == JFileChooser.APPROVE_OPTION) {
		  file = fileChooser.getSelectedFile();
		}
		
		return file;
	}

}
