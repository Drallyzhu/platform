package com.cloudsoft.common.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {
	/**
	 * 是否正整数
	 */
	public static final boolean isPositiveInteger(Object value){
		return value != null && org.apache.commons.lang3.math.NumberUtils.isDigits(value.toString()) && Integer.parseInt(value.toString()) > 0;
	}

	public static boolean isInteger(Object value){
		if(value != null){
			Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(value.toString());
			return mer.find();
		}
		return false;
	}

	public static boolean isPositiveNumber(Object value){
		if(value != null){
			try{
				double d = Double.parseDouble(value.toString());
				return d > 0;
			}catch(Exception e){
				return false;
			}
		}
		return false;
	}

	public static boolean isNumber(Object value){
		if(value != null){
			try{
				Double.parseDouble(value.toString());
			}catch(Exception e){
				return false;
			}
			return true;
		}
		return false;
	}
}
