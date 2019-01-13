package itsu.mcbe.nbsapi.entities;

public class Instrument {

    private int instrument;
    private int key;

    public Instrument(int instrument, int key) {
        this.instrument = instrument;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }
}
