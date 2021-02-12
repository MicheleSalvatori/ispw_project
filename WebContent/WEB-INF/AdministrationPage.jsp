<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.bean.LessonBean"%>
<%@ page import="logic.utilities.SQLConverter"%>
<%@ page import="logic.utilities.Role"%>

<%
UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
LessonBean lesson = (LessonBean) request.getAttribute("lesson");
%>

<head>
<meta charset="utf-8">
<title>App - Administration Control Panel</title>
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
						class="nav-button" type="button"> 
								<svg fill="white" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
									<path d="M24 14.187v-4.374c-2.148-.766-2.726-.802-3.027-1.529-.303-.729.083-1.169 1.059-3.223l-3.093-3.093c-2.026.963-2.488 1.364-3.224 1.059-.727-.302-.768-.889-1.527-3.027h-4.375c-.764 2.144-.8 2.725-1.529 3.027-.752.313-1.203-.1-3.223-1.059l-3.093 3.093c.977 2.055 1.362 2.493 1.059 3.224-.302.727-.881.764-3.027 1.528v4.375c2.139.76 2.725.8 3.027 1.528.304.734-.081 1.167-1.059 3.223l3.093 3.093c1.999-.95 2.47-1.373 3.223-1.059.728.302.764.88 1.529 3.027h4.374c.758-2.131.799-2.723 1.537-3.031.745-.308 1.186.099 3.215 1.062l3.093-3.093c-.975-2.05-1.362-2.492-1.059-3.223.3-.726.88-.763 3.027-1.528zm-4.875.764c-.577 1.394-.068 2.458.488 3.578l-1.084 1.084c-1.093-.543-2.161-1.076-3.573-.49-1.396.581-1.79 1.693-2.188 2.877h-1.534c-.398-1.185-.791-2.297-2.183-2.875-1.419-.588-2.507-.045-3.579.488l-1.083-1.084c.557-1.118 1.066-2.18.487-3.58-.579-1.391-1.691-1.784-2.876-2.182v-1.533c1.185-.398 2.297-.791 2.875-2.184.578-1.394.068-2.459-.488-3.579l1.084-1.084c1.082.538 2.162 1.077 3.58.488 1.392-.577 1.785-1.69 2.183-2.875h1.534c.398 1.185.792 2.297 2.184 2.875 1.419.588 2.506.045 3.579-.488l1.084 1.084c-.556 1.121-1.065 2.187-.488 3.58.577 1.391 1.689 1.784 2.875 2.183v1.534c-1.188.398-2.302.791-2.877 2.183zm-7.125-5.951c1.654 0 3 1.346 3 3s-1.346 3-3 3-3-1.346-3-3 1.346-3 3-3zm0-2c-2.762 0-5 2.238-5 5s2.238 5 5 5 5-2.238 5-5-2.238-5-5-5z"/>
								</svg>
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
		<a class="title-label">Administration Control Panel</a>
		<div style="margin-right: 100px; margin-left: 100px;">
			<button class="button" type="button" name="addCourse" onclick="alert('Functionality not yet implemented.')">
				Add Course in the System
			</button>
			
			<button class="button" style="float: right; width: auto;" type="button" name="createCredentials" onclick="alert('Functionality not yet implemented.')">
				Create credentials for professor
			</button>
		</div>

		<div style="margin-top: 15px; border: 2px solid #0C0B0B; border-radius: 14px; height: 100%;">
			<div class="overflow">
			<table style="border-collapse: separate; border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
				<c:if test="${empty listOfCommunications}">
					<tr>
						<td colspan="3" class="title-label" align="center">No
							communications to view.</td>
					</tr>
				</c:if>
				<c:forEach items="${listOfCommunications}" var="communication">
					<tr height="50px" class="tr">
						<td style="border-radius: 14px 0 0 14px; padding: 1vw;">
							<table style="display: inline; vertical-align: middle;">
								<tr>
									<td class="id"><img style="vertical-align: middle;"
										class="img" src="res/img/Megaphone.png" alt="com"></td>
									<td class="id">${communication.getId()}</td>
								</tr>

								<tr>
									<td style="padding-top: 0;" class="communication-date">
										${SQLConverter.date(communication.getDate())}</td>
								</tr>
							</table>
						</td>

						<td style="border-radius: 0 14px 14px 0; padding: 1vw;">
							<table style="display: inline; vertical-align: middle;">
								<tr>
									<td class="communication-title">${communication.getTitle()}</td>
								</tr>
								<tr>
									<td style="word-break: break-all" class="communication-text">
										${communication.getText()}</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
		</div>

		<a style="padding-bottom: 2vw;" href="/ispw_project/PostCommunicationServlet"
						type="button">
				<button type="button" class="button" id="postCommunication"
					name="postCommunication" style = "margin-top:15px;">
					<img style="vertical-align: middle;" class="plus-img"
						src="res/img/Plus.png" alt="add"> Post Communication
				</button>
		</a>
	</div>
</body>

<%String message = (String)request.getAttribute("alertMsg");
  if(message != null){ 
  System.out.println(message);%>
  	<script type="text/javascript">
    	alert("${alertMsg}");
  	</script>
<%} %>
</html>