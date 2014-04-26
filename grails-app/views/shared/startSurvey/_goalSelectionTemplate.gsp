<div style="display: block;float:left; width : 800px">
	<g:form action="${branch?.survey?.name?:name}">
		<div style="display: block;">
			<ol id="selectable">					
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

 <script>
$(function() {
  $( "#selectable" ).selectable();
});
</script>	