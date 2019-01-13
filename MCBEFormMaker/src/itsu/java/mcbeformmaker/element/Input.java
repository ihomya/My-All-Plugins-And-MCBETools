package itsu.java.mcbeformmaker.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import itsu.java.mcbeformmaker.core.Controller;
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

public class Input extends Element {

    private JTextField text;
    private JTextField title;

    private Input input;

    private String con = "";
    private String place = "";
	private boolean isEditable = true;

    public Input() {
        this("インプット - ここにタイトルを入力できます。", "クリックでテキストを編集/右クリックでプレースホルダーを編集できます。");
    }

    public Input(String content, String placeHolder) {
        this.input = this;
        this.place = placeHolder;

        this.setMaximumSize(new Dimension(520, 100));
        this.setLayout(null);
        this.setBackground(Colors.COLOR_FORM_CONTENT);

        title = new JTextField(content);
        title.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        title.setBackground(Colors.COLOR_FORM_CONTENT);
        title.setBorder(null);
        title.setBounds(0, 10, 520, 20);
        this.add(title);

        text = new JTextField(placeHolder) {
                @Override
             public void paintComponent(Graphics g) {
                 g.setColor(Color.BLACK);
                 g.fillRect(0, 0, this.getWidth(), this.getHeight());
                 g.setColor(Colors.COLOR_FORM_CONTENT_SELECTED);
                 g.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);

                 g.setColor(Colors.COLOR_FORM_TEXT);
                 g.fillRect(4, 4, this.getWidth() - 6, this.getHeight() - 6);

                 g.setColor(Colors.COLOR_FORM_BASE);
                 g.fillRect(2, this.getHeight() - 4, this.getWidth() - 4, 2);
                 g.fillRect(this.getWidth() - 4, 4, 2, this.getHeight() - 6);

                 String txt = this.getText();
                 if(txt.length() == 0) {
                     g.setColor(Color.LIGHT_GRAY);
                     g.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
                     g.drawString(place, 10, 40);

                 } else {
                     g.setColor(Color.WHITE);
                     g.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
                     g.drawString(this.getText(), 10, 40);
                 }
             }
        };

        text.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        text.setOpaque(false);
        text.setForeground(Colors.COLOR_FORM_TEXT);
        text.setBounds(0, 30, 520, 70);
        text.addMouseListener(new MouseListener() {

            @Override public void mouseClicked(MouseEvent e) {
                if(e.getButton() == 3 && isEditable) {
                    place = MessagePopup.input("プレースホルダー（何も入力されていないときのテキスト）を入力してください。", place);

                    if(place == null || place.equals("")) {
                        MessagePopup.alert("空白文字は指定できません。");
                        return;
                    }

                    else input.text.repaint();
                }
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        this.add(text);
    }

    @Override
    public String getName() {
        return "input";
    }
    
    @Override
    public Object getResult() {
    	return text.getText();
    }
    
    @Override
    public void setEditable(boolean bool) {
    	text.setEditable(bool);
    	title.setEditable(bool);
    	isEditable  = bool;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("text", title.getText());
        data.put("type", "input");
        data.put("placeholder", place);
        data.put("default", text.getText());
        return data;
    }

}
