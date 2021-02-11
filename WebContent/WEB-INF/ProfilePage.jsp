<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.utilities.Role" %>

<%
	UserBean user = (UserBean) session.getAttribute("loggedUser");
%>
	
	<head>
		<meta charset="utf-8">
		<title>App - HomePage</title>
		<link rel="stylesheet" href="res/style/ProfilePage.css">
		<link rel="stylesheet" href="res/style/ModalBox.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
	
		<!-- Navigation bar -->
		<jsp:include page="NavigationBar.jsp"/>
	
		<!-- Status bar -->
		<jsp:include page="StatusBar.jsp"/>
		
		<!-- Page info -->
		<div class="content row">
			
			<!-- Profile info -->
			<div style="width: 35%; margin-right: 5vw; float: left;">
				<div class="info-text">Profile</div>
				<div style="border: 2px solid #0C0B0B; border-radius: 14px; width: 100%; height: 100%;">
					<table align="center" style="width: 100%; padding: 10px; height: 100%; overflow: auto;">
						<tr>
							<td colspan="2" align="center">
								<img class="img-avatar" src="res/img/Logo.png" alt="avatar">
							</td>
						</tr>

						<tr>
							<td class="info-text">
								Username:
							</td>

							<td class="value-text">
								<%=user.getUsername()%>
							</td>
						</tr>

						<tr>
							<td class="info-text">
								Name:
							</td>

							<td class="value-text">
								<%=user.getName()%>
							</td>
						</tr>

						<tr>
							<td class="info-text">
								Surname:
							</td>

							<td class="value-text">
								<%=user.getSurname()%>
							</td>
						</tr>

						<tr>
							<td class="info-text">
								E-mail:
							</td>

							<td class="value-text">
								<%=user.getEmail()%>
							</td>
						</tr>

						<tr>
							<td class="info-text">
								Password:
								<input type="checkbox" id="checkbox" onclick="show()" style="display: none; text-align: right; ">
								<label for="checkbox"></label>
							</td>

							<td style="text-align: right;">
								<input class="value-text" type="password" value="<%=user.getPassword()%>" id="password" readonly style="border: 0; background-color: transparent;">
							</td>
						</tr>
					</table>
				</div>
				
				<form action="/ispw_project/ProfilePageServlet" name="passwordForm" method="post">
				<div style="padding-top: 25px; text-align: center; width: 100%;">
					<input name="password" type="hidden">
					<input class="change-text" type="submit" name="changePassword" value="Change your password" onclick="return getPassword()">
				</div>
				</form>
			</div>
			
			<!-- Courses info -->
			<div class="col" style="float: right;">
				<div class="info-text">Courses</div>
				<div style="border: 2px solid #0C0B0B; border-radius: 14px; width: 100%; height: 100%;">
					<div class="overflow">
					<table style="border-collapse: separate; border-spacing: 0 10px; width: 100%; border: 15px solid transparent;">
						<c:if test="${empty listOfCourse && empty listOfRequest}">
							<tr>
								<td colspan="3" class="course-text" align="center">
									No course found.									
								</td>
							</tr>
						</c:if>
						<c:forEach items="${listOfCourse}" var="course" varStatus="courseind">
						<tr height="50px" class="course">
							<td style="border-radius: 14px 0 0 14px; white-space: nowrap; padding: 1vw;">
								<table style="display: inline; vertical-align: middle;" spacing="0">
									<tr>
										<td class="course-text">
											<a href="/ispw_project/CoursePageServlet?course=${course.getAbbreviation()}">
												${course.getAbbreviation()}
											</a>
										</td>
									</tr>

									<tr>
										<td style="padding-top: 0; " class="course-professor">
											${listOfProfessor[courseind.index].getName()} ${listOfProfessor[courseind.index].getSurname()}
										</td>
									</tr>
								</table>
							</td>

							<td align="right" class="course-year" style="width: 8vw; text-align: center;">
								${course.getYear()}
							</td>

							<td align="right" class="course-year" style="width: 8vw; border-radius: 0 14px 14px 0; white-space: nowrap; text-align: center;">
								${course.getSemester()}
							</td>	
						</tr>
						</c:forEach>
						
						<c:forEach items="${listOfRequest}" var="request" varStatus="requestind">
						<tr height="50px" class="course">
							<td style="border-radius: 14px 0 0 14px; white-space: nowrap; padding: 1vw;">
								<table style="display: inline; vertical-align: middle;" spacing="0">
									<tr>
										<td class="course-text">
											${request.getAbbreviation()}
										</td>
									</tr>

									<tr>
										<td style="padding-top: 0; " class="course-professor">
											${listOfProfessor[requestind.index].getName()} ${listOfProfessor[requestind.index].getSurname()}
										</td>
									</tr>
								</table>
							</td>

							<td colspan="2" style="border-radius: 0 14px 14px 0; white-space: nowrap; text-align: right; padding-right: 2vw;">
								<form action="/ispw_project/ProfilePageServlet" method="post">
								<input type="hidden" name="course" value="${request.getAbbreviation()}" />
								<button name="deleteRequest" class="delete-button" type="submit" onclick="return confirm('Are you sure you want to delete?')">
									Delete
								</button>
								</form>
							</td>
						</tr>
						</c:forEach>
					</table>
					</div>
				</div>
				<div style="text-align: center; padding-top: 20px;">
					<button name="btnAdd" id="btnAdd" class="button-add" style="margin-right: 5vw;">
						<img style="vertical-align: middle;" class="plus-img" src="res/img/Plus.png" alt="add">
						Add Course
					</button>
						
					<button name="btnRemove" id="btnRemove" class="button-add">
						<img style="vertical-align: middle;" class="plus-img" src="res/img/Minus.png" alt="remove">
						Remove Course
					</button>
				</div>
			</div>
		</div>
		
		<!-- Modal add -->
		<div id="modalAdd" class="modal">
		
		   	<!-- Modal content -->
		   	<form action="/ispw_project/ProfilePageServlet" method="post">
			<div class="modal-content">
			
			  <div class="modal-header">
			    <span class="close-add">&times;</span>
			    <a class="modal-text">Add Course</a>
			  </div>
			  
			  <div class="modal-body" style="text-align: center; padding: 25px 0 25px 0;">
			  
			  	<select name="course-select" class="select" name="courses" id="course-select" style="width: 50%;" required>	
			  		<c:if test="${!empty listOfAvailable}">
			  			<option value="" disabled selected>Select course</option>
			  		</c:if>
			  		
			  		<c:if test="${empty listOfAvailable}">
			  			<option value="" disabled selected>No course available</option>
			  		</c:if>
			  		
				    <c:forEach items="${listOfAvailable}" var="course">
				    <option value="${course.getAbbreviation()}">
				    	${course.getName()}
				    </option>
				    </c:forEach>
				</select>
			  </div>
			  
			  <div class="modal-footer" style="text-align: right; padding: 10px 10px 10px 0;">
			    	<button class="modal-button" type="submit" name="submitAdd">Submit</button>
			  </div>
			</div> 
			</form>
		
		</div>
		
		<!-- Modal remove -->
		<div id="modalRemove" class="modal">
		
		   	<!-- Modal content -->
		   	<form action="/ispw_project/ProfilePageServlet" method="post">
			<div class="modal-content">
			
			  <div class="modal-header">
			    <span class="close-remove">&times;</span>
			    <a class="modal-text">Remove Course</a>
			  </div>
			  
			  <div class="modal-body" style="text-align: center; padding: 25px 0 25px 0;">
			  
			  	<select name="course-select" class="select" name="courses" id="course-select" style="width: 50%;" required>	
			  		<c:if test="${!empty listOfCourse}">
			  			<option value="" disabled selected>Select course</option>
			  		</c:if>
			  		
			  		<c:if test="${empty listOfCourse}">
			  			<option value="" disabled selected>No course available</option>
			  		</c:if>
			  		
				    <c:forEach items="${listOfCourse}" var="course">
				    <option value="${course.getAbbreviation()}">
				    	${course.getName()}
				    </option>
				    </c:forEach>
				</select>
			  </div>
			  
			  <div class="modal-footer" style="text-align: right; padding: 10px 10px 10px 0;">
			    	<button class="modal-button" type="submit" name="submitRemove">Submit</button>
			  </div>
			</div> 
			</form>
		
		</div> 
		
		<%String message = (String)session.getAttribute("error");
    	if(message != null){ 
    		session.setAttribute("error", null); %>
	        <script type="text/javascript">
	            var msg = "<%=message%>";
	            alert(msg);
	        </script>
   		<%} %>
		
	</body>
	
