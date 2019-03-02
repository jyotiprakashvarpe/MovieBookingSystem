package com.javaproject.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.javaproject.dao.DBInitializer;
import com.javaproject.model.Movie;
import com.javaproject.model.ShowTime;
import com.javaproject.util.DBManager;

public class AdminService {
	 
		DBInitializer database= new DBInitializer();
		Connection conn = database.initializeDB();
	public long addMovie(String title, String description, String posterurl, int duration ){
		try{
			PreparedStatement pstmt=conn.prepareStatement("INSERT INTO MOVIES (TITLE, DESCRIPTION, POSTERURL,RUNNINGTIME) VALUES (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
//			pstmt.executeUpdate(PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, title);
			pstmt.setString(2, description);
			pstmt.setString(3, posterurl);
			pstmt.setInt(4, duration);
			pstmt.executeUpdate();
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			while(generatedKeys.next())
			return generatedKeys.getLong(1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	}

	public long addShow(String movieid, Date starttime, Date endtime, int seats, Date showDate ){
		try{
			//INSERT INTO `showtime` (`showid`, `movieid`, `starttime`, `endtime`, `seats`, `showdate`) VALUES (NULL, '', '', '', '', NULL)
			PreparedStatement pstmt=conn.prepareStatement("INSERT INTO SHOWTIME (movieid, starttime, endtime, seats, showdate) VALUES (?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
//			pstmt.executeUpdate(PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, movieid);
			pstmt.setTimestamp(2, new java.sql.Timestamp(starttime.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(endtime.getTime()));
			pstmt.setInt(4, seats);
			pstmt.setTimestamp(5, new java.sql.Timestamp(showDate.getTime()));
			pstmt.executeUpdate();
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			while(generatedKeys.next())
			return generatedKeys.getLong(1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	}
	
	public List<Movie> getMovieSchedule() {
		List<Movie> movieList = new ArrayList<>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT movies.movieid, movies.title, movies.description, movies.posterurl, movies.runningtime,"
					+ "showtime.showid, showtime.starttime, showtime.seats "
					+ "FROM MOVIES LEFT OUTER JOIN SHOWTIME ON movies.movieid = showtime.movieid");
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfHr = new SimpleDateFormat("HH");
			SimpleDateFormat sdfMin = new SimpleDateFormat("mm");
			HashMap<Integer, Movie> movieMap = new HashMap<>();
			while(rs.next()){
				int movieId = rs.getInt(1);
				if(!movieMap.containsKey(movieId)){
					Movie m = new Movie();
					String movieTitle = rs.getString(2);
					String description = rs.getString(3);;
					String posterURL = rs.getString(4);
					int runningTime = rs.getInt(5);
					List<ShowTime> showList = new ArrayList<>();
					m.setMovieId(movieId);
					m.setDescription(description);
					m.setMovieTitle(movieTitle);
					m.setPosterURL(posterURL);
					m.setRunningTime(runningTime);
					m.setShowList(showList);
					if(rs.getTimestamp(7)!=null){
						ShowTime show = new ShowTime();
						show.setId(rs.getInt(6));
						show.setStartTime(rs.getTimestamp(7)!=null?new Date(rs.getTimestamp(7).getTime()):null);
						show.setAvailableSeats(30-rs.getInt(8));
						showList.add(show);
						show.setDisplayDate(sdfDate.format(show.getStartTime()));
						show.setDisplayDate(sdfDate.format(show.getStartTime()));
						show.setHrs(sdfHr.format(show.getStartTime()));
						show.setMin(sdfMin.format(show.getStartTime()));
					}
					movieMap.put(movieId, m);
				}else{
					Movie m = movieMap.get(movieId);
					ShowTime show = new ShowTime();
					show.setId(rs.getInt(6));
					show.setStartTime(rs.getTimestamp(7)!=null?new Date(rs.getTimestamp(7).getTime()):null);
					show.setAvailableSeats(30-rs.getInt(8));
					show.setDisplayDate(sdfDate.format(show.getStartTime()));
					show.setHrs(sdfHr.format(show.getStartTime()));
					show.setMin(sdfMin.format(show.getStartTime()));
					List<ShowTime> showList = m.getShowList();
					showList.add(show);
				}
			}
			movieList.addAll(movieMap.values());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movieList;
	}
	
	public static void main(String[] args) {
		AdminService serv = new AdminService();
//		serv.addMovie("Movie1", "asdasdasd ads asdasd ", "/image/movie1.jpg",120);
		System.out.println(serv.updateMovie(1, "UpdatedTitle", "desc", "UpdatedTitle"));
	}

	public boolean validateShowTiming(Date startDate, Date endTime) {
		boolean valid = false;
		System.out.println(startDate);
		System.out.println(endTime);
		String query = "SELECT showid FROM showtime WHERE showtime.starttime BETWEEN ? AND ? OR showtime.endtime BETWEEN ? AND ?";
		try{
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
			ps.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(startDate.getTime()));
			ps.setTimestamp(4, new java.sql.Timestamp(endTime.getTime()));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				System.out.println(rs.getInt(1));
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valid;
	}

	public long updateMovie(int id, String title, String description, String title2) {

		try{
			//INSERT INTO MOVIES (TITLE, DESCRIPTION, POSTERURL,RUNNINGTIME) VALUES (?,?,?,?)
			PreparedStatement pstmt=conn.prepareStatement("UPDATE MOVIES SET TITLE = ?, DESCRIPTION = ?,"
					+ "POSTERURL=?"
					+ " WHERE movieid = ?");
//			pstmt.executeUpdate(PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, title);
			pstmt.setString(2, description);
			pstmt.setString(3, title2);
			pstmt.setInt(4, id);
			int rowCnt = pstmt.executeUpdate();
			return 0L+rowCnt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	
	}

	public int deleteMovie(int id) {


		try{
			//INSERT INTO MOVIES (TITLE, DESCRIPTION, POSTERURL,RUNNINGTIME) VALUES (?,?,?,?)
			PreparedStatement pstmt=conn.prepareStatement("DELETE FROM MOVIES "
					+ " WHERE movieid = ?");
//			pstmt.executeUpdate(PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, id);
			int rowCnt = pstmt.executeUpdate();
			return rowCnt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	
	}

	public int updateShow(int id, String movieID, Date startTime, Date endTime, int i, Date startDate) {
		try{
			//INSERT INTO SHOWTIME (movieid, starttime, endtime, seats, showdate) VALUES (?,?,?,?,?)
			PreparedStatement pstmt=conn.prepareStatement("UPDATE SHOWTIME SET starttime = ?, endtime = ?,"
					+ "showdate=?"
					+ " WHERE showid = ?");
//			pstmt.executeUpdate(PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setTimestamp(1, new Timestamp(startTime.getTime()));
			pstmt.setTimestamp(2, new Timestamp(endTime.getTime()));
			pstmt.setTimestamp(3, new Timestamp(startDate.getTime()));
			pstmt.setInt(4, id);
			int rowCnt = pstmt.executeUpdate();
			return rowCnt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	}
	
	public int deleteShow(int id) {


		try{
			//INSERT INTO MOVIES (TITLE, DESCRIPTION, POSTERURL,RUNNINGTIME) VALUES (?,?,?,?)
			PreparedStatement pstmt=conn.prepareStatement("DELETE FROM ShowTime "
					+ " WHERE showid = ?");
//			pstmt.executeUpdate(PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, id);
			int rowCnt = pstmt.executeUpdate();
			return rowCnt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	
	}
	
	
}
