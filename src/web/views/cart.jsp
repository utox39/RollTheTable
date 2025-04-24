<%@ page import="model.bean.CartBean" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>RollTheTable - Cart</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
		<!-- Custom CSS -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
	</head>
	<body>
		<jsp:include page="navbar.jsp" flush="true" />

		<div id="cart" class="container mt-5">
		<%
			CartBean cart = (CartBean) session.getAttribute("cart");

			if (cart == null || cart.getCart().isEmpty()) { %>
				<h3 class="text-center">Il carrello è vuoto...</h3>
		<%	} else { %>
				<div class="list-group-item py-3 px-4">
					<% for (Map.Entry<ProductBean, Integer>productEntry : cart.getCart().entrySet()) {%>
					<div class="row align-items-center">
						<!-- Product's image -->
						<div class="col-md-2 col-3">
							<img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
								 class="img-fluid rounded border"
								 style="width: 100%; height: auto; object-fit: cover;" alt="Prodotto">
						</div>

						<div class="col-md-7 col-9">
							<h5 class="mb-1 fw-semibold"><%= productEntry.getKey().getName() %></h5>
							<div class="mt-2">
								<a href="${pageContext.request.contextPath}/cart?op=remove&productId=<%= productEntry.getKey().getProductId() %>"
								   class="text-decoration-none me-3 text-danger">Rimuovi</a>
							</div>
						</div>

						<div class="col-md-3 text-end">
							<h5 class="fw-bold"><%= productEntry.getKey().getSalePrice() %> &euro;</h5>
							<h5 class="fw-bold">Quantit&agrave;: <%= productEntry.getValue() %></h5>
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
