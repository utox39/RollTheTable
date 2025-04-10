<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Errore Critico</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">

	<!-- Custom CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/style/fallback.css">
</head>
<body class="bg-dark text-white d-flex align-items-center justify-content-center min-vh-100">

<div class="container bg-black text-center p-4 bg-secondary bg-opacity-10 rounded shadow-lg">
	<img src="${pageContext.request.contextPath}/assets/images/critical_failure.jpg"
		 alt="Critical Failure" class="img-fluid rounded-pill mb-4" style="max-width: 200px;">

	<h1 class="text-danger">⚠️ Errore critico!</h1>
	<p class="lead">Oops! Qualcosa è andato storto nel dungeon del server...</p>

	<h3 class="text-light mt-4">Errore: <%= request.getAttribute("javax.servlet.error.status_code") %></h3>
	<p>Messaggio: <%= request.getAttribute("javax.servlet.error.message") %></p>

	<% if (request.getAttribute("javax.servlet.error.exception_type") != null) { %>
	<p>Tipo di errore: <%= request.getAttribute("javax.servlet.error.exception_type") %></p>
	<% } %>

	<% Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (throwable != null) { %>
	<div class="alert alert-danger text-start mt-3">
		<strong>Dettagli tecnici:</strong>
		<pre class="mb-0"><%= throwable.getMessage() %></pre>
	</div>
	<% } %>

	<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-danger mt-4">
		🔙 Torna alla Home
	</a>

</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
