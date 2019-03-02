<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.javaproject.model.*"%>    
<%@ page import="com.javaproject.dao.*"%>  
<%@ page import="java.text.DateFormat"%>    
<%@ page import="java.text.SimpleDateFormat"%> 
<%@ page import="java.util.Date"%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	
		Movie movie =  new Movie();
		MovieListDAO movieListDAO =  new MovieListDAO();
		movie = movieListDAO.getMovieById( (Integer)session.getAttribute("movieid"));
		String movieTitle = movie.getMovieTitle();
		session.setAttribute("movieid", movie.getMovieId());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		UserDAO userDAO = new UserDAO();
		Object obj = session.getAttribute("userid");
		Integer userid = (Integer) obj;
		String email = userDAO.getEmailById(userid);
		ShowTime showtime = movieListDAO.getMovieShowTimeForToday(movie.getMovieId());
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
		Date startDate = new Date(showtime.getStartTime().getTime());
		String formattedStartDate = sdf.format(startDate);
		Date endDate = new Date(showtime.getEndTime().getTime());
		String formattedEndDate = sdf.format(endDate);
		int availableSeats = showtime.getAvailableSeats();
		
		
		
		

%>
<html>
    <head>
        <style>
        /* Basics */
        html, body
        {
            padding: 0;
            margin: 0;
            width: 100%;
            height: 100%;
            font-family: "Helvetica Neue" , Helvetica, sans-serif;
            background: #FFFFFF;
        }
        .logincontent
        {
            position: fixed;
            width: 350px;
            height: 300px;
            top: 50%;
            left: 50%;
            margin-top: -150px;
            margin-left: -175px;
            background: #07A8C3;
            padding-top: 10px;
        }
        .loginheading
        {
            border-bottom: solid 1px #ECF2F5;
            padding-left: 18px;
            padding-bottom: 10px;
            color: #ffffff;
            font-size: 20px;
            font-weight: bold;
            font-family: sans-serif;
        }
        label
        {
            color: #ffffff;
            display: inline-block;
            margin-left: 18px;
            padding-top: 10px;
            font-size: 15px;
        }
        input[type=text], input[type=password]
        {
            font-size: 14px;
            padding-left: 10px;
            margin: 10px;
            margin-top: 12px;
            margin-left: 18px;
            width: 300px;
            height: 35px;
            border: 1px solid #ccc;
            border-radius: 2px;
            box-shadow: inset 0 1.5px 3px rgba(190, 190, 190, .4), 0 0 0 5px #f5f5f5;
            font-size: 14px;
        }
        input[type=checkbox]
        {
            margin-left: 18px;
            margin-top: 30px;
        }
        .loginremember
        {
            background: #ECF2F5;
            height: 70px;
            margin-top: 20px;
        }
        .check
        {
            font-family: sans-serif;
            position: relative;
            top: -2px;
            margin-left: 2px;
            padding: 0px;
            font-size: 12px;
            color: #321;
        }
        .loginbtn
        {
            
           
            font-size: 14px;
            font-weight: bold;
            color: #fff;
            background-color: #07A8C3;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#07A8C3), to(#6EE4E8));
            background-image: -moz-linear-gradient(top left 90deg, #07A8C3 0%, #6EE4E8 100%);
            background-image: linear-gradient(top left 90deg, #07A8C3 0%, #6EE4E8 100%);
             padding: 15px 24px;
            border: none;
            cursor: pointer;
        }
        .loginbtn:hover
        {
            background-image: -webkit-gradient(linear, left top, left bottom, from(#b6e2ff), to(#6ec2e8));
            background-image: -moz-linear-gradient(top left 90deg, #b6e2ff 0%, #6ec2e8 100%);
            background-image: linear-gradient(top left 90deg, #b6e2ff 0%, #6ec2e8 100%);
        }
        h1
        {
            border-bottom: solid 1px #ECF2F5;
            padding-left: 18px;
            background-color:#F5F5F5
            padding-bottom: 10px;
            color: #07A8C3;
            font-size: 30px;
            font-weight: bold;
            font-family: sans-serif;
        }
    a:link, a:visited {
    background-color: #07A8C3;
    color: white;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    
}
ul {
    
    padding: 20px;
}
li {
	color:#07A8C3;
    background: #F5F5F5;
    margin: 5px;
    
}

a:hover, a:active {
    background-color: #07A8C3;
}

.row {
  display: flex;
  flex-wrap: wrap;
  padding: 0 4px;
}

/* Create two equal columns that sits next to each other */
.column {
  flex: 50%;
  padding: 0 4px;
}

.column img {
  margin-top: 8px;
  vertical-align: middle;
}
div{
  
  width:auto;
  height:auto;
  padding:1em;
}
</style>
       
        <title>Movie Booking</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
       
        <center>
<h1>Your booking is Done!!!</h1>
</center>

	
	<form action="HomeController" method="POST">
	<div class="section group">
			<table style="width:100%">
	<tr>		
	<th>
		<img src="<%="resources/images/"+movieTitle+".jpg"%>" height='200' width='200' />
	</th>	
	
	<th>
	<ul></ul>
	<li>
	<% 				
			out.print( "Movie Title: "+movie.getMovieTitle() );%> </li>
	<li><% 		
			out.print( "Description: "+movie.getDescription() ); %></li>
	<li> <%		
			out.print( "Movie Period in min: "+movie.getRunningTime() );
			%>	</li>	
				<li> <%		
			out.print( "Date: "+dateFormat.format(currentDate));
			%>	</li>
			<li> <%		
			out.print( "Current available seats: "+showtime.getAvailableSeats() );
			%>	</li>	
			<li> <%		
			out.print( "Movie Starttime: "+formattedStartDate +" CST (24 hour clock)" );
			%>	</li>	
			<li> <%		
			out.print( "Movie EndTime: "+formattedEndDate +" CST (24 hour clock)" );
			%>	</li>	
			</ul>
			</ul>
	</th>	
	</tr>
	</table>
	
	</div>

	<input type="submit" class="loginbtn" value="Home"/>	
	<a href="pages/monthlyMovieList.jsp">Monthly Movie List</a>		
	<a href="pages/cancelBooking.jsp">Cancel Booking</a>
	<a href="/UnionMovieBooking/HomeController">Home</a>	
	<a href="/UnionMovieBooking/LogoutController">Logout</a>	
	</form>
    </body>
</html>
