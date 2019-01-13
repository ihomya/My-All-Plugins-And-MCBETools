package itsu.java.msgfe.propedit.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import itsu.java.msgfe.components.BlackButton;
import itsu.java.msgfe.core.Controller;
import itsu.java.msgfe.plugin.Plugin;
import itsu.java.msgfe.ui.BlackScrollBarUI;
import itsu.java.msgfe.utils.Colors;
import itsu.java.msgfe.utils.IOUtils;

public class PropertyEditor extends Plugin {
	
	private JPanel panel;
	
	private JList<String> list;
	private JScrollPane pane;
	private JPanel paneBase;
	
	private JTextArea area;
	private BlackButton button;
	
	private List<String> keys;
	private String keyMemory;
	
	public PropertyEditor() {
		initUI();
		initGUI();
	}
	
	private void initGUI() {
		panel = new JPanel();
		panel.setBackground(Colors.BACKGROUND);
		panel.setBorder(null);
		panel.setLayout(null);
		
		keys = new ArrayList<>();
		for(String str : Controller.getServerInfo().keySet()) {
			keys.add(str);
		}
		
		list = new JList<>();
		list.setListData((String[]) keys.toArray(new String[0]));
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!area.getText().equals("")) {
					Controller.getServerInfo().put(keyMemory, area.getText());
				}
				
				area.setText(Controller.getServerInfo().get(list.getSelectedValue()));
				keyMemory = list.getSelectedValue();
			}
		});
		
		pane = new JScrollPane(list);
		pane.setBorder(null);
		pane.getVerticalScrollBar().setUI(new BlackScrollBarUI());
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setPreferredSize(new Dimension(Controller.getDataObject().getWindowWidth() / 2, Controller.getDataObject().getWindowHeight()));
		
		paneBase = new JPanel();
		paneBase.setBackground(Colors.BACKGROUND);
		paneBase.setBorder(null);
		paneBase.setLayout(new BorderLayout());
		paneBase.setBounds(0, 0, Controller.getDataObject().getWindowWidth() / 2, Controller.getDataObject().getWindowHeight());
		paneBase.add(pane, BorderLayout.CENTER);
		panel.add(paneBase);
		
		area = new JTextArea();
		area.setBackground(Colors.BACKGROUND);
		area.setForeground(Color.WHITE);
		area.setSelectedTextColor(Colors.TEXTAREA_SELECTED_FOREGROUND);
		area.setSelectionColor(Colors.TEXTAREA_SELECTED_BACKGROUND);
		area.setCaretColor(Colors.TEXTAREA_CARET);
		area.setBorder(new LineBorder(new Color(11, 11, 11)));
		area.setBounds(Controller.getDataObject().getWindowWidth() / 2, 0, Controller.getDataObject().getWindowWidth() / 2, (Controller.getDataObject().getWindowHeight() / 3) * 2);
		area.setLineWrap(true);
		panel.add(area);
		
		button = new BlackButton("保存", "Save") {
			@Override
			public void onClick() {
				try {
					String str = "#Properties Config file\r\n#" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "\r\n";
					for(String data : Controller.getServerInfo().keySet()) {
						str += data + "=" + Controller.getServerInfo().get(data) + "\r\n";
					}
					IOUtils.writeFile(new File("server.properties"), str);
					Controller.initServerInfo();
				} catch(IOException e) {
					Controller.alert("ファイルの書き込みに失敗しました。");
				}
			}
		};
		button.setBounds(Controller.getDataObject().getWindowWidth() / 2, (Controller.getDataObject().getWindowHeight() / 3) * 2, Controller.getDataObject().getWindowWidth() / 2, Controller.getDataObject().getWindowHeight() / 3 - 115);
		panel.add(button);
		
		Controller.addTab("PropertyEditor", panel);
	}
	
	private void initUI() {
        UIManager.put("List.background", new Color(33, 33, 33));
        UIManager.put("List.foreground", Color.WHITE);
        UIManager.put("List.selectionBackground", new Color(66, 66, 66));
        UIManager.put("List.selectionForeground", Color.WHITE);
        UIManager.put("List.focusCellHighlightBorder", new Color(33, 33, 33));
        UIManager.put("List.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
	}

}
