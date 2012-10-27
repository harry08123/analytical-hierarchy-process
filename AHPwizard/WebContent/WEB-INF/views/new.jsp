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

	<jsp:include page="topNav.jsp"/>

	<div class="container">
	
	<!--  set the criteria weightings -->
		<div class="page-header">
			<h1>
				${model.goalName } <small>${model.goalDescription}</small>
			</h1>
		</div>
	
	<c:choose >
		<c:when test="${ empty model}">
		Select a project from the projects page
		</c:when>
		<c:when test="${ model.status eq 'prepare'}">
		<form class="form-horizontal" method="post" action="prepare">
			<c:set var="count" value="${1}" />
			<div id="count_${count}" class="slider_container">
				<table class="mTable">
					<c:set var="outerCount" value="${0}" />
					<c:forEach items="${model.criteriaLabels }" var="altLabel">
						<c:set var="outerCount" value="${outerCount + 1 }" />
						<c:set var="innerCount" value="${0}" />
						<c:forEach items="${model.criteriaLabels }" var="altLabel1">
							<c:set var="innerCount" value="${innerCount + 1 }" />
							 <c:if test="${ innerCount gt outerCount }">
								
									<tr>
										<td class="label-td"><c:out value="${altLabel}"></c:out></td>
										<td><div class="slider" id="${critLabel}:${altLabel}:${altLabel1}"></div></td>
										<td class="label-td"><c:out value="${altLabel1}"></c:out></td>
									</tr>
								
							</c:if>
						</c:forEach>
					</c:forEach>
				</table>
			</div>
			<div class="control-group"  id="count_${count}">
				<div class="controls">
					<div class="btn-group">
						<input id="valuePrepare" class="btn" type="button" value="submit"/>
					</div>
				</div>
			</div>
		</form>
		
		<!-- end set the criteria weightings -->
		</c:when>
		<c:when test="${ model.status eq 'ready'}">
		
		<form class="form-horizontal" method="post" action="evaluate">
		<c:set var="count" value="${1}" />
		<c:set var="cTypes" value="${model.criteriaType}" />
		<c:forEach items="${model.criteriaLabels }" var="critLabel" >
		<div id="count_${count}" class="slider_container">
		<h4><c:out value="${critLabel}"></c:out></h4>
			<c:choose> 
				  <c:when test="${cTypes[critLabel] eq 'REAL'}" >
				  	<table class="mTable">
							
				  	<c:forEach items="${model.alternativeLabels }" var="altLabel">
						<tr>
							<td class="label-td"><span class="label"><c:out value="${altLabel}"></c:out></span></td>
							<td><input type="text" class="input_real" id="${critLabel}:${altLabel}"/></td>
							<td class="label-td" ></td>
						</tr>			
					</c:forEach>
						
					</table>
				  </c:when>
				  <c:otherwise>
					  <c:set var="outerCount" value="${0}" />
					  <table class="mTable">
						<c:forEach items="${model.alternativeLabels }" var="altLabel">
							<c:set var="outerCount" value="${outerCount + 1 }" />
							<c:set var="innerCount" value="${0}" />
							<c:forEach items="${model.alternativeLabels }" var="altLabel1">
								<c:set var="innerCount" value="${innerCount + 1 }" />
								 <c:if test="${ innerCount gt outerCount }">
						
							<tr>
								<td class="label-td"><span class="label"><c:out value="${altLabel}"></c:out></span></td>
								<td><div class="slider" id="${critLabel}:${altLabel}:${altLabel1}"></div></td>
								<td class="label-td" ><span class="label"><c:out value="${altLabel1}"></c:out></span></td>
							</tr>
						
								</c:if>
							</c:forEach>
						</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
				<div class="control-group">
					<div class="controls">
						<div class="btn-group">
							<c:if test="${count > 1}" >
								<input class="btn prev" type="button" id="__${count}" value="back" />
							</c:if>
								<input class="btn next" type="button" id="_${count}" value="next" />
							<c:set var="count" value="${count+1}" />
						</div>
					</div>
				</div>
		</div>
		</c:forEach>
		
		<div class="control-group"  id="count_${count}"  style="display:none;">
				<div class="controls">
					<div class="btn-group">
						<input class="btn prev" type="button" id="__${count}" value="back" />
						<input id="valueSubmit" class="btn" type="button" value="submit"/>
					</div>
				</div>
		</div>
		</form>
		
		
		</c:when>
		</c:choose>
		
		
		<div id="chart">
		
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
        $( ".slider" ).slider({ min:-8, max:8, value:0});
        result = '';
        $('#valueSubmit').click(
        		function(){
        			$('.slider').each(function(i){ 
        				var s = $(this);
        				result= result+','+s.attr('id')+':'+s.slider('value');
        			});
        			$('.input_real').each(function(i){ 
        				var s = $(this);
        				console.log(s.attr('id')+':'+s.attr('value'));
        				result= result+','+s.attr('id')+':'+s.attr('value');
        			});
        			$.ajax({url:'evaluate',handleAs:'json', type :'post',data:{results:result }, 
        				success:function(result){renderChart(result);}});
        		}
        );
        $('#count_1').show();
        $('.next').click( function(){
        	var o = $(this).attr('id');
        	var i = parseInt(o.substring(1));
        	$('#count_'+i).hide();
        	var n = i+1;
        	$('#count_'+n).show();
        });
        $('.prev').click(function(){
        	var o = $(this).attr('id');
        	var i = parseInt(o.substring(2));
        	$('#count_'+i).hide();
        	var n = i-1;
        	$('#count_'+n).show();
        	$('#chart').html('');
        });
        
        $('#valuePrepare').click(
        		function(){
        			$('.slider').each(function(i){ 
        				var s = $(this);
        				result= result+','+s.attr('id')+':'+s.slider('value');
        			});
        			
        			$.ajax({url:'prepare',handleAs:'json', type :'post',data:{results:result }, 
        				success:function(result){
        					window.location = 'http://'+location.host+'/AHPwizard/prepare';
        					}
        			});
        		}
        );
        
    });
    
    
    </script>
    		<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
		<script type="text/javascript" >
		var chart;
		function renderChart( values) {
		         var seriesVals = new Array();
		         values=eval("("+values+")");
		         for ( val in values ){
		             seriesVals.push( { name: val, data: [values[val]]}  );                 
		         }
		         console.log( seriesVals);
		        chart = new Highcharts.Chart({
		            chart: {
		                renderTo: 'chart',
		                type: 'column'
		            },
		            title: {
		                text: '${model.goalName }'
		            },
		            subtitle: {
		                text: '${model.goalDescription }'
		            },
		            xAxis: {
		                categories: [
		                    'Alternatives'
		                ]
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: 'Ranking'
		                }
		            },
		            legend: {
		                layout: 'vertical',
		                backgroundColor: '#FFFFFF',
		                align: 'left',
		                verticalAlign: 'top',
		                x: 100,
		                y: 70,
		                floating: true,
		                shadow: true
		            },
		            tooltip: {
		                formatter: function() {
		                    return ''+
		                    	this.series.name +': '+ this.y;
		                }
		            },
		            plotOptions: {
		                column: {
		                    pointPadding: 0.2,
		                    borderWidth: 0
		                }
		            },
		            series:seriesVals
		        });
		        $('#chart').show();
		    
		};
		</script>
	
</body>
</html>