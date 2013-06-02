<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Spring 3 MVC Series - Contact Manager</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
.data,.data td {
	border-collapse: collapse;
	width: 100%;
	border: 1px solid #aaa;
	margin: 2px;
	padding: 2px;
}

.data th {
	font-weight: bold;
	background-color: #5C82FF;
	color: white;
}
</style>

<jsp:include page="headers.jsp" />
</head>
<body>

	<a href="/new">New Contact</a>
	<a href="/secure/contact">Test Secure</a>
	
	<div>
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				Action <span class="caret"></span>
			</a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
				<li><a tabindex="-1" href="#">Action</a></li>
				<li><a tabindex="-1" href="#">Another action</a></li>
				<li><a tabindex="-1" href="#">Something else here</a></li>
				<li class="divider"></li>
				<li><a tabindex="-1" href="#">Separated link</a></li>
			</ul>
		</div>
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				Action <span class="caret"></span>
			</a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
				<li><a tabindex="-1" href="#">Action</a></li>
				<li><a tabindex="-1" href="#">Another action</a></li>
				<li><a tabindex="-1" href="#">Something else here</a></li>
				<li class="divider"></li>
				<li><a tabindex="-1" href="#">Separated link</a></li>
			</ul>
		</div>
		<div class="btn-group">
			<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
				Action <span class="caret"></span>
			</a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
				<li><a tabindex="-1" href="#">Action</a></li>
				<li><a tabindex="-1" href="#">Another action</a></li>
				<li><a tabindex="-1" href="#">Something else here</a></li>
				<li class="divider"></li>
				<li><a tabindex="-1" href="#">Separated link</a></li>
			</ul>
		</div>
	</div>

	<div>
		<spring:eval expression="entity.getProperty('email')" />
	</div>

	<h3>Contacts</h3>
	<c:if test="${!empty contactList}">
		<table class="data">
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Telephone</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${contactList}" var="contact">
				<tr>
					<td><spring:eval expression="contact.getLastname()" />,
						${contact.firstname}</td>
					<td>${contact.email}</td>
					<td>${contact.telephone}</td>
					<td><a onclick="return confirm('Delete this item ?')"
						href="delete?contactId=${fn:escapeXml(contact.id)}">delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</body>
</html>
