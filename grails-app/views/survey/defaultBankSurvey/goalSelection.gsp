<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
  <style>
  #selectable .ui-selecting { background: #FECA40; }
  #selectable .ui-selected { background: #F39814; color: white; }
  #selectable { list-style-type: none; margin: 0; padding: 0; width: 450px; }
  #selectable li { margin: 3px; padding: 1px; float: left; width: 100px; height: 80px; font-size: 1em; text-align: center; }
  .buttons{
  	display: block;
  }
  </style>
		
	</head>
	<body>
		<div id="page-body" role="main">		
			<div style="display: block;float:left; width : 800px">
			<g:form action="${branch?.survey?.name?:name}">
				<div style="display: block;">
					<ol id="selectable">				
						
					<!-- 	
						<g:checkBox name="goalSelectionCommand?.items?.name?name" value="true" checked="goalSelectionCommand?.items?.isSelected?isSelected" />
				 			
				  <g:each var="item" in="${goalSelectionCommand?.items}" > 
				  
				  
				  	<g:checkBox name="goalSelectionCommand?.items?.${item.value}" value="true" checked="goalSelectionCommand?.items?.${item?.isSelected}}" />

				    <label for="item">${item.name}  ${item.isSelected}</label>
				   
				  </g:each> 
				  -->	
					  	
					<g:each in="${goalSelectionCommand?.items}" var="item">
						<li class="ui-state-default">
							<g:checkBox name="items" value="${item.value}" checked="${item.isSelected}" />
							<label for="item">${item.name}</label>
						</li> 	
					</g:each>				  
					  	
				  	</ol>				

						
				</div>		
				<div class="buttons" style="display: block;float:left;">			
			   		<g:submitButton name="next" value="Next"></g:submitButton>
			   	</div>					

			</g:form>				
			</div>
		</div>
		  <script>
		  $(function() {
		    $( "#selectable" ).selectable();
		  });
		  </script>			
	</body>
</html>