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

	<title>Report</title>
  </head>
  
  <body>	
	<%@ include file="/WEB-INF/templates/header.html"%>
	<div class="fabflix__body">
		<div class="fabflix__content">
		<p style="text-align: center"><font size ="6">Like Predicate</font></p>
		<p style="width: 700px; margin: 0 auto;"> We used the like predicate when searching for movies. For example, we used it when a user types in
			a query and it will check if any movie information starts with the user given query.
			A variation of that is used when searching for movie titles and directors. If the given query is 3
			or less letters long, it will check if the information starts with those letters. If more than 3 letters
			is given, it will search if that is a substring of the information needed. For stars, we search for stars
			that begin with the user's query. For example, if 'li' is searched, movies that have stars with first or
			last names that start with 'li' will show up in the results page.</p>	
			
		<p style="text-align: center"><font size ="6">Optimization</font></p>
		<p style="width: 700px; margin: 0 auto;">Batch Insert was used every time 1000 objects were parsed. 
		Batch insert was also optimized appending "useServerPrepStmts=false&rewriteBatchedStatements=true". "rewriteBatchedStatements" will 
		pack as many queries as possible into a single packet in order to reduce network overhead. In terms of data storage, we used HashSet
		in order to store values inefficiently.</p>
		</div>
    </div>
      

	

	

    <!-- jQuery first, then Tether, then Bootstrap JS. -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/auto-complete.js"></script>
  </body>
</html>
