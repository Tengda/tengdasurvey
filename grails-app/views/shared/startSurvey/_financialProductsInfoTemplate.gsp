
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>
<g:renderErrors bean="${financialProductsInfoCommand}" />
           			
<div class="buttons">
	<g:form action="${branch?.survey?.name?:name}">
		<div>
			Sex
			<g:radioGroup name="sex" labels="['Male','Female']" values="['male','female']" value="${financialProductsInfoCommand?.sex?:sex}" >
				<g:message code="${it.label}" />${it.radio}
			</g:radioGroup>	
			Marital Status
			<g:radioGroup name="marital_status" labels="['Marry','Single']"  values="[marry,single]" value="${financialProductsInfoCommand?.marital_status?:marital_status}" >
				<g:message code="${it.label}" />${it.radio}
			</g:radioGroup>							
		</div>
		<div>
			Age: 
			<div id=ageSlider></div>
			<g:textField id="age" name="age" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.age?:age}"/>	
		</div>

		<div>
			Kids: 
			<div id=kidsSlider></div>
			<g:textField id="kids" name="kids" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.kids?:kids}"/>				
		</div>

		<div>
			Debt: 
			<div id=debtSlider></div>
			<g:textField id="debt" name="debt" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.debt?:debt}"/>				
		</div>

   		<g:submitButton name="next" value="Next"></g:submitButton>
		<g:submitButton name="next" value="Back"></g:submitButton>
	</g:form>
</div>					
<script type="text/javascript">
$(document).ready(function(){
	//$("#age").prop("hidden", true);	
    $( "#ageSlider" ).slider({
        value:  ${financialProductsInfoCommand==null?0:financialProductsInfoCommand.age?:age},
       min: 0,
       max: 500,
       step: 50,
       slide: function( event, ui ) {
         $( "#age" ).val(ui.value);
       }
     });

  // $("#kids").prop("disabled", true);	    
   $( "#kidsSlider" ).slider({
       value:${financialProductsInfoCommand==null?0:financialProductsInfoCommand.kids?:kids},
       min: 0,
       max: 500,
       step: 50,
       slide: function( event, ui ) {
         $( "#kids" ).val(ui.value );
       }
     });

  // $("#debt").prop("disabled", true);
   $( "#debtSlider" ).slider({
       value:${financialProductsInfoCommand==null?0:financialProductsInfoCommand.debt?:debt},
       min: 0,
       max: 500,
       step: 50,
       slide: function( event, ui ) {
         $( "#debt" ).val(ui.value );
       }
     });	    	      	    	      

  })
</script>				

		


