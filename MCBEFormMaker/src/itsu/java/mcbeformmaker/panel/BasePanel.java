package itsu.java.mcbeformmaker.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import itsu.java.mcbeformmaker.core.Controller;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class BasePanel extends JPanel {
	
	private SimpleTabbedPane tab;
	
	public BasePanel() {
		this.setBackground(new Color(33, 33, 33));
		this.setLayout(new BorderLayout());
		initTab();
	}
	
	private void initTab() {
		tab = new SimpleTabbedPane();
		tab.setBackground(Color.RED);
		this.add(tab, BorderLayout.CENTER);
		Controller.setTab(tab);
	}

}
