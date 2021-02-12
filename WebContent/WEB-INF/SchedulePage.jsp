<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.Role" %>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>
	
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - SchedulePage</title>
		<link rel="stylesheet" href="res/style/SchedulePage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->		
      	<div class="content">
			
			<!-- Schedule lesson -->
			<div>
			<a class="text">Schedule Lesson</a><br>
			<div class="row">
				<div class="col" style="float: left;">
					<textarea name="topic" form="lessonForm" class="input-text" placeholder="Insert lesson topic" required></textarea>
				</div>

				<div class="col" style="float: right;">
					
					<table style="width: 100%;">
						<tr>
							<td style="text-align: right;">
								<select name="selCourseLesson" form="lessonForm" class="select-schedule" required> 
									<c:if test="${empty listOfCourse}">
										<option disabled selected>No course found</option>
									</c:if>
									<c:if test="${!empty listOfCourse}">
										<option disabled selected>Select Course</option>
									</c:if>
									<c:forEach items="${listOfCourse}" var="course">
										<option value="${course.getAbbreviation()}">
											${course.getAbbreviation()}
										</option>
									</c:forEach>
								</select>
							</td>
							
							<td style="text-align: left; padding-left: 1vw;">
								<select name="selClassLesson" form="lessonForm" class="select-schedule" required>
									<option disabled selected>Select Class</option>
									<c:forEach items="${listOfClass}" var="classroom">
										<option value="${classroom.getName()}">
											${classroom.getName()}
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 15px;">
								<input name="dateLesson" form="lessonForm" type="date" class="datepicker-input-schedule" required>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 15px;">
								<input name="timeLesson" form="lessonForm" type="time" class="time-input-schedule" required>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 15px;">
								<form id="lessonForm" action="/ispw_project/SchedulePageServlet" method="post">
								<button name="btnAddLesson" type="submit" class="button-add" style="width: 35%;" onclick="return confirm('Do you really want to add a new lesson?')">
									<img class="img-time" src="res/img/Plus.png" alt="plus">
									Schedule Lesson
								</button>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</div>
			
			<!-- Schedule exam -->
			<div style="padding-top: 50px;">
			<a class="text">Schedule Exam</a><br>
			<div class="row">
				<div class="col" style="float: left;">
					<textarea name="note" form="examForm" class="input-text" placeholder="Note" required></textarea>
				</div>

				<div class="col" style="float: right;">
					
					<table style="width: 100%;">
						<tr>
							<td style="text-align: right;">
								<select name="selCourseExam" form="examForm" class="select-schedule" required> 
									<c:if test="${empty listOfCourse}">
										<option disabled selected>No course found</option>
									</c:if>
									<c:if test="${!empty listOfCourse}">
										<option disabled selected>Select Course</option>
									</c:if>
									<c:forEach items="${listOfCourse}" var="course">
										<option value="${course.getAbbreviation()}">
											${course.getAbbreviation()}
										</option>
									</c:forEach>
								</select>
							</td>
							
							<td style="text-align: left; padding-left: 1vw;">
								<select name="selClassExam" form="examForm" class="select-schedule" required>
									<option disabled selected>Select Class</option>
									<c:forEach items="${listOfClass}" var="classroom">
										<option value="${classroom.getName()}">
											${classroom.getName()}
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 15px;">
								<input name="dateExam" form="examForm" type="date" class="datepicker-input-schedule" required>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 15px;">
								<input name="timeExam" form="examForm" type="time" class="time-input-schedule" required>	
							</td>
						</tr>
						
						<tr>
							<td colspan="2" style="text-align: center; padding-top: 15px;">
								<form id="examForm" action="/ispw_project/SchedulePageServlet" method="post">
								<button name="btnAddExam" class="button-add" style="width: 35%;" onclick="return confirm('Do you really want to add a new exam?')">
									<img class="img-time" src="res/img/Plus.png" alt="plus">
									Schedule Exam
								</button>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</div>
			
		</div>
		
	<%String message = (String)session.getAttribute("alert");
   	if(message != null){ 
    	session.setAttribute("alert", null); %>
	    <script type="text/javascript">
	    	var msg = "<%=message%>";
	        alert(msg);
		</script>
   	<%} %>
		
	</body>

</html>