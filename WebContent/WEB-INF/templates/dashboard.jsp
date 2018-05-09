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
	
	<title>Home</title>
  </head>
  
  <body>	
	<div class="container l-home__header">
	<div class="row">
		<div class="col-4 l-home__col">
			<a href="${pageContext.request.contextPath}/dashboard"><img src="${pageContext.request.contextPath}/resources/fabflix.png" height ="90%" class="l-home__title"></a>
		</div>
		
		<div class="col-8 l-home__col">
			<div class="row l-home__header--logout">
				<p style="padding: 5px 20px 0px 0px;"> Welcome: ${efullname} </p>
				<a href="${pageContext.request.contextPath}/dashboard/login"><button class="btn btn-link l-home__header--logout__link" type="button">Log out</button></a>
			</div>
		</div>
	</div>
</div>
	
    <div class="fabflix__body">
    	<div class="fabflix__content">
    	<c:if test= "${insert == 'star' && success == 'yes'}">
			<h1 class="fabflix__results-error">Star Inserted.</h1>
		</c:if>
		<c:if test= "${insert == 'star' && success == 'no'}">
			<h1 class="fabflix__results-error">Failed to insert Star.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && success == 'YES'}">
			<h1 class="fabflix__results-error">Movie Inserted.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && success =='NO'}">
			<h1 class="fabflix__results-error">Movie already Exists.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && success =='FAIL'}">
			<h1 class="fabflix__results-error">Failed to insert Movie.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && star_success =='FAIL'}">
			<h1 class="fabflix__results-error">Failed to insert Star into Movie.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && star_success =='NA'}">
			<h1 class="fabflix__results-error">Star does not exist.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && star_success =='YES'}">
			<h1 class="fabflix__results-error">Star Inserted.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && star_success =='NO'}">
			<h1 class="fabflix__results-error">Star Already Exists.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && genre_success =='FAIL'}">
			<h1 class="fabflix__results-error">Failed to insert Genre into movie.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && genre_success =='NA'}">
			<h1 class="fabflix__results-error">Genre does not exist.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && genre_success =='YES'}">
			<h1 class="fabflix__results-error">Genre Inserted.</h1>
		</c:if>
		<c:if test= "${insert == 'movie' && genre_success =='NO'}">
			<h1 class="fabflix__results-error">Genre Already Exists.</h1>
		</c:if>
    	<div class="l-dashboard__container">
    	<div class="l-dashboard__box"style="max-height: 490px;">
    		<div style="text-align:center;">
				<p> Insert a Star </p>
			</div>
    		<form action="${pageContext.request.contextPath}/dashboard" method="post">
				<div class="form-group">
				    <label for="text">First Name</label>
				    <input type="text" class="form-control" id="name" placeholder="Enter first name" name="firstname" required>
				</div>
				<div class="form-group">
				    <label for="text">Last Name (Optional)</label>
				    <input type="text" class="form-control" id="name" placeholder="Enter last name" name="lastname">
				</div>
				<div class="form-group">
				    <label for="text">Date Of Birth (Optional)</label>
				    <input type="date" class="form-control" id="dob" placeholder="Enter Date of Birth" name="dob">
				</div>
				<div class="form-group">
				    <label for="text">Photo URL (Optional)</label>
				    <input type="text" class="form-control" id="photo_url" placeholder="Enter Photo URL" name="photo">
				</div>
				<div style="float: right;">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>
    	</div>
    	<div class="l-dashboard__box" style="max-height: 750px;">
    		<div style="text-align:center;">
				<p> Insert a Movie </p>
			</div>
    		<form action="${pageContext.request.contextPath}/dashboard" method="post">
				<div class="form-group">
				    <label for="title">Title</label>
				    <input type="text" class="form-control" id="title" placeholder="Enter Title" name="title" required>
				</div>
				<div class="form-group">
				    <label for="year">Year</label>
				    <input type="number" class="form-control" id="year" placeholder="Enter Year" name="year" required>
				</div>
				<div class="form-group">
				    <label for="director">Director</label>
				    <input type="text" class="form-control" id="director" placeholder="Enter Director" name="director" required>
				</div>
				<div class="form-group">
				    <label for="banner_url">Banner URL (Optional)</label>
				    <input type="text" class="form-control" id="banner_url" placeholder="Enter banner URL" name="banner_url">
				</div>
				<div class="form-group">
				    <label for="trailer_url">Trailer URL (Optional)</label>
				    <input type="text" class="form-control" id="trailer_url" placeholder="Enter trailer URL" name="trailer_url">
				</div>
				<div class="form-group">
				    <label for="star">Star ID</label>
				    <input type="number" class="form-control" id="star" placeholder="Enter Star" name="star" required>
				</div>
				<div class="form-group">
				    <label for="genre">Genre ID</label>
				    <input type="number" class="form-control" id="genre" placeholder="Enter Genre" name="genre" required>
				</div>
				<div style="float: right;">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>
    	</div>
    	</div>
    <div style="display:flex; flex-direction: row; flex-wrap: wrap; margin-top: 40px;">
   		<c:forEach var="table" items="${data}">
   			<div style="padding: 10px;">
    			<h1 style="font-size: 1.5rem;"> ${table.key}</h1>
    			<c:forEach var="attr" items="${table.value}">
    				<span style="font-size: 0.8rem;">${attr.key} -- ${attr.value}</span><br>
    			</c:forEach>
    		</div>
   		</c:forEach>
  		</div>
	</div>
	</div>
	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
  </body>
</html>



