<%@ page import="model.bean.OrdersBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
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
        ArrayList<OrdersBean> orders = (ArrayList<OrdersBean>) session.getAttribute("cart");

        if (orders == null || orders.isEmpty()) { %>
    <h3 class="text-center">Non ci sono ordini...</h3>
    <%	} else { %>
    <div class="list-group-item py-3 px-4">
        <%for(OrdersBean order : orders) {%>
        <div class="row align-items-center">
            <div class="col-md-7 col-9">
                <h5 class="mb-1 fw-semibold">
                    Order id: <%= order.getOrderId() %> - Customer: <%= order.getCustomerId() %>
                </h5>
            </div>

            <div class="col-md-3 text-end">
                <h5 class="fw-bold">foo</h5>
                <h5 class="fw-bold">foo</h5>
            </div>
        </div>
        <br>
        <%}%>
    </div>
    <%	} %>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
