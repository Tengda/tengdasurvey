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
			<tmpl:/shared/startSurvey/goalSelectionTemplate/>	
		</div>			
	</body>
</html>