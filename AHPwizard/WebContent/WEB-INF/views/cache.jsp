<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
						AHP <small>Active Projects</small>
					</h1>
				</div>
				<p>
					Projects that are currently being evaluated are listed below

				</p>
				<p>
				
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Goal</th>
							<th>Contributors</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${aggregates}" var="entry" >
					    <tr> 
					    	<td>${entry.key}</td>
					    	<td>${entry.value}</td>
					    	<td><a href="cache?aggregate=${entry.key}" ><i class="icon-remove-sign"></i></a></td>
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