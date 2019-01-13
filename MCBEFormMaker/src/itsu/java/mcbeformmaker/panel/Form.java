package itsu.java.mcbeformmaker.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.core.MCBEFormMaker;
import itsu.java.mcbeformmaker.element.Button;
import itsu.java.mcbeformmaker.element.Element;
import itsu.java.mcbeformmaker.ui.MCBEFormScrollBarUI;
import itsu.java.mcbeformmaker.utils.Colors;
import itsu.java.mcbeformmaker.utils.MessagePopup;
import itsu.java.mcbeformmaker.utils.PopupMenu;

/**
 * MCBEFormMaker
 *
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 *
 * @author itsu
 *
 */

public class Form extends JPanel {

    public static final int TYPE_MODAL_FORM = 0;
    public static final int TYPE_CUSTOM_FORM = 1;
    public static final int TYPE_SIMPLE_FORM = 2;

    /*共通部品*/
    private JTextField titleBar;
    private JTextField idBar;
    private JPanel base;
    private JPanel content;
    private JScrollPane pane;
    private BoxLayout layout;

    /*Modal部品*/
    private Button btn1;
    private Button btn2;
    private JTextArea area;

    /*Custom*/
    private Button send;

    private int type;
    private String contentText;
    private List<Element> contents = new ArrayList<>();

    public Form(int type, String title) {
        super();
        this.setLayout(null);
        this.setSize((int) MCBEFormMaker.getFrame().getBounds().getWidth(), (int) MCBEFormMaker.getFrame().getBounds().getHeight());

        this.type = type;

        if(type == TYPE_MODAL_FORM || type == TYPE_CUSTOM_FORM) {
            contentText = "テキストを入力...";
        }

        initGUI();
        titleBar.setText(title + ": タイトルを入力");
    }

