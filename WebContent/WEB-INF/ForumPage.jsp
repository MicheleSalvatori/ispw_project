<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="logic.bean.UserBean"%>
<%@ page import="logic.bean.QuestionBean"%>
<%@ page import="logic.utilities.SQLConverter"%>
<%@ page import="logic.utilities.Role"%>
<%@ page import="java.util.List"%>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>

	<head>
		<meta charset="utf-8">
		<title>.myUniversity - ForumPage</title>
		<link rel="stylesheet" href="res/style/ForumPage.css">
		<link rel="stylesheet" href="res/style/ModalBox.css">
	</head>

	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>

		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>

		<!-- Page info -->
		<div class="content">
			<!--  Left side -->
			<div class="content-question">
				<a class="title-label">Questions</a>
				<!-- Question Table -->
				<div class="border-table" style="height: 100%;">
					<div class="overflow">
					<table class="table-question" id="tableQuestions">
						<tbody style="display: block; border-spacing: 0 10px;">
						<c:if test="${empty listOfQuestion}">
						<tr>
							<td colspan="7" class="exam-text">
								No one seems to have any questions to ask in your courses. Be the first!
							</td>
						</tr>
						</c:if>
							<c:forEach items="${listOfQuestion}" var="question">
								<tr height="50px" class="question">
									<td
										style="border-radius: 14px 0 0 14px; white-space: nowrap; padding: 1vw; width: 2vw;">
										<img class="img" src="res/img/Question.png" alt="q">
									</td>
									<td class="id">${question.getId()}</td>
									<td align="left" class="question-subject"
										style="text-align: left; flex: 1">
										<table style="display: inline; vertical-align: middle; flex: 1;">
											<tr>
												<td style="padding: 0;" class="question-subject">${question.getTitle()}</td>
											</tr>
		
											<tr>
												<td style="padding: 0;" class="question-date">${question.getDate()}</td>
											</tr>
		
											<tr>
												<c:choose>
													<c:when test="${question.isSolved()}">
														<td style="padding: 0; color: green;"
															class="question-solved">Solved</td>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when
																test="${question.getStudent().getUsername() == sessionScope.loggedUser.getUsername()}">
																<td>
																	<form action="/ispw_project/ForumPageServlet"
																		method="post">
																		<button name="set-solved" class="button-solved"
																			value="${question.getId()}"
																			onclick="return confirm('Are you sure you want to set this question as solved?')">Set
																			Solved</button>
																	</form>
																</td>
															</c:when>
															<c:otherwise>
																<td style="padding: 0; color: red;"
																	class="question-solved">Unsolved</td>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</tr>
										</table>
									<td align="right" style="text-align: left;">
										<table style="display: inline; vertical-align: middle; flex: 1;">
											<tr>
												<td class="author">${question.getStudent().getName()}</td>
											</tr>
											<tr>
												<td class="author">${question.getStudent().getSurname()}</td>
											</tr>
										</table>
								<td align="right" class="course" style="white-space: nowrap; text-decoration: underline; text-align: left; flex: 1">
									<a href="${pageContext.request.contextPath}/CoursePageServlet?course=${question.getCourse()}">
										${question.getCourse()}
									</a>
								</td>
								<td align="right"
									style="padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%; flex: 1">
									<form action="${pageContext.request.contextPath}/QuestionPageServlet" method="get">
										<input type="hidden" name="questionID" value="${question.getId()}" /> 
										<button class="button-view" type="submit">View</button>
									</form>
								</td>
								<td style = "border-radius: 0 14px 14px 0;">
									<input type="hidden" id = "question-username" name="question-username" value="${question.getStudent().getUsername()}">
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
			</div>
		
			<!-- Buttons -->
			<div class="content-right">
				<%if (user.getRole() == Role.STUDENT) { %>
				<a href="/ispw_project/NewQuestionPageServlet">
					<button type="button" class="new-question" id="newQuestion">
						Insert New Question
					</button>
				</a>
				<div>
					<button type="button" class="questions-button" id="myQuestion"
						name="myQuestion" onclick="filterMyQuestions()">My
						Questions</button>
		
					<button type="button" class="questions-button" style="float: right;"
						id="allQuestions" name="allQuestion" disabled="disabled" onclick="filterAllQuestions()">All
						Questions</button>
				</div>
				<% } else { %>
				<a href="/ispw_project/NewAssignmentPageServlet">
					<button type="button" class="new-question">
						Insert New Assignment
					</button>
				</a>
				<% } %>
		
				<div style="margin-top: 30px; height: 70%;">
					<a class="title-label">Assignments</a>
					<div class="border-table">
						<div class="overflow">
						<table class="table-question;" style="border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
								<c:forEach items="${listOfAssignment}" var="assignment">
									<tr height="30px" class="question">
										<td
											style="border-radius: 14px 0 0 14px; white-space: nowrap; padding: 1vw; width: 2vw;">
											<img class="img" src="res/img/Assignment.png" alt="q">
										</td>
										<td class="id">${assignment.getId()}</td>
										<td align="left" class="question-subject"
											style="text-align: left;">
											<table style="display: inline; vertical-align: middle;">
												<tr>
													<td style="padding: 0;" class="question-subject">${assignment.getTitle()}</td>
												</tr>
												<tr>
													<td style="padding: 0;" class="question-date">${assignment.getDate()}</td>
												</tr>
											</table>
										<td align="right" class="course" style="white-space: nowrap; text-decoration: underline; text-align: left;">
											<a href="${pageContext.request.contextPath}/CoursePageServlet?course=${assignment.getCourse()}">
												${assignment.getCourse()}
											</a>	
										</td>
		
										<td align="right" style="border-radius: 0 14px 14px 0; padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%;">
											<a href="${pageContext.request.contextPath}/AssignmentPageServlet?assignment=${assignment.getId()}" class="button-view">
												<button class="button-view" type="button">
													View
												</button>
											</a>
										</td>
									</tr>
									
								</c:forEach>
						</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
<script>
	function filterMyQuestions() {
		var td, username;
		var tableRows = document.getElementById("tableQuestions").rows;
		var sessionUsername = "${loggedUser.getUsername()}";

		for (i = 0; i < tableRows.length; i++) {
			var tdInput = tableRows[i].cells[6].getElementsByTagName('input')[0].value;
			
			if (tdInput != sessionUsername){
				tableRows[i].style.display = "none";
			}
		}
		document.getElementById("myQuestion").disabled = true;
		document.getElementById("allQuestions").disabled = false;
	}
	
	function filterAllQuestions() {
		var tableRows = document.getElementById("tableQuestions").rows;
		for (i = 0; i < tableRows.length; i++) {
			tableRows[i].style.display = "";
		}
		document.getElementById("myQuestion").disabled = false;
		document.getElementById("allQuestions").disabled = true;
	}
</script>

</html>