package itsu.mcbe.nbsapi.entities;

import cn.nukkit.Player;

import java.util.Map;

public class NBSSong {

    private Map<Integer, Layer> soundData;
    private short length;
    private short height;
    private String name;
    private String author;
    private String originalAuthor;
    private String description;
    private short tempo;

    public NBSSong(Map<Integer, Layer> soundData, short length, short height, String name, String author, String originalAuthor, String description, short tempo) {
        this.soundData = soundData;
        this.length = length;
        this.height = height;
        this.name = name;
        this.author = author;
        this.originalAuthor = originalAuthor;
        this.description = description;
        this.tempo = tempo;
    }

    public Map<Integer, Layer> getSoundData() {
        return soundData;
    }

    public void setSoundData(Map<Integer, Layer> soundData) {
        this.soundData = soundData;
    }

    public short getTempo() {
        return tempo;
    }

    public void setTempo(short tempo) {
        this.tempo = tempo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public void setOriginalAuthor(String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }
}
