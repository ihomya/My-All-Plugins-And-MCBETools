package mcserver.gui.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Setting {
	
	public Setting()
	{
	}
	
	public String getBackGround(){
		 String bun = null;
		try{
		 FileReader fr12 = new FileReader("./MCServerGUI/Option/Background.dat");
	     BufferedReader br12 = new BufferedReader(fr12);
		 bun = br12.readLine();
	     fr12.close();
	     br12.close();
		}catch(IOException ex){}
    	return bun;
	}
	
	public void setBackGround(String pass){
		if(pass.equals("")){
			return;
		}
		    	try{
					  FileWriter fw1 = new FileWriter("./MCServerGUI/Option/Background.dat", false);  //※1
					  PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
					  pw1.println(pass);
					  pw1.close();
				  }catch(IOException ex){}
   	return;
	}
	
	public String getStartFile(){
		 String bun = null;
		try{
		 FileReader fr12 = new FileReader("./MCServerGUI/startFile.txt");
	     BufferedReader br12 = new BufferedReader(fr12);
		 bun = br12.readLine();
	     fr12.close();
	     br12.close();
		}catch(IOException ex){}
   	return bun;
	}
	
	public void setAutoReloadCheck(int i){
		    	try{
					  FileWriter fw1 = new FileWriter("./MCServerGUI/Option/AutoReload.dat", false);  //※1
					  PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
					  pw1.println(i);
					  pw1.close();
				  }catch(IOException ex){}
   	return;
	}
	
	public int getAutoReloadCheck(){
		 String bun = null;
		 int i = 0;
		try{
		 FileReader fr12 = new FileReader("./MCServerGUI/Option/AutoReload.dat");
	     BufferedReader br12 = new BufferedReader(fr12);
		 bun = br12.readLine();
	     fr12.close();
	     br12.close();
	     i = Integer.parseInt(bun);
		}catch(IOException ex){}
   	return i;
	}
	
	public void setRun(){
    	try{
			  FileWriter fw1 = new FileWriter("./MCServerGUI/Option/run.dat", false);  //※1
			  PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
			  pw1.println("1");
			  pw1.close();
		  }catch(IOException ex){}
    	return;
	}
	
	public int getRun(){
		 String bun = null;
		 int i = 0;
		try{
		 FileReader fr12 = new FileReader("./MCServerGUI/Option/run.dat");
	     BufferedReader br12 = new BufferedReader(fr12);
		 bun = br12.readLine();
	     fr12.close();
	     br12.close();
	     i = Integer.parseInt(bun);
		}catch(IOException ex){}
  	return i;
	}
}
