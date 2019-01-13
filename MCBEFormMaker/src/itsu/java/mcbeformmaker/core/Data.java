package itsu.java.mcbeformmaker.core;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.Serializable;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class Data implements Serializable{

    private String OSName;
    
    private Dimension desktopSize;
    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private int desktopWidth;
    private int desktopHeight;
    
    private String systemFont;


    public Data(){

    }

    public void initData() throws IOException{
    	
        OSName = System.getProperty("os.name").toLowerCase();
        
        desktopSize = Toolkit.getDefaultToolkit().getScreenSize();
        desktopWidth = desktopSize.width;
        desktopHeight = desktopSize.height;
        
        systemFont = initSystemFont();
        
    }

    private String initSystemFont(){
    	
        String osLower = OSName;

        if(osLower.startsWith("windows")){
            return "メイリオ";
            
        }else if(osLower.startsWith("linux")){
            return "TakaoPゴシック";
            
        }else if(osLower.startsWith("mac")){
            return "ヒラギノ角ゴ";
            
        }else{
            return "ＭＳ Ｐゴシック";
        }
        
    }

    public int getWindowWidth(){
        return this.desktopWidth;
    }

    public int getWindowHeight(){
        return this.desktopHeight;
    }
    
    public String getSystemFont(){
        return systemFont;
    }
    
    public String getOS(){
    	return this.OSName;
    }
}
