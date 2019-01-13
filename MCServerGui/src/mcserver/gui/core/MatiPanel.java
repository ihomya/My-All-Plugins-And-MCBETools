package mcserver.gui.core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MatiPanel extends JPanel {
	Setting sett = new Setting();
	  Image backgroundImage;

	  public MatiPanel() {
	  backgroundImage = Toolkit.getDefaultToolkit().createImage(sett.getBackGround()); // 背景画像の準備
	  setOpaque(false); // 背景を透明にする。不透明だと背景画像を描画してもJPanelが背景色で上書きしてしまう。
	  }

	  @Override // 上位クラスのメソッドを定義しなおしていることを示すJavaの注釈。なくても構いません
	  public void paint(Graphics g) {
	  g.drawImage(backgroundImage, 0, 0, this); // 背景イメージを描画
	  super.paint(g); // 子供コンポーネントの描画等、上位クラスで実現している表示内容の描画
	  }
}
