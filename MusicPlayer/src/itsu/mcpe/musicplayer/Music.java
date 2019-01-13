package itsu.mcpe.musicplayer;

public class Music {
	
	private String year = "";
	private String token = "";
	private String artist = "";
	private String composer = "";
	private String genre = "";
	private String title = "";
	
	public Music(String title, String artist, String year, String token, String composer, String genre) {
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.token = token;
		this.composer = composer;
		this.genre = genre;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getYear() {
		return year;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getComposer() {
		return composer;
	}
	
	public String getGenre() {
		return genre;
	}
	
	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("[");
		bf.append(title);
		bf.append(", ");
		bf.append(artist);
		bf.append(", ");
		bf.append(year);
		bf.append(", ");
		bf.append(token);
		bf.append(", ");
		bf.append(composer);
		bf.append(", ");
		bf.append(genre);
		bf.append("]");
		return bf.toString();
	}
}
