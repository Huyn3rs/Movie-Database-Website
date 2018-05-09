<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.*" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
  	<link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.min.css">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
	 <script type="text/javascript">
		var Msg ='<%=session.getAttribute("confirmation")%>';
		    if (Msg == "no") {
			 function checkout_alert(){
			 alert("Incorrect credit card and/or expiration date.");
			 } 
			}
    </script> 
	
	<title>Checkout</title>
  </head>
  
  <body id = "checkout">	
	<%@ include file="/WEB-INF/templates/header.html"%>
	<div class="fabflix__body">
		<div class="fabflix__content">
		<div class="fabflix__checkout-box">
			<p style="text-align: center"><font size ="6">Checkout</font></p>
			<p style="text-align: left"><b><font size ="4">Ship To</font></b></p>
			<p style="text-align: center"><b>${firstname} ${lastname}</b></p>	
			<p style="text-align: center">${address}</p>
			<p style="text-align: left"><b><font size ="4">Billing Information</font></b></p>	
			<form action="${pageContext.request.contextPath}/checkout" method="post">
			<div class="form-group">
			    <label for="firstName">First Name</label>
			    <input type="text" class="form-control" id="firstName" placeholder="First name" name="firstName">
			</div>
			<div class="form-group">
			    <label for="lastName">Last Name</label>
			    <input type="text" class="form-control" id="lastName" placeholder="Last name" name="lastName">
			</div>
			<div class="form-group">
			    <label for="creditCard">CC Number</label>
			    <input type="text" class="form-control" id="creditCard" placeholder="CC no." name="creditCard">
			</div>
			<div class="form-group">
			    <label for="expDate">Expiration Date</label>
			    <input type="text" class="form-control" id="expDate" placeholder="YYYY-MM-DD" name="expDate">
			</div>
			<div style="float: right;">
				<button type="submit" class="btn btn-primary">Pay Now</button>
			</div>
			
			</form>
		</div>
    </div>
    </div>

	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script type="text/javascript"> window.onload = checkout_alert; </script>
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js"></script>
  </body>
</html>