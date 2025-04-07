<%@ page import="model.dao.ProductDAO" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/home.css">

</head>
<body class="container mt-5">

<div class="main-header text-center">
    <h1>🎲 RollTheTable - Home</h1>
    <p class="text-secondary">Benvenuto nel mondo dei giochi da tavolo!</p>
</div>

<div class="alert alert-success text-center" role="alert">
    Bootstrap funziona! 🎉
</div>

<div class="text-center mb-4">
    <button class="btn btn-primary btn-lg" id="main-button">Click me!</button>
</div>

<h2 class="text-white">Lista di elementi</h2>
<ul>
    <%
        List<ProductBean> res = ProductDAO.getAllProducts();

        if (res == null) {
            System.err.println("Ops");
    %>
    <li class="text-danger">Errore nel caricamento dei prodotti.</li>
    <%
    } else {
        for (ProductBean product : res) {
    %>
    <li><%= product.toString() %></li>
    <%
            }
        }
    %>
</ul>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>

<!-- Custom JS -->
<script src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>
