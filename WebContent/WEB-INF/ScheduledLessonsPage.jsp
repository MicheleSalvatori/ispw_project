<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.Role" %>
<%@ page import="logic.utilities.SQLConverter" %>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>
	
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - ScheduledLessonPage</title>
		<link rel="stylesheet" href="res/style/ScheduledPage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content row">
			
			<!-- Requests info -->
			<div style="width: 60%; margin-right: 5vw; float: left; height: 80vh;">
				<div class="info-text">Lessons</div>
				<div style="border: 2px solid #0C0B0B; border-radius: 14px; width: 100%; height: 100%;">
					<div class="overflow">
					<table id="tableLesson" style="border-collapse: separate; border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
						<c:if test="${empty listOfLesson}">
						<tr>
							<td colspan="6" class="request-text" align="center">
								No lesson found.
							</td>
						</tr>
						</c:if>	
						<c:forEach items="${listOfLesson}" var="lesson">
						<tr height="60px" class="request">
						
							<td style="border-radius: 14px 0 0 14px; padding: 1vw; white-space: nowrap; width: 50px;">
								<img class="img" src="res/img/Lesson.png" alt="lesson">
							</td>
							
							<td>
								<table style="display: inline; vertical-align: middle;" spacing="0">
									<tr>
										<td class="lesson-text">
											${SQLConverter.date(lesson.getDate())}
										</td>
									</tr>

									<tr>
										<td style="padding-top: 0;" class="lesson-classroom">
											${lesson.getClassroom().getName()}
										</td>
									</tr>
								</table>
							</td>

							<td align="right" class="course" style="width: 8vw; text-align: center;">
								<a href="/ispw_project/CoursePageServlet?course=${lesson.getCourse()}">
									${lesson.getCourse()}
								</a>
							</td>
							
							<td align="right">
								<img class="time-img" src="res/img/Time.png" alt="time">
							</td>

							<td style="vertical-align: middle; padding-left: 0.5vw; white-space: nowrap; width: 1%;" class="lesson-time">
								<label style="display: inline; vertical-align: middle;">
									${SQLConverter.time(lesson.getTime())}
								</label>
							</td>
				
							<td style="border-radius: 0 14px 14px 0; white-space: nowrap; text-align: right; padding-right: 2vw;">
								<form action="/ispw_project/LessonPageServlet" method="get" >
									<button class="button" type="submit">View</button>
									<input type="hidden" name="lessonCourse" value="${lesson.getCourse()}">
									<input type="hidden" name="lessonDate" value="${lesson.getDate()}">
									<input type="hidden" name="lessonTime" value="${lesson.getTime()}">
								</form>
							</td>
						</tr>
						</c:forEach>
					</table>
					</div>
				</div>
			</div>
			
			<!-- Courses filter -->
			<div class="col" style="float: right;">
				<div class="info-text">Courses</div>
				<div style="border: 2px solid #0C0B0B; border-radius: 14px; width: 100%; height: 100%;">
					<div class="overflow">
					<table style="border-collapse: separate; border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
						<tr class="course-row">
							<td style="border-radius: 14px; white-space: nowrap; text-align: center; padding: 10px 0 10px 0;">
								<button onclick="filter(this.value)" class="button-small" value="">
									Reset
								</button>
							</td>	
						</tr>	
						<c:forEach items="${listOfCourse}" var="course">
						<tr class="course-row">
							<td style="border-radius: 14px; white-space: nowrap; text-align: center; padding: 10px 0 10px 0;">
								<button onclick="filter(this.value)" class="button-small" value="${course.getAbbreviation()}">
									${course.getName()}
								</button>
							</td>	
						</tr>
						</c:forEach>
					</table>
					</div>
				</div>
			</div>
		
		</div>
	</body>
	
<script>
function filter(val) {
  // Declare variables
  var table, tr, td, i, txtValue;
  table = document.getElementById("tableLesson");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[4];
    if (td) {
      txtValue = td.innerText;
      var txt = txtValue.replace(/\s/g, "");
      if (val.localeCompare("") == 0) {
    	tr[i].style.display = "";
      }
      else if (txt.localeCompare(val) == 0) {
        tr[i].style.display = "";
      } 
      else {
        tr[i].style.display = "none";
      }
    }
  }
}
</script>

<% if (request.getParameter("course") != null) { %>
	<script>
		filter("<%=request.getParameter("course")%>");
	</script>
<% } %>

</html>