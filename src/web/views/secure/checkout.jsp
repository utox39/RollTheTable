<%--suppress t --%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RollTheTable-Purchase</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true"/>

<div id="checkout" class="container mt-5">
    <div class="main-header text-center">
        <h1>Che il divertimento abbia inizio!</h1>
    </div>

    <%--!    SHIPPING ADDRESS    --%>
    <% AddressBean selectedAddress = null; %>
    <% List<AddressBean> addressBeanList = (List<AddressBean>) session.getAttribute("addresses"); %>
    <% if (addressBeanList == null || addressBeanList.isEmpty()) {%>
        <div class="alert alert-warning">
            Nessun indirizzo di spedizione disponibile.
            <button class="btn btn-sm btn-primary ms-2" id="showShippingAddressFormBtn" type="button">
                Aggiungi un indirizzo di spedizione
            </button>
        </div>
    <% } else {
        int firstShippingAddressIndex = 0;

        while((firstShippingAddressIndex < addressBeanList.size()) && (!addressBeanList.get(firstShippingAddressIndex).getType().equals("shipping"))) {
           firstShippingAddressIndex++;
        }

        selectedAddress = addressBeanList.get(firstShippingAddressIndex); // Show the first one
    %>
    <div class="mb-4">
        <label class="form-label text-white fw-semibold">
            Seleziona l'indirizzo di spedizione:
            <button class="btn btn-danger btn-primary ms-2" id="showShippingAddressFormBtn" type="button">
                Aggiungi un indirizzo di spedizione
            </button>
        </label>
        <div class="dropdown">
            <button
                    class="btn btn-secondary dropdown-toggle w-100 text-start"
                    type="button"
                    id="shippingAddressDropdown"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <%= selectedAddress.getName() %> <%= selectedAddress.getSurname() %>,
                <%= selectedAddress.getStreetName() %> <%= selectedAddress.getStreetNumber() %>,
                <%= selectedAddress.getPostcode() %> <%= selectedAddress.getCity() %> (<%= selectedAddress.getProvince() %>),
                <%= selectedAddress.getCountry() %>
            </button>
            <ul class="dropdown-menu dropdown-menu-dark w-100" aria-labelledby="addressDropdown">
                <% for (AddressBean address : addressBeanList) {
                        if (address.getType().equals("shipping")) {
                %>
                        <li>
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('addressDropdown').innerHTML='<% AddressBean selectedShippingAddress = address; %> <%= selectedAddress.getName() %> <%= selectedAddress.getSurname() %>, <%= address.getStreetName() %> <%= address.getStreetNumber() %>, <%= address.getPostcode() %> <%= address.getCity() %> (<%= address.getProvince() %>), <%= address.getCountry() %>';">
                                <div>
                                    <strong><%= address.getType() != null ? address.getType() : "Indirizzo" %></strong><br>
                                    <%= address.getName() %> <%= address.getSurname() %><br>
                                    <%= address.getStreetName() %> <%= address.getStreetNumber() %><br>
                                    <%= address.getPostcode() %> <%= address.getCity() %> (<%= address.getProvince() %>)<br>
                                    <%= address.getCountry() %>
                                </div>
                            </a>
                        </li>
                <%  }
                } %>
            </ul>
        </div>
        <br>

        <%--!    BILLING ADDRESS    --%>
        <%
            int firstBillingAddressIndex = 0;

            while((firstBillingAddressIndex < addressBeanList.size()) && (!addressBeanList.get(firstBillingAddressIndex).getType().equals("billing"))) {
                firstBillingAddressIndex++;
            }

            if (firstBillingAddressIndex < addressBeanList.size()) {
                selectedAddress = addressBeanList.get(firstBillingAddressIndex); // Show the first one
            }
        %>

        <%
            if (firstBillingAddressIndex == addressBeanList.size()) {
        %>
        <div class="alert alert-warning">
            Nessun indirizzo di fatturazione disponibile.
            <button class="btn btn-sm btn-primary ms-2" id="showBillingAddressFormBtn" type="button">
                Aggiungi un indirizzo di fatturazione
            </button>
        </div>
        <%  } else {%>
        <label class="form-label text-white fw-semibold">
            Seleziona l'indirizzo di fatturazione:
            <button class="btn btn-danger btn-primary ms-2" id="showBillingAddressFormBtn" type="button">
                Aggiungi un indirizzo di fatturazione
            </button>
        </label>
        <div class="dropdown">
            <button
                    class="btn btn-secondary dropdown-toggle w-100 text-start"
                    type="button"
                    id="billingAddressDropdown"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <%= selectedAddress.getName() %> <%= selectedAddress.getSurname() %>,
                <%= selectedAddress.getStreetName() %> <%= selectedAddress.getStreetNumber() %>,
                <%= selectedAddress.getPostcode() %> <%= selectedAddress.getCity() %> (<%= selectedAddress.getProvince() %>),
                <%= selectedAddress.getCountry() %>
            </button>
            <ul class="dropdown-menu dropdown-menu-dark w-100" aria-labelledby="addressDropdown">
                <% for (AddressBean address : addressBeanList) {
                    if (address.getType().equals("billing")) {
                %>
                <li>
                    <a class="dropdown-item" href="#"
                       onclick="document.getElementById('addressDropdown').innerHTML='<% AddressBean selectedBillingAddress = address; %> <%= selectedAddress.getName() %> <%= selectedAddress.getSurname() %>, <%= address.getStreetName() %> <%= address.getStreetNumber() %>, <%= address.getPostcode() %> <%= address.getCity() %> (<%= address.getProvince() %>), <%= address.getCountry() %>';">
                        <div>
                            <strong><%= address.getType() != null ? address.getType() : "Indirizzo" %></strong><br>
                            <%= address.getName() %> <%= address.getSurname() %><br>
                            <%= address.getStreetName() %> <%= address.getStreetNumber() %><br>
                            <%= address.getPostcode() %> <%= address.getCity() %> (<%= address.getProvince() %>)<br>
                            <%= address.getCountry() %>
                        </div>
                    </a>
                </li>
                <%  }
                } %>
            </ul>
        </div>
        <% }%>
    </div>
    <% } %>

    <%--! Hidden shipping address form --%>
    <div id="shippingAddressFormContainer" class="card mt-3 bg-dark text-white" style="display: none;">
        <div class="card-body">
            <h5 class="card-title">Nuovo indirizzo</h5>
            <form method="post" action="${pageContext.request.contextPath}/secure/customer/address" id="submit-shipping-address">
                <input type="hidden" id="address-type" name="address-type" value="shipping">

                <br>

                <div class="mb-3">
                    <label for="name" class="form-label">Nome</label>
                    <input type="text" name="name" id="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="surname" class="form-label">Cognome</label>
                    <input type="text" name="surname" id="surname" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="street-name" class="form-label">Via</label>
                    <input type="text" name="street-name" id="street-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="street-number" class="form-label">Numero Civico</label>
                    <input type="text" name="street-number" id="street-number" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="postcode" class="form-label">CAP</label>
                    <input type="text" name="postcode" id="postcode" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="city" class="form-label">Città</label>
                    <input type="text" name="city" id="city" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="province" class="form-label">Provincia</label>
                    <input type="text" name="province" id="province" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="country" class="form-label">Paese</label>
                    <input type="text" name="country" id="country" class="form-control" value="Italia" required>
                </div>
                <div class="mb-3">
                    <label for="notes" class="form-label">Note</label>
                    <textarea class="form-control" id="notes" name="notes" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-success">Salva indirizzo</button>
                <button type="button" class="btn btn-danger" id="hideShippingAddressFormContainer">Annulla</button>
            </form>
        </div>
    </div>

    <%--! Hidden billing address form --%>
    <div id="billingAddressFormContainer" class="card mt-3 bg-dark text-white" style="display: none;">
        <div class="card-body">
            <h5 class="card-title">Nuovo indirizzo</h5>
            <form method="post" action="${pageContext.request.contextPath}/secure/customer/address" id="submit-billing-address">
                <input type="hidden" id="address-type" name="address-type" value="billing">

                <br>

                <div class="mb-3">
                    <label for="name" class="form-label">Nome</label>
                    <input type="text" name="name" id="name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="surname" class="form-label">Cognome</label>
                    <input type="text" name="surname" id="surname" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="street-name" class="form-label">Via</label>
                    <input type="text" name="street-name" id="street-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="street-number" class="form-label">Numero Civico</label>
                    <input type="text" name="street-number" id="street-number" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="postcode" class="form-label">CAP</label>
                    <input type="text" name="postcode" id="postcode" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="city" class="form-label">Città</label>
                    <input type="text" name="city" id="city" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="province" class="form-label">Provincia</label>
                    <input type="text" name="province" id="province" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="country" class="form-label">Paese</label>
                    <input type="text" name="country" id="country" class="form-control" value="Italia" required>
                </div>

                <button type="submit" class="btn btn-success">Salva indirizzo</button>
                <button type="button" class="btn btn-danger" id="hideBillingAddressFormContainer">Annulla</button>
            </form>
        </div>
    </div>

    <%-- TODO: add payment method selector/form --%>
    <%--!    PAYMENT METHOD    --%>
    <% PaymentMethodBean selectedPaymentMethod = null; %>
    <% List<PaymentMethodBean> paymentMethodList = (List<PaymentMethodBean>) session.getAttribute("paymentMethods"); %>
    <% if (paymentMethodList == null || paymentMethodList.isEmpty()) {%>
    <div class="alert alert-warning">
        Nessun metodo di pagamento.
        <button class="btn btn-sm btn-primary ms-2" id="showPaymentMethodFormBtn" type="button">
            Aggiungi un metodo di pagamento
        </button>
    </div>
    <% } else {
        selectedPaymentMethod = paymentMethodList.get(0); // Show the first one
    %>

    <div class="mb-4">
        <label class="form-label text-white fw-semibold">
            Seleziona il metodo di pagamento:
            <button class="btn btn-danger btn-primary ms-2" id="showPaymentMethodFormBtn" type="button">
                Aggiungi un metodo di pagamento
            </button>
        </label>
        <div class="dropdown">
            <button
                    class="btn btn-secondary dropdown-toggle w-100 text-start"
                    type="button"
                    id="paymentMethodDropdown"
                    data-bs-toggle="dropdown"
                    aria-expanded="false">
                <%= selectedPaymentMethod.getOwner() %>, <%= selectedPaymentMethod.getCardNumber() %>, <%= selectedPaymentMethod.getExpireDate() %>
            </button>
            <ul class="dropdown-menu dropdown-menu-dark w-100" aria-labelledby="paymentMethodDropdown">
                <% for (PaymentMethodBean paymentMethod : paymentMethodList) {%>
                <li>
                    <a class="dropdown-item" href="#"
                       onclick="document.getElementById('addressDropdown').innerHTML='<% selectedPaymentMethod = paymentMethod; %> <%= selectedPaymentMethod.getOwner() %> <%= selectedPaymentMethod.getCardNumber() %>, <%= selectedPaymentMethod.getExpireDate() %>">
                        <div>
                            <%= paymentMethod.getOwner() %>, <%= paymentMethod.getCardNumber() %>, <%= selectedPaymentMethod.getExpireDate() %>
                        </div>
                    </a>
                </li>
                <% } %>
            </ul>
        </div>
        <br>
    </div>
    <%}%>

    <%--! Hidden Payment Method form --%>
    <div id="paymentMethodFormContainer" class="card mt-3 bg-dark text-white" style="display: none;">
        <div class="card-body">
            <h5 class="card-title">Nuovo indirizzo</h5>
            <form method="post" action="${pageContext.request.contextPath}/secure/customer/payment-methods" id="submit-payment-method">
                <br>

                <div class="mb-3">
                    <label for="owner" class="form-label">Titolare della carta</label>
                    <input type="text" name="owner" id="owner" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="card-number" class="form-label">Numero della carta</label>
                    <input type="text" name="card-number" id="card-number" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="expiration-date" class="form-label">Data di scadenza</label>
                    <input type="text"
                           name="expiration-date"
                           id="expiration-date"
                           class="form-control"
                           placeholder="MM/YYYY"
                           pattern="(0[1-9]|1[0-2])\/[0-9]{4}"
                           title="Inserisci una data nel formato MM/YYYY"
                           required>
                </div>

                <button type="submit" class="btn btn-success">Salva metodo di pagamento</button>
                <button type="button" class="btn btn-danger" id="hidePaymentMethodFormContainer">Annulla</button>
            </form>
        </div>
    </div>

    <%
        OrdersBean orderBean = new OrdersBean();
        double total = 0;
    %>
    <h4>Articoli ordine:</h4>
    <% CartBean cart = (CartBean) session.getAttribute("cart"); %>
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

            <%
                total += productEntry.getKey().getSalePrice() * productEntry.getValue();

                orderBean.addProducts(productEntry.getKey(), productEntry.getValue(), productEntry.getKey().getSalePrice());
            %>
        </div>
        <br>
        <%}%>
    </div >

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="text-white m-0">Totale: <%= total %> &euro;</h4>
        <a href="#" class="btn btn-success">
            Acquista ora
        </a>
    </div>
</div>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/address.js"></script>
<script src="${pageContext.request.contextPath}/js/paymentMethod.js"></script>
</body>
</html>
