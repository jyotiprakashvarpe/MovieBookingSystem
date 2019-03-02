package com.javaproject.dao;
import java.sql.*;

import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.util.Arrays;

import com.javaproject.model.Movie;
import com.javaproject.util.DBManager;
import com.mysql.jdbc.Driver;

public class UserDAO {
	 private Statement stmt;
	 private PreparedStatement pstmt;
	 private Connection connection;
	 
	public UserDAO() {
		super();
		//initializeDB();
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		
	}
	private void initializeDB() {
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
	  }
//
	public String getEmailById(int id) {
		String query=null;
		String email=null;
		try 
		{
			
			query = "select username from user where userid = ?";
			pstmt = connection.prepareStatement(query);
			
			pstmt.setInt(1, id);
			
			System.out.println(pstmt.toString());
			ResultSet rset = pstmt.executeQuery();
			
		    if (rset.next()) {
		    	
		        
		        String username= rset.getString(1);
		        email = username+"@ucmo.edu";
		        
		        
		      } 
		   	
		  } 
		  catch (SQLException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	
		return email;
	}
	public String validateLoginInfo(int userId, String password) {
		String loginInfo = null;
		String query;
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		
		try 
		{
			
			query = "select * from user where userid = ? and password = ?";
			pstmt = connection.prepareStatement(query);
			System.out.println(userId+" "+password);
			pstmt.setInt(1, userId);
			pstmt.setString(2, password);
			System.out.println(pstmt.toString());
			ResultSet rset = pstmt.executeQuery();
			int userid; 
			//= rset.getInt(1);
	        System.out.println(rset.toString());
		    if (rset.next()) {
		    	
		        userid = rset.getInt(1);
		        //System.out.println(userid);
		        String username= rset.getString(2);
		        //System.out.println(username);
		        String role = rset.getString(3);
		        //System.out.println(role);
		        if(role.equalsIgnoreCase("student")) {
		        	loginInfo = "student";
		        	
		        	MovieListDAO movieList = new MovieListDAO();
		        	for(Movie movie:movieList.getTodayMovieList()) {
		        	//	System.out.println(movie.toString());
		        	}
		        }
		        else if(role.equalsIgnoreCase("admin")) {
		        	loginInfo = "admin";
		        			}
		        
		      } 
		    else {
		    	loginInfo = "invalid";
		    }		
		  } 
		  catch (SQLException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		return loginInfo;
	}


}
