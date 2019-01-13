package itsu.mcpe.highendloginsystem.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import cn.nukkit.scheduler.AsyncTask;

public class SettingFrame extends AsyncTask {
	
	private JFrame frame;
	private JTable table;

	@Override
	public void onRun() {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
    	
		frame = new JFrame();
		frame.setTitle("HighEndLoginSystem - " + HighEndLoginSystem.instance.getServer().getMotd());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setBounds(0, 0, 800, 500);
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[][]{}, new String[]{"管理ID", "プレイヤー名", "IPアドレス", "クライアントID", "BAN"}) {
			@Override public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		List<Map<String, Object>> map = Server.getSQLSystem().getAccounts();
		
		for(Map<String, Object> account : map) {
			tableModel.addRow(new Object[]{account.get("id"), account.get("name"), account.get("ipAddress"), account.get("clientId"), account.get("banned")});
		}
		
		table = new JTable(tableModel);
		JScrollPane sp = new JScrollPane(table);
	    sp.setPreferredSize(new Dimension(800, 500));
	    
	    frame.getContentPane().add(sp, BorderLayout.CENTER);
	    frame.setVisible(true);
	}

}
