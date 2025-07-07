<%--suppress t --%>
<%@ page import="model.bean.AddressBean" %>
<%@ page import="model.bean.CartBean" %>
<%@ page import="model.bean.PaymentMethodBean" %>
<%@ page import="model.bean.ProductBean" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>RollTheTable - Checkout</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/main.css">
</head>
<body>
<jsp:include page="/views/navbar.jsp" flush="true"/>

<div id="checkout" class="container mt-5 main-content">
    <div class="main-header text-center">
        <h1>Che il divertimento abbia inizio!</h1>
        <br>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/secure/order-summary" id="submit-order">
        <%
            List<AddressBean> addressBeanList = (List<AddressBean>) session.getAttribute("addresses");

            long billingAddressCount = addressBeanList.stream().filter(a -> a.getType().equals("billing")).count();
            long shippingAddressCounter = addressBeanList.stream().filter(a -> a.getType().equals("shipping")).count();
        %>
        <% if ((shippingAddressCounter == 0) || (billingAddressCount == 0)) {%>
        <div class="row">
            <% if (shippingAddressCounter == 0) { %>
            <div class="col-md-6 mb-3">
                <div class="alert alert-warning">
                    Nessun indirizzo di spedizione disponibile.
                    <button class="btn btn-sm btn-primary ms-2" id="showShippingAddressFormBtn" type="button">
                        Aggiungi un indirizzo di spedizione
                    </button>
                </div>
            </div>
            <% } %>

            <% if (billingAddressCount == 0) {%>
            <div class="col-md-6 mb-3">
                <div class="alert alert-warning">
                    Nessun indirizzo di fatturazione disponibile.
                    <button class="btn btn-sm btn-primary ms-2" id="showBillingAddressFormBtn" type="button">
                        Aggiungi un indirizzo di fatturazione
                    </button>
                </div>
            </div>
            <% } %>
        </div>
        <% } else {%>
        <div class="mb-4">
            <div class="row">
                <%--! SHIPPING ADDRESS --%>
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white fw-semibold" for="shippingAddress">
                        Seleziona l'indirizzo di spedizione:
                        <button class="btn btn-danger btn-primary ms-2" id="showShippingAddressFormBtn" type="button">
                            Aggiungi un indirizzo di spedizione
                        </button>
                    </label>
                    <select name="shippingAddress" id="shippingAddress"
                            class="form-select form-select-lg mb-3 bg-dark text-light border-secondary">
                        <%
                            for (AddressBean address : addressBeanList) {
                                if (address.getType().equals("shipping")) {
                        %>
                        <option value="<%= address.getAddressId() %>">
                            <%= address.getName() %> <%= address.getSurname() %>,
                            <%= address.getStreetName() %> <%= address.getStreetNumber() %>,
                            <%= address.getPostcode() %> <%= address.getCity() %> (<%= address.getProvince() %>),
                            <%= address.getCountry() %>
                        </option>
                        <% }
                        }%>
                    </select>
                </div>

                <%--! BILLING ADDRESS --%>
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white fw-semibold" for="billingAddress">
                        Seleziona l'indirizzo di fatturazione:
                        <button class="btn btn-danger btn-primary ms-2" id="showBillingAddressFormBtn" type="button">
                            Aggiungi un indirizzo di fatturazione
                        </button>
                    </label>
                    <select name="billingAddress" id="billingAddress"
                            class="form-select form-select-lg mb-3 bg-dark text-light border-secondary">
                        <%
                            for (AddressBean address : addressBeanList) {
                                if (address.getType().equals("billing")) {
                        %>
                        <option value="<%= address.getAddressId() %>">
                            <%= address.getName() %> <%= address.getSurname() %>,
                            <%= address.getStreetName() %> <%= address.getStreetNumber() %>,
                            <%= address.getPostcode() %> <%= address.getCity() %> (<%= address.getProvince() %>),
                            <%= address.getCountry() %>
                        </option>
                        <% }
                        }%>
                    </select>
                </div>
            </div>
        </div>
        <% } %>

        <%--! Hidden shipping address form --%>
        <div id="shippingAddressFormContainer" class="card mt-3 bg-dark text-white" style="display: none;">
            <div class="card-body">
                <h5 class="card-title">Nuovo indirizzo</h5>
                <%--            <form method="post" action="${pageContext.request.contextPath}/secure/customer/address" id="submit-shipping-address">--%>
                <input type="hidden" id="shipping-address-type" name="shipping-address-type" value="shipping">

                <br>

                <div class="mb-3">
                    <label for="shipping-name" class="form-label">Nome</label>
                    <input type="text" name="shipping-name" id="shipping-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shipping-surname" class="form-label">Cognome</label>
                    <input type="text" name="shipping-surname" id="shipping-surname" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shipping-street-name" class="form-label">Via</label>
                    <input type="text" name="shipping-street-name" id="shipping-street-name" class="form-control"
