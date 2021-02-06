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
						class="nav-button" type="button"> <svg class="nav-icon"
								width="27" height="26" viewBox="0 0 27 26" fill="white"
								xmlns="http://www.w3.org/2000/svg">
								<path
									d="M 25.0001 4.16666 C 23.3575 4.16666 21.7737 4.37439 20.2617 4.72615 C 19.9464 4.79974 19.6618 4.96944 19.4471 5.2118 C 19.2324 5.45417 19.0983 5.75722 19.0634 6.07909 L 18.7318 9.10237 C 18.6233 10.0931 18.051 10.9696 17.1876 11.4685 C 16.3258 11.9664 15.2799 12.0215 14.3677 11.6211 H 14.3657 L 11.5886 10.3983 C 11.2924 10.268 10.963 10.2328 10.646 10.2977 C 10.3289 10.3626 10.0398 10.5243 9.81858 10.7605 C 7.65973 13.0621 6.00285 15.849 5.06191 18.9595 C 4.96818 19.2691 4.97284 19.6002 5.07524 19.907 C 5.17764 20.2138 5.37273 20.4813 5.63361 20.6726 L 8.09739 22.4792 C 8.90193 23.0707 9.37506 24.0029 9.37506 25 C 9.37506 25.9976 8.90194 26.9306 8.09739 27.5207 L 5.63361 29.3253 C 5.37273 29.5166 5.17764 29.7841 5.07524 30.0909 C 4.97284 30.3978 4.96818 30.7288 5.06191 31.0384 C 6.00275 34.1486 7.65832 36.9376 9.81858 39.2395 C 10.04 39.4754 10.3292 39.6367 10.6463 39.7012 C 10.9633 39.7657 11.2926 39.7302 11.5886 39.5996 L 14.3657 38.3769 C 15.2783 37.9755 16.3252 38.0333 17.1876 38.5315 C 18.051 39.0304 18.6233 39.9069 18.7318 40.8976 L 19.0634 43.9209 C 19.0986 44.2421 19.2326 44.5445 19.4468 44.7865 C 19.6611 45.0284 19.9451 45.198 20.2597 45.2718 C 21.7723 45.6248 23.3575 45.8333 25.0001 45.8333 C 26.6427 45.8333 28.2265 45.6256 29.7384 45.2738 C 30.0537 45.2002 30.3384 45.0305 30.553 44.7882 C 30.7677 44.5458 30.9018 44.2428 30.9367 43.9209 L 31.2684 40.8976 C 31.3768 39.9069 31.9491 39.0304 32.8126 38.5315 C 33.6743 38.0336 34.7202 37.9764 35.6324 38.3769 L 38.4115 39.5996 C 38.7075 39.7302 39.0368 39.7657 39.3538 39.7012 C 39.6709 39.6367 39.9601 39.4754 40.1815 39.2395 C 42.3404 36.9379 43.9973 34.1489 44.9382 31.0384 C 45.0319 30.7288 45.0273 30.3978 44.9249 30.0909 C 44.8225 29.7841 44.6274 29.5166 44.3665 29.3253 L 41.9027 27.5207 C 41.0982 26.9306 40.6251 25.9976 40.6251 25 C 40.6251 24.0024 41.0982 23.0694 41.9027 22.4792 L 44.3665 20.6746 C 44.6274 20.4834 44.8225 20.2159 44.9249 19.9091 C 45.0273 19.6022 45.0319 19.2712 44.9382 18.9616 C 43.9973 15.851 42.3404 13.0621 40.1815 10.7605 C 39.9601 10.5246 39.6709 10.3633 39.3538 10.2988 C 39.0368 10.2343 38.7075 10.2698 38.4115 10.4004 L 35.6324 11.6231 C 34.7202 12.0236 33.6743 11.9664 32.8126 11.4685 C 31.9491 10.9696 31.3768 10.0931 31.2684 9.10237 L 30.9367 6.07909 C 30.9015 5.75786 30.7675 5.45545 30.5533 5.21352 C 30.3391 4.97158 30.0551 4.80202 29.7405 4.72818 C 28.2278 4.37515 26.6427 4.16666 25.0001 4.16666 Z M 25.0001 7.29166 C 26.015 7.29166 26.9896 7.47382 27.9664 7.64566 L 28.1617 9.44213 C 28.3782 11.4202 29.5281 13.1795 31.2501 14.1744 C 32.9732 15.1699 35.071 15.2844 36.8917 14.4836 L 38.5438 13.7573 C 39.8123 15.2806 40.8167 17.0012 41.5182 18.8863 L 40.0534 19.9605 C 38.4496 21.137 37.5001 23.0101 37.5001 25 C 37.5001 26.9899 38.4496 28.863 40.0534 30.0395 L 41.5182 31.1137 C 40.8167 32.9988 39.8123 34.7194 38.5438 36.2427 L 36.8917 35.5163 C 35.071 34.7156 32.9732 34.8301 31.2501 35.8256 C 29.5281 36.8205 28.3782 38.5798 28.1617 40.5578 L 27.9664 42.3543 C 26.9896 42.5257 26.0145 42.7083 25.0001 42.7083 C 23.9852 42.7083 23.0106 42.5262 22.0338 42.3543 L 21.8384 40.5578 C 21.6219 38.5798 20.472 36.8205 18.7501 35.8256 C 17.027 34.8301 14.9291 34.7156 13.1084 35.5163 L 11.4564 36.2427 C 10.1875 34.7196 9.18333 32.9989 8.48191 31.1137 L 9.94676 30.0395 C 11.5505 28.863 12.5001 26.9899 12.5001 25 C 12.5001 23.0101 11.5498 21.1358 9.94676 19.9585 L 8.48191 18.8843 C 9.18374 16.9985 10.1891 15.279 11.4584 13.7553 L 13.1084 14.4816 C 14.9291 15.2823 17.027 15.1699 18.7501 14.1744 C 20.472 13.1795 21.6219 11.4202 21.8384 9.44213 L 22.0338 7.64566 C 23.0105 7.4743 23.9857 7.29166 25.0001 7.29166 Z M 25.0001 16.6667 C 20.4162 16.6667 16.6667 20.4161 16.6667 25 C 16.6667 29.5839 20.4162 33.3333 25.0001 33.3333 C 29.5839 33.3333 33.3334 29.5839 33.3334 25 C 33.3334 20.4161 29.5839 16.6667 25.0001 16.6667 Z M 25.0001 19.7917 C 27.8951 19.7917 30.2084 22.105 30.2084 25 C 30.2084 27.895 27.8951 30.2083 25.0001 30.2083 C 22.1051 30.2083 19.7917 27.895 19.7917 25 C 19.7917 22.105 22.1051 19.7917 25.0001 19.7917 Z"
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
		<a class="title-label">Administration Control Panel</a>
		<div style="margin-right: 100px; margin-left: 100px;">
			<button class="button" type="button" name="addCourse">Add
				Course in the System</button>
			<button class="button" style="float: right; width: auto;"
				type="button" name="createCredentials">Create credentials
				for professor</button>
		</div>

		<div
			style="margin-top: 15px; height: 90%; border: 2px solid #0C0B0B; border-radius: 14px;">

			<table
				style="height: 100px; border-collapse: separate; border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
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

		<a href="/ispw_project/PostCommunicationServlet"
						class="nav-button" type="button">
				<button type="button" class="button" id="postCommunication"
					name="postCommunication" style = "margin-top:15px;">
					<img style="vertical-align: middle;" class="plus-img"
						src="res/img/Plus.png" alt="add"> Post Communication
				</button>
		</a>
	</div>
</body>
</html>