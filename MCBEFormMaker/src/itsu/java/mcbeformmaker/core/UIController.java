package itsu.java.mcbeformmaker.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class UIController {

    public UIController(){
        UIManager.put("Menu.background", new Color(33, 33, 33));
        UIManager.put("Menu.selectionBackground", new Color(66, 66, 66));
        UIManager.put("Menu.foreground", Color.WHITE);
        UIManager.put("Menu.selectionForeground", Color.WHITE);
        UIManager.put("Menu.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("MenuItem.background", new Color(33, 33, 33));
        UIManager.put("MenuItem.foreground", Color.WHITE);
        UIManager.put("MenuItem.selectionBackground", new Color(66, 66, 66));
        UIManager.put("MenuItem.selectionForeground", Color.WHITE);
        UIManager.put("MenuItem.preferredSize", new Dimension(300, 30));
        UIManager.put("MenuItem.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("Button.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("Button.focus", new Color(33, 33, 33));
        UIManager.put("Button.background", new Color(0, 0, 0));
        UIManager.put("Button.select", new Color(100, 100, 100));
        UIManager.put("TabbedPane.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("List.selectionBackground", new Color(66, 66, 66));
        UIManager.put("List.selectionForeground", Color.WHITE);
        UIManager.put("List.focusCellHighlightBorder", new Color(33, 33, 33));
        UIManager.put("List.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("TextField.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("TextField.caretForeground", new Color(0, 128, 255));
        UIManager.put("TextField.selectionBackground", new Color(0, 128, 255));
        UIManager.put("TextField.selectionForeground", Color.WHITE);
        UIManager.put("TextArea.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("TextArea.caretForeground", new Color(0, 128, 255));
        UIManager.put("TextArea.selectionBackground", new Color(0, 128, 255));
        UIManager.put("TextArea.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("ComboBox.focus", new Color(33, 33, 33));
        UIManager.put("ComboBox.selectionBackground", new Color(66, 66, 66));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("Separator.foreground", Color.LIGHT_GRAY);
        UIManager.put("Label.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("CheckBox.font", new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 12));
        UIManager.put("SplitPaneDivider.border", BorderFactory.createEmptyBorder());
    }

}
