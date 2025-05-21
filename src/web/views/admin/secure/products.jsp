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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/products.css">
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true"/>

<div class="container mt-5">
  <div class="main-header text-center">
    <h1>🎲 RollTheTable - Prodotti</h1>
    <p class="text-secondary">Gestione dei prodotti in catalogo</p>
  </div>

  <div class="text-end mb-3">
    <!-- TODO: Implementare link a form per aggiungere nuovo prodotto -->
    <a href="${pageContext.request.contextPath}/admin/secure/manageProducts" class="btn btn-success">➕ Aggiungi Prodotto</a>
  </div>

  <h2 class="text-white">Lista dei Prodotti</h2>
  <div class="row">
    <%1
      List<ProductBean> products = new ProductDAO().doRetriveAll();

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
            <!-- TODO: Implementare collegamento alla modifica -->
            <a href="${pageContext.request.contextPath}/admin/secure/manageProducts?op=edit&id=<%= product.getProductId() %>" class="btn btn-warning btn-sm">✏️ Modifica</a>

            <!-- TODO: Implementare eliminazione prodotto -->
            <form action="${pageContext.request.contextPath}/admin/secure/manageProducts" method="post" style="display:inline;">

              <input type="hidden" name="id" value="<%= product.getProductId() %>">
              <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Sei sicuro di voler eliminare questo prodotto?')">🗑️ Elimina</button>
            </form>
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
