<%@ page contentType="text/html;charset=UTF-8" language="java" import="model.dao.ProductDAO,model.bean.ProductBean,java.util.List" %>
<%
    List<ProductBean> newArrivals = (List<ProductBean>) request.getAttribute("latestProducts");
    List<ProductBean> specialOffers = (List<ProductBean>) request.getAttribute("discountedProducts");
    List<ProductBean> popularProducts = (List<ProductBean>) request.getAttribute("popularProducts");
    List<ProductBean> acclaimedProducts = (List<ProductBean>) request.getAttribute("acclaimedProducts");

    if (newArrivals == null) newArrivals = new java.util.ArrayList<>();
    if (specialOffers == null) specialOffers = new java.util.ArrayList<>();
    if (popularProducts == null) popularProducts = new java.util.ArrayList<>();
    if (acclaimedProducts == null) acclaimedProducts = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/homepage.css"/>
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true"/>

<main class="container my-5">

    <header class="text-center mb-5">
        <h1 class="display-4 text-danger">🎲 RollTheTable</h1>
        <p class="lead text-danger">Esplora il mondo dei giochi da tavolo!</p>
    </header>

    <!-- NUOVI ARRIVI -->
    <section class="product-carousel mb-5 p-4">
        <h2 class="section-title text-center text-danger">Nuovi Arrivi</h2>
        <div id="newArrivalsCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="4000">
            <div class="carousel-inner">

                <%
                    boolean first = true;
                    for(ProductBean product : newArrivals) {
                %>
                <div class="carousel-item <%= first ? "active" : "" %>">
                    <div class="row justify-content-center">
                        <div class="col-12 col-md-6 col-lg-4">
                            <div class="card h-100 shadow-sm">
                                <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
                                     class="card-img-top"
                                     alt="<%= product.getName() %>">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title"><%= product.getName() %></h5>
                                    <p class="card-text flex-grow-1 text-muted">
                                        <%= product.getDescription() %>
                                    </p>
                                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>" class="btn btn-danger mt-2">Scopri di più</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                        first = false;
                    }
                %>

            </div>

            <button class="carousel-control-prev" type="button"
                    data-bs-target="#newArrivalsCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button"
                    data-bs-target="#newArrivalsCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>

        </div>
    </section>

    <!-- OFFERTE SPECIALI -->
    <section class="product-grid mb-5">
        <h2 class="section-title text-center text-danger">Offerte Speciali</h2>
        <div class="row g-4">
            <%
                for(ProductBean product : specialOffers) {
            %>
            <div class="col-6 col-md-4 col-lg-2">
                <div class="card h-100 text-center">
                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top img-fluid" alt="<%= product.getName() %>">
                        <div class="card-body">
                            <h6 class="card-title mb-1"><%= product.getName() %></h6>
                            <p class="fw-bold text-danger mb-0">Sconto: <%= product.getDiscount() %>%</p>
                        </div>
                    </a>
                </div>
            </div>
            <% } %>
        </div>
    </section>

    <!-- PIÙ POPOLARI -->
    <% if (!popularProducts.isEmpty()) { %>
    <section class="product-grid mb-5">
        <h2 class="section-title text-center text-danger">Più Popolari</h2>
        <div class="row g-4">
            <%
                for(ProductBean product : popularProducts) {
            %>
            <div class="col-6 col-md-4 col-lg-2">
                <div class="card h-100 text-center">
                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top img-fluid" alt="<%= product.getName() %>">
                        <div class="card-body">
                            <h6 class="card-title mb-0"><%= product.getName() %></h6>
                        </div>
                    </a>
                </div>
            </div>
            <% } %>
        </div>
    </section>
    <% } %>

    <!-- PRODOTTI ACCLAMATI -->
    <% if (!acclaimedProducts.isEmpty()) { %>
    <section class="product-grid mb-5">
        <h2 class="section-title text-center text-danger">Prodotti Acclamati</h2>
        <div class="row g-4">
            <%
                for(ProductBean product : acclaimedProducts) {
            %>
            <div class="col-6 col-md-4 col-lg-2">
                <div class="card h-100 text-center">
                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top img-fluid" alt="<%= product.getName() %>">
                        <div class="card-body">
                            <h6 class="card-title mb-0"><%= product.getName() %></h6>
                        </div>
                    </a>
                </div>
            </div>
            <% } %>
        </div>
    </section>
    <% } %>

</main>

<hr>
<jsp:include page="/views/footer.jsp" flush="true"/>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
