package org.mehaexample.asdDemo.utils;

public class StringUtils {
	
	public static boolean isNullOrEmpty(String str){
		
		if (str == null && str.trim().length() == 0){
			return true;
		}
		
		return false;
	}

}
