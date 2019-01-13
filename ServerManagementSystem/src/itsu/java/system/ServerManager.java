package itsu.java.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ServerManager {

    private static ProcessBuilder builder;
    private static Process server;
    
    private static boolean isStarted = false;
    private static boolean isInited = false;
    
    private static BufferedReader reader;

    public static void init() {
    	isStarted = true;
    	isInited = true;
        builder = new ProcessBuilder("cmd", "/c", "start", "start.bat");
    }

    public static boolean start() throws IOException, InterruptedException {
    	if(isStarted && isInited) {
    		isInited = false;
            isStarted = true;
            System.out.println(ServerManagementSystem.getTimeAsString() + "サーバーを起動しました。");
            server = builder.start();
            reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            server.waitFor();
            return true;
    	}
    	return false;
    }

    public static boolean stop() throws IOException, InterruptedException {
        OutputStream console = server.getOutputStream();
        console.write("say §a[ServerManagementSystem] §cサーバーを終了します。".getBytes());
        console.write("stop".getBytes());
        console.flush();
        server.waitFor();
        isStarted = false;
        return true;
    }
    
    public static void killServer() {
    	if(server != null) server.destroyForcibly();
    	isStarted = false;
    }
    
    public static boolean sendMessage(String message) throws IOException, InterruptedException {
        OutputStream console = server.getOutputStream();
        console.write(("say §a[ServerManagementSystem] " + message).getBytes());
        console.flush();
        server.waitFor();
        return true;
    }

    public static boolean isEnded() {
    	try {
    		return reader.readLine() == null ? true : new File("ExitChecker.dat").exists();
    	} catch(NullPointerException | IOException e) {
    		return true;
    	}
    }
    
    public static boolean isStarted() {
    	return isStarted;
    }

}
