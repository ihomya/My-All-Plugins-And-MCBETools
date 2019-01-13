package itsu.java.mcbeformmaker.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import itsu.java.mcbeformmaker.core.Controller;
import itsu.java.mcbeformmaker.utils.MessagePopup;
import itsu.java.mcbeformmaker.utils.Utils;
import itsu.java.mcbeformmaker.utils.Version;
import itsu.java.mcbeformmaker.windows.FormMaker;
import itsu.java.mcbeformmaker.windows.SaveFileDialog;

/**
 * MCBEFormMaker
 * 
 * <p>このソフトウェアはGPL v3.0ライセンスのもとで開発されています。
 * 
 * @author itsu
 * 
 */

public class MenuBar extends JMenuBar {

    private JMenu file;
    private JMenu develop;
    private JMenu help;

    private JMenuItem newForm;
    private JMenuItem export;
    private JMenuItem phpExport;
    private JMenuItem javaExport;

    private JMenuItem show;

    private JMenuItem colors;
    private JMenuItem use;
    private JMenuItem version;

    public MenuBar() {
        this.setBackground(new Color(33, 33, 33));

        file = new JMenu("ファイル");
        develop = new JMenu("開発");
        help = new JMenu("ヘルプ");

        newForm = new JMenuItem("新規フォーム                        ");
        newForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMaker.show();
            }
        });

        export = new JMenuItem("JSONエクスポート");
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Controller.getNowTab() == null) {
                        MessagePopup.alert("プロジェクトを作成してください。");
                        return;
                    }

                    Form form = Controller.getNowTab().getForm();
                    String json = form.toJson();
                    File file = SaveFileDialog.show(export, "json");

                    if(file == null) return;

                    if(!file.getAbsolutePath().endsWith(".json")) {
                        file = new File(file.getAbsolutePath() + ".json");
                    }

                    Utils.writeFile(file, json);
                } catch(IOException ex) {
                    MessagePopup.exception("エラーが発生しました。ご不便をおかけします。", ex);
                }
            }
        });

        phpExport = new JMenuItem("PMMPコードでエクスポート");
        phpExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Controller.getNowTab() == null) {
                        MessagePopup.alert("プロジェクトを作成してください。");
                        return;
                    }

                    Form form = Controller.getNowTab().getForm();
                    String json = form.toJsonUseGson();
                    File file = SaveFileDialog.show(export, "php");

                    if(file == null) return;

                    if(!file.getAbsolutePath().endsWith(".php")) {
                        file = new File(file.getAbsolutePath() + ".php");
                    }

                    StringBuffer bf = new StringBuffer();
                    bf.append("$pk = new ModalFormRequestPacket();\n");
                    bf.append("$pk->formId = " + form.getId() + ";\n");
                    bf.append("$pk->data = \"" + json + "\";");

                    Utils.writeFile(file, bf.toString());
                } catch(IOException ex) {
                    MessagePopup.exception("エラーが発生しました。ご不便をおかけします。", ex);
                }
            }
        });

        javaExport = new JMenuItem("Nukkit/Jupiterコードでエクスポート");
        javaExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Controller.getNowTab() == null) {
                        MessagePopup.alert("プロジェクトを作成してください。");
                        return;
                    }

                    Form form = Controller.getNowTab().getForm();
                    String json = form.toJsonUseGson();
                    File file = SaveFileDialog.show(export, "java");

                    if(file == null) return;

                    if(!file.getAbsolutePath().endsWith(".java")) {
                        file = new File(file.getAbsolutePath() + ".java");
                    }

                    StringBuffer bf = new StringBuffer();
                    bf.append("ModalFormRequestPacket pk = new ModalFormRequestPacket();\n");
                    bf.append("pk.formId = " + form.getId() + ";\n");
                    bf.append("pk.data = \"" + json + "\";");

                    Utils.writeFile(file, bf.toString());
                } catch(IOException ex) {
                    MessagePopup.exception("エラーが発生しました。ご不便をおかけします。", ex);
                }
            }
        });
        
        show = new JMenuItem("Jsonを確認                          ");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.showJsonChecker();
            }
        });

        colors = new JMenuItem("MCBE 16進数色コード一覧       ");
        colors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.showColorsWindow();
            }
        });

        use = new JMenuItem("使い方");
        use.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.showHowToUseWindow();
            }
        });

        version = new JMenuItem("バージョン情報");
        version.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuffer bf = new StringBuffer();
                bf.append("MCBEFormMaker\n");
                bf.append("バージョン: " + Version.VERSION + "\n");
                bf.append("動作環境: Java8 jre8u_141 以降推奨\n");
                bf.append("This software is developed under GPL v3.0 License.");
                MessagePopup.information(bf.toString());
            }
        });

        file.add(newForm);
        file.add(export);
        file.add(phpExport);
        file.add(javaExport);

        develop.add(show);

        help.add(colors);
        help.add(use);
        help.add(version);

        this.add(file);
        this.add(develop);
        this.add(help);
    }

}
