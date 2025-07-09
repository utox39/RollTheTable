<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
    <head>
        <title>Admin Panel - RollTheTable</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
    </head>
    <body>
        <jsp:include page="/views/admin/navbar.jsp" flush="true"/>
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-4 mb-4 d-flex">
                    <div class="card w-100" style="width: 18rem;">
                        <a href="#">
                            <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top" alt="">
                        </a>

                        <div class="card-body">
                            <h5 class="card-title">Modifica prodotti</h5>
                            <a href="${pageContext.request.contextPath}/views/admin/secure/products.jsp" class="btn btn-danger">Modifica prodotti</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4 mb-4 d-flex">
                    <div class="card w-100" style="width: 18rem;">
                        <a href="">
                            <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top" alt="">
                        </a>

                        <div class="card-body">
                            <h5 class="card-title">Visualizza utenti</h5>
                            <a href="${pageContext.request.contextPath}/admin/secure/users" class="btn btn-danger">Visualizza utenti</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4 mb-4 d-flex">
                    <div class="card w-100" style="width: 18rem;">
                        <a href="#">
                            <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" class="card-img-top" alt="">
                        </a>

                        <div class="card-body">
                            <h5 class="card-title">Visualizza ordini</h5>
                            <a href="${pageContext.request.contextPath}/admin/secure/orders" class="btn btn-danger">Visualizza ordini</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
