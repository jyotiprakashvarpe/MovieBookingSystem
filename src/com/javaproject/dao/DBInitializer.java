package com.javaproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DBInitializer {
	private Statement stmt;
	 private PreparedStatement pstmt;
	 private Connection connection;
	 
	public DBInitializer() {
		super();
		initializeDB();
		
	}
	public Connection initializeDB() {
	    try {
	    //connection = DBManager.getConnection();
	    //System.out.println("Database connected");

	    		
	      // Load the JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      System.out.println("Driver loaded");

	      // Establish a connection
	      connection = DriverManager.getConnection
	        ("jdbc:mysql://localhost/unionmoviebooking", "jyoti", "jyo@123");

	      System.out.println("Database connected");

	      // Create a statement
	      stmt = connection.createStatement();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return connection;
	  }

}
