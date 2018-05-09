<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.*" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en" id="login">
  <head>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
  
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <script type="text/javascript">
		var Msg ='<%=session.getAttribute("alert")%>';
		    if (Msg == "yes") {
			 function login_alert(){
			 alert("Incorrect user and/or password.");
			 } 
			}
    </script> 
    
    <title>Login</title>
    <script src='https://www.google.com/recaptcha/api.js'></script>
  </head>
  
  <body id="login">
	<div class="l-login__box">
	<img src="${pageContext.request.contextPath}/resources/fabflix.png" class="l-login__logo" width="90%">	
	<form action="${pageContext.request.contextPath}/login" method="post">
		<div class="form-group">
		    <label for="email">Email address</label>
		    <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
		</div>
		<div class="form-group">
		    <label for="password">Password</label>
		    <input type="password" class="form-control" id="password" placeholder="Enter password" name="password" required>
		</div>
		<div class="g-recaptcha" data-sitekey="6LfnNCEUAAAAAMhMSt2-2AJo8Y46YUzp-jd7csI3" style="margin-bottom: 10px;"></div>
		<div style="float: right;">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</form>
	<a href="${pageContext.request.contextPath}/dashboard/login">Employee Login</a>
	</div>

	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script type="text/javascript"> window.onload = login_alert; </script>
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
  </body>
</html>
