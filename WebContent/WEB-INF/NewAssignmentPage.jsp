<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.SQLConverter" %>
<%@ page import="logic.utilities.Role" %>

<%	
	UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
%>
	
	<head>
		<meta charset="utf-8">
		<title>App - NewAssignmentPage</title>
		<link rel="stylesheet" href="res/style/NewAssignmentPage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content">
			<form action="${pageContext.request.contextPath}/NewAssignmentPageServlet" method="post">
			<table style="width: 100%">
				<tr>
					<td class="text" colspan="3" style="text-align: left;">
						Add new assignment
					</td>					  
				</tr>
				
				<tr>
					<td width="30%;">
						<input class="text-input" type="text" name="assignment-title" placeholder="Title" style="padding-left: 5px; width: 100%; height: 40px;" required>
					</td>
					
					<td style="text-align: left; padding-left: 2vw;">
						<input class="datepicker-input" type="date" name="assignment-date" style="width: 80%; height: 40px;" required>
					</td>
					
					<td style="text-align: right;">
						<select name="assignment-course" class="select" required>
							<option disabled>Select course</option>
							<c:forEach items="${listOfCourses}" var="course">
								<option value="${course.getAbbreviation()}">${course.getAbbreviation()}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				
				<tr>
					<td colspan="3" style="text-align: center; padding-top: 25px;">
						<textarea name="assignment-text" style="width: 99%; height: 50vh;" class="input-text" placeholder="Description" required></textarea>
					</td>
				</tr>
				
				<tr>
					<td colspan="3" style="text-align: right; padding-top: 25px;">
						<button class="button">
							Submit
						</button>
					</td>
				</tr>
			</table>
			</form>	
		</div>
	</body>
</html>