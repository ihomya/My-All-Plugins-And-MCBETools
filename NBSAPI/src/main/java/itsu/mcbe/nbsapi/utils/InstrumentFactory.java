package itsu.mcbe.nbsapi.utils;

import cn.nukkit.level.Sound;

public class InstrumentFactory {

    public static Sound get(int instrument) {
        switch (instrument) {
            case 0:
                return Sound.NOTE_HARP;

            case 1:
                return Sound.NOTE_BASS;

            case 2:
                return Sound.NOTE_BD;

            case 3:
                return Sound.NOTE_SNARE;

            case 4:
                return Sound.NOTE_BASSATTACK;

            case 5:
                return Sound.NOTE_HAT;

            case 6:
                return Sound.NOTE_HARP;

            case 7:
                return Sound.NOTE_HARP;

            case 8:
                return Sound.NOTE_HARP;

            case 9:
                return Sound.NOTE_HARP;

            default:
                return Sound.NOTE_HARP;
        }
    }
}
