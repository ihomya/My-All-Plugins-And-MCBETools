package itsu.java.system;

import java.util.Calendar;

public class ServerManagementSystem {

    public static void main(String args[]) {
        new Thread(new ServerRunTask()).start();
    }

    public static int getTime() {
        return Calendar.HOUR_OF_DAY;
    }

    public static String getTimeAsString() {
        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);
        int m = now.get(Calendar.MINUTE);
        int s = now.get(Calendar.SECOND);
        return "[" + h + ":" + m + ":" + s + "] ";
    }

}
