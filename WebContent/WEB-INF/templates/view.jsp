<%@ page language="java" contentType="text/html" %>
<%@ page import="java.util.*" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<link rel="stylesheet" type="text/css" href="css/style.css">
  	<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
	<c:if test="${not empty movie}">
		<title><c:out value="${movie.title}" /></title>
	</c:if>
	<c:if test="${not empty actor}">
		<title><c:out value="${actor.first_name}" />  <c:out value="${actor.last_name}" /></title>
	</c:if>
	<c:if test="${empty actor && empty movie}">
		<title>Not Found.</title>
	</c:if>
  </head>
  
  <body>	
	<%@ include file="/WEB-INF/templates/header.html"%>
	<div class="fabflix__body">
		<div class="fabflix__content">
		
		<c:if test="${not empty movie}">
			<div class="row movie__listing">
				<div class='col col-3 movie__poster'>
					<img src ="<c:out value="${movie.banner_url}" />" width = "175px">
	    		</div>
	    		<div class='col col-9 movie__info'>
    				<p class="movie__id"><c:out value="${movie.id}" /></p>
					<p class="movie__link"><c:out value="${movie.title}" /></p>
					<p>(<c:out value="${movie.year}" />)</p>
					<p>Director: <c:out value="${movie.director}" /></p>
					<a class="movie__trailer" href="<c:out value="${movie.trailer_url}" />"><p>Trailer</p></a>
    				
    				<div class="movie__genres">
	    				<span> Genres: </span>
	    				<c:forEach items="${movie.listOfGenres}" var="genre">
							<a href="${pageContext.request.contextPath}/search?genre=<c:out value="${genre}" />"><c:out value="${genre}" /></a>
						</c:forEach>
					</div>
					<div class="fabflix__add-to-cart">
						<p class="fabflix__price">$<c:out value="${movie.price}" /></p>
						<form id="add-to-cart" action="${pageContext.request.contextPath}/cart" method="post">
							<input type="hidden" name="id" value="${movie.id}">
							<button type="submit" class="btn btn-primary">Add to Cart</button>
						</form>
					</div>
			    </div>	
			  </div>
			  <c:forEach items="${movie.listOfActors}" var="actor">
			  	<div class="row actor__listing">
			  		<div class='col col-4 actor__poster'>
			  			<img src ="<c:out value="${actor.photo_url}" />" width = "166px">
			  		</div>
			  		<div class='col col-8 actor__info'>
			  			<a class="actor__link" href="${pageContext.request.contextPath}/view?star=<c:out value="${actor.id}" />"><c:out value="${actor.first_name}" />  <c:out value="${actor.last_name}" /></a>
			  			<p>Date of Birth: <c:out value="${actor.dob}" /></p>
			  		</div>
				</div>
				
			  </c:forEach>
			  
		</c:if>  
		<c:if test="${not empty actor}">
			<div class="row actor__listing">
			  		<div class='col col-4 actor__poster'>
			  			<img src ="<c:out value="${actor.photo_url}" />" width = "166px">
			  			
			  		</div>
			  		<div class='col col-8 actor__info'>
			  			<p class="actor__link"><c:out value="${actor.first_name}" />  <c:out value="${actor.last_name}" /></p>
			  			<p>Date of Birth: <c:out value="${actor.dob}" /></p>
			  		</div>
			</div>
			<c:forEach items="${actor.listOfMovies}" var="current">
				<div class="row movie__listing">
					<div class='col col-3 movie__poster'>
						<img src ="<c:out value="${current.banner_url}" />" width = "175px">
		    		</div>
		    		<div class='col col-9 movie__info'>
		    				<p class="movie__id"><c:out value="${current.id}" /></p>
							<a class="movie__link" href="${pageContext.request.contextPath}/view?movie=<c:out value="${current.id}" />"><c:out value="${current.title}" /></a>
							<p>(<c:out value="${current.year}" />)</p>
							<p>Director: <c:out value="${current.director}" /></p>
							<a class="movie__trailer" href="<c:out value="${current.trailer_url}" />"><p>Trailer</p></a>
		    				
		    				<div class="movie__genres">
			    				<span> Genres: </span>
			    				<c:forEach items="${current.listOfGenres}" var="genre">
									<a href="${pageContext.request.contextPath}/search?genre=<c:out value="${genre}" />"><c:out value="${genre}" /></a>
								</c:forEach>
							</div>
							<div class="fabflix__add-to-cart">
								<p class="fabflix__price">$<c:out value="${current.price}" /></p>
								<form id="add-to-cart" action="${pageContext.request.contextPath}/cart" method="post">
									<input type="hidden" name="id" value="${current.id}">
									<button type="submit" class="btn btn-primary">Add to Cart</button>
								</form>
							</div>
			    		</div>	
		   		</div>
				
				</c:forEach>
			
		
		</c:if>
		<c:if test="${empty actor && empty movie}">
			<h1 class="fabflix__results-error">Not found.</h1>
		</c:if>
		
		</div>
    </div>
      

	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js"></script>
  </body>
</html>



