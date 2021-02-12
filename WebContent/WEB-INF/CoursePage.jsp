<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="logic.bean.LessonBean" %>
<%@ page import="logic.bean.CourseBean" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.Role" %>
<%@ page import="logic.utilities.SQLConverter" %>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
	CourseBean course = (CourseBean) request.getAttribute("courseBean");
	LessonBean lesson = (LessonBean) request.getAttribute("lesson");
%>
	
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - CoursePage</title>
		<link rel="stylesheet" href="res/style/CoursePage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content">
			
			<div class="row">
				<div class="col">
				<!-- Info -->
				<div class="info">
						<table style="width: 100%;">
							<tr>
								<td class="course-text">
									Course:
								</td>

								<td class="course-name-info" style="text-align: right;">
									<%=request.getParameter("course")%>
								</td>
							</tr>

							<tr>
								<td class="course-prof">
									Professor:
								</td>

								<td class="course-prof-info" style="text-align: right;">
									<div class="course-prof-info" style="height: 3vh; overflow-y: scroll;">
									<c:forEach items="${listOfProfessor}" var="professor">
										${professor.getName()} ${professor.getSurname()}<br>
									</c:forEach>
									</div>
								</td>
							</tr>
						</table>
				</div>



				<div style="float: left; width: 100%; margin-right: 5vw; padding-top: 20px;">
				
					<!-- Next Lesson -->
					<div style="width: 100%">
						<table class="lesson" style="width: 100%;">
							<%if (lesson == null) { %>
							<tr>
								<td align="center" class="next-lessons-label" style="width: 100%;">
									There are no future lesson today.
								</td>
							</tr>

							<%} else { %>
								<tr>
									<td style="padding: 1vw; white-space: nowrap; width: 50px;">
										<img class="img" src="res/img/Lesson.png" alt="lesson">
									</td>

									<td style="vertical-align: middle;" align="left">
										<table style="display: inline; vertical-align: middle;" spacing="0">
											<tr>
												<td class="lesson-text">
													<%=SQLConverter.date(lesson.getDate())%>
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
										<label style="display: inline; vertical-align: middle;">10:00</label>
									</td>

									<td align="right" style="padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%;">
										<form action="/ispw_project/LessonPageServlet" method="get" >
											<button class="button-view" type="submit" onclick="f(<%=lesson%>)">View</button>
											<input type="hidden" name="lessonCourse" value="<%=lesson.getCourse()%>">
											<input type="hidden" name="lessonDate" value="<%=lesson.getDate()%>">
											<input type="hidden" name="lessonTime" value="<%=lesson.getTime()%>">
										</form>
									</td>
								</tr>
							<%} %>
							</table>
						</div>

						<!-- Weekly Lessons-->
						<div style="padding-top: 2vw; width: 100%; class: overflow; height: 100%;">
							<table style="border-collapse: collapse; width: 100%;">

								<thead>
									<tr>
										<th colspan="3" class="next-lessons-label" align="left">
											Weekly lessons
										</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${listOfWeekly}" var="weekly">
									<tr class="lesson">
										<td style="padding: 1vw; white-space: nowrap; width: 50px; border-radius: 14px 0 0 14px;">
											<img class="img" src="res/img/Lesson.png" alt="lesson">
										</td>

										<td style="vertical-align: middle;" align="left">
											<table style="display: inline; vertical-align: middle;" spacing="0">
												<tr>
													<td class="lesson-text">
													  ${weekly.getDay().substring(0, 1).toUpperCase()}${weekly.getDay().substring(1)}
													</td>
												</tr>

												<tr>
													<td style="padding-top: 0;" class="lesson-classroom">
														${weekly.getClassroom()}
													</td>
												</tr>
											</table>

											<br>

										</td>

										<td align="right">
											<img class="time-img" src="res/img/Time.png" alt="time">
										</td>

										<td style="vertical-align: middle; padding-left: 0.5vw; padding-right: 1.5vw; white-space: nowrap; width: 1%; border-radius: 0 14px 14px 0;" class="lesson-time">
											<label style="display: inline; vertical-align: middle;">
												${SQLConverter.time(weekly.getTime())}
											</label>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

				</div>
				</div>
			
			
				<div class="col">
					<div style="float: right; width: 80%;">
						<a href="${pageContext.request.contextPath}/ScheduledLessonsPageServlet?course=<%=request.getParameter("course")%>">
							<button class="button">
								View Scheduled Lessons
							</button>
						</a>
						<br><br><br>
						<a href="${pageContext.request.contextPath}/ScheduledExamsPageServlet?course=<%=request.getParameter("course")%>">
							<button class="button">
								View Scheduled Exams
							</button>
						</a>
					</div>
					
					<div style="float: right; width: 80%; min-height: 50vh; padding-top: 30px;">
						<table class="info-tab" style="height: 100%;">
							<tr>
								<td class="course-text">
									Year:
								</td>
								
								<td class="course-name-info">
									<%=course.getYear()%>
								</td>
							</tr>
							
							<tr>
								<td class="course-prof">
									Semester:
								</td>
								
								<td class="course-prof-info">
									<%=course.getSemester()%>
								</td>
							</tr>
							
							<tr>
								<td class="course-prof">
									Credits:
								</td>
								
								<td class="course-prof-info">
									<%=course.getCredits()%>
								</td>
							</tr>
							
							<tr>
								<td class="course-prof">
									Prerequisites:
								</td>
								
								<td class="course-prof-info">
									<%=course.getPrerequisites()%>
								</td>
							</tr>
							
							<tr>
								<td class="course-prof">
									Goal:
								</td>
								
								<td class="course-prof-info">
									<%=course.getGoal()%>
								</td>
							</tr>
							
							<tr>
								<td class="course-prof">
									Reception:
								</td>
								
								<td style="padding-bottom: 15px;"class="course-prof-info">
									<%=course.getReception()%>
								</td>
							</tr>
						</table>
					</div>
				</div>
			
			</div>
		</div>
	
	</body>

</html>