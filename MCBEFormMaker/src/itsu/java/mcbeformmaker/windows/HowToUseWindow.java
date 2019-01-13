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
import itsu.java.mcbeformmaker.panel.Form;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class HowToUseWindow extends JFrame {

    private JPanel base;
    private JList<String> list;
    private JTextArea description;

    private JFrame frame;
    private Form form;

    private final String[] contents = {"フォームを作成する", "フォームの説明（共通）", "ModalFormの作成", "CustomFormの作成", "Formの作成", "JSONとしてエクスポート", "実行"};

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

        list = new JList<>(contents);
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

                switch(list.getSelectedIndex()) {
                    case 0:
                        description.setText("メニューバーの[ファイル]から[新規フォームを作成]を選択します。\nフォーム名を入力し、タイプを選択して作成ボタンを押してください。");
                        break;

                    case 1:
                        description.setText("[IDの指定]\n左上のボックスにはフォームIDを入力してください。数値のみ入力できます。\n\n[タイトルの指定]\n上部のタイトルバーに入力してください。");
                        break;

                    case 2:
                        description.setText("テキストと二つのボタンがついたシンプルなフォームです。\n中央部の黒いところにテキスト、そして二つのボタンにテキストを入力してください。");
                        break;

                    case 3:
                        description.setText("多種のエレメントを設置できるフォームです。\n中央部の黒い部分を右クリックすることでエレメントの追加ができます。");
                        break;

                    case 4:
                        description.setText("テキストとボタンのフォームです。\n中央部の黒い部分を右クリックでボタンを追加できます。\nまた、ボタンをクリックすることで画像を挿入できます。");
                        break;

                    case 5:
                        description.setText("作成したフォームをJSONでエクスポートします。\n作成されたJSONは実際にプラグインで使うことができます。");
                        break;

                    case 6:
                        description.setText("CustomFormを作成している場合、「送信」ボタンをクリックすることでどのようなレスポンスが返ってくるのかをテストすることができます。\nなお、ModalFormの場合は上のボタンがtrue、下がfalseとなり、SimpleFormの場合は押されたボタンの順番で、一番上が0となるような値が返ってきます。");
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
                            frame.setVisible(false);
                            break;
                    }
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

    public void showWindow() {
        this.setVisible(true);
    }
}
