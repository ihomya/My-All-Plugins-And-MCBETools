package mcserver.gui.console;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Start {
	
	public Start()
	{
	}
	
	public void startConsole(Process process, OutputStream out){
		@SuppressWarnings("rawtypes")
		ArrayList al = getPlatForm();
		String platform;
        try
        {
            process = Runtime.getRuntime().exec((String)al.get(0));
            out = process.getOutputStream();
            platform = (String)al.get(1);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        return;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getPlatForm(){
        ArrayList inner = new ArrayList();
        String run = getRunTime();
        String platform;
        if(run == "java")
            platform = "NUKKIT";
        else
            platform = "POCKETMINE";
        String args = Command(getSource(), run, platform);
        inner.add(args);
        inner.add(platform);
        return inner;
	}
	
	public String getRunTime(){
		File f = new File(System.getProperty("user.dir"));
		File f1[] = f.listFiles();
		 int i = f1.length;
	        for(int j = 0; j < i; j++)
	        {
	            File binary = f1[j];
	            if(binary.getName().startsWith("nukkit") && binary.getName().endsWith("jar"))
	                return "java";
	            if(binary.getName().contains("bin"))
	                return getPHP(binary);
	        }
	        return null;
	}
	
	public String getPHP(File f){
        String user = System.getProperty("os.name").toLowerCase();
        if(user.startsWith("windows"))
        {
            File windowsPHP = new File((new StringBuilder()).append(f.getPath()).append(File.separator).append("php").append(File.separator).append("php.exe").toString());
            if(windowsPHP.exists())
                return windowsPHP.getPath();
            else
                return null;
        }
        File linuxPHP7 = new File((new StringBuilder()).append("./bin").append(File.separator).append("php7").append(File.separator).append("bin").append(File.separator).append("php").toString());
        if(linuxPHP7.exists())
            return linuxPHP7.getPath();
        else
            return null;
		
	}
	
	public String getSource(){
	        File currentdir = new File(System.getProperty("user.dir"));
	        File afile[] = currentdir.listFiles();
	        int i = afile.length;
	        for(int j = 0; j < i; j++)
	        {
	            File src = afile[j];
	            if(src.getName().endsWith("jar") && src.getName().toLowerCase().startsWith("nukkit"))
	                return src.getName();
	            if(src.getName().endsWith("phar"))
	                return src.getName();
	            if(src.getName().contains("src"))
	                return getStart(src);
	        }
	        return null;
	}
	
	public String getStart(File f){
		return (new StringBuilder()).append(f.getPath()).append(File.separator).append("pocketmine").append(File.separator).append("PocketMine.php").toString();
	}
	
    public String Command(String start, String run, String platform)
    {
        if(platform == "NUKKIT")
        {
            String command = (new StringBuilder()).append(run).append(" ").append("-Djline.terminal=jline.UnsupportedTerminal -jar ").append(start).toString();
            return command;
        }
        String os = System.getProperty("os.name").toLowerCase();
        if(os.startsWith("windows"))
        {
            String command = (new StringBuilder()).append(run).append(" -c bin").append(File.separator).append("php ").append(start).append(" %*").toString();
            return command;
        } else
        {
            String command = (new StringBuilder()).append(run).append(" ").append(start).toString();
            return command;
        }
    }

}
