<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<gvisualization:apiImport/>	
		<!--  
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>	
		-->
	</head>
	<body>
		<div id="page-body" role="main">	
			this is midsummary	
			<h1>show survey</h1>
			<g:link event="next" class="buttons"><g:message code="default.fhc.button.next"/></g:link>					
		</div>	

		<div id="chart_div" style="width: 900px; height: 500px;"></div>
	    <script type="text/javascript">
	      google.load("visualization", "1", {packages:["corechart"]});
	      google.setOnLoadCallback(drawChart);
	      function drawChart() {
	    	var raw = '${midSummaryCommand?.concevertToChartData()}';
	    	raw = raw.replace(/&quot;/ig,'"');
	        var data = google.visualization.arrayToDataTable(JSON.parse(raw));
	
	        var options = {
	          title: 'Company Performance'
	        };
	
	        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	        chart.draw(data, options);
	      }
	    </script>

			
	</body>
</html>