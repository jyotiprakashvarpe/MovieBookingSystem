package com.javaproject.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaproject.model.Movie;
import com.javaproject.model.ShowTime;

public class MovieListDAO {
	private Statement stmt;
	 private PreparedStatement pstmt,pstmt2;
	 private Connection connection;
	public ShowTime getMovieShowTimeForToday(int movieid) {
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		String query;
		ShowTime showtime = new ShowTime();
		try {
			query = "select * from showtime where movieid = ? and showdate=CURDATE()";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, movieid);
			System.out.println(pstmt.toString());
			ResultSet rset = pstmt.executeQuery(); 
			
		    if(rset.next()) {
		    	System.out.println(rset.getInt(1));
		    	showtime.setId(rset.getInt(1));
		    	showtime.setMovie(this.getMovieById(rset.getInt(2)));
		    	System.out.println(rset.getInt(2));
		    	System.out.println("Starttime:"+rset.getTimestamp(3));
		    	System.out.println("Endtime:"+rset.getTimestamp(4));
		    	showtime.setStartTime(rset.getTimestamp(3));
		    	showtime.setEndTime(rset.getTimestamp(4));
		    	showtime.setAvailableSeats(rset.getInt(5));
		    	System.out.println(rset.getInt(5));
		    	
		    	
		    }
		}
		catch(Exception ex){
			ex.getStackTrace();
		}
		return showtime;
	}
	public void addBookingEntry(HttpServletRequest request, HttpServletResponse response,ServletContext sc) {
		Statement stmt;
		 PreparedStatement pstmtone,pstmttwo;
		 Connection connection;
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		String queryone,querytwo;
		
		HttpSession session = request.getSession();
		//PrintWriter out = response.getWriter();
       response.setContentType("text/html;charset=UTF-8");
       int movieid = (Integer)session.getAttribute("movieid");
       ShowTime showtime= new MovieListDAO().getMovieShowTimeForToday(movieid);	
      // ServletContext sc = this.getServletContext();
       String isUserExists =null;
       int requriedSeats = Integer.parseInt(request.getParameter("seats"));
       int availableSeats = showtime.getAvailableSeats();
       try {
		querytwo = "INSERT INTO booking(userid,movieid,showid,seatsbooked) VALUES(?,?,?,?)";
		System.out.println(querytwo);
		Object useridString = session.getAttribute("useridstring");
		String userStr = (String) useridString;
		pstmttwo = connection.prepareStatement(querytwo);
		pstmttwo.setString(1, userStr);
		pstmttwo.setInt(2,movieid );
		pstmttwo.setInt(3, showtime.getId());
		pstmttwo.setInt(4, requriedSeats);
		System.out.println(pstmttwo.toString());
		pstmttwo.execute();
		System.out.println("Output of booking table entry ");
       }
		catch(Exception ex){
			ex.getStackTrace();
		}
		
	}
    public Movie getMovieById(int movieid) {
    	DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		String query;
		Movie movie = new Movie();
		try 
		{
			
			query = "select * from movies where movieid = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, movieid);
			System.out.println(pstmt.toString());
			ResultSet rset = pstmt.executeQuery();    	    	
		    	if(rset.next()) {
		    		
		    		movie.setMovieId(rset.getInt(1));
		    		movie.setMovieTitle(rset.getString(2));
		    		movie.setDescription(rset.getString(3));
		    		movie.setRunningTime(rset.getInt(5));
		    		
		    	}
		    	
		  } 
		  catch (SQLException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
    	return movie;
    }
	public ArrayList<Movie> getTodayMovieList(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		DBInitializer database= new DBInitializer();
		connection = database.initializeDB();
		String query,query2;
		ArrayList<Movie> currentMovieList = new ArrayList<>();
		try 
		{
			
			query = "select * from showtime where showdate = CURDATE()";
			pstmt = connection.prepareStatement(query);
			//pstmt.setString(1, dateFormat.format(currentDate));
			//System.out.println(pstmt.toString());
			ResultSet rset = pstmt.executeQuery();
		    while (rset.next()) {
		    	
		    	query2 = "select * from movies where movieid = ?";
		    	pstmt2 = connection.prepareStatement(query2);	    	
		    	int showid = rset.getInt(1);
		    	//System.out.println(showid);
		    	int movieid = rset.getInt(2);
		    	//System.out.println(movieid);
		    	pstmt2.setInt(1, movieid);
		    	ResultSet rset2 = pstmt2.executeQuery();
		    	if(rset2.next()) {
		    		Movie movie = new Movie();
		    		movie.setMovieId(rset2.getInt(1));
		    		movie.setMovieTitle(rset2.getString(2));
		    		movie.setDescription(rset2.getString(3));
		    		movie.setRunningTime(rset2.getInt(5));
		    		currentMovieList.add(movie);
		    	}
		    	Date starttime = rset.getDate(3);
		    	Date endtime = rset.getDate(4);
		    	int seats = rset.getInt(5);
       
		        
		      } 
		    	
		  } 
		  catch (SQLException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		return currentMovieList;
	}
}
