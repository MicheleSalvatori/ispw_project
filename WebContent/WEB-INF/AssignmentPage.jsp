<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.AssignmentBean" %>
<%@ page import="logic.utilities.SQLConverter" %>
<%@ page import="logic.utilities.Role" %>

<jsp:useBean id="assignment" class="logic.bean.AssignmentBean" scope="request" />

<%	
	UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
	assignment = (AssignmentBean) request.getAttribute("assignment");
%>
	
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - AssignmentPage</title>
		<link rel="stylesheet" href="res/style/AssignmentPage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
		
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>

			<!-- Page info -->
		<div class="content" style="height: 60vh;">
			
			<!-- First Row -->
			<div class="row" style="width: 47%;">
				<table class="hello" style="width: 100%;">
					<tr>
						<td class="primary-text">
							Course:
						</td>
						
						<td class="primary-text-info" style="text-align: right; padding-right: 1.5vw;">
							<%=assignment.getCourse()%>
						</td>
					</tr>
					
					<tr>
						<td class="secondary-text" style="padding-bottom: 20px; padding-left: 1.5vw;">
							Deadline:
						</td>
						
						<td class="secondary-text-info" style="text-align: right; padding-right: 1.5vw;">
							<%=SQLConverter.date(assignment.getDate())%>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- Second Row -->
			<div class="row" style="padding-top: 25px; width: 47%;">
				
				<!-- Hello -->
				<div class="hello col">
					<table style="width: 100%; height: 100%;">
						<tr>
							<td class="primary-text">
								Title:
							</td>
							
							<td class="primary-text-info" style="text-align: right; padding-right: 2vw;">
								<%=assignment.getTitle()%>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
		
			<!-- Third Row -->
			<div style="padding-top: 25px; height: 60%; width: 46%;">
				<a class="secondary-text" style="padding-left: 0;">Assignment</a><br>
				<textarea class="text-area" readonly style="height: 100%;"><%=assignment.getText()%></textarea>
			</div>
		</div>
	
	</body>

</html>