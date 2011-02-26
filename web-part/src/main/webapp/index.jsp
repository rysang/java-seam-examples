
<%@page import="java.util.Date"%>
<%
	//Set Cache-Control to no-cache.
	response.setHeader("Cache-Control", "no-cache");
	// Prevent proxy caching.
	response.setHeader("Pragma", "no-cache");
	// Set expiration date to a date in the past.
	response.setDateHeader("Expires", 946080000000L); //Approx Jan 1, 2000
	// Force always modified.
	response.setDateHeader("Last-Modified", new Date().getTime());
%>

<html>
<body>
<object width="550" height="400">
	<param name="movie" value="flash/flex-part-1.0-SNAPSHOT.swf">
	<embed src="flash/flex-part-1.0-SNAPSHOT.swf" width="100%"
		height="100%">
	</embed> </object>
</body>
</html>
