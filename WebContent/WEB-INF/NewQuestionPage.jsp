<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.bean.CourseBean"%>
<%@ page import="logic.utilities.Role"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.RequestDispatcher"%>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>

	<head>
		<meta charset="utf-8">
		<title>App - Ask a Question</title>
		<link rel="stylesheet" href="res/style/NewQuestionPage.css">
		<link rel="stylesheet" href="res/style/ModalBox.css">
	</head>

	<body>
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>

		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content">
			<a class="title-label">Post new question</a>
			<div style="margin-top: 20px;">
				<input class="input-subject" type="text" id="question-subject"
					name="question-subject" placeholder="Subject" onkeyup="fun()"
					onclick="fun()" form="question-form"> <select
					class="course-select" id="courses" name="courses" onclick="fun()"
					onkeyup="fun()" form="question-form">
					<option disabled selected value="0">Select course</option>
					<c:forEach items="${listOfCourses}" var="course">
						<option value="${course.getAbbreviation()}">${course.getAbbreviation()}</option>
					</c:forEach>
				</select>
			</div>
			<div style="margin-top: 20px;">
				<textarea id="questionText" onkeyup="fun()" onclick="fun()"
					placeholder="Question" name="question-text" form="question-form"></textarea>
			</div>
			<form
				action="${pageContext.request.contextPath}/NewQuestionPageServlet"
				method="post" id="question-form">
				<button class="button-submit" id="submit" name="submit"
					disabled="disabled"
					onclick="return confirm('Are you sure you want to ask this question?')">Submit</button>
			</form>
		</div>
	</body>

<script>
	function fun() {
		var text = document.getElementById("questionText").value.length;
		var course = document.getElementById("courses").value;
		var subject = document.getElementById("question-subject").value.length;

		if (text > 0 && subject > 0 && course != "0") {
			document.getElementById("submit").disabled = false;
		} else {
			document.getElementById("submit").disabled = true;
		}

	}
</script>


</html>
