<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Create a New Project</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Le styles -->

<link href="/AHPwizard/resources/bootstrap-cust/css/bootstrap.css"
	rel="stylesheet">
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
.slider{
width:200px;
}
</style>
<link
	href="/AHPwizard/resources/bootstrap-cust/css/bootstrap-responsive.css"
	rel="stylesheet">

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="../assets/ico/favicon.ico">

<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../assets/ico/apple-touch-icon-57-precomposed.png">

<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />
</head>

<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">

			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a class="brand" href="#">AHP</a>
				<div class="nav-collapse collapse">

					<ul class="nav">
						<li><a href="index">Projects</a></li>
						<li class="active"><a href="new">New Project</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>

		</div>
	</div>

	<div class="container">
	
	
	
	<c:choose >
	<c:when test="${ empty model}">
		<div class="page-header">
			<h1>
				AHP <small>Create a new project</small>
			</h1>
		</div>
		<form class="form-horizontal" method="post" action="new">
			<div class="control-group">
				<label class="control-label" for="goalName">Name</label>
				<div class="controls">
					<input type="text" id="goalName" name="goalName"
						placeholder="goalName" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="goalDescription">Description</label>
				<div class="controls">
					<input type="text" id="goalDescription" name="goalDescription"
						placeholder="goalDescription" />
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="btn-group">
						<input class="btn" type="submit" value="Create" /> <input
							class="btn" type="reset" value="Reset" />
					</div>
				</div>
			</div>
		</form>
		</c:when>
		<c:when test="${ model.status eq 'ready'}">
		
		
		<div>Model ${model.goalName } is ${model.status}</div>
		<form class="form-horizontal" method="post" action="evaluate">
		<table>
		<c:set var="count" value="1" />
		<c:forEach items="${model.criteriaLabels }" var="critLabel" >
		<tr>
			<th colspan="3"> <c:out value="${critLabel}"></c:out></th>
		</tr>
				<c:set var="outerCount" value="${0}" />
				<c:forEach items="${model.alternativeLabels }" var="altLabel">
					<c:set var="outerCount" value="${outerCount + 1 }" />
					<c:set var="innerCount" value="${0}" />
					<c:forEach items="${model.alternativeLabels }" var="altLabel1">
						<c:set var="innerCount" value="${innerCount + 1 }" />
						 <c:if test="${ innerCount gt outerCount }">
							<tr>
								<td><c:out value="${altLabel}"></c:out></td>
								<td><div class="slider"></div></td>
								<td><c:out value="${altLabel1}"></c:out></td>
							</tr>
						</c:if>
					</c:forEach>
				</c:forEach>
		
		</c:forEach>
		
		</table>
		
		<input type="submit" >
		</form>
		
		
		</c:when>
		<c:when test="${ model.status eq 'complete'}">
				
				<div>Model ${model.goalName } is ${model.status}</div>
		
		
		</c:when>
		</c:choose>
	</div>
	
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="/AHPwizard/resources/bootstrap-cust/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
    <script>
    $(function() {
        $( ".slider" ).slider({ min:-9, max:9, value:0}).slider({
        	   slide: function(event, ui) {console.log(ui); }
        	});
    });
    
    
    </script>
	
</body>
</html>