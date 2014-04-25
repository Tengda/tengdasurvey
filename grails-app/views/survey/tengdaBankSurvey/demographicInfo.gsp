<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>		
	</head>
	<body>
	Demo graph1
		<div id="page-body" role="main">	
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
           <g:renderErrors bean="${demographicCommand}" />
           			
            <div class="buttons">
	            <g:form action="${branch?.survey?.name?:name}">
					<div>
					Sex
						<g:radioGroup name="sex" labels="['Male','Female']" values="['male','female']" value="${demographicCommand?.sex?:sex}" >
							<g:message code="${it.label}" />${it.radio}
						</g:radioGroup>	
					Marital Status
						<g:radioGroup name="marital_status" labels="['Marry','Single']"  values="[marry,single]" value="${demographicCommand?.marital_status?:marital_status}" >
							<g:message code="${it.label}" />${it.radio}
						</g:radioGroup>							
					</div>
					<div>
						Age: 
						<div id=ageSlider></div>
						<g:textField id="age" name="age" value="${demographicCommand==null?0:demographicCommand.age?:age}"/>	
					</div>
					
					<div>
						Kids: 
						<div id=kidsSlider></div>
						<g:textField id="kids" name="kids" value="${demographicCommand==null?0:demographicCommand.kids?:kids}"/>				
					</div>
					
					<div>
						Debt: 
						<div id=debtSlider></div>
						<g:textField id="debt" name="debt" value="${demographicCommand==null?0:demographicCommand.debt?:debt}"/>				
					</div>

				    <g:submitButton name="financialProductsInfo" value="Next"></g:submitButton>
				</g:form>
            </div>								
		</div>
	    <script type="text/javascript">
	    	$(document).ready(function()
	        {
	    		$("#age").prop("disabled", true);		
	    	    $( "#ageSlider" ).slider({
	    	        value:  ${demographicCommand==null?0:demographicCommand.age?:age},
	    	        min: 0,
	    	        max: 500,
	    	        step: 50,
	    	        slide: function( event, ui ) {
	    	          $( "#age" ).val(ui.value);
	    	        }
	    	      });

	    	    $("#kids").prop("disabled", true);	    
	    	    $( "#kidsSlider" ).slider({
	    	        value:${demographicCommand==null?0:demographicCommand.kids?:kids},
	    	        min: 0,
	    	        max: 500,
	    	        step: 50,
	    	        slide: function( event, ui ) {
	    	          $( "#kids" ).val(ui.value );
	    	        }
	    	      });

	    	    $("#debt").prop("disabled", true);
	    	    $( "#debtSlider" ).slider({
	    	        value:${demographicCommand==null?0:demographicCommand.debt?:debt},
	    	        min: 0,
	    	        max: 500,
	    	        step: 50,
	    	        slide: function( event, ui ) {
	    	          $( "#debt" ).val(ui.value );
	    	        }
	    	      });	    	      	    	      

	        })
	    </script>			
	</body>
</html>


