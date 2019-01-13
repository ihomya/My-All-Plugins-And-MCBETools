package itsu.java.mcbeformmaker.element;

import java.util.Map;

import javax.swing.JPanel;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public abstract class Element extends JPanel {
	
	public abstract String getName();
	public abstract Map<String, Object> getData();
	public abstract Object getResult();
	public abstract void setEditable(boolean bool);

}
