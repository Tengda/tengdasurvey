<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<div id="page-body" role="main">
			<h1><g:message code="default.fhc.title"/></h1>
				<g:form>
					<fieldset class="buttons">
						<g:hiddenField name="branchId" value="${params.branchId}"/>
						<g:actionSubmit action="startSuvey" value="${message(code:'default.fhc.survey.start')}"/>
					</fieldset>
				</g:form>			
		</div>
	</body>
</html>
