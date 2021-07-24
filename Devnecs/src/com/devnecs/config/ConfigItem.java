package com.devnecs.config;

public class ConfigItem {
	
	private String path;
	private Object data;
	public ConfigItem(String path, Object data) {
		this.path = path;
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getData() {
		return data;
	}
}
