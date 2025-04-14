<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>RollTheTable</title>
		<meta name="viewport" content="width=device-width, initial-scale=1" />

		<!-- Bootstrap CSS -->
		<link
			rel="stylesheet"
			type="text/css"
			href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css"
		/>
		<!-- Custom CSS -->
		<link
			rel="stylesheet"
			type="text/css"
			href="${pageContext.request.contextPath}/style/main.css"
		/>
		<link
			rel="stylesheet"
			type="text/css"
			href="${pageContext.request.contextPath}/style/homepage.css"
		/>
	</head>
	<body>
		<jsp:include page="views/navbar.jsp" flush="true" />
		<div id="index" class="container mt-5">
			<div class="main-header text-center">
				<h1>🎲 RollTheTable - Home</h1>
				<p class="text-secondary">This is what happens when you lose!</p>
			</div>
		</div>

		<!-- Bootstrap JS -->
		<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
