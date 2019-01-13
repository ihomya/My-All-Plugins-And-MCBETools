package itsu.java.mcbeformmaker.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.panel.Form;
import itsu.java.mcbeformmaker.ui.BlackScrollBarUI;
import itsu.java.mcbeformmaker.utils.Colors;
import itsu.java.mcbeformmaker.utils.MessagePopup;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class JsonChecker extends JFrame {
	

    private JPanel base;
    private JTextArea text;
    private JScrollPane pane;

    public JsonChecker() {
        base = new JPanel();
        base.setBackground(new Color(33, 33, 33));
        base.setBorder(null);
        base.setLayout(new BorderLayout());
        
        text = new JTextArea();
        text.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        text.setForeground(Color.WHITE);
        text.setBackground(Colors.COLOR_FORM_CONTENT);
        text.setEditable(false);
        
        pane = new JScrollPane(text);
        pane.setBorder(new LineBorder(Colors.COLOR_FORM_TEXT));
        pane.getVerticalScrollBar().setUnitIncrement(25);
        pane.getVerticalScrollBar().setUI(new BlackScrollBarUI());
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.getHorizontalScrollBar().setUnitIncrement(25);
        pane.getHorizontalScrollBar().setUI(new BlackScrollBarUI());
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setBackground(Colors.COLOR_FORM_CONTENT);
        pane.setPreferredSize(new Dimension(800, 500));
        base.add(pane, BorderLayout.CENTER);
        
        this.setTitle("MCBEFormMaker - Jsonビューワ");
        this.setResizable(false);
        this.setBounds(0, 0, 800, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(base, BorderLayout.CENTER);
        this.getContentPane().add(base);
    }

    public void showWindow() {
    	if(Controller.getNowTab() == null) {
            MessagePopup.alert("プロジェクトを作成してください。");
            return;
        }
    	
        Form form = Controller.getNowTab().getForm();
        text.setText(form.toJson());
        
        this.setVisible(true);
    }
}
