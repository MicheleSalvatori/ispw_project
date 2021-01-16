<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<!doctype html>
<html lang="en">

<jsp:useBean id="userBean" scope="session" class="logic.bean.UserBean"/>


<head>
	<link rel="stylesheet" href="RES/style/LoginPage.css" >
	<meta charset="utf-8">
	<title>LoginPage</title>
	
	<%String message = (String)request.getAttribute("alertMsg");
    if(message != null){%>

        <script type="text/javascript">
            var msg = "<%=message%>";
            alert(msg);
        </script>
   	<%} %>
   	
</head>

<body>
	<form action="LoginServlet" method="post">
	<div class="content">
		<table class="table">
		  <caption></caption>
		  <tbody>
			  
			<tr>
			  <th id="logo" colspan="2" style="text-align: center; padding-bottom: 50px;">
				<img class="logo" src="RES/img/Logo.png" alt="Logo">
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
					
					<button class="button" type="button" name="facebook">
						<img src="RES/img/Facebook.png" alt="Facebook">
					</button>
					
					<button class="button" type="button" name="google">
						<img src="RES/img/Google.png" alt="Google">
					</button>
				</td>			
			</tr>
			  
			<tr>
				<td colspan="2" style="text-align: center; padding-bottom: 18px;">
					<a class="signup" href="SignupPage.jsp"> 
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script>
	function myFunction() {
  		alert("Functionality not implemented.");
	}
</script>

</html>
