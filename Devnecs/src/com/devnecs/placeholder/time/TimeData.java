package com.devnecs.placeholder.time;

public class TimeData {

	public String name;
	public String shortname;
	public long seconds;
	
	public TimeData(String name, String shortName, long seconds) {
		this.name = name;
		this.shortname = shortName;
		this.seconds = seconds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
}
