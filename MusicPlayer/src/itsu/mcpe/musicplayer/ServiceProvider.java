package itsu.mcpe.musicplayer;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {

    private static Map<String, Music> musics = new HashMap<>();

    public ServiceProvider() {

    }

    public static void setMusics(Map<String, Music> musics) {
        ServiceProvider.musics = musics;
    }

    public static Map<String, Music> getMusics() {
        return musics;
    }

}
