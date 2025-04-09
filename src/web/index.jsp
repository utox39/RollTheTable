<%@ page import="model.dao.ProductDAO" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/home.css">

</head>
    <body class="container mt-5">
        <div class="main-header text-center">
            <h1>🎲 RollTheTable - Home</h1>
            <p class="text-secondary">This is what happens when you lose!</p>
        </div>

        <h2 class="text-white">Catalogo dei prodotti</h2>
        <div class="row">
            <%
                List<ProductBean> res = ProductDAO.getAllProducts();

                if (res == null) {
            %>
                    <div class="text-danger">Errore nel caricamento dei prodotti.</div>
            <%
            } else {
                for (ProductBean product : res) {
            %>
                <div class="col-md-4 mb-4 d-flex">
                    <div class="card w-100" style="width: 18rem;">
                        <img src="./assets/images/logo.JPG" class="card-img-top" alt="">
                        <div class="card-body">
                            <h5 class="card-title"><%= product.getName() %></h5>
                            <p class="card-text"><%= product.getSalePrice() %> &euro;</p>
                            <a href="#" class="btn btn-primary">Add to cart</a>
                        </div>
                    </div>
                </div>
            <%
                }
            }
            %>
        </div>

        <!-- Bootstrap JS -->
        <script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>

        <!-- Custom JS -->
<%--        <script src="${pageContext.request.contextPath}/js/index.js"></script>--%>
    </body>
</html>
