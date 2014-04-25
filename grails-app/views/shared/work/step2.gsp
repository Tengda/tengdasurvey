<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Address</title>
    </head>
    <body>
    
        <div class="body">
            <h1>Address</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:renderErrors bean="${addressCmd}" />
            <div class="buttons">
	            <g:form action="work">
	            	<g:textField name="address" value="${addressCmd?.address?:address}" placeholder="Address"/>
	            	<g:textField name="city" value="${addressCmd?.city?:city}" placeholder="City"/>
				    <g:submitButton name="step3" value="Next"></g:submitButton>
				</g:form>
            </div>
        </div>
    </body>
</html>
