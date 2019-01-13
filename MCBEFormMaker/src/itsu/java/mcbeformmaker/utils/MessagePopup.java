package itsu.java.mcbeformmaker.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import itsu.java.mcbeformmaker.ui.MCBEFormScrollBarUI;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class MessagePopup {

    public MessagePopup(){

    }

    public static void information(String message){
    	Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(new JFrame(), message, "情報", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void alert(String message){
    	Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(new JFrame(), message, "警告", JOptionPane.WARNING_MESSAGE);
    }
    
    public static String input(String message, String defaultText) {
    	return JOptionPane.showInputDialog(new JFrame(), message, defaultText);
    }

    public static void exception(String message, Exception e){
        JFrame frame = new JFrame("エラー");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);


        JLabel label = new JLabel(message);
        label.setIcon(UIManager.getIcon ("OptionPane.errorIcon"));

        JTextArea area = new JTextArea();
        JScrollPane pane = new JScrollPane(area);

        pane.getHorizontalScrollBar().setUI(new MCBEFormScrollBarUI());
        pane.getVerticalScrollBar().setUI(new MCBEFormScrollBarUI());
        pane.setPreferredSize(new Dimension(400, 250));
        pane.setBorder(new LineBorder(Color.GRAY));

        String trace = e.getClass().getName() + ": " + e.getMessage() + "\n";

        for(StackTraceElement element : e.getStackTrace()) {
            trace += "    " + element.toString() + "\n";
        }

        area.setText(trace);
        area.setEditable(false);

        frame.getContentPane().add(label, BorderLayout.NORTH);
        frame.getContentPane().add(pane, BorderLayout.CENTER);

        frame.setVisible(true);
        Toolkit.getDefaultToolkit().beep();
    }

    public static void error(String message) {
    	Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(new JFrame(), message, "エラー", JOptionPane.ERROR_MESSAGE);
    }

}
