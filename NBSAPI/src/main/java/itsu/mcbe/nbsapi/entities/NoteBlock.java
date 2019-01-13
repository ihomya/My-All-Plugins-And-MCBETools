package itsu.mcbe.nbsapi.entities;

public class NoteBlock {

    private Instrument instrument;

    public NoteBlock(Instrument instrument) {
        this.instrument = instrument;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
