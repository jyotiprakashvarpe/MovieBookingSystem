<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/commonStyles.css">
</head>
<body>
<h1>Movie schedule</h1>

<jsp:useBean id="now" class="java.util.Date" />


<c:if test="${movieList.size() == 0}">
<c:out value="No movies scheduled"></c:out>
</c:if>
<c:if test="${movieList.size() gt 0}">
<table border="2">
	<tr> 
		<th colspan="3">Movie Details</th>
		<th colspan="2">Show Details</th>
	</tr>
	<c:forEach items="${movieList}" var="movie">
		<tr> 
			<td >
				<c:out value="${movie.movieTitle}"></c:out>
			</td>
			<td >
				<img src="resources/images/${movie.posterURL}.jpg" height="200" width="200" alt="-"/>
			</td>
			<td>
				<form action="pages/editMovie.jsp" method="POST">
					<input type="hidden" value="${movie.movieId }" name="movieId">
					<input type="hidden" value="${movie.movieTitle }" name="title">
					<input type="hidden" value="${movie.description }" name="description">
					<input type="hidden" value="${movie.posterURL}" name="poster">
					<input type="hidden" value="${movie.runningTime }" name="duration">
					<c:if test="${movie.showList.size() >0 }">
						<input type="hidden" value="false" name="showDelete">
					</c:if>
					<input type="submit" value="Edit/Delete">
				</form>
			</td>
			<td>
			<table border="0">
				<c:forEach items="${movie.showList}" var ="show">
				<tr>
				<td>
					<c:out value="${show.displayDate  }" ></c:out>
					<c:out value="${show.hrs}:" ></c:out>
					<c:out value="${show.min}" ></c:out>
				</td>
				<td>
				<c:if test="${now.getTime() < show.startTime.getTime()}">
					<form action="pages/editShow.jsp" method="POST">
						<input type="hidden" name="movieID" value="${movie.movieId}"/>
						<input type="hidden" name="title" value="${movie.movieTitle}"/>
						<input type="hidden" name="duration" value="${movie.runningTime}"/>
						<input type="hidden" name="date" value="${show.displayDate}"/>
						<input type="hidden" name="min" value="${show.min}"/>
						<input type="hidden" name="hrs" value="${show.hrs}"/>
						<input type="hidden" name="id" value="${show.id}"/>
						<input type="submit" value="Edit"/>
					</form> 
				</c:if>
				</td>
				</tr>
				</c:forEach>
			</table>
			</td>
			<td>
			<form action="pages/AdminAddShow.jsp" method="GET">
		<input type="hidden" name="movieID" value="${movie.movieId}"/>
		<input type="hidden" name="title" value="${movie.movieTitle}"/>
		<input type="hidden" name="duration" value="${movie.runningTime}"/>
		<input type="submit" value="Add Shows"/>
	</form>
			</td>
			
		</tr>
	</c:forEach>
</table>
	
		
	
</c:if>
<form action="AdminAddMovie.jsp" method="post">
<input type="submit" value="Add Movie"  style="margin-left: 50%;"/>
</form>
<a href="/UnionMovieBooking/LogoutController">Logout</a>
</body>
</html>