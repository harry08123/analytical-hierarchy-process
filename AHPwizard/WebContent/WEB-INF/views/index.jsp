<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="header.jsp" />
</head>

<body>

	<jsp:include page="topNav.jsp" />

	<div class="container">
		<div class="row">
			<div class="span8">
				<div class="page-header">
					<h1>
						AHP <small>Current projects</small>
					</h1>
				</div>
				<p>
					Select a model from the Projects page to begin<br/>
					or select the Set up page to create a new model.

				</p>
				<p>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Goal</th>
							<th>Goal Description</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${projects}" var="entry" >
					    <tr> 
					    	<td><a href="new?projectName=${entry.value.goalName}" >${entry.value.goalName}</a></td>
					    	<td>${entry.value.goalDescription}</td>
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