    private void initGUI() {
        /*共通部分*/
        /*タイトルバー*/
        titleBar = new JTextField();
        titleBar.setBorder(null);
        titleBar.setBackground(Colors.COLOR_FORM_BASE);
        titleBar.setForeground(Colors.COLOR_FORM_TEXT);
        titleBar.setText("タイトル");
        titleBar.setHorizontalAlignment(JTextField.CENTER);
        titleBar.setBounds(280, 35, 500, 30);
        titleBar.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 18));
        this.add(titleBar);

        /*IDバー*/
        idBar = new JTextField();
        idBar.setBorder(new LineBorder(Colors.COLOR_FORM_TEXT));
        idBar.setBackground(Colors.COLOR_FORM_BASE);
        idBar.setForeground(Colors.COLOR_FORM_TEXT);
        idBar.setText("100");
        idBar.setBounds(220, 35, 50, 30);
        idBar.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        this.add(idBar);

        /*SimpleForm/Form用エレメントパネル*/
        content = new JPanel();
        layout = new BoxLayout(content, BoxLayout.Y_AXIS);
        content.setLayout(layout);
        content.setBackground(Colors.COLOR_FORM_CONTENT);
        content.setComponentPopupMenu(new PopupMenu(this));

        if(type == TYPE_CUSTOM_FORM) {
            send = new Button("\n送信", false, false);
            MouseListener listener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton() == 1) {
                        try {
                            List<Element> elements = getElements();

                            StringBuffer buf = new StringBuffer();
                            buf.append("[");

                            for(Element element : elements) {
                                buf.append(element.getResult() + ", ");
                            }

                            String str = buf.toString();
                            str = str.substring(0, str.length() - 2);
                            str += "]";

                            MessagePopup.information("レスポンス: \n" + str);
                        } catch(StringIndexOutOfBoundsException ex) {
                            MessagePopup.alert("エレメントを追加してください。");
                        }
                    }
                }

                @Override public void mouseEntered(MouseEvent e) {}
                @Override public void mouseExited(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseReleased(MouseEvent e) {}
            };
            send.addButtonListener(listener);
            content.add(Box.createRigidArea(new Dimension(10, 10)));
            content.add(send);
        }

        /*共通スクロールバー*/
        pane = new JScrollPane(content);
        pane.getVerticalScrollBar().setUI(new MCBEFormScrollBarUI());
        pane.setPreferredSize(new Dimension(220, 75));
        pane.setBackground(Colors.COLOR_FORM_CONTENT);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.getVerticalScrollBar().setUnitIncrement(25);

        /*共通ベースパネル*/
        base = new JPanel();
        base.setBackground(Colors.COLOR_FORM_CONTENT);
        base.setLayout(new BorderLayout());

        if(type == TYPE_CUSTOM_FORM || type == TYPE_SIMPLE_FORM) {
            content.setPreferredSize(new Dimension(480, 480));
            base.setBounds(220, 75, 560, 480);

        } else {
            content.setPreferredSize(new Dimension(480, 280));
            base.setBounds(220, 75, 560, 280);
        }

        base.add(pane, BorderLayout.CENTER);
        this.add(base);

        /*Modal用テキストエリア*/
        if(type == TYPE_MODAL_FORM) {
            area = new JTextArea(contentText);
            area.setBackground(Colors.COLOR_FORM_CONTENT);
            area.setForeground(Color.WHITE);
            area.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
            area.setLineWrap(true);
            pane.setViewportView(area);

            btn1 = new Button("\n送信ボタン1 - ここにテキストを入力できます。", false, true);
            btn1.setBounds(220, 375, 560, 70);
            this.add(btn1);

            btn2 = new Button("\n送信ボタン2 - ここにテキストを入力できます。", false, true);
            btn2.setBounds(220, 465, 560, 70);
            this.add(btn2);

        } else {
            pane.setViewportView(content);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Colors.COLOR_FORM_TEXT);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Colors.COLOR_FORM_BASE);
        g.fillRoundRect(200, 25, 600, 550, 3, 3);
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return titleBar.getText();
    }

    public int getId() {
        return Integer.parseInt(idBar.getText());
    }

    public void addButtonMouseListener(MouseListener listener) {
        send.addMouseListener(listener);
    }

    public void removeButtonMouseListener(MouseListener listener) {
        send.removeMouseListener(listener);
    }

    public List<Element> getElements() {
        return contents;
    }

    public void setEditable(boolean bool) {
        idBar.setEditable(bool);
        titleBar.setEditable(bool);
        for(Element e : contents) {
            e.setEditable(false);
        }
    }

    public void addElement(Element element) {
        element.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        if(type == TYPE_CUSTOM_FORM) content.remove(send);

        if(contents.size() == 0) content.add(Box.createRigidArea(new Dimension(10, 30)));
        else if(!(element instanceof Button)) content.add(Box.createRigidArea(new Dimension(10, 10)));
        else content.add(Box.createRigidArea(new Dimension(10, 0)));

        int height = contents.size() * 10 + 120;
        for(Element e : contents) {
            height += e.getMaximumSize().getHeight();
        }
        height += element.getMaximumSize().getHeight();

        if(height > content.getPreferredSize().getHeight()) {
            content.setPreferredSize(new Dimension(480, height + 30));
            content.revalidate();
            content.repaint();
        }

        content.add(element);

        if(type == TYPE_CUSTOM_FORM) {
            content.add(Box.createRigidArea(new Dimension(10, 10)));
            content.add(send);
        }

        content.repaint();

        this.contents.add(element);
    }

    @SuppressWarnings("unchecked")
    public String toJson() {
        try {
            int tabCount = 0;
            StringBuffer bf = new StringBuffer();
            bf.append(getTab(tabCount) + "{\n");
            tabCount++;
            bf.append(getTab(tabCount) + "\"type\": \"" + getTypeText() + "\",\n");
            bf.append(getTab(tabCount) + "\"title\": \"" + titleBar.getText() + "\",\n");

            if(type == TYPE_MODAL_FORM) {
                 bf.append(getTab(tabCount) + "\"content\": \"" + area.getText() + "\",\n");
                 bf.append(getTab(tabCount) + "\"button1\": \"" + btn1.getText() + "\",\n");
                 bf.append(getTab(tabCount) + "\"button2\": \"" + btn2.getText() + "\"\n");
                 tabCount--;
                 bf.append(getTab(tabCount) + "}");

            } else if(type == TYPE_CUSTOM_FORM) {
                bf.append(getTab(tabCount) + "\"content\": " + "[\n");
                tabCount++;

                List<Map<String, Object>> elementData = new ArrayList<>();
                for(Element element : contents) {
                    elementData.add(element.getData());
                }

                for(Map<String, Object> map : elementData) {
                    List<String> keys = new ArrayList<>();
                    for(String str : map.keySet()) {
                        keys.add(str);
                    }

                    List<Object> values = new ArrayList<>();
                    for(Object obj : map.values()) {
                        values.add(obj);
                    }

                    bf.append(getTab(tabCount) + "{\n");
                    tabCount++;

                    for(int i = 0;i < map.size();i++) {
                        Object val = values.get(i);
                        if(val instanceof String) {
                            bf.append(getTab(tabCount) + "\"" + keys.get(i) + "\": " + "\"" + val + "\",\n");

                        } else if(val instanceof List) {
                            bf.append(getTab(tabCount) + "\"" + keys.get(i) + "\": [\n");
                            tabCount++;

                            for(String str : (List<String>) val) {
                                bf.append(getTab(tabCount) + "\"" + str + "\",\n");
                            }

                            tabCount--;
                            bf.append(getTab(tabCount) + "],\n");

                        } else {
                            bf.append(getTab(tabCount) + "\"" + keys.get(i) + "\": " + val + ",\n");
                        }
                    }

                    tabCount--;
                    bf.append(getTab(tabCount) + "},\n");
                }

                tabCount--;
                bf.append(getTab(tabCount) + "],\n");
                bf.append(getTab(tabCount) + "\"button1\": \"" + "\",\n");
                bf.append(getTab(tabCount) + "\"button2\": \"" + "\"\n");
                tabCount--;
                bf.append(getTab(tabCount) + "}");

            } else if(type == TYPE_SIMPLE_FORM) {
                bf.append(getTab(tabCount) + "\"buttons\": " + "[\n");
                tabCount++;

                List<Map<String, Object>> elementData = new ArrayList<>();
                for(Element element : contents) {
                    elementData.add(element.getData());
                }

                for(Map<String, Object> map : elementData) {
                	List<String> keys = new ArrayList<>();
                    for(String str : map.keySet()) {
                        keys.add(str);
                    }

                    List<Object> values = new ArrayList<>();
                    for(Object obj : map.values()) {
                        values.add(obj);
                    }

                    bf.append(getTab(tabCount) + "{\n");
                    tabCount++;
                    
                    for(int i = 0;i < map.size();i++) {
                         bf.append(getTab(tabCount) + "\"" + keys.get(i) + "\": " + "\"" + values.get(i) + "\",\n");
                    }
                    
                    tabCount--;
                    bf.append(getTab(tabCount) + "},\n");
                }

                tabCount--;
                bf.append(getTab(tabCount) + "]\n");
                tabCount--;
                bf.append(getTab(tabCount) + "}");
            }

            return bf.toString();
        } catch(Exception e) {
            return "";
        }
    }

    public String toJsonUseGson() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", getTypeText());
        data.put("title", titleBar.getText());

        if(type == TYPE_MODAL_FORM) {
            data.put("content", area.getText());
            data.put("button1", btn1.getText());
            data.put("button2", btn2.getText());

        } else if(type == TYPE_CUSTOM_FORM) {
            List<Map<String, Object>> elementData = new ArrayList<>();
            for(Element element : contents) {
                elementData.add(element.getData());
            }
            data.put("content", elementData);
            data.put("button1", "");
            data.put("button2", "");

        } else if(type == TYPE_SIMPLE_FORM) {
            List<Map<String, Object>> buttonData = new ArrayList<>();
            for(Element element : contents) {
                buttonData.add(element.getData());
            }
            data.put("buttons", buttonData);
        }
        return new Gson().toJson(data);
    }

    private String getTab(int count) {
        String result = "";
        for(int i=0;i < count;i++) {
            result += "\t";
        }
        return result;
    }

    private String getTypeText() {
        switch(type) {
            case TYPE_MODAL_FORM:
                return "modal";

            case TYPE_CUSTOM_FORM:
                return "custom_form";

            case TYPE_SIMPLE_FORM:
                return "form";
        }
        return "";
    }

}
