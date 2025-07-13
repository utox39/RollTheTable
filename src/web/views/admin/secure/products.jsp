<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="model.dao.ProductDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Prodotti</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/adminProducts.css">
</head>
<body>
<jsp:include page="/views/admin/navbar.jsp" flush="true"/>

<div id="products" class="container mt-5">
  <div class="main-header text-center">
    <h1>🎲 RollTheTable - Prodotti</h1>
    <p class="text-secondary">Gestione dei prodotti in catalogo</p>
  </div>

  <div class="text-end mb-3">
    <a href="${pageContext.request.contextPath}/admin/secure/manageProducts?op=showAddForm" class="btn btn-success">➕ Aggiungi Prodotto</a>
  </div>

  <h2 class="text-white">Lista dei Prodotti</h2>
  <div class="row">
    <%
      List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");
      if (products == null || products.isEmpty()) {
    %>
    <div class="text-danger">Nessun prodotto disponibile.</div>
    <%
    } else {
      for (ProductBean product : products) {
    %>
    <div class="col-md-4 mb-4 d-flex">
      <div class="card w-100">
        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
             class="card-img-top" alt="">

        <div class="card-body">
          <h5 class="card-title"><%= product.getName() %></h5>
          <p class="card-text">Prezzo: €<%= product.getSalePrice() %></p>
          <p class="card-text">Descrizione: <%= product.getDescription() %></p>

          <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/admin/secure/manageProducts?op=edit&id=<%= product.getProductId() %>" class="btn btn-warning btn-sm">✏️ Modifica</a>

            <a href="${pageContext.request.contextPath}/admin/secure/manageProducts?op=del&id=<%= product.getProductId() %>" onclick="return confirm('vuoi davvero cancellare questo prodotto?');" class="btn btn-danger btn-sm">🗑️ Elimina</a>
          </div>
        </div>
      </div>
    </div>
    <%
        }
      }
    %>
  </div>
</div>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
