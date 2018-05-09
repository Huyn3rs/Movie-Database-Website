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

	<title>Cart</title>
  </head>
  
  <body>	
	<%@ include file="/WEB-INF/templates/header.html"%>
	<div class="fabflix__body">
		<div class="fabflix__content">
		<c:if test="${cart.size == 0 }">
			<h1 class="fabflix__results-error">Your Cart is Empty.</h1>
		</c:if>
		<c:if test="${cart.size != 0 }">
			<div class="fabflix__wrapper">
				<a href="${pageContext.request.contextPath}/checkout"><button type="button" class="btn btn-primary">Checkout</button></a>
				<p style="margin-right: 10px;">Total: $${cart.totalString}</p>
			</div>
		</c:if>
		<c:forEach items="${cart.cart}" var="current">
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
						<div class="movie__actors">
		    				<span> Actors: </span>
		    				<c:forEach items="${current.listOfActors}" var="star">
								<a href="${pageContext.request.contextPath}/view?star=<c:out value="${star.id}" />"><c:out value="${star.first_name}" />  <c:out value="${star.last_name}" /></a>
							</c:forEach>
						</div>
						<div class="fabflix__update-cart">
							<p class="fabflix__price">$<c:out value="${current.price}" /> x ${current.count} = $${current.totalPriceString}</p>
							<form id="add-to-cart" action="${pageContext.request.contextPath}/cart" method="post" class="fabflix__update-cart--form">
								<input type="number" class="form-control fabflix__count" name="quantity" min="0" step="1" value="${current.count}">
								<input type="hidden" class="fabflix__update" name="id" value="${current.id}">
								<button type="submit" class="btn btn-primary">Update</button>
							</form>
						</div>
	    		</div>	
	   		</div>
		</c:forEach>
		</div>
		
		
    </div>
      

	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js"></script>
  </body>
</html>


