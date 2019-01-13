package itsu.mcbe.nbsapi;

import itsu.mcbe.nbsapi.entities.NBSSong;
import itsu.mcbe.nbsapi.core.NBSSongCreator;

public class NBSAPI {

    public static NBSSong createNBSSongFromFile(String path) {
        return NBSSongCreator.createNBSSongFromFile(path);
    }

    public static void playNBSSong(NBSSong nbsSong) {

    }

}
