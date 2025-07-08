<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/catalog.css">

</head>
<body>
<jsp:include page="../views/navbar.jsp" flush="true"/>
<div id="products" class="container mt-5 main-content">
    <div class="main-header text-center">
        <h1>🎲 RollTheTable - Prodotti</h1>
        <p class="text-secondary">This is what happens when you lose!</p>
    </div>

    <h2 class="text-white">Catalogo dei prodotti</h2>
    <div class="row">
        <%
            List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");

            if (products == null || products.isEmpty()) {
        %>
        <div class="text-danger">Errore nel caricamento dei prodotti.</div>
        <%
        } else {
            for (ProductBean product : products) {
        %>
        <div class="col-md-4 mb-4 d-flex">
            <div class="card w-100" style="width: 18rem;">
                <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top" alt="">
                </a>

                <div class="card-body">
                    <h5 class="card-title"><%= product.getName() %>
                    </h5>
                    <p class="card-text">
                        <% if (product.getDiscount() > 0) { %>
                        <span class="badge bg-danger me-2 small rounded-pill">
                            - <%= product.getDiscount() %> &percnt;
                        </span>

                        <%
                            BigDecimal salePrice = BigDecimal.valueOf(product.getSalePrice());
                            BigDecimal discountPercentage = BigDecimal.valueOf(product.getDiscount())
                                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                            BigDecimal discountValue = salePrice.multiply(discountPercentage);
                            BigDecimal discountedPrice = salePrice.subtract(discountValue).setScale(2, RoundingMode.HALF_UP);
                        %>

                        <span class="fw-bold me-2">
                            <%= discountedPrice %> &euro;
                        </span>

                        <span class="text-decoration-line-through">
                            <%= product.getSalePrice() %> &euro;
                        </span>

                        <% } else { %>
                        <%= product.getSalePrice() %> &euro;
                        <% } %>
                    </p>
<%--                    <a href="${pageContext.request.contextPath}/cart?op=add&productId=<%= product.getProductId() %>" class="btn btn-primary" id="addToCart">Add to cart</a>--%>
                    <button type="button" id="addToCart" class="btn btn-primary" onclick="addToTheCart(<%= product.getProductId() %>)">
                        Aggiungi al carrello
                    </button>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>

<hr>
<jsp:include page="../views/footer.jsp" flush="true"/>

<script src="${pageContext.request.contextPath}/js/cartOperations.js"></script>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
