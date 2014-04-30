
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>
<g:renderErrors bean="${financialProductsInfoCommand}" />
           			
<div class="buttons">

	Now let's learn a little about your </br>
	current financial products. </br>
	Please select all that apply. If you don't know exact amounts </br>
	on any of the items below, just do your best to estimate. </br>

	<g:form action="${branch?.survey?.name?:name}">
		<div>
			Salary Range: 
			<div id=salarySlider></div>
			<g:textField id="salary" name="salary" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.salary?:salary}"/>				
		</div>
		
		<div class="buttons">
			Banking Products</br>
			<g:link url="#" class="buttons" elementId="btn_savingAccounts">Savings Accounts</g:link>
			<g:link url="#" class="buttons" elementId="btn_checkingAccounts">Checking Accounts</g:link>
			<g:link url="#" class="buttons" elementId="btn_creditCards">Credit Cards</g:link>
			
			<g:textField style="display:none;" class="checkingAccountsCount" name="checkingAccountsCount" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.checkingAccountsCount?:checkingAccountsCount}"/>
			<g:textField style="display:none;" class="checkingAccountsAmount" name="checkingAccountsAmount" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.checkingAccountsAmount?:checkingAccountsAmount}"/>
		</div>

   		<g:submitButton name="next" value="Next"></g:submitButton>		
	</g:form>
	
		<div id="savingAccountsDialog">
			Saving Accounts</br>
			How many accounts you have? </br>
			<div id=checkingAccountsCountSlider></div>
			<g:textField class="checkingAccountsCount" name="checkingAccountsCount" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.checkingAccountsCount?:checkingAccountsCount}"/>			
			</br>
			<div id=checkingAccountsAmountSlider></div>
			<g:textField class="checkingAccountsAmount" name="checkingAccountsAmount" value="${financialProductsInfoCommand==null?0:financialProductsInfoCommand.checkingAccountsAmount?:checkingAccountsAmount}"/>
		</div>	
</div>					
<script type="text/javascript">
$(function(){

  // $("#salary").prop("disabled", true);
	$( "#salarySlider" ).slider({
		value:${financialProductsInfoCommand==null?0:financialProductsInfoCommand.salary?:salary},
        min: 0,
        max: 200000,
        step: 100,
        slide: function( event, ui ) {
        	$( "#salary" ).val(ui.value );
        }
	});	  


	$( "#checkingAccountsCountSlider" ).slider({
		value:${financialProductsInfoCommand==null?0:financialProductsInfoCommand.checkingAccountsCount?:checkingAccountsCount},
		min: 0,
		max: 5,
		step: 1,
		slide: function( event, ui ) {
			$( ".checkingAccountsCount" ).val(ui.value );
		}
    }); 

	$( "#checkingAccountsAmountSlider" ).slider({
		value:${financialProductsInfoCommand==null?0:financialProductsInfoCommand.checkingAccountsAmount?:checkingAccountsAmount},
	    min: 0,
	    max: 200000,
	    step: 100,
	    slide: function( event, ui ) {
			$( ".checkingAccountsAmount" ).val(ui.value );
	    }
	    });        	      	    	      
	$( '#savingAccountsDialog' ).dialog({
		autoOpen: false,
	    show: {
	    	effect: "blind",
	        duration: 1000
	    },
	    hide: {
	        effect: "explode",
	        duration: 1000
	    }
	});	
	
	$("#btn_savingAccounts").click(function(e){
	 	e.preventDefault();
	 	$( "#savingAccountsDialog" ).dialog( "open" );
	});

	$("#btn_checkingAccounts").click(function(e){
	  	e.preventDefault();
		alert('sf');
	});
	
	$("#btn_creditCards").click(function(e){
	  	e.preventDefault();
		alert('sf');
	});
});	
</script>				

		


