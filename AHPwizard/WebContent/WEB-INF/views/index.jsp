<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Current Projects</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Le styles -->

<link href="/AHPwizard/resources/bootstrap-cust/css/bootstrap.css"
	rel="stylesheet">
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<link href="/AHPwizard/resources/bootstrap-cust/css/bootstrap-responsive.css"
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
						<li class="active"><a href="index">Projects</a></li>
						<li><a href="new">New Project</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>

		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="span8">
				<div class="page-header">
					<h1>
						AHP <small>Current projects</small>
					</h1>
				</div>
				<p>
					Use this document as a way to quick start any new project.<br>
					All you get is this message and a barebones HTML document.
				</p>
				<p>
				<sql:query var="rs" dataSource="jdbc/ahp">
					select name, description from project
				</sql:query>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Objective</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${rs.rows}">
					    <tr> 
					    	<td>${row.name}</td>
					    	<td>${row.description}</td>
					    </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="span4"></div>
		</div>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="/AHPwizard/resources/bootstrap-cust/js/bootstrap.min.js"></script>
</body>
</html>