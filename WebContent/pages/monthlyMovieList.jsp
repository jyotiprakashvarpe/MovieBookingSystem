<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="com.javaproject.dao.DBInitializer"%>
<html>
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
            float: right;
            margin-right: 20px;
            margin-top: 20px;
            padding: 6px 20px;
            font-size: 14px;
            font-weight: bold;
            color: #fff;
            background-color: #07A8C3;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#07A8C3), to(#6EE4E8));
            background-image: -moz-linear-gradient(top left 90deg, #07A8C3 0%, #6EE4E8 100%);
            background-image: linear-gradient(top left 90deg, #07A8C3 0%, #6EE4E8 100%);
            border-radius: 30px;
            border: 1px solid #07A8C3;
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
            font-size: 40px;
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
       

    <head>
        <title>Monthly movie list</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    	<center>
        <h1>Monthly movie list</h1>
        
        <table border="2">
        <tr>
		<td>Show ID</td>
		<td>Movie Name</td>
		<td>Start Time</td>
		<td>End Time</td>
		<td>Remaining Seats</td>
		<td>Show Date</td>
		</tr>
		<%
			try
			{
				Statement stmt;
				 PreparedStatement pstmt;
				 Connection connection;
				DBInitializer database= new DBInitializer();
				connection = database.initializeDB();
				String query;
		       
				query = "select showid, title, starttime, endtime, seats, showdate from showtime, movies where showtime.movieid = movies.movieid and MONTH(showdate) = MONTH(CURRENT_DATE())";
				System.out.println(query);
				pstmt = connection.prepareStatement(query);
				
				System.out.println(pstmt.toString());
				ResultSet rs = pstmt.executeQuery();
				System.out.println("Output of booking table entry ");
				while(rs.next())
				{

	          %>
	          <tr>
			    <td><%=rs.getInt(1) %></td>
			    <td><%=rs.getString(2) %></td>
			   <td><%=rs.getTimestamp(3) %></td>
			    <td><%=rs.getTimestamp(4) %></td>
			    <td><%=rs.getInt(5) %></td>
			    <td><%=rs.getDate(6) %></td>
			    
			  </tr>
			  
              <%
		       }
		       }
				catch(Exception ex){
					ex.getStackTrace();
				}
				
			%>
			</table>
			</center>
			<div align="RIGHT">
			<a href="/UnionMovieBooking/HomeController">Home</a>	
	        <a href="/UnionMovieBooking/LogoutController">Logout</a>
			<a href="cancelBooking.jsp">Cancel Booking</a>
			</div>
    </body>
</html>
