import model.bean.*;
import model.dao.AddressDAO;
import model.dao.CartDAO;
import model.dao.OrdersDAO;
import model.dao.PaymentMethodDAO;
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

        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");
        CartBean cartBean = (CartBean) session.getAttribute("cart");

        if (user == null || cartBean == null) {
            response.sendRedirect("views/login.jsp");
            return;
        }

        OrdersBean orderBean = new OrdersBean();

        int orderId = 0;

        double productPrice;

        for (Map.Entry<ProductBean, Integer>productEntry : cartBean.getCart().entrySet()) {
            productPrice = productEntry.getKey().getSalePrice();

            if (productEntry.getKey().getDiscount() > 0) {
                BigDecimal salePrice = BigDecimal.valueOf(productEntry.getKey().getSalePrice());
                BigDecimal discountPercentage = BigDecimal.valueOf(productEntry.getKey().getDiscount())
                        .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                BigDecimal discountValue = salePrice.multiply(discountPercentage);
                BigDecimal discountedPrice = salePrice.subtract(discountValue).setScale(2, RoundingMode.HALF_UP);
                productPrice = discountedPrice.doubleValue();
            }

            orderBean.addProducts(productEntry.getKey(), productEntry.getValue(), productPrice);
        }

        orderBean.setCustomerId(user.getCustomerId());
        orderBean.setPaymentMethodId(Integer.parseInt(request.getParameter("paymentMethod")));
        orderBean.setShippingAddressId(Integer.parseInt(request.getParameter("shippingAddress")));
        orderBean.setBillingAddressId(Integer.parseInt(request.getParameter("billingAddress")));
        orderBean.setPurchaseDate(new Date());
        orderBean.setShippingPrice(Double.parseDouble(request.getParameter("shipping-option")));

        PaymentMethodBean paymentMethodBean = new PaymentMethodBean();
        AddressBean shippingAddressBean = new AddressBean();
        AddressBean billingAddressBean = new AddressBean();

        try {
            paymentMethodBean = new PaymentMethodDAO().doRetriveById(orderBean.getPaymentMethodId());
            shippingAddressBean = new AddressDAO().doRetrieveById(orderBean.getShippingAddressId());
            billingAddressBean = new AddressDAO().doRetrieveById(orderBean.getBillingAddressId());

            orderId = new OrdersDAO().doSave(orderBean);

            // clear the cart in the database
            new CartDAO().clearCart(cartBean.getCartId());
        } catch (SQLException e) {
            ServerLogger.severe(OrderController.class.getName(), "SQLException: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("views/fallback.jsp");
            return;
        }

        orderBean.setOrderId(orderId);

        session.setAttribute("paymentMethod", paymentMethodBean);
        session.setAttribute("shippingAddress", shippingAddressBean);
        session.setAttribute("billingAddress", billingAddressBean);

        session.setAttribute("orderDetails", orderBean);

        // clear the cart in the session
        session.setAttribute("cart", null);

        request.getRequestDispatcher("/views/secure/orderSummary.jsp").forward(request, response);
    }
}
