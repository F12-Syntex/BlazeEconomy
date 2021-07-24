package com.devnecs.cooldown;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CooldownManager {

	private List<CooldownUser> users;
	private List<CooldownRunnable> runnables;
	
	public CooldownManager() {
		setUsers(new ArrayList<CooldownUser>());
		setRunnables(new ArrayList<CooldownRunnable>());
	}
	
	public CooldownUser getUser(UUID uuid) {
		
		Stream<CooldownUser> stream = users.stream();
		
		List<CooldownUser> target = stream.filter(i -> i.getUuid().compareTo(uuid) == 0).collect(Collectors.toList());
		
		if(target.isEmpty()) {
			CooldownUser newUser = new CooldownUser(uuid);
			this.users.add(newUser);
			return newUser;
		}
		
		return target.get(0);
		
	}

	public List<CooldownUser> getUsers() {
		return users;
	}

	public void setUsers(List<CooldownUser> users) {
		this.users = users;
	}

	public List<CooldownRunnable> getRunnables() {
		return runnables;
	}

	public void setRunnables(List<CooldownRunnable> runnables) {
		this.runnables = runnables;
	}
	
	
	
	
}
