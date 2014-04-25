<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Name</title>
    </head>
    <body>
    <script>
    </script>
        <div class="body">
            <h1>Name</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
           <g:renderErrors bean="${nameCmd}" />
           
            <div class="buttons">
	            <g:form action="work">
	            	<g:textField name="fname" value="${nameCmd?.fname?:fname}" placeholder="First Name"/>
	            	<g:textField name="lname" value="${nameCmd?.lname?:lname}" placeholder="Last Name"/>
				    <g:submitButton name="step2" value="Next"></g:submitButton>
				</g:form>
            </div>
        </div>
    </body>
</html>
