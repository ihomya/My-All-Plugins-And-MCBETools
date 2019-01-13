package itsu.java.mcbeformmaker.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;

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

public class DropDown extends Element {

	private JTextField title;
    private JLabel label;
    private JWindow window;

    private DropDown dropdown;
    private int[] triangleX = {484, 496, 490};
    private int[] triangleY = {30, 30, 42};
    
    private String text = "";
    private List<String> data;

    private boolean isEntered = false;
	private boolean isEditable = true;

    public DropDown() {
        this("ドロップダウン - ここにタイトルを入力できます。", "右クリックで項目を追加できます。");
    }

    public DropDown(String content, String dropText) {
        this.dropdown = this;

        this.setMaximumSize(new Dimension(520, 100));
        this.setLayout(null);
        this.setBackground(Colors.COLOR_FORM_CONTENT);
        
        window = new JWindow();
        window.getContentPane().setBackground(Colors.COLOR_FORM_WINDOW);
        window.getContentPane().setLayout(null);
        
        title = new JTextField(content);
        title.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        title.setOpaque(false);
        title.setForeground(Color.WHITE);
        title.setBackground(Colors.COLOR_FORM_CONTENT);
        title.setBorder(null);
        title.setBounds(0, 10, 520, 20);
        this.add(title);

        label = new JLabel(dropText) {
        	 @Override
        	    public void paintComponent(Graphics g) {
        	        g.setColor(Color.BLACK);
        	        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        	        g.setColor(Color.WHITE);
        	        g.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);

        	        if(isEntered) {
        	            g.setColor(Colors.COLOR_FORM_SELECTED);
        	            g.fillRect(4, 4, this.getWidth() - 6, this.getHeight() - 6);
        	            label.setForeground(Color.WHITE);
        	            
        	        } else {
        	            g.setColor(Colors.COLOR_FORM_BASE);
        	            g.fillRect(4, 4, this.getWidth() - 6, this.getHeight() - 6);
        	            label.setForeground(Colors.COLOR_FORM_TEXT);
        	        }

        	        g.setColor(Colors.COLOR_FORM_CONTENT);
        	        g.fillRect(2, this.getHeight() - 4, this.getWidth() - 4, 2);
        	        g.fillRect(this.getWidth() - 4, 4, 2, this.getHeight() - 6);
        	        
        	        g.setColor(Colors.COLOR_FORM_TEXT);
        	        g.fillPolygon(triangleX, triangleY, triangleX.length);
        	        
        	        if(isEntered) {
            	        g.setColor(Color.WHITE);
            	        g.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
            	        g.drawString(this.getText(), 30, 40);
        	            
        	        } else {
        	        	g.setColor(Colors.COLOR_FORM_TEXT);
            	        g.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
            	        g.drawString(this.getText(), 30, 40);
        	        }

        	    }
        };
        
        label.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        label.setOpaque(false);
        label.setForeground(Colors.COLOR_FORM_TEXT);
        label.setBounds(0, 30, 520, 70);
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isEntered = true;
                dropdown.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isEntered = false;
                dropdown.repaint();
            }

            @Override public void mouseClicked(MouseEvent e) {
            	if(e.getButton() == 3 && isEditable) {

                    text = MessagePopup.input("追加する項目を入力してください。", text);
                    
                    if(text == null || text.equals("")) {
                    	MessagePopup.alert("空白文字は指定できません。");
                    	return;
                    }
                    else data.add(text);
                    
            	} else {
            		if(window.isVisible()) {
            			window.setVisible(false);
            			return;
            		}
            		
            		int height = data.size() * 70;
            		int y = 0;
            		Point p = dropdown.getLocationOnScreen();
            		
            		window.setSize(520, height);
            		window.getContentPane().removeAll();
            		window.setLocation(p.x, p.y + label.getHeight() + title.getHeight() + 10);
            		
            		for(String str : data) {
            			JLabel label = new JLabel(str);
            	        label.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
            	        label.setForeground(Color.WHITE);
            	        label.setBackground(Colors.COLOR_FORM_WINDOW);
            	        label.setBounds(0, y, 520, 70);
            	        label.setOpaque(true);
            	        label.addMouseListener(new MouseListener() {
            	        	
							@Override
							public void mouseEntered(MouseEvent e) {
								label.setBackground(Colors.COLOR_FORM_CONTENT_SELECTED);
							}
							
							@Override
							public void mouseExited(MouseEvent e) {
								label.setBackground(Colors.COLOR_FORM_WINDOW);
							}
							
							@Override public void mouseClicked(MouseEvent e) {
								String newText;
								String oldText = label.getText();
								int index = 0;
								
								if(e.getButton() == 3) {
									newText = MessagePopup.input("置き換える文字列を入力してください。", text);
									
				                    if(newText == null || newText.equals("")) {
				                    	MessagePopup.alert("空白文字は指定できません。");
				                    	return;
				                    }
				                    
				                    label.setText(newText);
				                    for(String str : data) {
				                    	if(str.equals(oldText)) {
				                    		data.set(index, newText);
				                    		break;
				                    	}
				                    	index++;
				                    }
				                    
				                    if(dropdown.label.getText().equals(oldText)) {
				                    	dropdown.label.setText(newText);
				                    }
				                    
								} else {
									dropdown.label.setText(label.getText());
									window.setVisible(false);
								}
							}
							
							@Override public void mousePressed(MouseEvent e) {}
							@Override public void mouseReleased(MouseEvent e) {}
            	        });
            	        
            	        y += 70;
            	        window.getContentPane().add(label);
            		}
            		
            		window.setVisible(true);
            	}
            }
            
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
        });
        this.add(label);
        
        data = new ArrayList<>();
        data.add(dropText);
    }

    public String getText() {
        return label.getText();
    }

	@Override
	public String getName() {
		return "dropdown";
	}
	
    @Override
    public Object getResult() {
    	return label.getText();
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
        data.put("type", "dropdown");
        data.put("options", this.data);
        
        int index = 0;
        for(String str : this.data) {
        	if(str.equals(label.getText())) {
        		break;
        	}
        	index++;
        }
        
        data.put("defaultOptionIndex", index);
        return data;
	}

}
