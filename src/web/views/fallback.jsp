<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Errore</title>
	</head>
	<body>
		<h1>Oops! Qualcosa &egrave; andato storto.</h1>

		<h2>Errore: <%= request.getAttribute("javax.servlet.error.status_code") %></h2>
		<p>Messaggio: <%= request.getAttribute("javax.servlet.error.message") %></p>
        <% if (request.getAttribute("javax.servlet.error.exception_type") != null) { %>
            <p> Tipo di errore:<% request.getAttribute("javax.servlet.error.exception_type"); %> </p>
        <% } %>

		<% Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable != null) { %>
		<h3>Dettagli:</h3>
		<pre><%= throwable.getMessage() %></pre>
		<% } %>

		<p><a href="${pageContext.request.contextPath}/index.jsp">Torna alla home</a></p>
	</body>
</html>
