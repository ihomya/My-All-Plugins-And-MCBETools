package itsu.java.mcbeformmaker.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

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

public class Button extends Element {

    private JTextArea text;
    private JLabel label;
    private String image = "";

    private Button button;

    private boolean isEntered = false;
    private boolean allowImage = true;
    private boolean isEditable = true;

    public Button() {
        this("ボタン - ここにテキストを入力できます。", true, true);
    }

    public Button(String content, boolean allowImage, boolean editable) {
        this.button = this;
        this.allowImage = allowImage;

        this.setMaximumSize(new Dimension(520, 70));
        this.setLayout(null);
        this.setBackground(Colors.COLOR_FORM_BASE);

        text = new JTextArea(content);
        text.setFont(new Font(Controller.getDataObject().getSystemFont(), Font.PLAIN, 15));
        text.setOpaque(false);
        text.setForeground(Colors.COLOR_FORM_TEXT);
        text.setBounds(70, 0, 470, 70);
        text.setEditable(editable);
        text.setLocation(70, 0);
        text.addMouseListener(new MouseListener() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isEntered = true;
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isEntered = false;
                button.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == 3 && button.allowImage && isEditable) {
                    image = MessagePopup.input("画像のURLを入力してください。", image);

                    if(image == null || image.equals("")) {
                        label.setIcon(null);
                        return;
                    }

                    try {
                        BufferedImage img = ImageIO.read(new URL(image));
                        BufferedImage img1 = img;

                        if (img.getHeight() != 70 || img.getWidth() != 70) { //resize
                            img1 = new BufferedImage(70, 70, img.getType());
                            Graphics2D g = img1.createGraphics();
                            g.drawImage(img, 0, 0, 70, 70, null);
                            g.dispose();
                        }

                        label.setIcon(new ImageIcon(img1));
                    } catch (IOException e1) {}
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
        });
        this.add(text);

        label = new JLabel();
        label.setBounds(0, 0, 70, 70);
        this.add(label);
    }

    public String getText() {
        return text.getText();
    }

    @Override
    public String getName() {
        return "button";
    }
    
    @Override
    public Object getResult() {
    	return null;
    }
    
    public void addButtonListener(MouseListener listener) {
    	text.addMouseListener(listener);
    }
    
    @Override
    public void setEditable(boolean bool) {
    	text.setEditable(bool);
    	isEditable = bool;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put("text", text.getText());
        data.put("imageType", "url");
        data.put("imagePath", image);
        return data;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);

        if(isEntered) {
            g.setColor(Colors.COLOR_FORM_SELECTED);
            g.fillRect(4, 4, this.getWidth() - 6, this.getHeight() - 6);
        } else {
            g.setColor(Colors.COLOR_FORM_BASE);
            g.fillRect(4, 4, this.getWidth() - 6, this.getHeight() - 6);
        }

        g.setColor(Colors.COLOR_FORM_CONTENT);
        g.fillRect(2, this.getHeight() - 4, this.getWidth() - 4, 2);
        g.fillRect(this.getWidth() - 4, 4, 2, this.getHeight() - 6);
    }

}
