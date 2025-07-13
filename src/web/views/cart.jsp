<%@ page import="model.bean.CartBean" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/cart.css">
	</head>
	<body>
		<jsp:include page="navbar.jsp" flush="true" />

		<div id="cart" class="container mt-5 main-content">
		<%
			CartBean cart = (CartBean) session.getAttribute("cart");

			if (cart == null || cart.getCart().isEmpty()) { %>
				<h3 class="text-center">Il carrello è vuoto...</h3>
		<%	} else { %>
				<div class="d-flex justify-content-between align-items-center mb-3">
					<h2 class="text-white m-0">Carrello</h2>
					<a href="${pageContext.request.contextPath}/secure/checkout" class="btn btn-success">
						Acquista ora
					</a>
				</div>
				<div class="list-group-item py-3 px-4">
					<%
						List<Map.Entry<ProductBean, Integer>> sortedEntries = new ArrayList<>(cart.getCart().entrySet());
						sortedEntries.sort(Comparator.comparing(e -> e.getKey().getName(), String.CASE_INSENSITIVE_ORDER));
					%>
					<% for (Map.Entry<ProductBean, Integer>productEntry : sortedEntries) {%>
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
								<button type="button" class="remove-from-cart btn btn-link text-decoration-none me-3 text-danger p-0" onclick="removeFromTheCart(<%= productEntry.getKey().getProductId() %>)">
									Rimuovi
								</button>
							</div>
						</div>

						<div class="col-md-3 text-end">
							<h5>
								<% if (productEntry.getKey().getDiscount() > 0) { %>
								<span class="badge bg-danger me-2 small rounded-pill">
                   					 - <%= productEntry.getKey().getDiscount() %> &percnt;
                				</span>

								<%
									BigDecimal salePrice = BigDecimal.valueOf(productEntry.getKey().getSalePrice());
									BigDecimal discountPercentage = BigDecimal.valueOf(productEntry.getKey().getDiscount())
											.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
									BigDecimal discountValue = salePrice.multiply(discountPercentage);
									BigDecimal discountedPrice = salePrice.subtract(discountValue).setScale(2, RoundingMode.HALF_UP);
								%>

								<span class="fw-bold me-2">
                    				<%= discountedPrice %> &euro;
                				</span>

								<span class="text-decoration-line-through">
                    				<%= productEntry.getKey().getSalePrice() %> &euro;
                				</span>
								<% } else { %>
								<span class="fw-bold">
									<%= productEntry.getKey().getSalePrice() %> &euro;
								</span>
								<% } %>
							</h5>
							<div class="d-flex align-items-center justify-content-end">
								<button type="button"
										class="btn btn-outline-secondary btn-sm me-2"
										onclick="decreaseQuantity(<%= productEntry.getKey().getProductId() %>)">
									&minus;
								</button>

								<h5 class="fw-bold"><%= productEntry.getValue() %></h5>

								<button type="button"
										class="btn btn-outline-secondary btn-sm ms-2"
										onclick="increaseQuantity(<%= productEntry.getKey().getProductId() %>)">
									&plus;
								</button>
							</div>
						</div>
					</div>
					<br>
					<%}%>
				</div>
		<%	} %>
		</div>

		<hr>
		<jsp:include page="../views/footer.jsp" flush="true"/>

		<script src="${pageContext.request.contextPath}/js/cartOperations.js"></script>
		<!-- Bootstrap JS -->
		<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
