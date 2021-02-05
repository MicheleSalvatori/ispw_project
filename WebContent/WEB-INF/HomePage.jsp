<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="logic.bean.UserBean" %>
<%@ page import="logic.bean.LessonBean" %>
<%@ page import="logic.utilities.SQLConverter" %>
<%@ page import="logic.utilities.Role" %>

<%	
	UserBean user = (UserBean) request.getSession().getAttribute("loggedUser");
	LessonBean lesson = (LessonBean) request.getAttribute("lesson");
	//session.setAttribute("lesson", lesson);
%>
	
	<head>
		<meta charset="utf-8">
		<title>App - HomePage</title>
		<link rel="stylesheet" href="res/style/HomePage.css">
		<link rel="stylesheet" href="res/style/NavigationBar.css">
		<link rel="stylesheet" href="res/style/StatusBar.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>
	
	<body>
		<!-- Side navigation -->
		<form action="${pageContext.request.contextPath}/HomePageServlet" method="post">
		<div class="sidenav">
		  <table class="nav-root" width="132" border="0">
			  <tbody>
				<tr>
					<td valign="top"><p class="nav-logo">F.</p></td>
				</tr>
				<tr>
					<td valign="top" style="padding-bottom: 20px;" align="center">
						<a href="/ispw_project/HomePageServlet" class="nav-button" type="button">
							<svg class="nav-icon" width="27" height="26" viewBox="0 0 27 26" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M14.6444 1.40837L14.6446 1.40854L25.2517 12.0152L25.2517 12.0153C25.2517 12.0153 25.2517 12.0153 25.2518 12.0153L25.2518 12.0153L25.2518 12.0154C25.2518 12.0154 25.2518 12.0154 25.2518 12.0154C25.2518 12.0154 25.2519 12.0154 25.2519 12.0154C25.2519 12.0154 25.2519 12.0155 25.2519 12.0155C25.7934 12.5585 25.793 13.4433 25.2509 13.9858C24.9858 14.2505 24.6392 14.3939 24.2656 14.3939H23.8736H22.8736V15.3939V23.1989C22.8736 24.191 22.0648 25 21.0721 25H17.161V19.1191C17.161 17.8621 16.14 16.8411 14.8829 16.8411H12.4355C11.1785 16.8411 10.1575 17.8621 10.1575 19.1191V25H6.24636C5.2537 25 4.44487 24.191 4.44487 23.1989V15.3939V14.3939H3.44487H3.03362L3.0184 14.3924L2.99124 14.3912C2.64014 14.3761 2.31541 14.2335 2.06733 13.9853L2.06716 13.9851C1.52582 13.4438 1.52465 12.5605 2.06463 12.0174L2.07253 12.0094L2.07623 12.0056L12.6737 1.40833L12.6737 1.40832C12.9385 1.14356 13.2852 1 13.6592 1C14.0331 1 14.3798 1.14362 14.6444 1.40837ZM25.2516 12.0151C25.2516 12.0151 25.2516 12.0151 25.2516 12.0151L25.2515 12.0151C25.2516 12.0151 25.2516 12.0151 25.2516 12.0151ZM2.07789 12.0039L2.07758 12.0042L2.07789 12.0039Z" stroke="white" stroke-width="2"/>
							</svg>
						</a>
					</td>
				</tr>
				  
				<tr>
					<td style="padding-bottom: 20px;" align="center">
						<%if (user.getRole() == Role.STUDENT) { %>
						<a href="/ispw_project/ExamPageServlet" class="nav-button" type="button" width="50%">
							<svg class="nav-icon" width="31" height="27" viewBox="0 0 31 27" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M28.7954 24.9126C28.7503 24.9903 28.7052 25.0669 28.6604 25.142C28.6156 25.0669 28.5705 24.9903 28.5254 24.9126C28.2844 24.4972 28.0548 24.0676 27.8884 23.689C27.8054 23.4998 27.7443 23.3374 27.7053 23.2055C27.686 23.1404 27.6741 23.0892 27.6672 23.051C27.6602 23.0117 27.6604 22.9961 27.6604 23C27.6604 22.6395 27.8555 22.3206 28.163 22.1419L28.6604 21.8527L29.1578 22.1419C29.4653 22.3206 29.6604 22.6395 29.6604 23C29.6604 22.9961 29.6606 23.0117 29.6536 23.051C29.6467 23.0892 29.6348 23.1404 29.6155 23.2055C29.5765 23.3374 29.5154 23.4998 29.4324 23.689C29.266 24.0676 29.0364 24.4972 28.7954 24.9126ZM15.6349 1.01359L15.6481 1.00699L15.6597 1.00076L15.6749 1.00908L15.6918 1.01752L29.5808 7.96009L29.6572 8L29.6243 8.01722L15.6858 14.9864L15.6727 14.993L15.6611 14.9992L15.6459 14.9909L15.629 14.9825L1.73994 8.03989L1.66356 8L1.74028 7.95994L15.6349 1.01359ZM17.4728 18.5643L17.4729 18.5643L23.6604 15.4698V18C23.6604 18.8524 23.0266 19.8346 21.5388 20.6612C20.085 21.469 18.0087 22 15.6604 22C13.3121 22 11.2358 21.469 9.78195 20.6612C8.29419 19.8346 7.6604 18.8524 7.6604 18V15.4694L13.8438 18.5603C13.8445 18.5607 13.8452 18.561 13.846 18.5614C13.9638 18.6209 14.0826 18.6708 14.2021 18.7123C14.6688 18.9009 15.159 19 15.6604 19C16.1634 19 16.6547 18.9007 17.123 18.7121C17.2399 18.6712 17.3561 18.6227 17.4728 18.5643Z" stroke="white" stroke-width="2"/>
							</svg>
						</a>
						<%} else { %>
						<a href="/ispw_project/RequestPageServlet" class="nav-button" type="button">
							<svg width="21" height="27" viewBox="0 0 21 27" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M0.0351562 0V3.375H1.19531C1.56885 8.89892 4.60547 12.2212 6.25781 13.6406C5.00098 14.77 1.61279 18.0835 1.19531 23.625H0.0351562V27H20.2852V23.625H19.125C18.7075 18.0835 15.3193 14.77 14.0625 13.6406C15.7192 12.2388 18.7646 8.97363 19.125 3.375H20.2852V0H0.0351562ZM3.48047 3.375H16.8398C16.3608 9.97998 11.8477 12.5156 11.8477 12.5156L10.4062 13.3594L11.707 14.3789C11.707 14.3789 16.2686 18.1099 16.8398 23.625H16.6641C15.7324 21.1685 12.2871 18 10.1602 18C8.0332 18 4.58789 21.1685 3.65625 23.625H3.48047C4.05176 18.1099 8.61328 14.3789 8.61328 14.3789L9.87891 13.3594L8.47266 12.5508C8.47266 12.5508 3.95947 9.89648 3.48047 3.375ZM7.91016 9C7.91016 10.0107 9.14942 12.375 10.1602 12.375C11.1709 12.375 12.4102 10.0107 12.4102 9H7.91016Z" fill="white"/>
							</svg>
						</a>
						<%} %>
					</td>
				</tr>
				  
				<tr>
					<td style="padding-bottom: 20px;" align="center">
						<a href="/ispw_project/ProfilePageServlet" class="nav-button" type="button">
							<svg class="nav-icon" width="26" height="32" viewBox="0 0 26 32" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M5.8 8C5.8 4.14822 8.94822 1 12.8 1C16.6518 1 19.8 4.14822 19.8 8C19.8 11.8518 16.6518 15 12.8 15C8.94822 15 5.8 11.8518 5.8 8ZM1 22.8C1 21.3783 2.17828 20.2 3.6 20.2H22C23.4217 20.2 24.6 21.3783 24.6 22.8V23.7594C24.6 25.6597 23.3904 27.4941 21.2951 28.8303C19.2095 30.1603 16.2915 31 12.8 31C9.30855 31 6.39049 30.1603 4.30487 28.8303C2.20957 27.4941 1 25.6597 1 23.7594V22.8Z" stroke="white" stroke-width="2"/>
							</svg>
						</a>
					</td>
				</tr>
				  
				<tr>
					<td style="padding-bottom: 20px;" align="center">
						<a href="/ispw_project/ForumPageServlet" class="nav-button" type="button">
							<svg class="nav-icon" width="27" height="25" viewBox="0 0 27 25" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M1.99755 18.8087L1.99599 18.8079C1.7829 18.701 1.6604 18.4965 1.6604 18.2735V4.1625C1.6604 2.43364 3.09398 1 4.8854 1H17.2354C19.0268 1 20.4604 2.43364 20.4604 4.1625V11.2067C20.4604 12.9356 19.0268 14.3692 17.2354 14.3692H9.10151H8.78016L8.51897 14.5564L2.65882 18.7564L2.6587 18.7565C2.54849 18.8355 2.41928 18.8757 2.28667 18.8757C2.18405 18.8757 2.08513 18.8528 1.99755 18.8087ZM18.7992 19.6795L18.5381 19.4923H18.2167H10.0854C9.36344 19.4923 8.6945 19.2556 8.15683 18.8589L9.84754 17.65H17.2354C20.8221 17.65 23.7604 14.7764 23.7604 11.2067V6.40404C24.8845 6.90443 25.6604 8.01711 25.6604 9.28557V23.399C25.6604 23.6222 25.5378 23.8222 25.3328 23.922L25.3209 23.9278L25.3091 23.9339C25.2242 23.9781 25.1294 24 25.0354 24C24.9006 24 24.7734 23.9606 24.6619 23.8807C24.6619 23.8807 24.6619 23.8807 24.6619 23.8807L18.7992 19.6795Z" stroke="white" stroke-width="2"/>
							</svg>
							</a>
					</td>
				</tr>
				  
				<tr>
					<td style="padding-bottom: 20px;" align="center">
						<button class="nav-button" type="button" name="news">
							<svg class="nav-icon" width="27" height="22" viewBox="0 0 27 22" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M22.6717 5.03109L21.9937 4.95841V4.25C21.9937 2.41389 20.4441 1 18.6366 1H5.01754C3.21004 1 1.6604 2.41389 1.6604 4.25V16.75C1.6604 19.1376 3.67989 21 6.06516 21H21.2556C23.6409 21 25.6604 19.1376 25.6604 16.75V8.25C25.6604 6.53348 24.3035 5.206 22.6717 5.03109Z" stroke="white" stroke-width="2"/>
							</svg>
						</button>
					</td>
				</tr>
				 	
				<tr>
					<td style="padding-bottom: 20px;" align="center">
						<%if (user.getRole() == Role.PROFESSOR) { %>
						<a href="/ispw_project/SchedulePageServlet" class="nav-button">
							<svg class="nav-icon" width="32" height="31" viewBox="0 0 32 31" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M21.3271 2.58333H23.9104V6.45833H21.3271V2.58333ZM8.4104 15.5C4.1479 15.5 0.6604 12.0125 0.6604 7.75C0.6604 3.4875 4.1479 0 8.4104 0C12.6729 0 16.1604 3.4875 16.1604 7.75C16.1604 12.0125 12.6729 15.5 8.4104 15.5ZM8.4104 2.58333C5.56873 2.58333 3.24373 4.90833 3.24373 7.75C3.24373 10.5917 5.56873 12.9167 8.4104 12.9167C11.2521 12.9167 13.5771 10.5917 13.5771 7.75C13.5771 4.90833 11.2521 2.58333 8.4104 2.58333ZM11.3812 9.42917L9.44373 7.62083L10.9937 4.90833L9.5729 4.13333L7.37707 7.87917L10.2187 10.7208L11.3812 9.42917Z" fill="white"/>
								<path d="M25.2021 5.16663H18.4053C18.6184 5.99329 18.7437 6.85613 18.7437 7.74996H25.2021V10.3333H18.4053C18.1689 11.2504 17.8137 12.1171 17.35 12.9166H25.2021L25.2034 25.8333H7.11873V17.9942C6.21586 17.8818 5.35044 17.6558 4.5354 17.3251V25.8333C4.5354 27.258 5.69403 28.4166 7.11873 28.4166H25.2021C26.6268 28.4166 27.7854 27.258 27.7854 25.8333V7.74996C27.7854 6.32525 26.6268 5.16663 25.2021 5.16663Z" fill="white"/>
							</svg>
						</a>
						<%} %>
					</td>
				</tr>
				  
				<tr>
					<td style="padding-bottom: 20px; padding-top: 100px;" align="center">
						<button class="nav-button" type="button" name="back" onclick="history.back()">
							<svg style="cursor: pointer;" width="27" height="26" viewBox="0 0 27 26" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M11.9158 13.1H9.50159L11.2087 14.8071L12.9533 16.5517C12.9918 16.5902 12.9918 16.6544 12.9533 16.6929C12.9148 16.7314 12.8506 16.7314 12.8121 16.6929L9.18981 13.0706C9.14973 13.0305 9.15103 12.9681 9.1889 12.9303L9.18981 12.9294L12.8121 9.30711C12.8506 9.26863 12.9148 9.26863 12.9533 9.30711C12.9918 9.34558 12.9918 9.40982 12.9533 9.44829L11.2087 11.1929L9.50159 12.9H11.9158H18.0604C18.1153 12.9 18.1604 12.9451 18.1604 13C18.1604 13.0549 18.1153 13.1 18.0604 13.1H11.9158ZM13.6604 1C7.03282 1 1.6604 6.37242 1.6604 13C1.6604 19.6276 7.03282 25 13.6604 25C20.288 25 25.6604 19.6276 25.6604 13C25.6604 6.37242 20.288 1 13.6604 1Z" stroke="white" stroke-width="2"/>
							</svg>
						</button>
					</td>
				</tr>
				  
			  </tbody>
			</table>
		</div>
		</form>
	
		<!-- Status bar -->
		<div class="topnav">
			<table align="right">
			  <tbody>
				<tr>
				  	<td style="padding-right: 10px;">
						<label class="status-label" id="statusName">
							<%=user.getName()%>
						</label>
					</td>
					
				  	<td style="padding-right: 10px;">
						<button class="status-button" type="button" name="notification">
							<svg width="24" height="26" viewBox="0 0 24 26" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M23.0209 11.6458C22.5724 11.6458 22.2084 11.2818 22.2084 10.8333C22.2084 7.72192 20.9974 4.79803 18.7981 2.59778C18.4807 2.2804 18.4807 1.76584 18.7981 1.44846C19.1155 1.13107 19.63 1.13107 19.9476 1.44846C22.4534 3.95519 23.8334 7.2887 23.8334 10.8333C23.8334 11.2818 23.4694 11.6458 23.0209 11.6458Z" fill="#0C0B0B"/>
								<path d="M0.8125 11.6458C0.363998 11.6458 0 11.2818 0 10.8333C0 7.28868 1.38022 3.95517 3.88695 1.44943C4.20433 1.13205 4.71909 1.13205 5.03647 1.44943C5.35385 1.76682 5.35385 2.28157 5.03647 2.59895C2.83621 4.79802 1.625 7.72191 1.625 10.8333C1.625 11.2818 1.261 11.6458 0.8125 11.6458Z" fill="#0C0B0B"/>
								<path d="M11.9165 26C9.67618 26 7.854 24.1778 7.854 21.9375C7.854 21.489 8.218 21.125 8.6665 21.125C9.11501 21.125 9.479 21.489 9.479 21.9375C9.479 23.282 10.572 24.375 11.9165 24.375C13.2608 24.375 14.354 23.282 14.354 21.9375C14.354 21.489 14.718 21.125 15.1665 21.125C15.615 21.125 15.979 21.489 15.979 21.9375C15.979 24.1778 14.1568 26 11.9165 26Z" fill="#0C0B0B"/>
								<path d="M20.8542 22.75H2.97922C1.93364 22.75 1.08325 21.8996 1.08325 20.8542C1.08325 20.2994 1.32486 19.7741 1.74638 19.4133C3.394 18.0212 4.33325 15.9977 4.33325 13.8538V10.8333C4.33325 6.65175 7.735 3.25 11.9167 3.25C16.0982 3.25 19.5 6.65175 19.5 10.8333V13.8538C19.5 15.9977 20.4392 18.0212 22.0761 19.4058C22.5084 19.7741 22.75 20.2994 22.75 20.8542C22.75 21.8996 21.8996 22.75 20.8542 22.75ZM11.9167 4.875C8.63081 4.875 5.95825 7.54756 5.95825 10.8333V13.8538C5.95825 16.4763 4.80893 18.9529 2.80585 20.6461C2.76796 20.6787 2.70825 20.7459 2.70825 20.8542C2.70825 21.0014 2.83183 21.125 2.97922 21.125H20.8542C21.0014 21.125 21.125 21.0014 21.125 20.8542C21.125 20.7459 21.0655 20.6787 21.0296 20.6483C19.0243 18.9529 17.875 16.4763 17.875 13.8538V10.8333C17.875 7.54756 15.2024 4.875 11.9167 4.875Z" fill="#0C0B0B"/>
								<path d="M11.9165 4.875C11.468 4.875 11.104 4.511 11.104 4.0625V0.8125C11.104 0.363998 11.468 0 11.9165 0C12.365 0 12.729 0.363998 12.729 0.8125V4.0625C12.729 4.511 12.365 4.875 11.9165 4.875Z" fill="#0C0B0B"/>
							</svg>
					  	</button>
					</td>
					
				  	<td style="padding-right: 10px;">
						<input class="status-avatar" type="image" src="res/img/Logo.png" alt="avatar"/>
					</td>
					
				  	<td style="padding-right: 10px;">
				  		<a href="/ispw_project/LoginServlet" onclick="session.invalidate();">
						<button class="status-button" type="button" name="logout">
							<svg width="27" height="27" viewBox="0 0 27 27" fill="none" xmlns="http://www.w3.org/2000/svg">
								<path d="M18.4067 19.9873V22.0269C18.4067 24.2761 16.5769 26.1059 14.3277 26.1059H4.07905C1.8298 26.1059 0 24.2761 0 22.0269V4.07905C0 1.8298 1.8298 0 4.07905 0H14.3277C16.5769 0 18.4067 1.8298 18.4067 4.07905V6.11857C18.4067 6.68183 17.9502 7.13833 17.3869 7.13833C16.8237 7.13833 16.3672 6.68183 16.3672 6.11857V4.07905C16.3672 2.95452 15.4522 2.03952 14.3277 2.03952H4.07905C2.95452 2.03952 2.03952 2.95452 2.03952 4.07905V22.0269C2.03952 23.1514 2.95452 24.0664 4.07905 24.0664H14.3277C15.4522 24.0664 16.3672 23.1514 16.3672 22.0269V19.9873C16.3672 19.4241 16.8237 18.9676 17.3869 18.9676C17.9502 18.9676 18.4067 19.4241 18.4067 19.9873ZM25.3592 11.3012L23.0757 9.01772C22.6774 8.61938 22.0316 8.61938 21.6335 9.01772C21.2351 9.41587 21.2351 10.0616 21.6335 10.4597L23.2577 12.0842H11.0134C10.4502 12.0842 9.99367 12.5407 9.99367 13.1039C9.99367 13.6672 10.4502 14.1237 11.0134 14.1237H23.2577L21.6335 15.7482C21.2351 16.1463 21.2351 16.792 21.6335 17.1902C21.8327 17.3893 22.0936 17.4889 22.3545 17.4889C22.6156 17.4889 22.8765 17.3893 23.0757 17.1902L25.3592 14.9066C26.3533 13.9126 26.3533 12.2953 25.3592 11.3012Z" fill="black"/>
							</svg>
						</button>
						</a>
					</td>
					
				</tr>
			  </tbody>
			</table>
		</div>
		
		<!-- Page info -->
		<div class="content">
			
			<!-- First Row -->
			<div class="row">
				
				<!-- Hello -->
				<div class="hello col" id="hello">
					<table>
						<tr>
							<td class="hello-text">
								Hello nome!
							</td>
						</tr>
						
						<tr>
							<td class="hello-sub">
								It's good to see you again.
							</td>
						</tr>
					</table>
				</div>

				<!-- Stats -->
				<div class="stat col">
					<table style="border-collapse: collapse;" align="center">
						<tr>
							<td class="stat-num">
								25
							</td>

							<td class="stat-text">
								New<br>Questions
							</td>
						</tr>
					</table>
				</div>
			
			</div>
			
			
			<!-- Second Row -->
			<div style="padding-top: 2vw;">
			
				<!-- First Col -->
				<div class="col" style="float: left; width: 47%; margin-right: 5vw;">
				
					<!-- Next Lesson -->
					<div style="width: 100%">
						
						<table class="lesson" style="width: 100%;">
						<%if (lesson == null) { %>
							<tr>
								<td align="center" class="next-lessons-label" style="width: 100%;">
									There are no future lesson today.
								</td>
							</tr>
							<!--<a align="center" class="next-lessons-label lesson" style="width: 100%;">No lesson found</a>-->
						<%} else { %>
							<tr>
								<td style="padding: 1vw; white-space: nowrap; width: 50px;">
									<img class="img" src="res/img/Lesson.png" alt="lesson">
								</td>

								<td style="vertical-align: middle;" align="left">
									<table style="display: inline; vertical-align: middle;" spacing="0">
										<tr>
											<td class="lesson-text">
												<%=lesson.getCourse().getAbbreviation()%>
											</td>
										</tr>

										<tr>
											<td style="padding-top: 0;" class="lesson-classroom">
												<%=lesson.getClassroom().getName()%>
											</td>
										</tr>
									</table>

									<br>

								</td>

								<td align="right">
									<img class="time-img" src="res/img/Time.png" alt="time">
								</td>

								<td style="vertical-align: middle; padding-left: 0.5vw; white-space: nowrap; width: 1%;" class="lesson-time">
									<label style="display: inline; vertical-align: middle;">
										<%=SQLConverter.time(lesson.getTime())%>
									</label>
								</td>
								<td align="right" style="padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%;">
								<form action="/ispw_project/LessonPageServlet" method="get" >
									<button name = "viewLesson" class="button-view" type="submit" onclick="f(<%=lesson%>)">View</button>
									<input type = "hidden" name = "lessonCourse" value = "<%=lesson.getCourse().getAbbreviation()%>">
									<input type = "hidden" name = "lessonDate" value = "<%=lesson.getDate()%>">
									<input type = "hidden" name = "lessonTime" value = "<%=lesson.getTime()%>">
								</form>
								</td>
							</tr>
						<%} %>
						</table>	
					</div>
					
					<!-- Next Lessons-->
					<div style="padding-top: 2vw; width: 100%;">
						<table style="border-collapse: separate; width: 100%; border-spacing: 0 15px;">
							
							<thead>
								<tr>
									<th colspan="3" class="next-lessons-label" align="left">
										Today Lessons
									</th>
								</tr>
							</thead>
							
							<tbody style="overflow: auto; width: 100%;">
								<c:if test="${empty listOfLesson}">
									<tr>
										<td colspan="5" class="next-lessons-label" style="text-align: center;">
											There are no future lesson today.
										</td>
									</tr>
								</c:if>
								<c:forEach items="${listOfLesson}" var="lesson">
								<tr class="lesson">
									<td style="padding: 1vw; white-space: nowrap; width: 50px; border-radius: 14px 0 0 14px;">
										<img class="img" src="res/img/Lesson.png" alt="lesson">
									</td>

									<td style="vertical-align: middle;" align="left">
										<table style="display: inline; vertical-align: middle;" spacing="0">
											<tr>
												<td class="lesson-text">
												  ${lesson.getCourse().getAbbreviation()}
												</td>
											</tr>

											<tr>
												<td style="padding-top: 0;" class="lesson-classroom">
													${lesson.getClassroom().getName()}
												</td>
											</tr>
										</table>

										<br>

									</td>

									<td align="right">
										<img class="time-img" src="res/img/Time.png" alt="time">
									</td>

									<td style="vertical-align: middle; padding-left: 0.5vw; white-space: nowrap; width: 1%;" class="lesson-time">
										<label style="display: inline; vertical-align: middle;">
											${SQLConverter.time(lesson.getTime())}
										</label>
									</td>

									<td align="right" style="padding: 0 1vw 0 1vw; white-space: nowrap; width: 1%; border-radius: 0 14px 14px 0;">
										<a href="/ispw_project/LessonPageServlet" class="button-view" type="button" onclick="<c:set var="lesson" value="${lesson}" scope="session"/>">View</a>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				
				</div>
				
				<!-- Second Col -->
				<div class="col" style="float: right; width: 45%;">
					<!-- Weather -->
					<table style="height: 80%;">
						<thead>
							<tr>
								<th colspan="3" class="next-lessons-label" align="left">
									Weather
								</th>
							</tr>
						</thead>
					
						<tbody>
						<tr>
							<c:forEach items="${listOfLesson}" var="mhanz">
							<td style="padding-right: 1vw; width: 20%;">
								<table class="weather" align="center">
									<tr>
										<td class="weather-text">
											15 °C
										</td>
									</tr>

									<tr>
										<td align="center">
											<img class="img" src="Cloud.png" alt="weath">
										</td>
									</tr>

									<tr>
										<td class="weather-text">
											10:00
										</td>
									</tr>
								</table>
							</td>
							</c:forEach>
						</tr>
						</tbody>
					
					</table>
				</div>
			
			</div>
			
		</div>
	</body>
</html>