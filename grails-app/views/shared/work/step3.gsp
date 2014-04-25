<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Region</title>
    </head>
    <body>
    
        <div class="body">
            <h1>Region</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:renderErrors bean="${regionCmd}" />
            <div class="buttons">
			<g:form action="work">
	            	<g:textField name="state" value="${regionCmd?.state?:state}" placeholder="State"/>
	            	<g:textField name="country" value="${regionCmd?.country?:country}" placeholder="Country"/>
				    <g:submitButton name="submit" value="Submit"></g:submitButton>
				</g:form>		            

            </div>
        </div>
    </body>
</html>
