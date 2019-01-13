package itsu.java.mcbeformmaker.core;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import itsu.java.mcbeformmaker.panel.BasePanel;
import itsu.java.mcbeformmaker.panel.MenuBar;
import itsu.java.mcbeformmaker.utils.Version;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class MCBEFormMaker {
	
    private static JFrame mainFrame;
	
	public static void main(String[] args) {
    	Controller.initUISettings();
        Controller.initData();
        Controller.initUI();
        initFrame();
	}
	
    private static void initFrame() {
        mainFrame = new JFrame();
        mainFrame.setTitle("MCBEFormMaker - " + Version.VERSION);
        mainFrame.setResizable(false);
        mainFrame.setBounds(0, 0, 1000, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(new MenuBar());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(new BasePanel(), BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
    
    public static JFrame getFrame() {
    	return mainFrame;
    }

}
