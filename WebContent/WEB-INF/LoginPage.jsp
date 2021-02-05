<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<head>
	<link rel="icon" href="res/img/Logo.png">
	<link rel="stylesheet" href="res/style/LoginPage.css">
	<meta charset="utf-8">
	<title>App - LoginPage</title>
</head>

<body>
	<form action="LoginServlet" method="post">
	<div class="content">
		<table class="table">
		  <caption></caption>
		  <tbody>
			  
			<tr>
			  <th id="logo" colspan="2" style="text-align: center; padding-bottom: 50px;">
				<img class="logo" src="res/img/Logo.png" alt="Logo">
			  </th>  
			</tr>
			  
			<tr>
			  	<td style="text-align: right; padding-right: 20px; padding-bottom: 18px;">
				  <input class="input" type="text" id="username" name="username" placeholder="Username">
				</td>
				
			  	<td style="text-align: left; padding-left: 20px; padding-bottom: 18px;">
					<input class="input" type="password" id="password" name="password" placeholder="Password">
				</td>
			</tr>
			  
			<tr>
				<td colspan="2" style="text-align: center; padding-bottom: 18px; padding-left: 7px;">
					<button class="login" type="submit" name="login">
						LOG IN
					</button>
					
					<button class="button" type="button" name="facebook" onclick="alert('Functionality not yet implemented.')">
						<img src="res/img/Facebook.png" alt="Facebook">
					</button>
					
					<button class="button" type="button" name="google" onclick="alert('Functionality not yet implemented.')">
						<img src="res/img/Google.png" alt="Google">
					</button>
				</td>			
			</tr>
			  
			<tr>
				<td colspan="2" style="text-align: center; padding-bottom: 18px;">
					<a class="signup" href="/ispw_project/SignupServlet"> 
						<button class="signup" type="button">
							Sign Up
						</button>
					</a>
				</td>  
			</tr>
			  
			<tr>
				<td colspan="3" style="text-align: center;">
					<button class="label" type="button">Forgot your password?</button>
				</td>  
			</tr>
		  </tbody>
		</table>
	</div>
	</form>
</body>

	
<%String message = (String)request.getAttribute("alertMsg");
  if(message != null){ %>
  	<script type="text/javascript">
    	var msg = "<%=message%>";
    	alert(msg);
  	</script>
<%} %>

</html>
