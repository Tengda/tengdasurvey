<div id='table_div'></div>	 
<div class="buttons">
	<g:form action="${branch?.survey?.name?:name}">
		<div>
			Email? 
			<g:textField name="email" value="${finalSummaryCommand?.email?:email}"/>				
		</div>	
		<div>
			Instant Telepressence?
			<g:textField name="telepressence" value="${finalSummaryCommand?.telepressence?:telepressence}"/>				
		</div>	
<!-- 	
      <span id='loginLink' style='position: relative; margin-right: 30px; float: right'>
      <sec:ifLoggedIn>
         Logged in as <sec:username/> (<g:link controller='logout'>Logout</g:link>)
      </sec:ifLoggedIn>
      <sec:ifNotLoggedIn>
         <a href='#' onclick='showLogin(); return false;'>Login</a>
      </sec:ifNotLoggedIn>
      </span>	
		<g:render template='/includes/ajaxLogin'/>
 -->	
		<g:submitButton name="next" value="Next"></g:submitButton>
	</g:form>
</div>


<script type="text/javascript">

// table
google.load('visualization', '1', {packages:['table']});
google.setOnLoadCallback(drawTable);
function drawTable() {
	var raw = '${finalSummaryCommand?.convertToTableData()}';
	raw = raw.replace(/&quot;/ig,'"');
	var data = google.visualization.arrayToDataTable(JSON.parse(raw));	
	var table = new google.visualization.Table(document.getElementById('table_div'));
	table.draw(data, {showRowNumber: false});
}

</script>