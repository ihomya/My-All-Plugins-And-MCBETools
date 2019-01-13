/*
 * This ui is made by:
 * http://ui-ideas.blogspot.jp/2012/06/mac-os-x-mountain-lion-scrollbars-in.html
 * 
 * Forked by Itsu
 */

package itsu.java.mcbeformmaker.ui;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MCBEFormScrollBarUI extends BasicScrollBarUI {
	
    private static final int THUMB_BORDER_SIZE = 5;
    private static final int THUMB_SIZE = 8;
    private static final Color THUMB_COLOR = new Color(198, 198, 198);
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new MyScrollBarButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new MyScrollBarButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    	g.setColor(new Color(60, 60, 60, 255));
    	g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    	g.setColor(new Color(30, 30, 30, 255));
    	g.fillRect(trackBounds.width / 3 + 1, trackBounds.y, trackBounds.width / 3 + 1, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int alpha = 255;
        int orientation = scrollbar.getOrientation();
        int x = thumbBounds.x + THUMB_BORDER_SIZE;
        int y = thumbBounds.y + THUMB_BORDER_SIZE;

        int width = orientation == JScrollBar.VERTICAL ?
                THUMB_SIZE : thumbBounds.width - (THUMB_BORDER_SIZE * 2);
        width = Math.max(width, THUMB_SIZE);

        int height = orientation == JScrollBar.VERTICAL ?
                thumbBounds.height - (THUMB_BORDER_SIZE * 2) : THUMB_SIZE;
        height = Math.max(height, THUMB_SIZE);

        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(x, y, width, height);
        
        graphics2D.setColor(new Color(THUMB_COLOR.getRed(), THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
        graphics2D.fillRect(x + 2, y + 2, width - 4, height - 4);
        graphics2D.dispose();
    }
}

class MyScrollBarButton extends JButton {
    MyScrollBarButton() {
        setOpaque(false);
        setFocusable(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
    }
}

