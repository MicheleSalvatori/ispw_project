<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.bean.CommunicationBean"%>
<%@ page import="logic.utilities.SQLConverter"%>
<%@ page import="logic.utilities.Role"%>
<%@ page import="javax.servlet.RequestDispatcher"%>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>

	<head>
		<meta charset="utf-8">
		<title>.myUniversity - Communications</title>
		<link rel="stylesheet" href="res/style/CommunicationsPage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>

		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>

		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<div class="content">
		<div>
			<a class="title-label">Communications</a>
		</div>
	
		<div style="border: 2px solid #0C0B0B; border-radius: 14px; height: 100%;">
			<div class="overflow"> 
			<table class="table">
				<c:if test="${empty listOfCommunications}">
					<tr>
						<td colspan="3" class="title-label" align="center">No communications to view.</td>
					</tr>
				</c:if>
				<c:forEach items="${listOfCommunications}" var="communication">
					<tr height="50px" class="tr">
						<td style="border-radius: 14px 0 0 14px; padding: 1vw;">
							<table style="display: inline; vertical-align: middle;" spacing="0">
								<tr>
									<td class="id"><img style="vertical-align: middle;" class="img" src="res/img/Megaphone.png" alt="com"></td>
									<td class="id">
										${communication.getId()}
									</td>
								</tr>
	
								<tr>
									<td style="padding-top: 0;" class="communication-date">
										${SQLConverter.date(communication.getDate())}
									</td>
								</tr>
							</table>
						</td>
	
						<td style="border-radius: 0 14px 14px 0; padding: 1vw;">
							<table style="display: inline; vertical-align: middle;">
								<tr>
									<td class="communication-title">
										${communication.getTitle()}
									</td>
								</tr>
								<tr>
									<td style="word-break: break-all" class="communication-text">
										${communication.getText()}
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
				</table>
				</div>
			</div>
		</div>
	</body>
	
</html>