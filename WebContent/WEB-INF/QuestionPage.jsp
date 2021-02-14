<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.bean.QuestionBean"%>
<%@ page import="logic.bean.AnswerBean"%>
<%@ page import="logic.utilities.SQLConverter"%>
<%@ page import="logic.utilities.Role"%>
<%@ page import="javax.servlet.RequestDispatcher"%>

<jsp:useBean id="question" class="logic.bean.QuestionBean" scope="request" />

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
	question = (QuestionBean) request.getAttribute("question");
%>
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - QuestionPage</title>
		<link rel="stylesheet" href="res/style/ModalBox.css">
		<link rel="stylesheet" href="res/style/StatusBar.css">
		<link rel="stylesheet" href="res/style/QuestionPage.css">
		
	</head>

	<body>
		
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>

		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content">
		
			<!-- Question info -->
			<div>
			<a class="title-label"><%=question.getTitle()%></a>
			<a class="author" style = "margin-left:10px;text-align: right;"><%=question.getStudent().getName() + " " + question.getStudent().getSurname() + " - "%> </a>
			<a class="author" style = "text-align: right;"><%=question.getDate()%></a>
			</div>
			<div
				style="height: 100%; border: 2px solid #0C0B0B; border-radius: 14px;">
				<table
					style="border-collapse: separate; border-spacing: 0; width: 100%; border: 15px solid transparent; table-layout: fixed;">
					<tbody style="overflow: auto; display: block;">
						<tr>
							<td style="word-break: break-all" class="question-text"><%=question.getText()%></td>
						</tr>
					</tbody>
				</table>
			</div>
			<form action="/ispw_project/QuestionPageServlet" method="post">
				<div
					style="width: 50%; float: right; text-align: right; padding-top: 10px;">
					<button type="button" class="add-answer" id="addAnswer"
						name="addAnswer">
						<img style="vertical-align: middle;" class="plus-img"
							src="res/img/Plus.png" alt="add"> Add Answer
					</button>
				</div>
			</form>
		
			<!-- Answers  -->
			<div style="padding-top: 100px">
				<c:choose>
					<c:when test="${empty listOfAnswers}">
						<a class="empty-label"> No one seems to have a solution. Be the
							first! </a>
					</c:when>
					<c:otherwise>
						<a class="title-label">Answers</a>
						<c:forEach items="${listOfAnswers}" var="answer">
							<div style="height: 100%; margin-top: 10px; border: 2px solid #0C0B0B; border-radius: 14px; padding: 4px;">
								<div class="overflow">
								<div style="margin-left: 10px; margin-right: 10px;">
									<a class="author"> ${answer.getUser().getName()}
										${answer.getUser().getSurname()}</a> <a class="author"
										style="float: right;">
										${SQLConverter.date(answer.getDate())}</a>
								</div>
								<hr
									style="height: 2px; border-width: 0; color: black; background-color: black; margin-left: 10px; margin-right: 10px;">
								<div>
									<a class="answer-text">${answer.getText()}</a>
								</div>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<div id="answerModal" class="modal">
		
			<!-- Modal content -->
			<form action="/ispw_project/QuestionPageServlet" method="post">
				<div class="modal-content">
		
					<div class="modal-header">
						<span class="close">&times;</span> <a class="modal-text">Post an
							answer:</a>
					</div>
		
					<div class="modal-body"
						style="text-align: center; padding: 25px 0 25px 0;">
		
						<textarea id="answerText" onkeyup="fun()" onclick="fun()"
							placeholder="Answer" name="answer-text"></textarea>
		
					</div>
					<div class="modal-footer"
						style="text-align: right; padding: 10px 10px 10px 0;">
						<button class="modal-button" type="submit" name="submitAdd"
							id="submit" disabled="disabled"
							onclick="return confirm('Are you sure you want to submit this answer?')">Submit</button>
						<input type="hidden" name="id" value="${questionID}">
					</div>
				</div>
			</form>
		
		</div>
	</body>

<script>
	var modal = document.getElementById("answerModal");
	var btn = document.getElementById("addAnswer");
	var span = document.getElementsByClassName("close")[0];

	btn.onclick = function() {
		modal.style.display = "block";
	}

	span.onclick = function() {
		modal.style.display = "none";
	}

	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

	function fun() {
		var text = document.getElementById("answerText").value.length;
		console.log("wlf");
		if (text > 0) {
			document.getElementById("submit").disabled = false;
		} else {
			document.getElementById("submit").disabled = true;
		}
	}
</script>
</html>