package itsu.mcbe.unknownenemy.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameServer {

    private static List<GamePlayer> gamePlayers;
    private static List<GamePlayer> entries;
    private static boolean isGameStarted;
    private static BufferedImage mapImage = null;
    
    private static int mapX;
    private static int mapZ;

    private static int gamePlayersCount;

    public static void startGameServer() {
        gamePlayers = new ArrayList<>();
        entries = new ArrayList<>();
        isGameStarted = false;
    }

    public static void addGamePlayer(GamePlayer player) {
        if(!gamePlayers.contains(player)) gamePlayers.add(player);
    }

    public static void removeGamePlayer(GamePlayer player) {
        gamePlayers.remove(player);
    }

    public static Stream<GamePlayer> getPlayingGamePlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.isPlaying());
    }

    public static Stream<GamePlayer> getNotPlayingGamePlayers() {
        return gamePlayers.stream()
                .filter(gamePlayer -> !gamePlayer.isPlaying());
    }

    public static GamePlayer getGamePlayerByName(String name) {
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getName().equals(name))
                .toArray(GamePlayer[]::new)[0];
    }

    public static int getPlayingGamePlayersCount() {
        return gamePlayers.size();
    }

    public static void entryGame(GamePlayer player) {
        if (!entries.contains(player)) entries.add(player);

        if (getEntriesCount() == GameData.getMaxGamersCount()) {
            entries.stream().forEach(entry -> entry.setPlaying(true));
            entries.clear();
            isGameStarted = true;
            GameController.startGame();
        }

    }

    public static void cancelEntry(GamePlayer player) {
        entries.remove(player);
    }

    public static void resetEntries(GamePlayer player) {
        entries.clear();
    }

    public static int getEntriesCount() {
        return entries.size();
    }

    public static boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean isGameStarted) {
        GameServer.isGameStarted = isGameStarted;
    }

    public static int getGamePlayersCount() {
        return gamePlayersCount;
    }

    public static void increaseGamePlayersCount() {
        gamePlayersCount++;
    }

    public static void decreaseGamePlayersCount() {
        gamePlayersCount--;
    }
    
    public static void setMapImage(BufferedImage image) {
    	mapImage = image;
    }
    
    public static void setMapX(int x) {
    	mapX = x;
    }
    
    public static void setMapZ(int z) {
    	mapZ = z;
    }
    
    public static int getMapX() {
    	return mapX;
    }
    
    public static int getMapZ() {
    	return mapZ;
    }
    
    public static BufferedImage getMapImage() {
    	return mapImage;
    }

}
