package mcserver.gui.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import mcserver.gui.plugin.Utils;

public class FileChecker {

	String pas;

	public FileChecker()
	{
	}

	public String checkFile(){
		  try{
			  FileReader fr1 = new FileReader("./MCServerGUI/startFile.txt");
			  BufferedReader br1 = new BufferedReader(fr1);

			  pas = br1.readLine();

			  fr1.close();
			  br1.close();
		  }catch(IOException ex){
			  	MessageDialog.freeDialog("[MCServerGUI]/MCServerGUI/startFile.txtの読み込みに失敗しました。");
			  	return pas;
		  }
		  return pas;
	}

	public void makeFile(){
		File f0 = new File("./MCServerGUI/");
		File f1 = new File("./MCServerGUI/images");
		File f2 = new File("./MCServerGUI/Option");
		File f3 = new File("./MCServerGUI/plugins");
		
		if(!f0.exists()){
			if(!f0.mkdirs()){
				MessageDialog.freeDialog("./MCServerGUI/の作成に失敗しました。");
			}else{
				 try{
					 Utils.writeFile(new File("./MCServerGUI/startFile.txt"), "nukkit.bat");
				 }catch(IOException ex){
					 MessageDialog.freeDialog("./MCServerGUI/startFile.txtの作成に失敗しました。");
				 }
			}
		}
		if(!f1.exists()){
			if(!f1.mkdirs()){
				MessageDialog.freeDialog("./MCServerGUI/images/の作成に失敗しました。");
			}else{
				try {
					Utils.writeFile("./MCServerGUI/images/Background.PNG", this.getClass().getClassLoader().getResourceAsStream("resources/Background.PNG"));
				} catch (IOException e) {
					MessageDialog.freeDialog("./MCServerGUI/images/Background.PNGの作成に失敗しました。");
					e.printStackTrace();
				}
			}
		}
		if(!f2.exists()){
			if(!f2.mkdirs()){
				MessageDialog.freeDialog("./MCServerGUI/Option/の作成に失敗しました。");
			}else{
				try {
					Utils.writeFile("./MCServerGUI/Option/AutoReload.dat", "0");
					Utils.writeFile("./MCServerGUI/Option/Background.dat", "./MCServerGUI/images/Background.PNG");
					Utils.writeFile("./MCServerGUI/Option/run.dat", "0");
				} catch (IOException e) {
					MessageDialog.freeDialog("./MCServerGUI/Option/の作成に失敗しました。");
					e.printStackTrace();
				}
			}
		}
		if(!f3.exists()){
			if(!f3.mkdirs()){
				MessageDialog.freeDialog("./MCServerGUI/pluginsの作成に失敗しました。");
			}
		}
		return;
	}
}
