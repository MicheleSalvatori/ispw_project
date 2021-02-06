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
	<div class="topnav">
		<table align="right">
			<tbody>
				<tr>
					<td style="padding-right: 10px;"><label class="status-label"
						id="statusName"> <%=user.getName()%>
					</label></td>

					<td style="padding-right: 10px;"><a
						href="/ispw_project/LoginServlet" onclick="session.invalidate();">
							<button class="status-button" type="button" name="logout">
								<svg width="27" height="27" viewBox="0 0 27 27"
									xmlns="http://www.w3.org/2000/svg">
								<path
										d="M18.4067 19.9873V22.0269C18.4067 24.2761 16.5769 26.1059 14.3277 26.1059H4.07905C1.8298 26.1059 0 24.2761 0 22.0269V4.07905C0 1.8298 1.8298 0 4.07905 0H14.3277C16.5769 0 18.4067 1.8298 18.4067 4.07905V6.11857C18.4067 6.68183 17.9502 7.13833 17.3869 7.13833C16.8237 7.13833 16.3672 6.68183 16.3672 6.11857V4.07905C16.3672 2.95452 15.4522 2.03952 14.3277 2.03952H4.07905C2.95452 2.03952 2.03952 2.95452 2.03952 4.07905V22.0269C2.03952 23.1514 2.95452 24.0664 4.07905 24.0664H14.3277C15.4522 24.0664 16.3672 23.1514 16.3672 22.0269V19.9873C16.3672 19.4241 16.8237 18.9676 17.3869 18.9676C17.9502 18.9676 18.4067 19.4241 18.4067 19.9873ZM25.3592 11.3012L23.0757 9.01772C22.6774 8.61938 22.0316 8.61938 21.6335 9.01772C21.2351 9.41587 21.2351 10.0616 21.6335 10.4597L23.2577 12.0842H11.0134C10.4502 12.0842 9.99367 12.5407 9.99367 13.1039C9.99367 13.6672 10.4502 14.1237 11.0134 14.1237H23.2577L21.6335 15.7482C21.2351 16.1463 21.2351 16.792 21.6335 17.1902C21.8327 17.3893 22.0936 17.4889 22.3545 17.4889C22.6156 17.4889 22.8765 17.3893 23.0757 17.1902L25.3592 14.9066C26.3533 13.9126 26.3533 12.2953 25.3592 11.3012Z"
										fill="black" />
							</svg>
							</button>
					</a></td>

				</tr>
			</tbody>
		</table>
	</div>

	<!-- Page info -->
	<div class="content">
		<a class="title-label">Post new communication</a>
		<div style="margin-top: 20px;">
			<input class="input-subject" type="text" id="communicationTitle"
				name="communication-title" placeholder="Title" onkeyup="fun()"
				onclick="fun()" form="communication-form">
		</div>
		<div style="margin-top: 20px;">
			<textarea id="communicationText" onkeyup="fun()" onclick="fun()"
				placeholder="Communication" name="communication-text" form="communication-form"></textarea>
		</div>
		<form
			action="${pageContext.request.contextPath}/PostCommunicationServlet"
			method="post" id="communication-form">
			<button class="button-submit" id="submit" name="submit"
				disabled="disabled"
				onclick="return confirm('Are you sure you want to post this communication?')">Submit</button>
		</form>
	</div>
</body>

<script>
	function fun() {
		var text = document.getElementById("communicationText").value.length;
		var subject = document.getElementById("communicationTitle").value.length;

		if (text > 0 && subject > 0) {
			document.getElementById("submit").disabled = false;
		} else {
			document.getElementById("submit").disabled = true;
		}

	}
</script>


</body>
</html>