package control;

import model.bean.*;
import model.dao.*;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

@WebServlet("/secure/order-summary")
public class OrderController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve the logged-in user and their cart
        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");
        CartBean cartBean = (CartBean) session.getAttribute("cart");

        if (user == null || cartBean == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }

        OrdersBean orderBean = new OrdersBean();

        // Build order products with discounts if applicable
        for (Map.Entry<ProductBean, Integer> entry : cartBean.getCart().entrySet()) {
            ProductBean product = entry.getKey();
            int quantity = entry.getValue();
            double priceAtPurchase = product.getSalePrice();

            if (product.getDiscount() > 0) {
                BigDecimal salePrice = BigDecimal.valueOf(product.getSalePrice());
                BigDecimal discount = BigDecimal.valueOf(product.getDiscount()).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                BigDecimal discountedPrice = salePrice.subtract(salePrice.multiply(discount)).setScale(2, RoundingMode.HALF_UP);
                priceAtPurchase = discountedPrice.doubleValue();
            }

            orderBean.addProducts(product, quantity, priceAtPurchase);
        }

        // Set order details
        orderBean.setCustomerId(user.getCustomerId());
        orderBean.setPaymentMethodId(Integer.parseInt(request.getParameter("paymentMethod")));
        orderBean.setShippingAddressId(Integer.parseInt(request.getParameter("shippingAddress")));
        orderBean.setBillingAddressId(Integer.parseInt(request.getParameter("billingAddress")));
        orderBean.setPurchaseDate(new Date());
        orderBean.setShippingPrice(Double.parseDouble(request.getParameter("shipping-option")));

        try {
            // Retrieve supporting beans
            PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
            AddressDAO addressDAO = new AddressDAO();

            PaymentMethodBean paymentMethod = paymentMethodDAO.doRetriveById(orderBean.getPaymentMethodId());
            AddressBean shippingAddress = addressDAO.doRetrieveById(orderBean.getShippingAddressId());
            AddressBean billingAddress = addressDAO.doRetrieveById(orderBean.getBillingAddressId());

            // Save order and get generated ID
            OrdersDAO ordersDAO = new OrdersDAO();
            int orderId = ordersDAO.doSave(orderBean);
            orderBean.setOrderId(orderId);

            // Calculate taxable total
            BigDecimal taxableTotal = BigDecimal.ZERO;
            for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> item : orderBean.getProducts().entrySet()) {
                BigDecimal unitPrice = BigDecimal.valueOf(item.getValue().getPriceAtPurchase());
                int quantity = item.getValue().getQuantity();
                taxableTotal = taxableTotal.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
            }

            // Compute tax
            final BigDecimal ivaRate = new BigDecimal("22");
            BigDecimal taxAmount = taxableTotal.multiply(ivaRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            // Create and save invoice
            InvoiceBean invoice = new InvoiceBean();
            invoice.setCustomerId(user.getCustomerId());
            invoice.setPurchaseId(orderBean.getOrderId());
            invoice.setTaxable(taxableTotal);
            invoice.setTax(taxAmount);
            invoice.setIva(ivaRate);
            invoice.setNotes("Fattura generata automaticamente");

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.doSave(invoice);

            // Clear cart in DB
            new CartDAO().clearCart(cartBean.getCartId());

            // Store order details and other beans in session
            session.setAttribute("paymentMethod", paymentMethod);
            session.setAttribute("shippingAddress", shippingAddress);
            session.setAttribute("billingAddress", billingAddress);
            session.setAttribute("orderDetails", orderBean);

            // Clear cart from session
            session.setAttribute("cart", null);

            // Forward to summary page
            request.getRequestDispatcher("/views/secure/orderSummary.jsp").forward(request, response);

        } catch (SQLException e) {
            ServerLogger.severe(OrderController.class.getName(), "SQLException: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("views/fallback.jsp");
        }
    }
}
