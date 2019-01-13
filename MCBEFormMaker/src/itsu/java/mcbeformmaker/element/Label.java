package itsu.java.mcbeformmaker.element;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.ui.MCBEFormScrollBarUI;
import itsu.java.mcbeformmaker.utils.Colors;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class Label extends Element {

    private JTextArea text;
    private JScrollPane pane;
    private JPanel base;

    public Label() {
        this("ラベル - ここにテキストを入力できます。\n実際には枠とスクロールバーは表示されません。");
    }

    public Label(String content) {
        this.setMaximumSize(new Dimension(520, 210));
        this.setLayout(null);
        this.setBackground(Colors.COLOR_FORM_CONTENT);
        
        base = new JPanel();
        base.setBounds(0, 10, 520, 200);
        base.setLayout(new BorderLayout());
        this.add(base);

        text = new JTextArea(content);
        text.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        text.setForeground(Color.WHITE);
        text.setBackground(Colors.COLOR_FORM_CONTENT);
        text.setLineWrap(true);
        
        pane = new JScrollPane(text);
        pane.setBorder(new LineBorder(Colors.COLOR_FORM_TEXT));
        pane.getVerticalScrollBar().setUnitIncrement(25);
        pane.getVerticalScrollBar().setUI(new MCBEFormScrollBarUI());
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setBackground(Colors.COLOR_FORM_CONTENT);
        pane.setPreferredSize(new Dimension(520, 200));
        base.add(pane, BorderLayout.CENTER);
    }

	@Override
	public String getName() {
		return "label";
	}
	
    @Override
    public Object getResult() {
    	return null;
    }
    
    @Override
    public void setEditable(boolean bool) {
    	text.setEditable(bool);
    }

	@Override
	public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("text", text.getText());
        data.put("label", "dropdown");
        return data;
	}

}
