//Itsu Original

package com.nao20010128nao.BloodyGarden.structures;

public class Group extends GroupSmall{
	public Chat[] chats;
	public User owner;
	public User[] sub_owners;
	public Object[] needs_to_join;
	public String now;
	public GroupMe me;
	public GroupBookmarkInfo group_bookmark_info;
	
	public User[] members;
	public Integer members_count;
	public Long members_next_cursor;
}
