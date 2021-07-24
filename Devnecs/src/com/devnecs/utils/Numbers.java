package com.devnecs.utils;

public class Numbers {

	
	public static boolean isNumber(String i) {
		try {
			Integer.parseInt(i);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
}
