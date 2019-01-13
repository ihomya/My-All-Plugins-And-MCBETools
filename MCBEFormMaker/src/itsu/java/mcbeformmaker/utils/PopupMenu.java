package itsu.java.mcbeformmaker.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.panel.Form;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class PopupMenu extends JPopupMenu {
	
	private JMenuItem add;
	private JMenuItem remove;
	
	private Form form;
	
	public PopupMenu(Form form) {
		this.setBorder(null);
		
		this.form = form;
		
		initItems();
	}

	private void initItems() {
		add = new JMenuItem("追加                           ");
		add.setBorder(null);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.showElementChooser(form.getType(), form);
			}
		});
		
		remove = new JMenuItem("すべて削除 ");
		remove.setBorder(null);
		
		this.add(add);
		this.add(remove);
	}

}
