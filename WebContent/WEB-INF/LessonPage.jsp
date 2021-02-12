<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.bean.LessonBean"%>
<%@ page import="logic.utilities.SQLConverter"%>
<%@ page import="logic.utilities.Role"%>
<%@ page import="java.lang.String"%>

<%
	UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
	LessonBean lesson = (LessonBean) request.getAttribute("lesson");
	int seatColumn = lesson.getClassroom().getSeatColumn();
	int row = lesson.getClassroom().getSeatRow();
%>

<head>
	<meta charset="utf-8">
	<title>.myUniversity - LessonPage</title>
	<link rel="stylesheet" href="res/style/LessonPage.css">
	<link rel="icon" href="res/img/Logo.png">
</head>

<body>

	<!-- Navigation bar -->
	<jsp:include page="NavigationBar.jsp"/>

	<!-- Status bar -->
	<jsp:include page="StatusBar.jsp"/>

	<!-- Page info -->
	<div class="content row">

		<div style="float: left; width: 45%; margin-right: 5vw;" class="col">

			<div class="info">
				<table style="width: 100%;">
					<tr>
						<td class="text">Course:</td>

						<td class="info" style="text-align: right;">
							<a href="${pageContext.request.contextPath}/CoursePageServlet?course=<%=lesson.getCourse()%>">
								<%=lesson.getCourse()%>
							</a>
						</td>
					</tr>

					<tr>
						<td class="secondary-text">Professor:</td>

						<td class="secondary-info" style="text-align: right;"><%=lesson.getProfessor().getName() + " " + lesson.getProfessor().getSurname()%>
						</td>
					</tr>

					<tr>
						<td class="secondary-text" style="padding-bottom: 0.5vw;">
							Classroom:</td>

						<td class="secondary-info"
							style="text-align: right; padding-bottom: 0.5vw;"><%=lesson.getClassroom().getName()%>
						</td>
					</tr>
				</table>
			</div>

			<div class="row">
				<div class="time">
					<table style="width: 100%; height: 100%;">
						<tr>
							<td style="text-align: right; width: 50%;"><img
								class="time-img" src="res/img/Time.png" alt="time"
								style="padding-right: 2vw;"></td>

							<td class="time-text"><%=SQLConverter.time(lesson.getTime())%></td>
						</tr>

						<tr>
							<td style="text-align: right;"><img class="time-img"
								src="res/img/Calendar.png" alt="date" style="padding-right: 2vw;">
							</td>

							<td class="time-text"><%=SQLConverter.date(lesson.getDate())%></td>
						</tr>
					</table>
				</div>

				<div class="weather" style="margin-left: 5%; padding: 10px;">
					<table align="center">
						<tr>
							<td class="weather-text" style="padding-top: 5px;">
								${weather.get(0)}
							</td>
						</tr>

						<tr>
							<td align="center">
								<img class="img" src="res/img/${weather.get(1)}" alt="weath">
							</td>
						</tr>

						<tr>
							<td class="weather-text" style="padding-bottom: 5px;">
								${weather.get(2)}
							</td>
						</tr>
					</table>
				</div>

			</div>

			<div style="margin-top: 25px; width: 98%;">
				<a class="text" style="padding-left: 0;">Lesson Topic</a> <br>
				<textarea style="height: 40vh;" class="text-area" readonly><%=lesson.getTopic()%></textarea>
			</div>

		</div>
		<div style="float: right; width: 50%;" class="col">
			<a class="text" style="padding-left: 0; text-align: left;">Seat Map</a>
			<div class="seat" style="height: 100%;">
				<div class="overflow">
				<form action="/ispw_project/LessonPageServlet" method="post">
				<div style="text-align: center;">
					<img style="padding:10px; max-height: 8vw; margin-top: 18px;" src="res/img/ClassProf.png" alt="classroom">
				</div>
				<table style="border-spacing: 10px;">
					<c:forEach var="i" begin="1" end="<%=row%>">
						<tr>
							<c:forEach var="j" begin="1" end="<%=seatColumn%>">
								<td>
								<input type = "hidden" name="lessonCourse" value="<%=lesson.getCourse()%>">
								<input type = "hidden" name="lessonDate" value="<%=lesson.getDate()%>">
								<input type = "hidden" name="lessonTime" value="<%=lesson.getTime()%>">
								<c:set var="seatColumn"	value="${lesson.getClassroom().getSeatColumn()}" />
								 <c:set var="idSeat" value="${(j-1)+seatColumn*(i-1)}" /> 
								 <c:choose>
										<c:when test="${idSeat ==  mySeat.getId()-1}">
											<button name="yourSeat"class="button-seat seat-your" value="${idSeat+1}"
											onclick="return confirm('Are you sure you want to free this seat?')">${j}</button>
										</c:when>
										<c:when test="${occupiedSeats[idSeat].isFree()}">
											<button name="bookSeat" class="button-seat seat-free" type="submit" value="${idSeat+1}"
											onclick="return confirm('Are you sure you want to book this seat?')" >${j}</button>
										</c:when>
										<c:otherwise>
											<button class="button-seat seat-booked" disabled="disabled">${j}</button>
										</c:otherwise>
									</c:choose>
									</td>
							</c:forEach>
							<c:set var="id" value="&\#${(i+64)}" />
							<td class="secondary-text" style="padding: 0;">${id}</td>
						</tr>
					</c:forEach>
				</table>
				</form>
				</div>
			</div>
		</div>
	</div>

</body>

<%String message = (String)request.getAttribute("alertMsg");
  if(message != null){ %>
  	<script type="text/javascript">
    	var msg = "<%=message%>";
    	alert(msg);
  	</script>
<%} %>

</html>