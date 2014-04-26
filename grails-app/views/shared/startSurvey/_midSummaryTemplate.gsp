<div id="chart_div" style="width: 900px; height: 500px;"></div>
<div id='table_div'></div>	 
<g:link event="next" class="buttons"><g:message code="default.fhc.button.next"/></g:link>					

<script type="text/javascript">
// chart
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
		var raw = '${midSummaryCommand?.convertToChartData()}';
		raw = raw.replace(/&quot;/ig,'"');
		var data = google.visualization.arrayToDataTable(JSON.parse(raw));
	    var options = {
	       title: 'INCOME FOR YOU AND YOUR AGE GROUP',
	       curveType: 'function',
	       legend: { position: 'bottom' }
	    };
	    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	    chart.draw(data, options);
}

// table
google.load('visualization', '1', {packages:['table']});
google.setOnLoadCallback(drawTable);
function drawTable() {
	var raw = '${midSummaryCommand?.convertToTableData()}';
	raw = raw.replace(/&quot;/ig,'"');
	var data = google.visualization.arrayToDataTable(JSON.parse(raw));	
	var table = new google.visualization.Table(document.getElementById('table_div'));
	table.draw(data, {showRowNumber: false});
}

</script> 