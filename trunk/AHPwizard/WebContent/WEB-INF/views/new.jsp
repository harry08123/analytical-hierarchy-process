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
				${model.goalName } <small class="hidden-phone">${model.goalDescription}</small>
			</h1>
		</div>
	
	<c:choose >
		<c:when test="${ empty model}">
		Select a project from the projects page
		</c:when>
		<c:when test="${ model.status eq 'prepare'}">
		<form class="form-horizontal" method="post" action="prepare">
			<c:set var="count" value="${1}" />
			<h3>Criteria</h3>
			<div id="count_${count}" class="slider_container">
				
					<c:set var="outerCount" value="${0}" />
					<c:forEach items="${model.criteriaLabels }" var="altLabel">
						<c:set var="outerCount" value="${outerCount + 1 }" />
						<c:set var="innerCount" value="${0}" />
						<c:forEach items="${model.criteriaLabels }" var="altLabel1">
							<c:set var="innerCount" value="${innerCount + 1 }" />
							 <c:if test="${ innerCount gt outerCount }">
							 <div class="row">
								 <div class="span8" style="text-align:center">
									   <div class="alert alert-info">
	    								<small><strong>${altLabel}</strong> and <strong>${altLabel1}</strong> are EQUAL</small>
	   								</div>
	   							 </div>
	   							 <div class="span4"></div>
							 </div>
							 <div class="row">
								<div class="span2 c-align"><c:out value="${altLabel}"></c:out></div>
								<div class="span4"><div class="slider" id="${critLabel}:${altLabel}:${altLabel1}"></div></div>
								<div class="span2 c-align"><c:out value="${altLabel1}"></c:out></div>
								<div class="span4">
   								</div>
							</div>	
							</c:if>
						</c:forEach>
					</c:forEach>
			</div>
			<div class="control-group"  id="count_${count}">
				<div class="controls">
					<div class="btn-group">
						<input id="valuePrepare" class="btn  btn-primary btn-large" type="button" value="submit"/>
					</div>
				</div>
			</div>
		</form>
		
		<!-- end set the criteria weightings -->
		</c:when>
		<c:when test="${ model.status eq 'ready'}">
		
		<form id="_eval_form" class="form-horizontal" method="post" action="evaluate">
		<c:set var="count" value="${1}" />
		<c:set var="cTypes" value="${model.criteriaType}" />
		<c:forEach items="${model.criteriaLabels }" var="critLabel" >
		<div id="count_${count}" class="slider_container">
		
		<h3><c:out value="${critLabel}"></c:out></h3>
			<c:choose> 
				  <c:when test="${cTypes[critLabel] eq 'REAL_HIGHER_IS_BETTER' || cTypes[critLabel] eq 'REAL_LOWER_IS_BETTER'}" >
				  	 
					<div class="row">
				  	<c:forEach items="${model.alternativeLabels }" var="altLabel">
				  	
				  	<div class="control-group">
						<label  class="control-label"><c:out value="${altLabel}"></c:out></label>
						 <div class="controls">
						<input type="text" class="input_real input-small" id="${critLabel}:${cTypes[critLabel]}:${altLabel}"/>
						</div>
					</div>	
							
					</c:forEach>
					</div>
						
				  </c:when>
				  <c:otherwise>
					  <c:set var="outerCount" value="${0}" />
						<c:forEach items="${model.alternativeLabels }" var="altLabel">
							<c:set var="outerCount" value="${outerCount + 1 }" />
							<c:set var="innerCount" value="${0}" />
							<c:forEach items="${model.alternativeLabels }" var="altLabel1">
								<c:set var="innerCount" value="${innerCount + 1 }" />
								 <c:if test="${ innerCount gt outerCount }">
								
							 <div class="row">
								 <div class="span8" style="text-align:center">
									   <div class="alert alert-info">
	    								<small><strong>${altLabel}</strong> and <strong>${altLabel1}</strong> are EQUAL with respect to <strong>${critLabel}</strong></small>
	   								</div>
	   							 </div>
	   							 <div class="span4"></div>
							 </div>
							 <div class="row">
								<div class="span2 c-align"><c:out value="${altLabel}"></c:out></div>
								<div class="span4"><div class="slider" id="${critLabel}:${altLabel}:${altLabel1}"></div></div>
								<div class="span2 c-align"><c:out value="${altLabel1}"></c:out></div>
								<div class="span4">
   								</div>
							</div>	
								</c:if>
							</c:forEach>
						</c:forEach>
					</c:otherwise>
				</c:choose>
				<!-- 
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
				 -->
		</div>
		</c:forEach>
		
		<div class="control-group"  id="count_${count}"  >
				<div class="controls">
					<div class="btn-group">
						<!-- <input class="btn prev" type="button" id="__${count}" value="back" /> -->
						<input id="valueSubmit" class="btn  btn-large btn-primary" type="button" value="submit"/>
					</div>
				</div>
		</div>
		</form>
		
		
		</c:when>
		</c:choose>
		
	<div id="error" class="alert hide">
	    <small><strong>Warning!</strong></small>
    </div>
    <div class="control-group hide" id="final-buttons"   >
				<div class="controls">
					
						<input id="refresh" class="btn btn-large  btn-primary" type="button" value="refresh"/>
						<a role="button" href="#criteriaModal" id="tweak" class="btn btn-large  btn-primary" data-toggle="modal" >Tweak</a>
					
				</div>
		</div>
		<div id="chart">
		
		</div>
		
		<div class="modal hide" id="criteriaModal" tabindex="-1" role="dialog"
			aria-labelledby="criteriaModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="criteriaModalLabel">Change Criteria</h3>
			</div>
			<div class="modal-body">
				
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				<button class="btn btn-primary" id="addCriteria"  data-dismiss="modal">Update</button>
			</div>
		</div>
		
		
		
	</div>
	
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	  <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
	<script src="/AHPwizard/resources/bootstrap-cust/js/bootstrap.min.js"></script>
  
    <script>
    var ratings = new Array(9);
    ratings[0] = '<strong>%{winner}</strong> and <strong>%{loser}</strong> are EQUAL';
    ratings[1] = '<strong>%{winner}</strong> is slightly better than <strong>%{loser}</strong>';
    ratings[2] = '<strong>%{winner}</strong> is moderately better than <strong>%{loser}</strong>';
    ratings[3] = '<strong>%{winner}</strong> is between moderately and strongly better than <strong>%{loser}</strong>';
    ratings[4] = '<strong>%{winner}</strong> is strongly better than <strong>%{loser}</strong>';
    ratings[5] = '<strong>%{winner}</strong> is between strongly and very strongly better than <strong>%{loser}</strong>';
    ratings[6] = '<strong>%{winner}</strong> is very strongly better than <strong>%{loser}</strong>';
    ratings[7] = '<strong>%{winner}</strong> is between very strongly and extremely better than <strong>%{loser}</strong>';
    ratings[8] = '<strong>%{winner}</strong> is extremly better than <strong>%{loser}</strong>';
    
    var critRatings = new Array(9);
    critRatings[0] = '<strong>%{winner}</strong> and <strong>%{loser}</strong> are EQUAL';
    critRatings[1] = '<strong>%{winner}</strong> is slightly more important than <strong>%{loser}</strong>';
    critRatings[2] = '<strong>%{winner}</strong> is moderately more important than <strong>%{loser}</strong>';
    critRatings[3] = '<strong>%{winner}</strong> is between moderately and strongly more important than <strong>%{loser}</strong>';
    critRatings[4] = '<strong>%{winner}</strong> is strongly more important than <strong>%{loser}</strong>';
    critRatings[5] = '<strong>%{winner}</strong> is between strongly and very strongly more important than <strong>%{loser}</strong>';
    critRatings[6] = '<strong>%{winner}</strong> is very strongly more important than <strong>%{loser}</strong>';
    critRatings[7] = '<strong>%{winner}</strong> is between very strongly and extremely more important than <strong>%{loser}</strong>';
    critRatings[8] = '<strong>%{winner}</strong> is extremly more important than <strong>%{loser}</strong>';
    
    $(function() {
    	$.ajaxSetup ({
    	    // Disable caching of AJAX responses
    	    cache: false
    	});
    	
        $( ".slider" ).slider({ min:-8, max:8, value:0,
        	change : function(event, ui){
        		var status = '${model.status}';
        		var val = ui.value;
        		var absVal = Math.abs(val)
        		var winner = '';
        		var loser = '';
        		var div = $(this).parent('div');
        		var divNext = div.next('div');
        		if ( val > -1 ){
        			winner = divNext.text();
        			loser = div.prev('div').text();
        		}else if (val < 0 ) {
        			loser = divNext.text();
        			winner = div.prev('div').text();
        		}
        		var message = '';
        		if ( status == 'ready'){
        			id = $(this).attr('id');
        			var split = id.split(':');
        			extra = ' with respect to <strong>'+split[0]+'</strong>';
        			message = ratings[absVal].replace('%{winner}', winner);
            		message = message.replace('%{loser}', loser);
            		message+=extra;
        			
        		}else{
        			message = critRatings[absVal].replace('%{winner}', winner);
            		message = message.replace('%{loser}', loser);
        			
        		}
        		
        		
        		div.closest('div.row').prev('div').children('div.span8').children('div').html( '<small>'+ message+'</small>' );
        	},
        });
        result = '';
        $('#valueSubmit').click(
        		function(){
        			var fail = false;
        			$('.slider').each(function(i){ 
        				var s = $(this);
        				result= result+','+s.attr('id')+':'+s.slider('value');
        			});
        			$('.input_real').each(function(i){ 
        				var s = $(this);
        				if ( !$.isNumeric(s.attr('value'))){
        					tip = $('#valueSubmit').tooltip( {title:"Please enter a number for all inputs", 
        						trigger:"manual"}).tooltip('show');
        					fail = true;
        					window.setTimeout("$('#valueSubmit').tooltip('hide')", 1500);
        				}
        				result= result+','+s.attr('id')+':'+s.attr('value');
        			});
        			if ( fail )return;
        			$.ajax({url:'evaluate',handleAs:'json', type :'post',data:{results:result }, 
        					success:function(result){
        						if(result.indexOf('//success' )== 1) {
        							$('#error').addClass('hide');
        							$('#_eval_form').addClass('hide');
        							$('#final-buttons').removeClass('hide');
        							renderChart(result);
        						}else if (result.indexOf('//error' )== 1 ){
        							$('#error').children('small').html('<strong>Warning</strong><pre>'+result+"</pre>");
        							$('#error').removeClass('hide');
        						}else{
        							alert( result)
        						}
        					}});
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
        						if(result.indexOf('//success' )== 1) {
        							$('#error').addClass('hide');
        							window.location = 'http://'+location.host+'/AHPwizard/prepare';
        						}else if (result.indexOf('//error' )== 1 ){
        							$('#error').children('small').html('<strong>Warning</strong><pre>'+result+"</pre>");
        							$('#error').removeClass('hide');
        						}else{
        							alert( result);
        						}
        					
        					}
        			});
        		}
        );
        
    });
    
    $('#refresh').click(
    	function(){
    		$.ajax({url:'refresh?projectName=${model.goalName }',handleAs:'json', type :'get' , 
    				success:function(result){
    						if(result.indexOf('//success' )== 1) {
    							$('#error').addClass('hide');
    							renderChart(result);
    						}else if (result.indexOf('//error' )== 1 ){
    							$('#error').children('small').html('<strong>Warning</strong><pre>'+result+"</pre>");
    							$('#error').removeClass('hide');
    						}else{
    							alert( result);
    						}
    					
    					}
    			});
    		}
    );
    
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
		                x: 70,
		                y: 100,
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