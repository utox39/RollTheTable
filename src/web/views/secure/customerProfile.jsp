<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.CustomerBean" %>
<%@ page import="java.util.List" %>
<%@ page import="model.bean.OrdersBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.bean.ProductBean" %>
<%
    CustomerBean customer = (CustomerBean) session.getAttribute("loggedUser");
    if (customer == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/customerProfile.css" />
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true" />

<div id="customerProfile" class="container mt-5 profile-content">
    <h2 class="mb-4 text-center">Profilo Utente</h2>

    <form id="profileForm" method="post" action="${pageContext.request.contextPath}/secure/customer" novalidate>
        <input type="hidden" id="customerId" name="customerId" value="<%= customer.getCustomerId() %>" />
        <input type="hidden" id="username" name="username" value="<%= customer.getUsername() %>" />

        <!-- Name -->
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

        <!-- Surname -->
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

        <!-- Phone -->
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

    <!-- Orders Section -->
    <h2 class="mb-4 text-center">I tuoi ordini</h2>
    <% List<OrdersBean> orders = (List<OrdersBean>) request.getAttribute("ordersList"); %>
    <% if (orders == null || orders.isEmpty()) { %>
    <h3 class="text-center">Non ci sono ordini...</h3>
    <% } else { %>
    <div class="list-group-item py-3 px-4">
        <% for (OrdersBean order : orders) { %>
        <div class="row align-items-center mb-4">
            <div class="w-100">
                <h3 class="mb-1 fw-semibold">Order id: <%= order.getOrderId() %></h3>
                <h5 class="mb-1">Data ordine: <%= order.getPurchaseDate() %></h5>

                <%
                    BigDecimal total = BigDecimal.ZERO;
                %>
                <ul class="mt-2">
                    <% for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> entry : order.getProducts().entrySet()) {
                        ProductBean product = entry.getKey();
                        OrdersBean.PurchaseInfo info = entry.getValue();
                        BigDecimal productTotal = BigDecimal.valueOf(info.getPriceAtPurchase()).multiply(BigDecimal.valueOf(info.getQuantity()));
                        total = total.add(productTotal);
                    %>
                    <li>
                        <strong><%= product.getName() %></strong><br>
                        Quantit&agrave;: <%= info.getQuantity() %>,
                        Prezzo: <%= info.getPriceAtPurchase() %> &euro;
                    </li>
                    <% } %>
                </ul>

                <h5 class="mb-1">Costo spedizione: <%= order.getShippingPrice() %> &euro;</h5>
                <h5 class="mb-1">Totale: <%= total.add(BigDecimal.valueOf(order.getShippingPrice())) %> &euro;</h5>

                <!-- Invoice Download Button for this order -->
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/secure/generate-invoice?orderId=<%= order.getOrderId() %>"
                       target="_blank"
                       class="btn btn-outline-secondary btn-lg">
                        Scarica la Fattura
                    </a>
                </div>
            </div>
        </div>
        <hr>
        <% } %>
    </div>
    <% } %>
</div>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/customerProfile.js"></script>
</body>
</html>
