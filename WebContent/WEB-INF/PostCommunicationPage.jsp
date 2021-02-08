<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.utilities.Role"%>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%
UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
%>

<head>
<meta charset="utf-8">
<title>App - PostCommunication</title>
<link rel="stylesheet" href="res/style/AdministrationPage.css">
<link rel="stylesheet" href="res/style/NavigationBar.css">
<link rel="stylesheet" href="res/style/StatusBar.css">
<link rel="icon" href="res/img/Logo.png">
</head>

<body>
	<!-- Side navigation -->
	<div class="sidenav">
		<table class="nav-root" width="132" border="0">
			<tbody>
				<tr>
					<td valign="top"><p class="nav-logo">F.</p></td>
				</tr>
				<tr>
					<td valign="top" style="padding-bottom: 20px;" align="center">
						<a href="/ispw_project/AdministrationPageServlet"
						class="nav-button" type="button"> <svg class="nav-icon"
								width="27" height="26" viewBox="0 0 27 26" fill="white"
								xmlns="http://www.w3.org/2000/svg">
								<path
									d="M14.6444 1.40837L14.6446 1.40854L25.2517 12.0152L25.2517 12.0153C25.2517 12.0153 25.2517 12.0153 25.2518 12.0153L25.2518 12.0153L25.2518 12.0154C25.2518 12.0154 25.2518 12.0154 25.2518 12.0154C25.2518 12.0154 25.2519 12.0154 25.2519 12.0154C25.2519 12.0154 25.2519 12.0155 25.2519 12.0155C25.7934 12.5585 25.793 13.4433 25.2509 13.9858C24.9858 14.2505 24.6392 14.3939 24.2656 14.3939H23.8736H22.8736V15.3939V23.1989C22.8736 24.191 22.0648 25 21.0721 25H17.161V19.1191C17.161 17.8621 16.14 16.8411 14.8829 16.8411H12.4355C11.1785 16.8411 10.1575 17.8621 10.1575 19.1191V25H6.24636C5.2537 25 4.44487 24.191 4.44487 23.1989V15.3939V14.3939H3.44487H3.03362L3.0184 14.3924L2.99124 14.3912C2.64014 14.3761 2.31541 14.2335 2.06733 13.9853L2.06716 13.9851C1.52582 13.4438 1.52465 12.5605 2.06463 12.0174L2.07253 12.0094L2.07623 12.0056L12.6737 1.40833L12.6737 1.40832C12.9385 1.14356 13.2852 1 13.6592 1C14.0331 1 14.3798 1.14362 14.6444 1.40837ZM25.2516 12.0151C25.2516 12.0151 25.2516 12.0151 25.2516 12.0151L25.2515 12.0151C25.2516 12.0151 25.2516 12.0151 25.2516 12.0151ZM2.07789 12.0039L2.07758 12.0042L2.07789 12.0039Z"
									stroke="white" stroke-width="2" />
							</svg>
					</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- Status bar -->
	<jsp:include page="StatusBar.jsp"/>

	<!-- Page info -->
	<div class="content">
		<a class="title-label">Post new communication</a>
		<form action="${pageContext.request.contextPath}/PostCommunicationServlet" method="post">
		
			<div style="margin-top: 20px;">
				<input class="input-subject" type="text" name="communication-title" placeholder="Title" required>
			</div>
			
			<div style="margin-top: 20px;">
				<textarea placeholder="Communication" name="communication-text" required></textarea>
			</div>
			
			<button class="button-submit" type="submit" name="submit" onclick="return confirm('Are you sure you want to post this communication?')">Submit</button>
		
		</form>
	</div>
</body>

</body>
</html>