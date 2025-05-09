<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.ProductBean" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">

</head>
<body>
<jsp:include page="../views/navbar.jsp" flush="true"/>
<div class="container mt-5" id="product">
    <% ProductBean productBean = (ProductBean) request.getAttribute("product");%>

    <% if (productBean != null) {%>
    <div class="row g-5">
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="img-fluid rounded"
                 alt="<%= productBean.getName() %>">
        </div>

        <div class="col-md-6">
            <h1 class="display-5"><%= productBean.getName() %>
            </h1>
            <p class="lead"><%= productBean.getDescription() %>
            </p>
            <h3 class="text-success mt-3"><%= productBean.getSalePrice() %> &euro;</h3>
            <a href="${pageContext.request.contextPath}/cart?op=add&productId=<%= productBean.getProductId() %>"
               class="btn btn-primary">Add to cart</a>
        </div>
    </div>
    <% } else { %>
    <div class="text-danger">Errore nel caricamento del prodotto.</div>
    <% } %>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
