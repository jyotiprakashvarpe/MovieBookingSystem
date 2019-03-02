package com.javaproject.util;

import java.util.ResourceBundle;

public class AdminUtils {

	private static ResourceBundle rb = ResourceBundle.getBundle("resources");
	
	public static String getPhotoPath(){
		return rb.getString("photoPath");
	}
}
