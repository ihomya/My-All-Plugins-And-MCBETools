package itsu.mcbe.unknownenemy.game;

import java.util.Random;

import cn.nukkit.item.ItemMap;

public class GamePlayer {
	
	private String name;
	private int lang;// 0=JPN 1=ENG
	private boolean isPlaying;
	private int id;
	private boolean isEntried;
	private boolean canMove = false;
	private ItemMap map;
	
	public GamePlayer(String name, int lang) {
		this.name = name;
		this.lang = lang;
		this.isPlaying = false;
		this.id = new Random().nextInt(0x7fffffff);
		this.isEntried = false;
		this.canMove = true;
		this.map = new ItemMap();
	}
	
	public String getName() {
		return name;
	}
	
	public int getLanguage() {
		return lang;
	}
	
	public int getId() {
		return id;
	}
	
	public ItemMap getMap() {
		return map;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public boolean isEntried() {
		return isEntried;
	}
	
	public boolean canMove() {
		return canMove;
	}
	
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public void setEntried(boolean isEntried) {
		this.isEntried = isEntried;
	}
	
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
	
	public void reset() {
		map = null;
	}

}
