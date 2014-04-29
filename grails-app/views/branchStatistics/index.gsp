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
		        <g:each in="${branchStatisticsCommand?.counterRequests}" var="counterRequest">
		        <tr>
		            <td>${counterRequest.name}</td>
		            <td>${counterRequest.hits}</td>
		        </tr>
		        </g:each>        
		    </table>			
		</div>
	</body>
</html>
