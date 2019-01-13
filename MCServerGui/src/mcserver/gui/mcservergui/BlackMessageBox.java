package mcserver.gui.mcservergui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class BlackMessageBox extends JFrame{
	JPanel title = new JPanel();
	JLabel titletxt = new JLabel(" MCServerGUI v4.0");
	String title2;
	JLabel x = new JLabel("x");
	DragWindowListener dwl = new DragWindowListener();

	int xx = 50;
	int yy = 50;
	int width = 300;
	int height = 200;

	public static final int SYSTEM_EXIT = 0;
	public static final int HIDE_WINDOW = 1;

	int act;

	static BlackMessageBox bmb;

	public BlackMessageBox(){
		this.setUndecorated(true);
        this.setBackground(new Color(0,0,0,200));
        this.setLayout(null);

		gui1();
	}

	public void setMsgTitle(String str){
		titletxt.setText(str);
		this.setTitle(str);
		return;
	}

	public void setFrameVisible(boolean b){
		this.setVisible(b);
		return;
	}

	public void gui1(){
        title.setBackground(new Color(0,0,0,200));
        title.setBounds(0,0,width,20);
        title.setLayout(null);
        titletxt.setBounds(0,0,width-100,20);
        titletxt.setForeground(Color.WHITE);
        title.addMouseListener(dwl);
        title.addMouseMotionListener(dwl);

        title.add(titletxt);
        this.setTitle(titletxt.getText());

        x.setBackground(Color.WHITE);
        x.setForeground(Color.GRAY);
        x.setFont(new Font("Arial", Font.PLAIN, 20));
        x.setBounds(width-40,0,40,20);
        x.setHorizontalAlignment(JLabel.CENTER);
        x.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(act){
					case SYSTEM_EXIT:
						System.exit(0);
					case HIDE_WINDOW:
						setFrameVisible(false);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				x.setBackground(Color.RED);
				x.setForeground(Color.RED);
				x.repaint();
				title.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				x.setBackground(Color.WHITE);
				x.setForeground(Color.GRAY);
				x.repaint();
				title.repaint();
			}
        });
        title.add(x);

        this.setBounds(xx,yy,width,height);
        this.add(title);
        setVisible(true);
        return;
	}

	public void setFrameBounds(int x, int y, int width1, int height1){
		xx = x;
		yy = y;
		width = width1;
		height = height1;
		this.setVisible(false);
		gui1();
		return;
	}

	public void setButtonAction(int act){
		this.act = act;
	}

}

class DragWindowListener extends MouseAdapter {
	  private final Point startPt = new Point();
	  private Window window;
	  @Override public void mousePressed(MouseEvent me) {
	    startPt.setLocation(me.getPoint());
	  }
	  @Override public void mouseDragged(MouseEvent me) {
	    if (window == null) {
	      window = SwingUtilities.windowForComponent(me.getComponent());
	    }
	    Point eventLocationOnScreen = me.getLocationOnScreen();
	    window.setLocation(eventLocationOnScreen.x - startPt.x,
	                       eventLocationOnScreen.y - startPt.y);
	  }
	}

