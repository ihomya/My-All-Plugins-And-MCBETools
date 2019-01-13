package mcserver.gui.core;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import sun.swing.SwingUtilities2;

public class SimpleTabbedPaneUI extends BasicTabbedPaneUI{
	
	private static final int TAB_ALPHA = 220;
    private static final Color TAB_COLOR = Color.BLACK;
    private static final int TAB_BORDER_SIZE = 5;
    private static final int TAB_HEIGHT = 40;
	
	@Override
	public void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
		Rectangle tabRect = rects[tabIndex];
        int alpha = TAB_ALPHA;
		ComponentOrientation orientation = ComponentOrientation.getOrientation(tabPane.getLocale());
        int x = tabRect.x + TAB_BORDER_SIZE;
        int y = tabRect.y + TAB_BORDER_SIZE;
        int w = tabRect.width;
        int h = tabRect.height + TAB_HEIGHT;

        String title = tabPane.getTitleAt(tabIndex);
        Font font = tabPane.getFont();
        FontMetrics metrics = SwingUtilities2.getFontMetrics(tabPane, g, font);
        Icon icon = getIconForTab(tabIndex);
        int selectedIndex = tabPane.getSelectedIndex();
        boolean isSelected = selectedIndex == tabIndex;
        String clippedTitle = null;
        Rectangle newTextRect = new Rectangle(textRect.x, textRect.y, textRect.width, textRect.height);

        layoutLabel(tabPlacement, metrics, tabIndex, title, icon,
                    tabRect, iconRect, newTextRect, isSelected);

        if (tabPane.getTabComponentAt(tabIndex) == null) {
            clippedTitle = title;

            paintText(g, tabPlacement, font, metrics,
                    tabIndex, clippedTitle, newTextRect, isSelected);

            paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
        }
        paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, newTextRect, isSelected);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(TAB_COLOR.getRed(), TAB_COLOR.getGreen(), TAB_COLOR.getBlue(), alpha));
        g2.fillRect(x + 2, y, w, h - 2);
        g2.setColor(Color.MAGENTA);
        g2.fillRect(x, y, 2, h);
        
        paintText(g, tabPlacement, font, metrics, tabIndex, clippedTitle, newTextRect, isSelected);
        paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
	}
}
