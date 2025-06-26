<%@ page import="model.bean.CustomerBean" %>
<%@ page import="model.bean.AdminBean" %>
<nav class="navbar bg-dark navbar-expand-lg position-relative" data-bs-theme="dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/">RollTheTable</a>
		<button
				class="navbar-toggler"
				type="button"
				data-bs-toggle="collapse"
				data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent"
				aria-expanded="false"
				aria-label="Toggle navigation"
		>
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse justify-content-between align-items-center" id="navbarSupportedContent">
			<ul class="navbar-nav mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link active" href="${pageContext.request.contextPath}/admin/secure/panel">Home</a>
				</li>
				<li class="nav-item">
					<a class="nav-link active" href="${pageContext.request.contextPath}/admin/secure/catalog">Prodotti</a>
				</li>
			</ul>

			<form class="d-flex position-absolute top-50 start-50 translate-middle" role="search" style="width: 50%;">
				<input
						class="form-control me-2"
						type="search"
						placeholder="Search"
						aria-label="Search"
				/>
				<button class="btn btn-outline-success" type="submit">Search</button>
			</form>

			<ul class="navbar-nav mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link active" href="${pageContext.request.contextPath}/cart?op=get">Carrello</a>
				</li>
				<%
					CustomerBean customer = (CustomerBean) session.getAttribute("loggedUser");
					AdminBean admin = (AdminBean) session.getAttribute("loggedAdmin");
					if (customer != null)  {
				%>
						<li class="nav-item">
							<a class="nav-link active" href="#">Ciao, <%= customer.getUsername()%>!</a>
						</li>
				<% 	} else if (admin != null) { %>
						<li class="nav-item">
							<a class="nav-link active" href="#">Benvenuto, <%= admin.getUsername()%>!</a>
						</li>
				<% 	} else { %>
						<li class="nav-item">
							<a class="nav-link active" href="${pageContext.request.contextPath}/login">Login</a>
						</li>
				<% 	} %>
			</ul>
		</div>
	</div>
</nav>
