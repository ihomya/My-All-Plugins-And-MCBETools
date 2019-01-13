package itsu.java.mcbeformmaker.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.element.Button;
import itsu.java.mcbeformmaker.element.DropDown;
import itsu.java.mcbeformmaker.element.Input;
import itsu.java.mcbeformmaker.element.Label;
import itsu.java.mcbeformmaker.element.Toggle;
import itsu.java.mcbeformmaker.panel.Form;
import itsu.java.mcbeformmaker.utils.MessagePopup;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class ElementChooser extends JFrame {

    private JPanel base;
    private JList<String> list;
    private JTextArea description;

    private JFrame frame;
    private Form form;

    private final String[] simple = {"Button"};
    private final String[] custom = {"Label", "Input", "Toggle", "DropDown", "StepSlider", "Slider"};

    {
        frame = this;

        base = new JPanel();
        base.setBackground(new Color(33, 33, 33));
        base.setBorder(null);
        base.setLayout(null);
        
        this.setTitle("MCBEFormMaker - ElementChooser");
        this.setResizable(false);
        this.setBounds(0, 0, 600, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(base, BorderLayout.CENTER);

        list = new JList<>();
        list.setBackground(new Color(33, 33, 33));
        list.setForeground(Color.WHITE);
        list.setSelectionBackground(new Color(66, 66, 66));
        list.setBorder(BorderFactory.createEmptyBorder());
        list.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        list.setForeground(Color.WHITE);
        list.setBounds(0, 0, 300, 500);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list.getSelectedValue() == null) return;

                switch(list.getSelectedValue()) {
                    case "Button":
                        description.setText("通常のボタンです。ボタンにはURL指定のアイコンとテキストを指定できます。");
                        break;

                    case "Label":
                        description.setText("テキストを表示するものです。");
                        break;

                    case "Input":
                        description.setText("一行の入力エリアです。タイトルとデフォルトで表示される文字、プレースホルダー（何も入力されていないときに表示される文字）を指定できます。");
                        break;

                    case "Toggle":
                        description.setText("スイッチです。タイトルを指定できます。");
                        break;

                    case "DropDown":
                        description.setText("アコーディオンタイプの選択ボックスです。タイトルとデフォルトの選択値を指定できます。");
                        break;

                    case "StepSlider":
                        description.setText("段階を設定できるスライダーです。タイトルと段階、デフォルトの選択値を指定できます。");
                        break;

                    case "Slider":
                        description.setText("数値の量を指定できるものです。タイトルと最大値、最小値、デフォルトの値を設定できます。");
                        break;
                }
            }
        });

        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && !list.isSelectionEmpty()) {
                    switch(list.getSelectedValue()) {
                        case "Button":
                            form.addElement(new Button());
                            break;
                            
                        case "Label":
                        	form.addElement(new Label());
                            break;

                        case "Input":
                        	form.addElement(new Input());
                            break;

                        case "Toggle":
                        	form.addElement(new Toggle());
                            break;

                        case "DropDown":
                        	form.addElement(new DropDown());
                            break;

                        case "StepSlider":
                            MessagePopup.alert("未実装要素です。ご不便をおかけします。");
                            break;

                        case "Slider":
                        	MessagePopup.alert("未実装要素です。ご不便をおかけします。");
                            break;
                    }
                    frame.setVisible(false);
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        base.add(list);

        description = new JTextArea();
        description.setBackground(new Color(33, 33, 33));
        description.setBorder(null);
        description.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        description.setForeground(Color.WHITE);
        description.setEditable(false);
        description.setBounds(300, 0, 300, 500);
        description.setLineWrap(true);
        base.add(description);

        frame.getContentPane().add(base);
    }

    public void show(int type, Form selectedForm) {
        form = selectedForm;
        switch(type) {
            case Form.TYPE_CUSTOM_FORM:
                showCustom();
                break;

            case Form.TYPE_SIMPLE_FORM:
                showSimple();
                break;

            default:
                throw new IllegalArgumentException();
        }
    }

    public void showSimple() {
        list.setListData(simple);
        this.setVisible(true);
    }

    public void showCustom() {
        list.setListData(custom);
        this.setVisible(true);
    }

}
