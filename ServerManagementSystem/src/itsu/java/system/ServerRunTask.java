package itsu.java.system;

import java.io.IOException;

public class ServerRunTask implements Runnable {

    @Override
    public void run() {
        try {
            while(true) {
            	if(ServerManager.isEnded()) {
            		ServerManager.killServer();
                    ServerManager.init();
                    ServerManager.start();
            	}
            	Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
