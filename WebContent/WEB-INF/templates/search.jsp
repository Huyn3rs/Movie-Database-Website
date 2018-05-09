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
	
	<title>Search Movies</title>
  </head>
  
  <body>	
	<%@ include file="/WEB-INF/templates/header.html"%>
	<div class="fabflix__body">
		<div class="fabflix__content">
			<c:if test="${search == 'yes'}">
				<div class="fabflix__search-box">
					<form id="advanced_search" action="${pageContext.request.contextPath}/search" method="get">
						<div class="form-group">
			   				<label for="title">Title</label>
			    			<input type="text" class="form-control" id="title" placeholder="Enter title" name="title">
						</div>
						<div class="form-group">
			   				<label for="year">Year</label>
			    			<input type="text" class="form-control" id="year" placeholder="Enter year" name="year">
						</div>
						<div class="form-group">
			   				<label for="director">Director</label>
			    			<input type="text" class="form-control" id="director" placeholder="Enter director" name="director">
						</div>
						<div class="form-group">
			   				<label for="star">Star</label>
			    			<input type="text" class="form-control" id="star" placeholder="Enter star name" name="star">
						</div>
						<div style="float: right;">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>		
					</form>
				</div>
			</c:if>
			<!-- 
			<p> genre = ${genre} </p>
			<p> query = ${q} </p>
			<p> type = ${type} </p>
			<p> title = ${title} </p>
			<p> year = ${year} </p>
			<p> director = ${director} </p>
			<p> star = ${star} </p>
			<p> sort = ${sort} </p>
			<p> display = ${display} </p>
			<p> start = ${start} </p> -->
			
			<div class="fabflix__wrapper">
				<div class="fabflix__sort-box">
					<form role="search">
						<div class="input-group">
							<select id="select_sort" class="form-control" name="sort" required>
								<option selected="selected" disabled value=""> Sort By </option>	
								<option value="title-asc"> Title (Ascending) </option>
								<option value="title-desc"> Title (Descending) </option>
								<option value="year-asc"> Year (Ascending) </option>
								<option value="year-desc"> Year (Descending) </option>
							</select>
						</div>
					</form>
				</div>
				<div class="fabflix__display-box">
					<form role="search">
						<div class="input-group">
							<select id="select_display" class="form-control" name="display" required>
								<option selected="selected" disabled value=""> Display Results</option>	
								<option value="10"> 10 </option>
								<option value="25"> 25 </option>
								<option value="50"> 50 </option>
								<option value="100"> 100 </option>
							</select>
						</div>
					</form>
				</div>
				<p class="fabflix__results-counter">${result_size} Result${result_size != 1 ? "s" : ""}</p>
			</div>
			<c:if test="${result_size != 0 }">
			<div class="fabflix__wrapper__pagination">
				<div style="margin: 0 auto">
				<nav aria-label="movie_pagination">
				  <ul class="pagination">
				    <li class="page-item ${(start - display) < 0 ? 'disabled' : ' ' }">
				      <a class="page-link pagination_prev" href="#">Previous</a>
				    </li>
				    <li class="page-item ${(start + display) > 1000 ? 'disabled' : ' ' }">
				      <a class="page-link pagination_next" href="#">Next</a>
				    </li>
				  </ul>
				</nav>
				</div>
			</div>
			</c:if>
			<c:if test="${result_size == 0 && size != 0}">
				<h1 class="fabflix__results-error">No results.</h1>
			</c:if>
			
		      <c:forEach items="${result}" var="current">
		      	<div class="row movie__listing--popup">
				<div class='col col-4 movie__poster'>
					<a class="movie__link" href="${pageContext.request.contextPath}/view?movie=<c:out value="${current.id}" />"><img src ="<c:out value="${current.banner_url}" />" width = "175px"></a>
	    		</div>
	    		<div class='col col-8 movie__info' data-movie_id="<c:out value="${current.id}" />">
						<p class="movie__link"><c:out value="${current.title}" /></p>
						<p><c:out value="${current.id}" /></p>
						<div class="fabflix__pop-up" id="pop-up<c:out value="${current.id}" />">
	    				</div>
	    				<!--  <a class="movie__trailer" href="<c:out value="${current.trailer_url}" />">Trailer &nbsp;&nbsp;</a>
		    				<p class="movie__year">(<c:out value="${current.year}" />)</p>
							<p class="movie__director">Director: <c:out value="${current.director}" /></p>
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
							<div class="fabflix__add-to-cart">
								<p class="fabflix__price">$<c:out value="${current.price}" /></p>
								<form id="add-to-cart" action="${pageContext.request.contextPath}/cart" method="post">
									<input type="hidden" name="id" value="${current.id}">
									<input type="hidden" name="quantity" value="new">
									<button type="submit" class="btn btn-primary">Add to Cart</button>
								</form>
							</div> -->
	    		</div>
	   		</div>
				
		      </c:forEach>
		      
		      <c:if test="${result_size != 0 }">
			<div class="fabflix__wrapper__pagination">
				<div style="margin: 0 auto">
				<nav aria-label="movie_pagination">
				  <ul class="pagination">
				    <li class="page-item ${(start - display) < 0 ? 'disabled' : ' ' }">
				      <a class="page-link pagination_prev" href="#">Previous</a>
				    </li>
				    <li class="page-item ${(start + display) > result_size ? 'disabled' : ' ' }">
				      <a class="page-link pagination_next" href="#">Next</a>
				    </li>
				  </ul>
				</nav>
				</div>
			</div>
			</c:if>
		</div>
    </div>
      

	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js"></script>
	<script src="${pageContext.request.contextPath}/js/mouse-over.js"></script>
	<script>

		$('#select_sort').change(function(){
			var selected = $('#select_sort').val(); 
			var url = document.location.href;
			var new_url;
			if (url.indexOf("sort") > -1){
				new_url = url.replace(/(sort=).*?(&|$)/,'$1' + selected + '$2');
			}
			else{
				new_url = document.location.href+"&sort="+selected;
			}
			var remove_start = new_url.replace(/&?start=([^&]$|[^&]*)/i, "");
		    document.location.href = remove_start;
			
		});
		
		$('#select_display').change(function(){
			var selected = $('#select_display').val(); 
			var url = document.location.href;
			var new_url;
			if (url.indexOf("display") > -1){
				new_url = url.replace(/(display=).*?(&|$)/,'$1' + selected + '$2');
			}
			else{
				new_url = document.location.href+"&display="+selected;
			}
			var remove_start = new_url.replace(/&?start=([^&]$|[^&]*)/i, "");
		    document.location.href = remove_start;
			
		});
		
		$('.pagination_next').click(function(){
			var selected = ${start} + ${display}; 
			var url = document.location.href;
			var new_url;
			if (url.indexOf("start") > -1){
				new_url = url.replace(/(start=).*?(&|$)/,'$1' + selected + '$2');
			}
			else{
				new_url = document.location.href+"&start="+selected;
			} 
		    //document.location.href = new_url;
		    $('.pagination_next').attr('href', new_url);
			
		});
		

		$('.pagination_prev').click(function(){
			var selected = ${start} - ${display}; 
			var url = document.location.href;
			var new_url;
			if (url.indexOf("start") > -1){
				new_url = url.replace(/(start=).*?(&|$)/,'$1' + selected + '$2');
			}
			else{
				new_url = document.location.href+"&start="+selected;
			}
			//alert(new_url);
		    //document.location.href = new_url;
			$('.pagination_prev').attr('href', new_url);
		});
		
		
	
	</script>
	
  </body>
</html>

