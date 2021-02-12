<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">

<head>
	<link rel="icon" href="res/img/Logo.png">
	<link rel="stylesheet" href="res/style/LoginPage.css">
	<link rel="stylesheet" href="res/style/ModalBox.css">
	<meta charset="utf-8">
	<title>.myUniversity - LoginPage</title>
</head>

<body>
	
	<div class="content">
		<form action="/ispw_project/LoginServlet" method="post">
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
					<button class="login" type="submit" name="login" id="login">
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
					<button id="modal" class="label" type="button">Forgot your password?</button>
				</td>  
			</tr>
		  </tbody>
		</table>
		</form>
	</div>
	
	<!-- The Modal -->
	<div id="myModal" class="modal">
		
	   	<!-- Modal content -->
	   	<form action="/ispw_project/LoginServlet" method="post">
		<div class="modal-content">
			
		    <div class="modal-header">
			    <span class="close">&times;</span>
			    <a class="modal-text">Insert your email</a>
			</div>
				  
			<div class="modal-body" style="text-align: center; padding: 25px 0 25px 0;">
					
			<input name="email" type="text" class="text-input" required>
	
			</div>
			  
			<div class="modal-footer" style="text-align: right; padding: 10px 10px 10px 0;">
		  		<button class="modal-button" type="submit" name="forgotPassword">Submit</button>
			</div>
		</div> 
		</form>
		
	</div> 
	
</body>

	
<%String message = (String)request.getAttribute("alertMsg");
  if(message != null){ %>
  	<script type="text/javascript">
    	var msg = "<%=message%>";
    	alert(msg);
  	</script>
<%} %>

<script>
	//Get the modal
	var modal = document.getElementById("myModal");
	
	// Get the button that opens the modal
	var btn = document.getElementById("modal");
	
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
