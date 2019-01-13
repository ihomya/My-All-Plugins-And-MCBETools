package itsu.mcbe.economysystemland.api;

import java.util.List;

public class ProtectedLand {
	
	private int id;
	private int startX;
	private int startZ;
	private int endX;
	private int endZ;
	private int size;
	private String world;
	private String owner;
	private List<String> invite;
	
	public ProtectedLand(int id, int startX, int startZ, int endX, int endZ, int size, String world, String owner, List<String> invite) {
		this.id = id;
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
		this.size = size;
		this.world = world;
		this.owner = owner;
		this.invite = invite;
	}
	
	public int getId() {
		return id;
	}
	
	public int getStartX() {
		return startX;
	}
	
	public int getStartZ() {
		return startZ;
	}
	
	public int getEndX() {
		return endX;
	}
	
	public int getEndZ() {
		return endZ;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getWorld() {
		return world;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public List<String> getInvite() {
		return invite;
	}

}
