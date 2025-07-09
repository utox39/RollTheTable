<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="model.bean.ProductBean,java.util.List" %>
<%
    // Retrieve products from request attributes
    List<ProductBean> newArrivals = (List<ProductBean>) request.getAttribute("latestProducts");
    List<ProductBean> specialOffers = (List<ProductBean>) request.getAttribute("discountedProducts");
    List<ProductBean> popularProducts = (List<ProductBean>) request.getAttribute("popularProducts");
    List<ProductBean> acclaimedProducts = (List<ProductBean>) request.getAttribute("acclaimedProducts");

    // Initialize empty lists if attributes are null to prevent NullPointerExceptions
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
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css"/>
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/main.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/homepage.css"/>
</head>
<body>
<jsp:include page="views/navbar.jsp" flush="true"/>

<div id="index" class="container my-5 main-content">

    <div class="main-header text-center">
        <h1>🎲 RollTheTable - Home</h1>
        <p class="text-secondary">This is what happens when you lose!</p>
    </div>

    <!-- New Arrivals Section (Carousel) -->
    <section class="product-carousel mb-5 p-4">
        <h2 class="section-title text-center">Nuovi Arrivi</h2>
        <div id="newArrivalsCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">

                <%
                    boolean first = true;
                    // Loop through newArrivals to create carousel items
                    for (ProductBean product : newArrivals) {
                %>
                <div class="carousel-item <%= first ? "active" : "" %>">
                    <div class="row justify-content-center">
                        <div class="col-12 col-md-6 col-lg-4">
                            <div class="card h-100 shadow-sm">
                                <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
                                     class="card-img-top"
                                     alt="<%= product.getName() %>">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title"><%= product.getName() %>
                                    </h5>
                                    <p class="card-text flex-grow-1 text-muted">
                                        <%= product.getDescription() %>
                                    </p>
                                    <%-- Link to product detail page --%>
                                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>"
                                       class="btn btn-danger mt-2">Scopri di più</a>
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

            <!-- Carousel Controls -->
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

    <!-- Special Offers Grid -->
    <section class="product-grid mb-5">
        <h2 class="section-title text-center">Offerte Speciali</h2>
        <div class="row g-4">
            <%
                // Loop through specialOffers to create product cards
                for (ProductBean product : specialOffers) {
            %>
            <div class="col-6 col-md-4 col-lg-2">
                <div class="card h-100 text-center">
                    <%-- Link to product detail page --%>
                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
                             class="card-img-top img-fluid" alt="<%= product.getName() %>">
                    </a>
                    <div class="card-body">
                        <h6 class="card-title mb-1"><%= product.getName() %>
                        </h6>
                        <p class="text-danger fw-bold mb-0">Sconto: <%= product.getDiscount() %>%</p>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </section>

    <!-- Popular Products Grid -->
    <% if (popularProducts != null && !popularProducts.isEmpty()) { %>
    <section class="product-grid mb-5">
        <h2 class="section-title text-center">Più Popolari</h2>
        <div class="row g-4">
            <%
                // Loop through popularProducts to create product cards
                for (ProductBean product : popularProducts) {
            %>
            <div class="col-6 col-md-4 col-lg-2">
                <div class="card h-100 text-center">
                    <%-- Link to product detail page --%>
                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
                             class="card-img-top img-fluid" alt="<%= product.getName() %>">
                    </a>
                    <div class="card-body">
                        <h6 class="card-title mb-0"><%= product.getName() %>
                        </h6>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </section>
    <% } %>

    <!-- Critically Acclaimed Products Grid -->
    <% if (acclaimedProducts != null && !acclaimedProducts.isEmpty()) { %>
    <section class="product-grid mb-5">
        <h2 class="section-title text-center">Prodotti Acclamati</h2>
        <div class="row g-4">
            <%
                // Loop through acclaimedProducts to create product cards
                for (ProductBean product : acclaimedProducts) {
            %>
            <div class="col-6 col-md-4 col-lg-2">
                <div class="card h-100 text-center">
                    <%-- Link to product detail page --%>
                    <a href="${pageContext.request.contextPath}/product?productId=<%= product.getProductId() %>">
                        <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
                             class="card-img-top img-fluid" alt="<%= product.getName() %>">
                    </a>
                    <div class="card-body">
                        <h6 class="card-title mb-0"><%= product.getName() %>
                        </h6>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </section>
    <% } %>

</div>

<hr>
<%-- Added a horizontal rule for visual separation before the footer --%>
<jsp:include page="views/footer.jsp" flush="true"/>

<!-- Bootstrap JS Bundle -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
