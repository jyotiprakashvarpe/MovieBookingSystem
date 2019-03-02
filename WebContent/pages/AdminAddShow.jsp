<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Show</title>
<link rel="stylesheet" href="../resources/css/commonStyles.css">

</head>

<body>
	<h1>Add New Show</h1>
	<table border="1">
		<tr>
			<td> <img alt="-" src="../resources/images/<%=request.getParameter("title")%>.jpg" width="200" height="150"/>
			</td>
			<td>
				<b> Movie : </b><%=request.getParameter("title") %>
				<p> <b> Running time : </b><%=request.getParameter("duration") %>
				</p>
			</td>
			<td>
				<form action="../addShow" method="post">
					<p>Date: <input type="date" name="dateTime" placeholder="Select Date" required="required" class="dateClass"></p>
					<p>Hrs: <input type="number" name="hours" min="7" max="23" required="required"></p>
					<p>Min: <input type="number" name="min" min="00" max="59" required="required"></p>
					<input type="hidden" value="<%=request.getParameter("duration") %>" name="duration"/>
					<input type="hidden" value="<%=request.getParameter("movieID") %>" name="movieID"/>  
					<input type="hidden" value="<%=request.getParameter("title")%>" name="title"/>
					<input type="submit" value="Add Show"/>
					
				</form>
			</td>
		</tr>
	</table>
	<a href="/UnionMovieBooking/LogoutController">Logout</a>
		<a href="../adminDashboard">Back</a>
<script>
	var now = new Date(),
	minDate = now.toISOString().substring(0,10);
	document.getElementsByClassName("dateClass")[0].setAttribute("min",minDate);	

</script>
</body>
</html>