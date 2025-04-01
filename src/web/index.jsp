<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">

    <!-- Our stylesheet -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/index.css">
</head>
<body class="container mt-5">

<h1 class="text-primary text-center text-white">RollTheTable - Home</h1>

<div class="alert alert-success" role="alert">
    Bootstrap works! 🎉
</div>

<button class="btn btn-primary" id="main-button">Click me!</button>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>

<!-- Our JS -->
<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>
