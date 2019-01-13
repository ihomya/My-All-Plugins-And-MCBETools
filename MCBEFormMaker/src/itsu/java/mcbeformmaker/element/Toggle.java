package itsu.java.mcbeformmaker.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicButtonUI;

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

public class Toggle extends Element {
	
    private JTextField title;
    private JRadioButton button;

    private Toggle toggle;
    
    private boolean bool = false;
	private boolean isEditable = true;

    public Toggle() {
        this("トグル - ここにタイトルを入力できます。", "クリックでテキストを編集/右クリックでプレースホルダーを編集できます。");
    }

    public Toggle(String titleText, String content) {
        this.toggle = this;

        this.setMaximumSize(new Dimension(520, 100));
        this.setLayout(null);
        this.setBackground(Colors.COLOR_FORM_CONTENT);
        this.add(new JPanel() {
        	@Override
        	public Rectangle getBounds() {
        		return new Rectangle(0, 0, 10, 10);
        	}
        });

        title = new JTextField(titleText);
        title.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        title.setBackground(Colors.COLOR_FORM_CONTENT);
        title.setBorder(null);
        title.setBounds(0, 10, 520, 20);
        this.add(title);
        
        button = new JRadioButton("右クリックでテキストを編集できます。");
        button.setUI(new MCBEFormSwitchUI());
        button.setBounds(0, 30, 520, 40);
        button.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        button.addMouseListener(new MouseListener() {

            @Override public void mouseClicked(MouseEvent e) {
                String text = button.getText();
                
                if(e.getButton() == 3 && isEditable) {
                    text = MessagePopup.input("トグルに表示するテキストを入力してください。", text);
                    
                    if(text == null || text.equals("")) {
                        MessagePopup.alert("空白文字は指定できません。");
                        return;
                        
                    } else {
                    	button.setText(text);
                    	button.repaint();
                    }
                }

            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        
        button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bool = bool ? false : true;
				System.out.println(bool);
			}
        });
        this.add(button);
    }

	@Override
	public String getName() {
		return "toggle";
	}
	
    @Override
    public Object getResult() {
    	return bool;
    }
    
    @Override
    public void setEditable(boolean bool) {
    	title.setEditable(bool);
    	isEditable  = bool;
    }

	@Override
	public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("text", title.getText());
        data.put("type", "toggle");
        data.put("defaultValue", bool);
        return data;
	}
	
	class MCBEFormSwitchUI extends BasicButtonUI {
		@Override
		public void paint(Graphics g, JComponent c){
			AbstractButton b = (AbstractButton) c;
	        ButtonModel model = b.getModel();
	        
	        clearTextShiftOffset();
	        
	        String txt = b.getText();
	        Graphics2D g2 = (Graphics2D)g;
	        
	        //Button Base
	        g2.setColor(Colors.COLOR_FORM_CONTENT);
	        g2.fillRect(0, 0, 520, 40);
	        
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 4, 70, 32);
	        
	        g2.setColor(Colors.COLOR_FORM_CONTENT_SELECTED);
	        g2.fillRect(2, 6, 66, 28);
	        
	        g2.setColor(Color.GRAY);
	        g2.fillRect(32, 12, 4, 16);
	        
	        //Button
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 0, 24, 40);
	        
	        g2.setColor(Colors.COLOR_FORM_BASE);
	        g2.fillRect(2, 2, 20, 36);
	        
	        g2.setColor(Colors.COLOR_FORM_CONTENT);
	        g2.fillRect(20, 4, 2, 34);
	        g2.fillRect(2, 36, 20, 2);
	        
	        g2.setColor(Color.WHITE);
	        g2.fillRect(2, 2, 20, 2);
	        
	        //Draw String
	        g2.setFont(b.getFont());
	        g2.drawString(txt, 80, 25);
	        
	        if(model.isSelected()){
	        	paintButtonPressed(g,b);
	        }
	        
	        if (model.isArmed() && model.isPressed()) {
	            paintButtonPressed(g,b);
	        }
	        
	        if (b.isFocusPainted() && b.hasFocus()) {
	            // paint UI specific focus
	            paintFocus(g,b,null,null,null);
	        }
		}
		
		@Override
		public void paintButtonPressed(Graphics g, AbstractButton b) {
			String txt = b.getText();
	        Graphics2D g2 = (Graphics2D)g;
	        //Button Base
	        g2.setColor(Colors.COLOR_FORM_CONTENT);
	        g2.fillRect(0, 0, 520, 40);
	        
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 4, 70, 32);
	        
	        g2.setColor(new Color(80, 80, 80));
	        g2.fillRect(2, 6, 66, 28);
	        
	        g2.setColor(Color.GRAY);
	        g2.fillRect(32, 12, 4, 16);
	        
	        //Button
	        g2.setColor(Color.BLACK);
	        g2.fillRect(46, 0, 24, 40);
	        
	        g2.setColor(Colors.COLOR_FORM_BASE);
	        g2.fillRect(48, 2, 20, 36);
	        
	        g2.setColor(Colors.COLOR_FORM_CONTENT);
	        g2.fillRect(66, 4, 2, 34);
	        g2.fillRect(48, 36, 20, 2);
	        
	        g2.setColor(Color.WHITE);
	        g2.fillRect(48, 2, 20, 2);
	        
	        //Draw String
	        g2.setFont(b.getFont());
	        g2.drawString(txt, 80, 25);
		}
		
	}

}
