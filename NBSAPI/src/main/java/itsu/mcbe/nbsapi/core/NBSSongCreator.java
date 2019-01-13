package itsu.mcbe.nbsapi.core;

import itsu.mcbe.nbsapi.entities.Instrument;
import itsu.mcbe.nbsapi.entities.Layer;
import itsu.mcbe.nbsapi.entities.NBSSong;
import itsu.mcbe.nbsapi.entities.NoteBlock;
import itsu.mcbe.nbsapi.utils.Binary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Acknowledgement: https://github.com/xxmicloxx/NoteBlockAPI/blob/master/src/main/java/com/xxmicloxx/NoteBlockAPI/NBSDecoder.java
 * @author xxmicloxx
 * @author Itsu
 */

public class NBSSongCreator {

    public static NBSSong createNBSSongFromFile(String path) {
        File file = new File(path);
        DataInputStream stream = null;
        Map<Integer, Layer> layerData = null;
        NBSSong song = null;

        short length = 0;
        short height = 0;
        String name = null;
        String author = null;
        String originalAuthor = null;
        String description = null;
        short tempo = 0;

        if (!file.exists()) return null;

        try {
            stream = new DataInputStream(new FileInputStream(file));
            layerData = new HashMap<>();

            length = Binary.readShort(stream);
            height = Binary.readShort(stream);
            name = Binary.readString(stream);
            author = Binary.readString(stream);
            originalAuthor = Binary.readString(stream);
            description = Binary.readString(stream);
            tempo = (short) (Binary.readShort(stream) / 100);

            stream.readByte();  // Auto-Saving
            stream.readByte();  // Auto-Saving durationn
            stream.readByte();  // Time Signature
            stream.readInt();  // Minutes spent
            stream.readInt();  // Left clicks
            stream.readInt();  // Right clicks
            stream.readInt();  // Blocks added
            stream.readInt();  // Blocks removed
            Binary.readString(stream); // MIDI/Schematic file name

            short tick = -1;

            while (true) {
                short jumpTicks = Binary.readShort(stream);

                if (jumpTicks == 0) break;

                tick += jumpTicks;

                short layer = -1;

                while (true) {
                    short jumpLayers = Binary.readShort(stream);
                    if (jumpLayers == 0) break;
                    layer += jumpLayers;

                    if (layerData.get((int) layer) == null) {
                        layerData.put((int) layer, new Layer());
                    }

                    layerData.get((int) layer).setNoteBlock(tick, new NoteBlock(new Instrument(stream.readByte(), stream.readByte())));
                }

            }

            for (int i = 0; i < height; i++) {
                Layer layer = layerData.get(i);
                if (layer != null) {
                    layer.setName(Binary.readString(stream));
                    layer.setVolume(stream.readByte());
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println("File not found!: " + path);
            return null;

        } catch (IOException ex) {
            ex.printStackTrace();

        }

        song = new NBSSong(layerData, length, height, name, author, originalAuthor, description, tempo);

        return song;
    }
}
