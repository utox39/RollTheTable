<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.CustomerBean" %>
<%
  CustomerBean customer = (CustomerBean) session.getAttribute("loggedUser");
%>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8" />
  <title>Profilo Utente</title>

  <!-- Bootstrap -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/web/bootstrap-5.3.3-dist/css/bootstrap.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/web/style/main.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/web/style/customerProfile.css" />
</head>
<body>
<jsp:include page="/web/views/navbar.jsp" />

<div class="container mt-5 main-content">
  <h2 class="mb-4 text-center">Profilo Utente</h2>

  <form id="profileForm" method="post" action="UpdateCustomerServlet" novalidate>

    <!-- Nome -->
    <div class="mb-4">
      <label for="name" class="form-label">Nome</label>
      <div class="d-flex justify-content-between align-items-center gap-2 flex-wrap">
        <div class="flex-grow-1 position-relative">
          <span id="nameText" class="form-value d-block"><%= customer.getName() %></span>
          <input type="text" class="form-control position-absolute top-0 start-0 w-100 d-none" id="name" name="name" value="<%= customer.getName() %>" />
        </div>
        <button type="button" class="btn btn-sm btn-outline-danger" onclick="enableField('name')">Modifica</button>
      </div>
    </div>

    <!-- Cognome -->
    <div class="mb-4">
      <label for="surname" class="form-label">Cognome</label>
      <div class="d-flex justify-content-between align-items-center gap-2 flex-wrap">
        <div class="flex-grow-1 position-relative">
          <span id="surnameText" class="form-value d-block"><%= customer.getSurname() %></span>
          <input type="text" class="form-control position-absolute top-0 start-0 w-100 d-none" id="surname" name="surname" value="<%= customer.getSurname() %>" />
        </div>
        <button type="button" class="btn btn-sm btn-outline-danger" onclick="enableField('surname')">Modifica</button>
      </div>
    </div>

    <!-- Email -->
    <div class="mb-4">
      <label for="email" class="form-label">Email</label>
      <div class="d-flex justify-content-between align-items-center gap-2 flex-wrap">
        <div class="flex-grow-1 position-relative">
          <span id="emailText" class="form-value d-block"><%= customer.getEmail() %></span>
          <input type="email" class="form-control position-absolute top-0 start-0 w-100 d-none" id="email" name="email" value="<%= customer.getEmail() %>" />
        </div>
        <button type="button" class="btn btn-sm btn-outline-danger" onclick="enableField('email')">Modifica</button>
      </div>
      <div id="emailError" class="alert alert-danger d-none mt-2 py-1 px-2" role="alert">
        Inserisci un'email valida.
      </div>
    </div>

    <!-- Telefono -->
    <div class="mb-4">
      <label for="phone" class="form-label">Telefono</label>
      <div class="d-flex justify-content-between align-items-center gap-2 flex-wrap">
        <div class="flex-grow-1 position-relative">
          <span id="phoneText" class="form-value d-block"><%= customer.getPhone() %></span>
          <input type="text"
                 class="form-control position-absolute top-0 start-0 w-100 d-none"
                 id="phone"
                 name="phone"
                 value="<%= customer.getPhone() %>"
                 inputmode="numeric"
                 oninput="this.value = this.value.replace(/[^0-9]/g, '')" />
        </div>
        <button type="button" class="btn btn-sm btn-outline-danger" onclick="enableField('phone')">Modifica</button>
      </div>
      <div id="phoneError" class="alert alert-danger d-none mt-2 py-1 px-2" role="alert">
        Il numero di telefono deve contenere solo cifre (8-15 cifre).
      </div>
    </div>

    <button type="submit" class="btn btn-danger mt-4 d-none" id="saveBtn">Salva Modifiche</button>
  </form>
</div>

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/web/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/web/js/customerProfile.js"></script>
</body>
</html>
