package itsu.mcbe.nbsapi.entities;

import java.util.HashMap;
import java.util.Map;

public class Layer {

    private Map<Integer, NoteBlock> layer;
    private byte volume = 100;
    private String name = "";

    public Layer() {
        this(new HashMap<>(), (byte) 100, "");
    }

    public Layer(Map<Integer, NoteBlock> layer, byte volume, String name) {
        this.layer = layer;
        this.volume = volume;
        this.name = name;
    }

    public byte getVolume() {
        return volume;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, NoteBlock> getLayer() {
        return layer;
    }

    public void setLayer(Map<Integer, NoteBlock> layer) {
        this.layer = layer;
    }

    public void setNoteBlock(int tick, NoteBlock noteBlock) {
        layer.put(tick, noteBlock);
    }
}
