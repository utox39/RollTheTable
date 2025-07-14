<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
    <head>
        <title>Admin Login - RollTheTable</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/login.css">
    </head>
    <body class="bg-dark d-flex align-items-center justify-content-center min-vh-100 text-white">

        <div class="container" style="max-width: 400px;">
            <div class="bg-secondary bg-opacity-10 border border-light rounded p-4 shadow-lg">
                <div class="text-center mb-4">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" alt="Logo" class="img-fluid rounded" style="max-width: 100px;">
                    <h2 class="mt-3 text-danger">Admin - Accedi</h2>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/admin/login">
                    <div class="mb-3">
                        <label for="login" class="form-label text-white">Username o Email</label>
                        <input type="text" class="form-control bg-dark text-white border-secondary" id="login" name="login" required>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label text-white">Password</label>
                        <input type="password" class="form-control bg-dark text-white border-secondary" id="password" name="password" required>
                    </div>

                    <button type="submit" class="btn btn-danger w-100">Login</button>
                </form>

                <% if (request.getAttribute("loginError") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= (String) request.getAttribute("loginError")%>
                </div>
                <% } %>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
