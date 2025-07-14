<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/product.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">

</head>
<body>
<jsp:include page="../views/navbar.jsp" flush="true"/>
<div class="container mt-5 main-content" id="product">
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
            <% if (productBean.getDiscount() > 0) { %>
            <h3 class="mt-3 d-flex align-items-center">
                <span class="badge bg-danger me-2 small rounded-pill">
                    - <%= productBean.getDiscount() %> &percnt;
                </span>

                <%
                    BigDecimal salePrice = BigDecimal.valueOf(productBean.getSalePrice());
                    BigDecimal discountPercentage = BigDecimal.valueOf(productBean.getDiscount())
                            .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                    BigDecimal discountValue = salePrice.multiply(discountPercentage);
                    BigDecimal discountedPrice = salePrice.subtract(discountValue).setScale(2, RoundingMode.HALF_UP);
                %>

                <span class="fw-bold me-2">
                    <%= discountedPrice %> &euro;
                </span>

                <span class="text-decoration-line-through">
                    <%= productBean.getSalePrice() %> &euro;
                </span>
            </h3>
            <% } else { %>
            <h3 class="mt-3 fw-bold"><%= productBean.getSalePrice() %> &euro;</h3>
            <% } %>

            <br>
            <button type="button" id="addToCart" class="btn btn-primary" onclick="addToTheCart(<%= productBean.getProductId() %>)">
                Aggiungi al carrello
            </button>
        </div>
    </div>
    <% } else { %>
    <div class="text-danger">Errore nel caricamento del prodotto.</div>
    <% } %>
</div>

<hr>
<jsp:include page="../views/footer.jsp" flush="true"/>

<script src="${pageContext.request.contextPath}/js/cartOperations.js"></script>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
