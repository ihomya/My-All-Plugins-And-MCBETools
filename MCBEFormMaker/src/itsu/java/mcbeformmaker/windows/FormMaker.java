package itsu.java.mcbeformmaker.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import itsu.java.mcbeformmaker.core.Controller;
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

public class FormMaker {

    private static JPanel base;
    private static JLabel nameTitle;
    private static JTextField name;
    private static JLabel comboTitle;
    private static JComboBox<String> combo;
    private static JButton button;

    private static JFrame frame;

    private static final String[] simple = {"Button"};
    private static final String[] custom = {"Label", "Input", "Toggle", "DropDown", "StepSlider", "Slider"};

    static {
        base = new JPanel();
        base.setBackground(new Color(33, 33, 33));
        base.setBorder(null);
        base.setLayout(null);
        
        frame = new JFrame();
        frame.setTitle("MCBEFormMaker - ElementChooser");
        frame.setResizable(false);
        frame.setBounds(0, 0, 600, 220);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(base, BorderLayout.CENTER);

        nameTitle = new JLabel("フォーム名");
        nameTitle.setForeground(Color.WHITE);
        nameTitle.setBounds(10, 10, 580, 20);
        base.add(nameTitle);

        name = new JTextField();
        name.setBorder(new LineBorder(new Color(198, 198, 198)));
        name.setBounds(10, 30, 580, 20);
        name.setBackground(new Color(33, 33, 33));
        name.setForeground(Color.WHITE);
        base.add(name);

        comboTitle = new JLabel("フォームタイプ");
        comboTitle.setForeground(Color.WHITE);
        comboTitle.setBounds(10, 70, 580, 20);
        base.add(comboTitle);

        combo = new JComboBox<>(new String[]{"modal", "custom_form", "form"});
        combo.setBorder(new LineBorder(new Color(198, 198, 198)));
        combo.setBounds(10, 90, 580, 20);
        combo.setBackground(new Color(33, 33, 33));
        combo.setForeground(Color.WHITE);
        base.add(combo);

        button = new JButton("作成");
        button.setBorder(new LineBorder(new Color(198, 198, 198)));
        button.setBounds(10, 150, 580, 20);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText().equals("")) {
                    MessagePopup.alert("フォーム名を設定してください。");
                    return;
                }
               	
                int type = 0;
                
                switch((String) combo.getSelectedItem()) {
	                case "modal":
	                	type = Form.TYPE_MODAL_FORM;
	                	break;
	                	
	                case "custom_form":
	                	type = Form.TYPE_CUSTOM_FORM;
	                	break;
	                	
	                case "form":
	                	type = Form.TYPE_SIMPLE_FORM;
	                	break;
                }
                
                Controller.addTab(type, name.getText());
                
                frame.setVisible(false);
                name.setText("");
                combo.setSelectedIndex(0);
            }
        });
        base.add(button);

        frame.getContentPane().add(base);
    }

    public static void show() {
        frame.setVisible(true);
    }
}
