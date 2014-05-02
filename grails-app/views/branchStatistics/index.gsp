<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<div id="page-body" role="main">
			<h1>BranchStatistics</h1>	

		    <table>
		        <tr>
		            <td>Name</td>
		            <td>hits</td>
		        </tr>
		        <g:each in="${branchStatisticsCommand?.pageStatisticses}" var="pageStatistics">
		        <tr>
		            <td>${pageStatistics.pageId}</td>
		            <td>${pageStatistics.odometer}</td>
		        </tr>
		        </g:each>        
		    </table>			
		</div>
	</body>
</html>