<%--                           pattern="\d+[a-zA-Z]?"--%>
<%--                           inputmode="numeric"--%>
<%--                           oninput="this.value = this.value.replace(/[^0-9a-zA-Z]/g, '')"--%>
                           required>
                </div>
                <div class="mb-3">
                    <label for="shipping-street-number" class="form-label">Numero Civico</label>
                    <input type="text" name="shipping-street-number" id="shipping-street-number" class="form-control"
                           required>
                </div>
                <div class="mb-3">
                    <label for="shipping-postcode" class="form-label">CAP</label>
                    <input type="text" name="shipping-postcode" id="shipping-postcode" inputmode="numeric"
                           class="form-control"
                           pattern="\d{5}"
                           maxlength="5"
                           oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                           title="Inserisci un CAP valido (5 cifre)"  required>
                </div>
                <div class="mb-3">
                    <label for="shipping-city" class="form-label">Città</label>
                    <input type="text" name="shipping-city" id="shipping-city" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shipping-province" class="form-label">Provincia</label>
                    <input type="text" name="shipping-province" id="shipping-province" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shipping-country" class="form-label">Paese</label>
                    <input type="text" name="shipping-country" id="shipping-country" class="form-control" value="Italia"
                           required>
                </div>
                <div class="mb-3">
                    <label for="shipping-notes" class="form-label">Note</label>
                    <textarea class="form-control" id="shipping-notes" name="shipping-notes" rows="3"></textarea>
                </div>
                <button type="button" class="btn btn-success" id="saveShippingAddress">Salva indirizzo</button>
                <button type="button" class="btn btn-danger" id="hideShippingAddressFormContainer">Annulla</button>
                <%--            </form>--%>
            </div>
        </div>

        <%--! Hidden billing address form --%>
        <div id="billingAddressFormContainer" class="card mt-3 bg-dark text-white" style="display: none;">
            <div class="card-body">
                <h5 class="card-title">Nuovo indirizzo</h5>
                <%--            <form method="post" action="${pageContext.request.contextPath}/secure/customer/address" id="submit-billing-address">--%>
                <input type="hidden" id="billing-address-type" name="billing-address-type" value="billing">

                <br>

                <div class="mb-3">
                    <label for="billing-name" class="form-label">Nome</label>
                    <input type="text" name="billing-name" id="billing-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="billing-surname" class="form-label">Cognome</label>
                    <input type="text" name="billing-surname" id="billing-surname" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="billing-street-name" class="form-label">Via</label>
                    <input type="text" name="billing-street-name" id="billing-street-name" class="form-control"
                           required>
                </div>
                <div class="mb-3">
                    <label for="billing-street-number" class="form-label">Numero Civico</label>
                    <input type="text" name="billing-street-number" id="billing-street-number" class="form-control"
                           required>
                </div>
                <div class="mb-3">
                    <label for="billing-postcode" class="form-label">CAP</label>
                    <input type="text" name="billing-postcode" id="billing-postcode" class="form-control" inputmode="numeric"
                           pattern="\d{5}"
                           maxlength="5"
                           oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                           title="Inserisci un CAP valido (5 cifre)" required>
                </div>
                <div class="mb-3">
                    <label for="billing-city" class="form-label">Città</label>
                    <input type="text" name="billing-city" id="billing-city" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="billing-province" class="form-label">Provincia</label>
                    <input type="text" name="billing-province" id="billing-province" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="billing-country" class="form-label">Paese</label>
                    <input type="text" name="billing-country" id="billing-country" class="form-control" value="Italia"
                           required>
                </div>

                <button type="button" class="btn btn-success" id="saveBillingAddress">Salva indirizzo</button>
                <button type="button" class="btn btn-danger" id="hideBillingAddressFormContainer">Annulla</button>
                <%--            </form>--%>
            </div>
        </div>

        <%--!    PAYMENT METHOD    --%>
        <% List<PaymentMethodBean> paymentMethodList = (List<PaymentMethodBean>) session.getAttribute("paymentMethods"); %>
        <% if (paymentMethodList == null || paymentMethodList.isEmpty()) {%>
        <div class="alert alert-warning">
            Nessun metodo di pagamento.
            <button class="btn btn-sm btn-primary ms-2" id="showPaymentMethodFormBtn" type="button">
                Aggiungi un metodo di pagamento
            </button>
        </div>
        <% } else {%>
        <div class="mb-4">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label text-white fw-semibold" for="paymentMethod">
                        Seleziona il metodo di pagamento:
                        <button class="btn btn-danger btn-primary ms-2" id="showPaymentMethodFormBtn" type="button">
                            Aggiungi un metodo di pagamento
                        </button>
                    </label>
                    <select name="paymentMethod" id="paymentMethod"
                            class="form-select form-select-lg mb-3 bg-dark text-light border-secondary">
                        <%
                            for (PaymentMethodBean paymentMethod : paymentMethodList) {
                        %>
                        <option value="<%= paymentMethod.getPaymentMethodId() %>">
                            <%
                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/yyyy");
                                String formattedDate = sdf.format(paymentMethod.getExpireDate());
                            %>
                            <%= paymentMethod.getOwner() %>, <%= paymentMethod.getCardNumber() %>, <%= formattedDate %>
                        </option>
                        <% } %>
                    </select>
                </div>
            </div>
            <br>
        </div>
        <%}%>

        <%--! Hidden Payment Method form --%>
        <div id="paymentMethodFormContainer" class="card mt-3 bg-dark text-white" style="display: none;">
            <div class="card-body">
                <h5 class="card-title">Nuovo metodo di pagamento</h5>
                <%--            <form method="post" action="${pageContext.request.contextPath}/secure/customer/payment-methods" id="submit-payment-method">--%>
                <br>

                <div class="mb-3">
                    <label for="owner" class="form-label">Titolare della carta</label>
                    <input type="text" name="owner" id="owner" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="card-number" class="form-label">Numero della carta</label>
                    <input type="text" name="card-number" id="card-number" class="form-control" maxlength="19"
                           oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>
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

                <button type="button" class="btn btn-success" id="savePaymentMethod">Salva metodo di pagamento</button>
                <button type="button" class="btn btn-danger" id="hidePaymentMethodFormContainer">Annulla</button>
                <%--            </form>--%>
            </div>
        </div>

        <%
            BigDecimal total = BigDecimal.valueOf(0.0);
        %>
        <h4>Articoli ordine:</h4>
        <% CartBean cart = (CartBean) session.getAttribute("cart"); %>
        <div class="list-group-item py-3 px-4">
            <% for (Map.Entry<ProductBean, Integer> productEntry : cart.getCart().entrySet()) {%>
            <div class="row align-items-center">
                <!-- Product's image -->
                <div class="col-md-2 col-3">
                    <img src="${pageContext.request.contextPath}/assets/images/logo.JPG"
                         class="img-fluid rounded border"
                         style="width: 100%; height: auto; object-fit: cover;" alt="Prodotto">
                </div>

                <div class="col-md-7 col-9">
                    <h5 class="mb-1 fw-semibold"><%= productEntry.getKey().getName() %>
                    </h5>
                    <div class="mt-2">
                        <a href="${pageContext.request.contextPath}/cart?op=remove&productId=<%= productEntry.getKey().getProductId() %>"
                           class="text-decoration-none me-3 text-danger">Rimuovi</a>
                    </div>
                </div>

                <div class="col-md-3 text-end">
                    <h5 class="fw-bold">
                        <% double productPrice = 0.0; %>
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
                            productPrice = discountedPrice.doubleValue();
                        %>

                        <span class="fw-bold me-2">
                            <%= discountedPrice %> &euro;
                        </span>

                        <span class="text-decoration-line-through">
                            <%= productEntry.getKey().getSalePrice() %> &euro;
                        </span>
                        <% } else { %>
                        <span class="fw-bold">
                            <% productPrice = productEntry.getKey().getSalePrice(); %>
                            <%= productPrice %> &euro;
                        </span>
                        <% } %>
                    </h5>
                    <h5 class="fw-bold">Quantit&agrave;: <%= productEntry.getValue() %>
                    </h5>
                </div>

                <%
                    total = total.add(BigDecimal.valueOf(productPrice * productEntry.getValue()));
                %>
            </div>
            <br>
            <%}%>
        </div>

        <%--! SHIPPING TYPE --%>
        <%-- NOTE: if the `total` is >= 30 the shipping is free --%>
        <h4>Spedizione: </h4>
        <% if (total.compareTo(BigDecimal.valueOf(30)) <= 0) { %>
        <br>
        <h5>Seleziona il tipo di spedizione: </h5>
        <div class="mb-3">
            <label for="standard-shipping" class="form-label">
                <input type="radio" name="shipping-option" id="standard-shipping" value="3.50" checked>
                Spedizione standard (3,50 &euro;)
            </label>
            <br>

            <label for="express-shipping" class="form-label">
                <input type="radio" name="shipping-option" id="express-shipping" value="5.00">
                Spedizione espressa (5,00 &euro;)
            </label>
        </div>
        <%} else {%>
        <br>
        <span>Pssst...sembrerebbe che il dungeon master abbia deciso di premiarti perch&eacute; il tuo ordine supera le 30 monete,</span>
        <span>quindi la spedizione standard &egrave; GRATIS</span>
        <br><br>

        <h5>Seleziona il tipo di spedizione: </h5>
        <div class="mb-3">
            <label for="standard-shipping" class="form-label">
                <input type="radio" name="shipping-option" id="standard-shipping" value="0.00" checked>
                Spedizione standard (0,00 &euro;)
            </label>
            <br>

            <label for="express-shipping" class="form-label">
                <input type="radio" name="shipping-option" id="express-shipping" value="5.00">
                Spedizione espressa (5,00 &euro;)
            </label>
        </div>
        <%}%>

        <br>
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="text-white m-0">Totale: <%= total %> &euro; (escluso costo di spedizione)</h4>

            <button type="submit" class="btn btn-success">Acquista ora</button>
        </div>
    </form>

</div>

<hr>
<jsp:include page="../../views/footer.jsp" flush="true"/>

<script src="${pageContext.request.contextPath}/bootstrap-5.3.3-dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/address.js"></script>
<script src="${pageContext.request.contextPath}/js/paymentMethod.js"></script>
<script src="${pageContext.request.contextPath}/js/formValidation.js"></script>
</body>
</html>
