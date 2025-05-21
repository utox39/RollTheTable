<%--
  Created by IntelliJ IDEA.
  User: vic_lan
  Date: 14/05/25
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.CustomerBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- TODO: add delete user button-->

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Users</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" type="text/css"
        href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
  <!-- Custom CSS -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/users.css">
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true"/>
<div id="products" class="container mt-5">
  <div class="main-header text-center">
    <h1>🎲 RollTheTable - Utenti</h1>
    <p class="text-secondary">This is what happens when you lose!</p>
  </div>

  <h2 class="text-white">Lista degli utenti</h2>
  <div class="row">
    <%
      List<CustomerBean> customers = (List<CustomerBean>) request.getAttribute("customers");

      if (customers == null || customers.isEmpty()) {
    %>
    <div class="text-danger">Errore nel caricamento degli utenti.</div>
    <%
    } else {
      for (CustomerBean customer : customers) {
    %>
    <div class="col-md-4 mb-4 d-flex">
      <div class="card w-100" style="width: 18rem;">
        <a href="${pageContext.request.contextPath}/product?productId=<%= customer.getCustomerId() %>">
          <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top" alt="">
        </a>

        <div class="card-body">
          <h5 class="card-title"><%= customer.getName() %>
          </h5>
          <p class="card-text"><%= customer.getUsername() %></p>
        </div>
      </div>
    </div>
    <%
        }
      }
    %>
  </div>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
