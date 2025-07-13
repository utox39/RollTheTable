<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Registrazione - RollTheTable</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/register.css">
</head>
<body class="bg-dark d-flex align-items-center justify-content-center min-vh-100 text-white">

<div class="container" style="max-width: 500px;">
    <div class="bg-secondary bg-opacity-10 border border-light rounded p-4 shadow-lg">
        <div class="text-center mb-4">
            <img src="${pageContext.request.contextPath}/assets/images/logo.JPG" alt="Logo" class="img-fluid rounded" style="max-width: 100px;">
            <h2 class="mt-3 text-danger">Registrati</h2>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="mb-3">
                <label for="name" class="form-label text-white">Nome</label>
                <input type="text" class="form-control bg-dark text-white border-secondary" id="name" name="name" required>
            </div>

            <div class="mb-3">
                <label for="surname" class="form-label text-white">Cognome</label>
                <input type="text" class="form-control bg-dark text-white border-secondary" id="surname" name="surname" required>
            </div>

            <div class="mb-3">
                <label for="username" class="form-label text-white">Username</label>
                <input type="text" class="form-control bg-dark text-white border-secondary" id="username" name="username" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label text-white">Email</label>
                <input type="email" class="form-control bg-dark text-white border-secondary" id="email" name="email" required>
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label text-white">Phone</label>
                <input type="tel" class="form-control bg-dark text-white border-secondary" id="phone" name="phone" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label text-white">Password</label>
                <input type="password" class="form-control bg-dark text-white border-secondary" id="password" name="password" required>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label text-white">Conferma Password</label>
                <input type="password" class="form-control bg-dark text-white border-secondary" id="confirmPassword" name="confirmPassword" required>
            </div>

            <button type="submit" id="register-button" class="btn btn-danger w-100">Registrati</button>
        </form>

        <% if (request.getAttribute("registrationError") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= (String) request.getAttribute("registrationError")%>
            </div>
        <% } %>

        <p class="mt-3 text-center text-light">
            Hai già un account? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none text-warning">Accedi</a>
        </p>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/checkRegistration.js"></script>
<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
