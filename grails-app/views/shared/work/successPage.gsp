<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>Your Details</title>
    </head>
    <body>
        <div class="body">
            <h1>Your Details</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="buttons">
            First Name:  ${fname}<br/>
			Last Name: ${lname}<br/>
			Address: ${address}<br/>
			City: ${city}<br/>
			State: ${state}<br/>
			Country: ${country}<br/>
	       
            </div>
        </div>
    </body>
</html>
