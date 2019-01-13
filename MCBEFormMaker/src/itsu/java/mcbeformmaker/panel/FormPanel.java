package itsu.java.mcbeformmaker.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

/**
 * MCBEFormMaker
 *
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 *
 * @author itsu
 *
 */

public class FormPanel extends JPanel {

    private Form form;

    public FormPanel(int type, String title) {
        super();
        this.setBackground(new Color(33, 33, 33));
        this.setLayout(null);

        form = new Form(type, title);
        form.setBounds(0, 0, 1000, 700);
        this.add(form, BorderLayout.CENTER);
    }

    public Form getForm() {
        return form;
    }
}
