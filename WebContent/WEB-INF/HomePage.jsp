<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.LessonBean" %>
<%@ page import="logic.utilities.SQLConverter" %>
<%@ page import="logic.utilities.Role" %>

<%	
	UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
	LessonBean lesson = (LessonBean) request.getAttribute("lesson");
%>
	
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - HomePage</title>
		<link rel="stylesheet" href="res/style/HomePage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content">
			
			<!-- First Row -->
			<div class="row">
				
				<!-- Hello -->
				<div class="hello col" id="hello">
					<table>
						<tr>
							<td class="hello-text">
								Hello nome!
							</td>
						</tr>
						
						<tr>
							<td class="hello-sub">
								It's good to see you again.
							</td>
						</tr>
					</table>
				</div>

				<!-- Stats -->
				<div class="stat col">
					<table style="border-collapse: collapse;" align="center">
						<% if (user.getRole() == Role.STUDENT) { %>
						<tr>
							<td class="stat-num">
								${verbs}
							</td>

							<td class="stat-text" style="padding-right: 5vw;">
								Verbalized<br>Exams
							</td>

							<td class="stat-num">
								${wpa}
							</td>

							<td class="stat-text">
								Average<br>Grade
							</td>
						</tr>
						<% } else { %>
						<tr>
							<td class="stat-num">
								${questions}
							</td>

							<td class="stat-text">
								New<br>Questions
							</td>
						</tr>
						<% } %>
					</table>
				</div>
			
			</div>
			
			
			<!-- Second Row -->
			<div style="padding-top: 2vw;">
			
				<!-- First Col -->
				<div class="col" style="float: left; width: 47%; margin-right: 5vw;">
				
					<!-- Next Lesson -->
					<div style="width: 100%">
						
						<table class="lesson" style="width: 100%;">
						<%if (lesson == null) { %>
							<tr>
								<td align="center" class="next-lessons-label" style="width: 100%;">
									There are no future lesson today.
								</td>
							</tr>
							<!--<a align="center" class="next-lessons-label lesson" style="width: 100%;">No lesson found</a>-->
						<%} else { %>
							<tr>
								<td style="padding: 1vw; white-space: nowrap; width: 50px;">
									<img class="img" src="res/img/Lesson.png" alt="lesson">
								</td>

								<td style="vertical-align: middle;" align="left">
									<table style="display: inline; vertical-align: middle;" spacing="0">
										<tr>
											<td class="lesson-text">
												<%=lesson.getCourse()%>
											</td>
										</tr>

										<tr>
											<td style="padding-top: 0;" class="lesson-classroom">
												<%=lesson.getClassroom().getName()%>
											</td>
										</tr>
									</table>

									<br>

								</td>

								<td align="right">
									<img class="time-img" src="res/img/Time.png" alt="time">
								</td>

								<td style="vertical-align: middle; padding-left: 0.5vw; white-space: nowrap; width: 1%;" class="lesson-time">
									<label style="display: inline; vertical-align: middle;">
										<%=SQLConverter.time(lesson.getTime())%>
									</label>
								</td>
								<td align="right" style="padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%;">
								<form action="/ispw_project/LessonPageServlet" method="get" >
									<button class="button-view" type="submit">View</button>
									<input type="hidden" name="lessonCourse" value="<%=lesson.getCourse()%>">
									<input type="hidden" name="lessonDate" value="<%=lesson.getDate()%>">
									<input type="hidden" name="lessonTime" value="<%=lesson.getTime()%>">
								</form>
								</td>
							</tr>
						<%} %>
						</table>	
					</div>
					
					<!-- Next Lessons-->
					<div class="overflow" style="padding-top: 2vw; width: 100%; height: 100%;">
						<table style="border-collapse: separate; width: 100%; border-spacing: 0 15px;">
							
							<thead>
								<tr>
									<th colspan="3" class="next-lessons-label" align="left">
										Today Lessons
									</th>
								</tr>
							</thead>
							
							<tbody style="width: 100%;">
								<c:if test="${empty listOfLesson}">
									<tr>
										<td colspan="5" class="next-lessons-label" style="text-align: center;">
											There are no future lesson today.
										</td>
									</tr>
								</c:if>
								<c:forEach items="${listOfLesson}" var="lesson">
								<tr class="lesson">
									<td style="padding: 1vw; white-space: nowrap; width: 50px; border-radius: 14px 0 0 14px;">
										<img class="img" src="res/img/Lesson.png" alt="lesson">
									</td>

									<td style="vertical-align: middle;" align="left">
										<table style="display: inline; vertical-align: middle;" spacing="0">
											<tr>
												<td class="lesson-text">
												  ${lesson.getCourse()}
												</td>
											</tr>

											<tr>
												<td style="padding-top: 0;" class="lesson-classroom">
													${lesson.getClassroom().getName()}
												</td>
											</tr>
										</table>

										<br>

									</td>

									<td align="right">
										<img class="time-img" src="res/img/Time.png" alt="time">
									</td>

									<td style="vertical-align: middle; padding-left: 0.5vw; white-space: nowrap; width: 1%;" class="lesson-time">
										<label style="display: inline; vertical-align: middle;">
											${SQLConverter.time(lesson.getTime())}
										</label>
									</td>

									<td align="right" style="padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%; border-radius: 0 14px 14px 0;">
										<form action="/ispw_project/LessonPageServlet" method="get" >
											<button class="button-view" type="submit">View</button>
											<input type="hidden" name="lessonCourse" value="${lesson.getCourse()}">
											<input type="hidden" name="lessonDate" value="${lesson.getDate()}">
											<input type="hidden" name="lessonTime" value="${lesson.getTime()}">
										</form>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				
				</div>
				
				<!-- Second Col -->
				<div class="col" style="float: right; width: 47%;">
					<!-- Weather -->
					<table style="height: 80%;">
						<thead>
							<tr>
								<th colspan="3" class="next-lessons-label" align="left">
									Weather
								</th>
							</tr>
						</thead>
					
						<tbody>
						<tr>
							<c:forEach items="${listOfWeather}" var="weather">
							<td style="padding-right: 1vw; width: 20%;">
								<table class="weather" align="center" style="padding: 10px;">
									<tr>
										<td class="weather-text">
											${weather.get(0)}
										</td>
									</tr>

									<tr>
										<td align="center">
											<img class="img" src="res/img/${weather.get(1)}" alt="weath">
										</td>
									</tr>

									<tr>
										<td class="weather-text">
											${weather.get(2)}
										</td>
									</tr>
								</table>
							</td>
							</c:forEach>
						</tr>
						</tbody>
					
					</table>
					
					<!-- Map -->
					<a class="next-lessons-label">Map</a>
					<iframe src="${map}" width="100%" height="400" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
				</div>
			
			</div>
			
		</div>
	</body>
</html>