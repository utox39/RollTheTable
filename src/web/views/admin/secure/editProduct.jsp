<%@ page import="model.bean.ProductBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    if (product == null) {
%>
<div class="alert alert-danger">Prodotto non trovato.</div>
<%
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Modifica Prodotto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/products.css">
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true"/>

<div class="container mt-5" id="product">
    <div class="main-header text-center">
        <h1>✏️ Modifica Prodotto</h1>
        <p class="text-secondary">Aggiorna le informazioni del prodotto</p>
    </div>

    <!-- Cambiato POST -> GET e aggiunto op=update nell'action -->
    <form action="${pageContext.request.contextPath}/admin/secure/manageProducts" method="get" class="mt-4">
        <input type="hidden" name="op" value="update">
        <input type="hidden" name="product_id" value="<%= product.getProductId() %>">

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="name" class="form-label">Nome Prodotto</label>
                <input type="text" class="form-control" id="name" name="name" value="<%= product.getName() %>" required>
            </div>
            <div class="col-md-6">
                <label for="vendor" class="form-label">Venditore</label>
                <input type="text" class="form-control" id="vendor" name="vendor" value="<%= product.getVendor() %>" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-4">
                <label for="purchase_price" class="form-label">Prezzo Acquisto</label>
                <input type="number" step="0.01" class="form-control" id="purchase_price" name="purchase_price" value="<%= product.getPurchasePrice() %>" required>
            </div>
            <div class="col-md-4">
                <label for="sale_price" class="form-label">Prezzo Vendita</label>
                <input type="number" step="0.01" class="form-control" id="sale_price" name="sale_price" value="<%= product.getSalePrice() %>" required>
            </div>
            <div class="col-md-4">
                <label for="iva" class="form-label">IVA (%)</label>
                <input type="number" step="0.01" class="form-control" id="iva" name="iva" value="<%= product.getIva() %>" required>
            </div>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Descrizione</label>
            <textarea class="form-control" id="description" name="description" rows="3" required><%= product.getDescription() %></textarea>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="stock_quantity" class="form-label">Quantità in Magazzino</label>
                <input type="number" class="form-control" id="stock_quantity" name="stock_quantity" value="<%= product.getStockQuantity() %>" required>
            </div>
            <div class="col-md-6">
                <label for="discount" class="form-label">Sconto (%)</label>
                <input type="number" step="0.01" class="form-control" id="discount" name="discount" value="<%= product.getDiscount() %>">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="category" class="form-label">Categoria</label>
                <input type="text" class="form-control" id="category" name="category" value="<%= product.getCategory() %>" required>
            </div>
            <div class="col-md-6">
                <label for="minimum_age" class="form-label">Età Minima</label>
                <input type="number" class="form-control" id="minimum_age" name="minimum_age" value="<%= product.getMinimumAge() %>">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="barcode" class="form-label">Codice a Barre</label>
                <input type="text" class="form-control" id="barcode" name="barcode" value="<%= product.getBarcode() %>" required>
            </div>
            <div class="col-md-6">
                <label for="sale_status" class="form-label">Stato Vendita</label>
                <select class="form-select" id="sale_status" name="sale_status" required>
                    <option value="ON_SALE" <%= "ON_SALE".equals(product.getSalesStatus().toString()) ? "selected" : "" %>>Disponibile</option>
                    <option value="OUT_OF_STOCK" <%= "OUT_OF_STOCK".equals(product.getSalesStatus().toString()) ? "selected" : "" %>>Esaurito</option>
                    <option value="NOT_AVAILABLE" <%= "NOT_AVAILABLE".equals(product.getSalesStatus().toString()) ? "selected" : "" %>>Non Disponibile</option>
                    <option value="NO_LONGER_ON_SALE" <%= "NO_LONGER_ON_SALE".equals(product.getSalesStatus().toString()) ? "selected" : "" %>>Non più in Vendita</option>
                </select>
            </div>
        </div>

        <div class="mb-3">
            <label for="images_path" class="form-label">Percorso Immagine</label>
            <input type="text" class="form-control" id="images_path" name="images_path" value="<%= product.getImagesPath() %>" required>
        </div>

        <div class="text-end">
            <button type="submit" class="btn btn-primary">💾 Salva Modifiche</button>
            <a href="${pageContext.request.contextPath}/admin/secure/manageProducts" class="btn btn-secondary">↩️ Annulla</a>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
