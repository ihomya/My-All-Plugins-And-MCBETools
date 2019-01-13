package itsu.java.mcbeformmaker.core;

import java.awt.Component;
import java.awt.Toolkit;
import java.io.IOException;

import itsu.java.mcbeformmaker.panel.Form;
import itsu.java.mcbeformmaker.panel.FormPanel;
import itsu.java.mcbeformmaker.panel.SimpleTabbedPane;
import itsu.java.mcbeformmaker.utils.MessagePopup;
import itsu.java.mcbeformmaker.windows.ColorsWindow;
import itsu.java.mcbeformmaker.windows.ElementChooser;
import itsu.java.mcbeformmaker.windows.HowToUseWindow;
import itsu.java.mcbeformmaker.windows.JsonChecker;

/**
 * MCBEFormMaker
 *
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 *
 * @author itsu
 *
 */

public class Controller {

    private static Data data = new Data();
    private static SimpleTabbedPane tab;
    private static ElementChooser elementChooser = new ElementChooser();
    private static ColorsWindow colorsWindow = new ColorsWindow();
    private static HowToUseWindow howToUseWindow = new HowToUseWindow();
    private static JsonChecker jsonChecker = new JsonChecker();

    public static void initUISettings() {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        Toolkit.getDefaultToolkit().setDynamicLayout(true);

    }

    public static void initUI() {
        new UIController();
    }

    public static void initData(){
        try {
            data.initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setTab(SimpleTabbedPane pane) {
        tab = pane;
    }

    public static void addTab(int type, String title) {
        tab.add(new FormPanel(type, title), title);
    }

    public static FormPanel getNowTab() {
        Component com = tab == null ? null : tab.getSelectedComponent();
        return com == null ? null : (FormPanel) tab.getSelectedComponent();
    }

    public static void showElementChooser(int type, Form form) {
        elementChooser.show(type, form);
    }
    
    public static void showHowToUseWindow() {
    	howToUseWindow.showWindow();
    }

    public static void showColorsWindow() {
        colorsWindow.showWindow();
    }
    
    public static void showJsonChecker() {
        jsonChecker.showWindow();
    }

    public static Data getDataObject(){
        return data;
    }
    public static void information(String message){
        MessagePopup.information(message);
    }

    public static void alert(String message){
        MessagePopup.alert(message);
    }

    public static void exception(String message, Exception e){
        MessagePopup.exception(message, e);
    }

    public static void error(String message){
        MessagePopup.error(message);
    }
}
