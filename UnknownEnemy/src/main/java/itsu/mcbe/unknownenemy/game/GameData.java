package itsu.mcbe.unknownenemy.game;

public class GameData {

    private static int maxGamersCount;

    private static int groundStartX;
    private static int groundStartZ;
    private static int groundEndX;
    private static int groundEndZ;
    private static String groundName;
    
    private static String mapName;
    
    private static int firstPeriod;

    public static void initGameData() {
        maxGamersCount = 0;
        groundName = "";
        firstPeriod = 0;
        mapName = "";
    }

    public static void setMaxGamersCount(int count) {
        maxGamersCount = count;
    }

    public static int getMaxGamersCount() {
        return maxGamersCount;
    }

    public static void setGroundSize(int startX, int startZ, int endX, int endZ) {
        groundStartX = startX;
        groundStartZ = startZ;
        groundEndX = endX;
        groundEndZ = endZ;
    }
    
    public static void setGroundName(String name) {
    	groundName = name;
    }
    
    public static void setFirstPeriod(int second) {
    	firstPeriod = second;
    }
    
    public static void setMapName(String name) {
    	mapName = name;
    }
    
    public static int[] getGroundSize() {
    	return new int[] {groundStartX, groundStartZ, groundEndX, groundEndZ};
    }
    
    public static String getGroundName() {
    	return groundName;
    }
    
    public static int getFirstPeriod() {
    	return firstPeriod;
    }
    
    public static String getMapName() {
    	return mapName;
    }

}
