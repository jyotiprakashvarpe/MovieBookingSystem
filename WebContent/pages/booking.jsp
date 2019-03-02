<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="com.javaproject.dao.*"%>
<%@ page import="com.javaproject.model.*"%>
<%@ page import="com.javaproject.dao.*"%>
<%@ page import="com.javaproject.dao.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.PrintWriter"%>

<%
		PrintWriter outprint = response.getWriter();
		ArrayList<Movie> nowShowingMovieList = new MovieListDAO().getTodayMovieList();
		int sliceSize = nowShowingMovieList.size() / 3;
		int remainder = nowShowingMovieList.size() % 3;		
		String message = (String)request.getAttribute("alertMsg");
%>							
<html>
    <head>
    
        <title>Movie List</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <script  language="JavaScript">
		function exportToForm(i) {
			var jspcall = "pages/movieDetails.jsp?param1="+i;
			//document.form.action="movieDetails.jsp?param1="+i; 
			//document.form.submit(); 
			//$.post("login.jsp", { param1: i });
			//request.open("GET", "pages/movieDetails.jsp?param1="+i, true);
		    window.location.href = jspcall;
		 }
		</script>
		<script type="text/javascript">
    var msg = "<%=message%>";
    var value = "Number of seats not available";
    if(!(msg.localeCompare(value))){
    	alert(msg);
    }
    
</script>

<style type="text/css">

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
  background:#F5F5F5;
  width:auto;
  height:auto;
  padding:1em;
}

</style>
    <link rel="stylesheet" href="/styles.css">   
</head>
    <body>
    	<center>
			<h1>Movie Listings</h1> 
		</center>		
		<div class="section group">
			<table style="width:100%">
			<% for ( int j = 0; j < sliceSize; j++ ) { %>
				<div class="col span_1_of_3">
					<tr class="ul_2">
						<% for ( int i = j*3; i < (j*3+1); i++ ) { %>
							<th class="li_2">	
													
								<img onclick="javascript:exportToForm(<%=nowShowingMovieList.get( i ).getMovieId() %>)" height='200' width='200' src="<%="resources/images/"+nowShowingMovieList.get( i ).getMovieTitle()+".jpg"%>"/>
								
							</th>
							<th class="li_2">	
								
								<img onclick="javascript:exportToForm(<%=nowShowingMovieList.get( i + 1 ).getMovieId() %>)" height='200' width='200' src="<%="resources/images/"+nowShowingMovieList.get( i + 1 ).getMovieTitle()+".jpg"%>"/>					
								
							</th>
							<th class="li_2">
								
								<img onclick="javascript:exportToForm(<%=nowShowingMovieList.get( i + 2).getMovieId() %>)" height='200' width='200' src="<%="resources/images/"+nowShowingMovieList.get( i + 2 ).getMovieTitle()+".jpg"%>"/>					
								
							</th>
						<% } %>							
					</tr>
				</div>
			<% } %>
			</table>
		</div>	
		<br>
		<div align="RIGHT">
  
		<a href="/UnionMovieBooking/LogoutController">Logout</a>
		<a href="pages/monthlyMovieList.jsp">Monthly Movie List</a>
		<a href="pages/cancelBooking.jsp">Cancel Booking</a>
		</div>
    </body>
</html>