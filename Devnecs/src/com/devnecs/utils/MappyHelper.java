package com.devnecs.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MappyHelper {
	
	private List<Map<?,?>> map;
	
	public MappyHelper(List<Map<?,?>> map) {
		this.setMap(map);
	}

	public List<Map<?,?>> getMap() {
		return map;
	}

	public void setMap(List<Map<?,?>> map) {
		this.map = map;
	}
	
	public int getMin() {
		List<Integer> values = new ArrayList<Integer>();
		
		for(Map<?,?> i : map) {
			Object key = i.keySet().toArray()[0];
			Object value = i.get(key);
			values.add(Integer.parseInt(value.toString()));
		}
		
		 return values.stream().mapToInt(Integer::intValue).min().getAsInt();
	}
	
	public int getMax() {
		List<Integer> values = new ArrayList<Integer>();
		
		for(Map<?,?> i : map) {
			Object key = i.keySet().toArray()[0];
			Object value = i.get(key);
			values.add(Integer.parseInt(value.toString()));
		}
		
		 return values.stream().mapToInt(Integer::intValue).max().getAsInt();
	}

	public List<MappyObject> decode() {
		
		List<MappyObject> content = new ArrayList<MappyObject>();
		
		for(Map<?,?> i : map) {
			Object key = i.keySet().toArray()[0];
			Object value = i.get(key);
			MappyObject mappy = new MappyObject(key, value);
			content.add(mappy);
		}
		
		return content;
		
	}
	
}
