<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.OrdersBean" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    OrdersBean ordersBean = (OrdersBean) session.getAttribute("orderDetails");
    DecimalFormat df = new DecimalFormat("0.00");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    double subtotal = 0;
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>RollTheTable - Riepilogo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<main class="container py-5 main-content">
    <div class="text-center mb-5">
        <h1 class="display-5 fw-bold">Grazie per il tuo acquisto!</h1>
        <p class="lead">Ecco il riepilogo del tuo ordine.</p>
    </div>

    <div class="card shadow-sm mb-4">
        <div class="card-header bg-danger text-white">
            <h5 class="mb-0 bg-danger">Dettagli Ordine</h5>
        </div>
        <div class="card-body">
            <table class="table table-bordered align-middle">
                <thead class="table-light">
                <tr>
                    <th>Prodotto</th>
                    <th>Quantità</th>
                    <th>Prezzo Unitario</th>
                    <th>Totale</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (ordersBean != null && ordersBean.getProducts() != null) {
                        for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> entry : ordersBean.getProducts().entrySet()) {
                            ProductBean product = entry.getKey();
                            OrdersBean.PurchaseInfo info = entry.getValue();
                            int qty = info.getQuantity();
                            double unitPrice = info.getPriceAtPurchase();
                            double totalPrice = qty * unitPrice;
                            subtotal += totalPrice;
                %>
                <tr>
                    <td><%= product.getName() %></td>
                    <td><%= qty %></td>
                    <td>€ <%= df.format(unitPrice) %></td>
                    <td>€ <%= df.format(totalPrice) %></td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3" class="text-end fw-bold">Subtotale:</td>
                    <td>€ <%= df.format(subtotal) %></td>
                </tr>
                <tr>
                    <td colspan="3" class="text-end fw-bold">Spedizione:</td>
                    <td>€ <%= df.format(ordersBean.getShippingPrice()) %></td>
                </tr>
                <tr class="text-white">
                    <td colspan="3" class="text-end fw-bold ">Totale Ordine:</td>
                    <td class="fw-bold">€ <%= df.format(subtotal + ordersBean.getShippingPrice()) %></td>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>

    <!-- Order Info -->
    <div class="row mb-4">
        <div class="col-md-6">
            <p><strong>ID Ordine:</strong> <%= ordersBean.getOrderId() %></p>
            <p><strong>Data Acquisto:</strong> <%= sdf.format(ordersBean.getPurchaseDate()) %></p>
        </div>
    </div>

    <!-- Invoice Download -->
    <div class="text-center mb-4">
        <a href="${pageContext.request.contextPath}/secure/generate-invoice" target="_blank"
           class="btn btn-outline-secondary btn-lg">
            Scarica la Fattura
        </a>
    </div>

    <!-- Back Home -->
    <div class="text-center">
        <a href="${pageContext.request.contextPath}/home" class="btn btn-danger btn-primary ms-2">Torna alla Home</a>
    </div>
</main>

<hr>
<jsp:include page="../../views/footer.jsp" flush="true"/>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
