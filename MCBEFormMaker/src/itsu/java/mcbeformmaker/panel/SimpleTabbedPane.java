package itsu.java.mcbeformmaker.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import itsu.java.mcbeformmaker.ui.TabbedPaneUI;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class SimpleTabbedPane extends JTabbedPane {

    public SimpleTabbedPane() {
        super();
        
        this.setUI(new TabbedPaneUI());
    }

    @Override
    public void addTab(String title, final Component content) {
        JPanel tab = new JPanel(new BorderLayout());
        tab.setOpaque(false);

        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setHorizontalTextPosition(CENTER);
        label.setFont(new Font("メイリオ", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tab.add(label,  BorderLayout.WEST);
        tab.setBorder(BorderFactory.createEmptyBorder(6, 1, 1, 1));

        super.addTab(null, content);

        setTabComponentAt(getTabCount() - 1, tab);
    }
}
