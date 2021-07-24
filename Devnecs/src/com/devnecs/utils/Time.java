package com.devnecs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Time {

	public static Date convertStringToDate(String inputString) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date date = sdf1.parse(inputString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getCurrentTimeInString() {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
		String outputString = sdf2.format(Calendar.getInstance().getTime());
		return outputString;
	}
	
	public static Date getCurrentTimeInDate() {
		return new Date(System.currentTimeMillis());
	}
	
	public static String ConvertDateToString(Date date) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
		String outputString = sdf2.format(date);
		return outputString;
	}
	
	public static long getCurrentDateAsInt() {
		return System.currentTimeMillis();
	}
	
	public static Date longToDate(long date) {
		return new Date(date);
	}
	
	public static long difference(Date join, Date leave) {
	    long diffInMillies = Math.abs(join.getTime() - leave.getTime());
	    long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    return diff;
	}
	
	
}
	