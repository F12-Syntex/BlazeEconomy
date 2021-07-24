package com.devnecs.commands;

import java.util.ArrayList;
import java.util.List;

public class AutoComplete {

	private List<String> paths;
	
	
	public AutoComplete() {
		this.paths = new ArrayList<String>();
	}
	
	public void createEntry(String entry) {
		this.paths.add(entry);
	}
	
	public List<String> getPaths() {
		return this.paths;
	}
	
	public List<String> filter(String key){
		
		String[] keys = key.split("\\.");
		
		List<String> filter = new ArrayList<String>();
		
		if(keys.length == 1) {
			for(String o : paths) {
				if(o.contains(".")) {
					filter.add(o.split("\\.")[0]);
				}else {
					filter.add(o);
				}
			}
			return filter;
		}
		
		String parentKey = key.substring(key.split("\\.")[0].length() + 1); 
		
		List<String> valid = new ArrayList<String>();
		
		for(String i : paths) {
			if(i.toLowerCase().startsWith(parentKey) && !i.toLowerCase().equals(parentKey)) {
				
				String filtered = i.substring(parentKey.length() + 1);
				
				if(filtered.contains(".")) {
					valid.add(filtered.split("\\.")[0]);
				}else {
					valid.add(filtered);
				}
				
			}
		}
		return valid;
	}
	
}