<script>
	//Get the modal
	var modalAdd = document.getElementById("modalAdd");
	var modalRemove = document.getElementById("modalRemove");
	
	// Get the button that opens the modal
	var btnAdd = document.getElementById("btnAdd");
	var btnRemove = document.getElementById("btnRemove");
	
	// Get the <span> element that closes the modal
	var spanAdd = document.getElementsByClassName("close-add")[0];
	var spanRemove = document.getElementsByClassName("close-remove")[0];
	
	// When the user clicks on the button, open the modal
	btnAdd.onclick = function() {
	  modalAdd.style.display = "block";
	}
	btnRemove.onclick = function() {
	  modalRemove.style.display = "block";
	}
	
	// When the user clicks on <span> (x), close the modal
	spanAdd.onclick = function() {
		modalAdd.style.display = "none";
	}
	spanRemove.onclick = function() {
		modalRemove.style.display = "none";
	}
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	  if (event.target == modalAdd) {
	    modalAdd.style.display = "none";
	  }
	  if (event.target == modalRemove) {
		modalRemove.style.display = "none";
	  }
	}
	
	function show() {
	  var x = document.getElementById("password");
	  if (x.type === "password") {
	    x.type = "text";
	  } else {
	    x.type = "password";
	  }
	}
	
	function getPassword() {
		var pass = prompt("Insert a new password");
		var form = document.forms.passwordForm;
		form.elements.password.value = pass;
		if (pass) {
			return confirm('Are you sure you want to continue?');
		}
		else if (pass == null) {
			return false;
		}
		else {
			alert("Blank password is not allowed");
			return false;
		}
	}
</script>

</html>