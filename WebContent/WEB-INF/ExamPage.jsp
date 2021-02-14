<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.SQLConverter" %>
<%@ page import="logic.utilities.Role" %>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>
	
	<head>
		<meta charset="utf-8">
		<title>.myUniversity - ExamPage</title>
		<link rel="stylesheet" href="res/style/ExamPage.css">
		<link rel="stylesheet" href="res/style/ModalBox.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content">
		
			<a class="exam-label">Exams</a>
			
			<!-- Exam table	-->
			<div width="100%" style="height: 100%; border: 2px solid #0C0B0B; border-radius: 14px;">
				<div class="overflow">
				<table style="border-collapse: separate; border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
					<c:if test="${empty listOfExam}">
						<tr>
							<td colspan="7" class="exam-text" style="text-align: center;">
								No exam found.
							</td>
						</tr>
					</c:if>
					<c:forEach items="${listOfExam}" var="exam" varStatus="examind">	
						<tr height="50px" class="exam">
							<td style="border-radius: 14px 0 0 14px; white-space: nowrap; padding: 1vw; width: 2vw;">
								<img class="img" src="res/img/Exam.png" alt="exam">
							</td>

							<td style="text-align: left; width: 2vw;" class="exam-number">
								${examind.count}
							</td>

							<td>
								<table style="display: inline; vertical-align: middle;" spacing="0">
									<tr>
										<td class="exam-text">
											${exam.getCourse().getName()}
										</td>
									</tr>

									<tr>
										<td style="padding-top: 0;" class="exam-date">
											${SQLConverter.date(exam.getDate())}
										</td>
									</tr>
								</table>
							</td>

							<td align="right" class="exam-grade" style="width: 8vw; text-align: center;">
								${exam.getGrade()}
							</td>

							<td align="right" class="exam-grade" style="width: 8vw; text-align: center;">
								${exam.getCourse().getCredits()}
							</td>

							<td align="right" class="exam-grade" style="width: 8vw; text-decoration: underline; text-align: center;">
								<a href="/ispw_project/CoursePageServlet?course=${exam.getCourse().getAbbreviation()}">
									${exam.getCourse().getAbbreviation()}
								</a>
							</td>
							
							<td align="right" style="border-radius: 0 14px 14px 0; white-space: nowrap; text-align: center;">
								<form action="/ispw_project/ExamPageServlet" method="post">
								<input type="hidden" name="course" value="${exam.getCourse().getAbbreviation()}" />
								<button name="deleteExam" class="exam-button" type="submit" onclick="return confirm('Are you sure you want to delete?')">
									Delete
								</button>
								</form>
							</td>
						</tr>	
					</c:forEach>
				</table>
				</div>
			</div>
		
			<!-- Averages -->
			<div style="padding-top: 0.5vw; float: left;">
				<table>
					<tr>
						<td class="exam-average">
							Verbalized Exams:
						</td>
						
						<td class="num-average">
							${fn:length(listOfExam)}
						</td>
					</tr>
					
					<tr>
						<td class="exam-average">
							Grade point average:
						</td>
						
						<td class="num-average">
							<%=request.getAttribute("gpa")%>
						</td>
					</tr>
					
					<tr>
						<td class="exam-average">
							Weighted point average:
						</td>
						
						<td class="num-average">
							<%=request.getAttribute("wpa")%>
						</td>
					</tr>
				
				</table>
			</div>
			
			<!-- Add button	-->
			<form action="/ispw_project/ExamPageServlet" method="post">
			<div style="width: 50%; float: right; text-align: right; padding-top: 50px;">
				<button type="button" class="add-exam-button" id="addExam" name="addExam">
					<img style="vertical-align: middle;" class="plus-img" src="res/img/Plus.png" alt="add">
					Add Exam
				</button>
			</div>
			</form>
		
		</div>
		
		<!-- The Modal -->
		<div id="myModal" class="modal">
		
		   	<!-- Modal content -->
		   	<form action="/ispw_project/ExamPageServlet" method="post">
			<div class="modal-content">
			
			  <div class="modal-header">
			    <span class="close">&times;</span>
			    <a class="modal-text">Add Exam</a>
			  </div>
			  
			  <div class="modal-body" style="text-align: center; padding: 25px 0 25px 0;">
			  
			  	
			  	<select name="course-select" class="select" name="courses" id="course-select" style="float: left; margin-left: 25px;" required>	
			  		<c:if test="${!empty listOfCourse}">
			  			<option value="" disabled selected>Select course</option>
			  		</c:if>
			  		
			  		<c:if test="${empty listOfCourse}">
			  			<option value="" disabled selected>No course available</option>
			  		</c:if>
			  		
				    <c:forEach items="${listOfCourse}" var="course">
				    <option value="${course.getAbbreviation()}">
				    	${course.getAbbreviation()}
				    </option>
				    </c:forEach>
				</select>
				
				<input name="date" type="date" class="datepicker-input" required>
				
				<select name="grade-select" class="select" name="grades" id="grade-select" style="float: right; margin-right: 25px;" required>
					<option value="" disabled selected>Select grade</option>
					<c:forEach var="i" begin="18" end="30">
						<option value="${i}">${i}</option>
					</c:forEach>
				</select>
			  </div>
			  
			  <div class="modal-footer" style="text-align: right; padding: 10px 10px 10px 0;">
			    	<button class="modal-button" type="submit" name="submitAdd">Submit</button>
			  </div>
			</div> 
			</form>
		
		</div> 
		
	</body>
	
<script>
	//Get the modal
	var modal = document.getElementById("myModal");
	
	// Get the button that opens the modal
	var btn = document.getElementById("addExam");
	
	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];
	
	// When the user clicks on the button, open the modal
	btn.onclick = function() {
	  modal.style.display = "block";
	}
	
	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
	  modal.style.display = "none";
	}
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	  if (event.target == modal) {
	    modal.style.display = "none";
	  }
	} 
</script>

</html>