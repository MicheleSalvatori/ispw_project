<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    
<!doctype html>
<html lang="en">

<jsp:useBean id="userBean" scope="request" class="logic.bean.UserBean"/>

	<head>
		<meta charset="utf-8">
		<title>App - Signup</title>
		<link rel="stylesheet" href="res/style/SignupPage.css">
		<link rel="icon" href="res/img/Logo.png">
	</head>

	<body>
		<form action="SignupServlet" method="post">
		<table class="content">
		<caption></caption>
		  <tbody>
			  
			<tr>
			  	<th id="logo" style="text-align: center; padding-bottom: 50px;" colspan="2">
					<img class="logo" src="res/img/Logo.png" alt="Logo">
				</th>
			</tr>
			  
			<tr>
			  	<td style="text-align: right; padding-right: 20px; padding-bottom: 18px;">
					<input class="input" type="text" name="username" placeholder="Username" required>
				</td>
			  
				<td style="text-align: left; padding-left: 20px; padding-bottom: 18px;">
					<input class="input" type="password" name="password" placeholder="Password" required>
				</td>
			</tr>
			  
			<tr>
			  	<td style="text-align: right; padding-right: 20px; padding-bottom: 18px;">
					<input class="input" type="text" name="email" placeholder="Email" required>
				</td>
			  	
				<td style="text-align: left; padding-left: 20px; padding-bottom: 18px;">
					<input class="input" type="password" name="confirmPassword" placeholder="Confirm Password" required>
				</td>
			</tr>
			  
			<tr>
			  	<td style="text-align: right; padding-right: 20px; padding-bottom: 18px;">
					<input class="input" type="text" name="name" placeholder="Name" required>
				</td>
			  	
				<td style="text-align: left; padding-left: 20px; padding-bottom: 18px;">
					<input class="input" type="text" name="surname" placeholder="Surname" required>
				</td>
			</tr>
			  
			<tr>
			  	<td colspan="2" style="text-align: center; padding-bottom: 18px;">
					<button class="signup" type="submit">Sign Up</button>
				</td>
			</tr>
			  
			<tr>
			  	<td style="text-align: right; padding-right: 3px; padding-bottom: 18px;">
					<button class="signupwith" type="button">
						<table>	
							<tr>
								<td style="text-align: left; padding-right: 10px;">
									Sign up with
								</td>
								
								<td style="text-align: right;">
									<img src="res/img/Facebook.png" alt="Facebook">
								</td>
							</tr>
						</table>	
					</button>
				</td>
				
			  	<td style="text-align: left; padding-left: 3px; padding-bottom: 18px;">
					<button class="signupwith" type="button">
						<table>				
							<tr>
								<td style="text-align: left; padding-right: 10px;">
									Sign up with
								</td>
								
								<td style="text-align: right;">
									<img src="res/img/Google.png" alt="Google">
								</td>
							</tr>
						</table>
					</button>
				</td>
			</tr>
			  
			<tr>
			  	<td colspan="2" style="text-align: center;">
			  		<a href="/ispw_project/LoginServlet">
						<button class="label" type="button">
							Already have an account? Log In
						</button>
					</a>
				</td>
			</tr>
			  
		  </tbody>
		</table>
		</form>
		
		<%String message = (String)request.getAttribute("alertMsg");
    	if(message != null){ %>
	        <script type="text/javascript">
	            var msg = "<%=message%>";
	            alert(msg);
	        </script>
   		<%} %>
   		
	</body>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script>
	function myFunction() {
  		alert("Functionality not implemented.");
	}
</script>

</html>
