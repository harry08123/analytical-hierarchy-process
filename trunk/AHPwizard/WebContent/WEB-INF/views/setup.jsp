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
		<div class="page-header">
			<h1>
				AHP <small>Set up project properties</small>
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
			<div class="span1" ></div>
			<div class="control-group row" id="ahpBucket">
			<div class="span3"  id="criteriaList"><h1><small>Criteria</small></h1></div>
			<div class="span3" id="alternativeList"><h1><small>Alternatives</small></h1></div>
			<div class="span1" ></div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="btn-group">
						<a href="#criteriaModal" role="button" class="btn" data-toggle="modal" id="aCriteria">Add Criteria</a>
						<a href="#alternativeModal" role="button" class="btn" data-toggle="modal" id="aAlternative">Add Alternative</a>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<input class="btn" type="button" value="Create Project" id="setup"/>
				</div>
			</div>
			
		</form>
		<div class="modal hide fade" id="criteriaModal" tabindex="-1" role="dialog"
			aria-labelledby="criteriaModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="criteriaModalLabel">Add Criteria</h3>
			</div>
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label" for="cName">Criteria Name</label>
					<div class="controls">
						<input type="text" id="cName" name="cName"
							placeholder="criteria name" />
					</div>
					<label class="control-label" for="cType">Type</label>
					<div class="controls">
							<select id="cType"  name="cType">
								<option>PAIRWISE</option>
								<option>REAL</option>
							</select>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				<button class="btn btn-primary" id="addCriteria"  data-dismiss="modal">Save changes</button>
			</div>
		</div>

		<div class="modal hide fade" id="alternativeModal" tabindex="-1" role="dialog"
			aria-labelledby="alternativeModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="alternativeModalLabel">Add Alternative</h3>
			</div>
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label" for="aName">Alternative Name</label>
					<div class="controls">
						<input type="text" id="aName" name="aName"
							placeholder="alternative name" />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				<button class="btn btn-primary" id="addAlternative"  data-dismiss="modal">Save changes</button>
			</div>
		</div>
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
         $('#setup').click(
        		function(){
        			var result = '';
        			$('#criteriaList div p').each(function(i){ 
        				var s = $(this);
        				result= result+','+'criteria:'+s.html();
        			});
        			
        			$('#alternativeList div p').each(function(i){ 
        				var s = $(this);
        				result= result+','+'alternative:'+s.html();
        			});
        			result=result+',goalName:'+$('#goalName').val()+',goalDescription:'+$('#goalDescription').val();
        			console.log(result);
        			$.ajax({url:'setup',handleAs:'json', type :'post',data:{results:result }, 
        				success:function(result){
        					alert('Project properties set successfully');
        					}
        			});
        		}
        );
         var div = '<div class="alert alert-success"><button type="button" class="close" data-dismiss="alert">×</button><p>';
         var divE = "</p></div>";
        $('#addCriteria').click(function(){
        	 $('#criteriaList').append(div+$('#cName').attr('value')+ ':' + $('#cType').val() +divE);
        	 $('#cName').attr('value', '');
        });
        $('#addAlternative').click(function(){
        	 $('#alternativeList').append(div+$('#aName').attr('value')+divE);
        	 $('#aName').attr('value', '');
        });
    });
    
    
    </script>	
</body>
</html>
