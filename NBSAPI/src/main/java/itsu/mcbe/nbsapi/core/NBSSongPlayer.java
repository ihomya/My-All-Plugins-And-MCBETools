package itsu.mcbe.nbsapi.core;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.network.protocol.PlaySoundPacket;
import itsu.mcbe.nbsapi.entities.Layer;
import itsu.mcbe.nbsapi.entities.NBSSong;
import itsu.mcbe.nbsapi.entities.NoteBlock;
import itsu.mcbe.nbsapi.utils.InstrumentFactory;
import itsu.mcbe.nbsapi.utils.PitchFactory;

public class NBSSongPlayer {

    public static void playNBSSongAt(Player player, Level level, Location loc, NBSSong nbsSong, int tick) {
        if (loc == null) {
            System.err.println("Location must not be null.");
            return;
        }

        if (nbsSong == null) {
            System.err.println("NBSSong must not be null.");
            return;
        }

        for (Layer layer : nbsSong.getSoundData().values()) {
            NoteBlock block = layer.getLayer().get(tick);

            if (block == null) continue;
            PlaySoundPacket pk = new PlaySoundPacket();
            pk.name = "note.harp";
            pk.pitch = PitchFactory.getPitch(block.getInstrument().getKey() - 33);
            pk.volume = 1.0f;
            pk.x = (int) loc.x;
            pk.y = (int) loc.y;
            pk.z = (int) loc.z;
            player.dataPacket(pk);

            //level.addSound(loc, InstrumentFactory.get(block.getInstrument().getInstrument()), 1.0f, PitchFactory.getPitch(block.getInstrument().getKey() - 33));
        }
    }
}
