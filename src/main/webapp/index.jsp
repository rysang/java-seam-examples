<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
<link type="text/css" href="css/sunny/jquery-ui-1.8.13.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
</head>
<body>
	<h1>Hello World!</h1>
	<c:out value="${testDao.version}" />

	<script type="text/javascript">
		var val = 10;
		$(document).ready(
				function() {
					$("#progress").progressbar({
						value : val
					});
					
					
					$("#makeprogress").click(function(){
						val += 5;
						
						$("#progress").progressbar({
							value : val
						});
						
						if(val == 100){
							val = 0;
						}
					});

					$("#makeprogress").button({
						icons : {
							primary : "ui-icon-arrowrefresh-1-e"
						},
						text : false
					});

					$.get('rest/applicationStatus', function(data) {
						$('#version').html(data.version);
						$('#name').html(data.name);
					}).error(
							function(axObj) {

								var lo = "";
								for ( var f in axObj) {
									lo = lo + f + " | ";
								}

								alert("status: " + axObj.status
										+ "\nstatusText: " + axObj.statusText
										+ "\nresponseText: "
										+ axObj.responseText);
							});
				});
	</script>

	<table class="ui-state-highlight ui-corner-all" style="padding: 10px">
		<thead>
			<tr>
				<td>Version</td>
				<td>Name</td>
			</tr>
		</thead>

		<tbody>
			<tr>
				<td id="version">0</td>
				<td id="name">0</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="progress" style="height: 20px"></div></td>
			</tr>
			<tr>
				<td colspan="2"><button id="makeprogress"></button></td>
			</tr>
		</tbody>
	</table>
</body>
</html>
