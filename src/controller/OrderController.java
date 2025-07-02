import model.bean.*;
import model.dao.AddressDAO;
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

        OrdersBean orderBean = new OrdersBean();

        int orderId = 0;

        for (Map.Entry<ProductBean, Integer>productEntry : cartBean.getCart().entrySet()) {
            orderBean.addProducts(productEntry.getKey(), productEntry.getValue(), productEntry.getKey().getSalePrice());
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
        } catch (SQLException e) {
            ServerLogger.severe(OrderController.class.getName(), "SQLException: " + e.getMessage());
        }

        orderBean.setOrderId(orderId);

        session.setAttribute("paymentMethod", paymentMethodBean);
        session.setAttribute("shippingAddress", shippingAddressBean);
        session.setAttribute("billingAddress", billingAddressBean);

        session.setAttribute("orderDetails", orderBean);

        request.getRequestDispatcher("/views/secure/orderSummary.jsp").forward(request, response);
    }
}
