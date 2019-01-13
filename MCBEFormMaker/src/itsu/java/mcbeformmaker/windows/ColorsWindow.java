package itsu.java.mcbeformmaker.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import itsu.java.mcbeformmaker.core.Controller;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class ColorsWindow extends JFrame {
	

    private JPanel base;

    public ColorsWindow() {
        base = new JPanel();
        base.setBackground(new Color(33, 33, 33));
        base.setBorder(null);
        base.setLayout(null);
        
        int height = 0;
        for(int i = 0;i < 20;i++) {
        	JLabel label = new JLabel();
        	label.setBounds(10, 10 + height, 500, 20);
        	label.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        	
        	switch(i) {
        		case 0:
        			label.setText("§0 - Black");
        			label.setForeground(Color.BLACK);
        			break;
        			
        		case 1:
        			label.setText("§1 - DarkBlue");
        			label.setForeground(Color.BLUE);
        			break;
        			
        		case 2:
        			label.setText("§2 - DarkGreen");
        			label.setForeground(new Color(0, 100, 0));
        			break;
        			
        		case 3:
        			label.setText("§3 - DarkAqua");
        			label.setForeground(new Color(0, 100, 100));
        			break;
        			
        		case 4:
        			label.setText("§4 - DarkRed");
        			label.setForeground(Color.RED);
        			break;
        			
        		case 5:
        			label.setText("§5 - Purple");
        			label.setForeground(new Color(100, 0, 100));
        			break;
        			
        		case 6:
        			label.setText("§6 - Gold");
        			label.setForeground(new Color(100, 100, 0));
        			break;
        			
        		case 7:
        			label.setText("§7 - Gray");
        			label.setForeground(Color.GRAY);
        			break;
        			
        		case 8:
        			label.setText("§8 - DarkGray");
        			label.setForeground(Color.DARK_GRAY);
        			break;
        			
        		case 9:
        			label.setText("§9 - Blue");
        			label.setForeground(new Color(0, 100, 200));
        			break;
        			
        		case 10:
        			label.setText("§a - Green");
        			label.setForeground(Color.GREEN);
        			break;
        			
        		case 11:
        			label.setText("§b - Aqua");
        			label.setForeground(Color.CYAN);
        			break;
        			
        		case 12:
        			label.setText("§c - Red");
        			label.setForeground(new Color(200, 50, 50));
        			break;
        			
        		case 13:
        			label.setText("§d - LightPurple");
        			label.setForeground(new Color(200, 0, 200));
        			break;
        			
        		case 14:
        			label.setText("§e - Yellow");
        			label.setForeground(Color.YELLOW);
        			break;
        			
        		case 15:
        			label.setText("§f - White");
        			label.setForeground(Color.WHITE);
        			break;
        			
        		case 16:
        			label.setText("§k - Obfuscated（ランダムで変化する文字）");
        			label.setForeground(Color.WHITE);
        			break;
        			
        		case 17:
        			label.setText("§l - Bold（太字）");
        			label.setForeground(Color.WHITE);
        			break;
        			
        		case 18:
        			label.setText("§o - Italic（斜体）");
        			label.setForeground(Color.WHITE);
        			break;
        			
        		case 19:
        			label.setText("§r - Reset（文字装飾をリセット）");
        			label.setForeground(Color.WHITE);
        			break;
        	}
        	
        	base.add(label);
        	height += 20;
        }
        
        this.setTitle("MCBEFormMaker - 色コード一覧");
        this.setResizable(false);
        this.setBounds(0, 0, 400, 460);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(base, BorderLayout.CENTER);
        this.getContentPane().add(base);
    }

    public void showWindow() {
        this.setVisible(true);
    }
}
