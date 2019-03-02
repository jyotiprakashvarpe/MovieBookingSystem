package com.javaproject.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBManager {

	private static ResourceBundle rb = ResourceBundle.getBundle("DB.properties");
	private static Connection conn;
	
	private static String getDriver(){
		System.out.println(rb.getString("driver"));
		return rb.getString("driver");
	}
	
	private static String getDBURL(){
		System.out.println(rb.getString("url"));
		return rb.getString("url");
	}
	
	private static String getUserName(){
		System.out.println(rb.getString("user"));
		return rb.getString("user");
	}
	
	private static String getPassword(){
		System.out.println(rb.getString("password"));
		return rb.getString("password");
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(conn!=null && !conn.isClosed()){
			return conn;
		}
		Class.forName(getDriver());
		conn = DriverManager.getConnection(getDBURL(), getUserName(), getPassword());
		return conn;
	}
	
	public void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
