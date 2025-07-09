<%@ page import="model.bean.OrdersBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Admin - Orders - RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<jsp:include page="/views/admin/navbar.jsp" flush="true" />

<div id="cart" class="container mt-5">
    <%
        ArrayList<OrdersBean> orders = (ArrayList<OrdersBean>) request.getAttribute("orders");

        if ((orders == null) || orders.isEmpty()) {
    %>
    <h3 class="text-center">Non ci sono ordini...</h3>
    <%	} else { %>

    <div class="container mb-4">
        <form class="row g-3" method="get" action="${pageContext.request.contextPath}/admin/secure/orders">
            <div class="col-md-4">
                <label for="startDate" class="form-label">Data Inizio</label>
                <input type="date" class="form-control" id="startDate" name="startDate"
                       value="<%= request.getParameter("startDate") != null ? request.getParameter("startDate") : "" %>">
            </div>
            <div class="col-md-4">
                <label for="endDate" class="form-label">Data Fine</label>
                <input type="date" class="form-control" id="endDate" name="endDate"
                       value="<%= request.getParameter("endDate") != null ? request.getParameter("endDate") : "" %>">
            </div>
            <div class="col-md-4 align-self-end">
                <button type="submit" class="btn btn-primary">Filtra per date</button>
            </div>
        </form>
    </div>

    <div class="container mb-4">
        <form class="row g-3" method="get" action="${pageContext.request.contextPath}/admin/secure/orders">
            <div class="col-md-4">
                <label for="customerId" class="form-label">ID Cliente</label>
                <input type="number" class="form-control" id="customerId" name="customerId">
            </div>
            <div class="col-md-4 align-self-end">
                <button type="submit" class="btn btn-primary">Filtra per cliente</button>
            </div>
        </form>
    </div>

    <div class="list-group-item py-3 px-4">
        <% for(OrdersBean order : orders) { %>
        <div class="row align-items-center">
            <div class="col-md-7 col-9">
                <h3 class="mb-1 fw-semibold">
                    Order id: <%= order.getOrderId() %> - Customer: <%= order.getCustomerId() %>
                </h3>
                <h5 class="mb-1">
                    Data ordine: <%= order.getPurchaseDate() %>
                </h5>

                <%
                    BigDecimal total = BigDecimal.valueOf(0.0);
                %>
                <ul class="mt-2">
                    <% for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> productInfo : order.getProducts().entrySet()) { %>
                    <li>
                        <strong><%= productInfo.getKey().getName() %></strong>, Quantit&agrave;: <%= productInfo.getValue().getQuantity() %>,
                        Prezzo: <%= productInfo.getValue().getPriceAtPurchase() %> &euro;
                    </li>
                    <%
                        total = total.add(BigDecimal.valueOf(productInfo.getValue().getPriceAtPurchase()));
                    %>
                    <% } %>
                </ul>

                <h5 class="mb-1">
                    Costo spedizione: <%= order.getShippingPrice() %> &euro;
                </h5>
                <br>
                <h5 class="mb-1">
                    Totale: <%= total.add(BigDecimal.valueOf(order.getShippingPrice())) %> &euro;
                </h5>
            </div>
        </div>
        <hr>
        <% } %>
    </div>
    <%	} %>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
