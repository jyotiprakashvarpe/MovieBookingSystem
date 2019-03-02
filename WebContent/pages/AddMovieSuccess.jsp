<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Movie Successful</title>
<link rel="stylesheet" href="resources/css/commonStyles.css">
</head>
<body>
	<h1><%=request.getAttribute("message") %></h1>
	<!-- <form action="pages/AdminAddShow.jsp" method="GET">
		<input type="hidden" name="movieID" value=<%=request.getAttribute("movieId") %>/>
		<input type="hidden" name="title" value=<%=request.getAttribute("movieTitle") %>/>
		<input type="hidden" name="duration" value=<%=request.getAttribute("movieDuration") %>/>
		<input type="submit" value="Add Shows"/>
		<c:if test="${addMovieError} }">
			<c:out value="${addMovieError}"></c:out>
		</c:if>
	</form>
	 -->
	<a href="/UnionMovieBooking/LogoutController">Logout</a>
		<a href="/UnionMovieBooking/adminDashboard">Back</a>
</body>
</html>