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

@WebServlet("/secure/order-summary")
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");
        OrdersBean orderBean = (OrdersBean) session.getAttribute("orderDetails");

        orderBean.setCustomerId(user.getCustomerId());
        orderBean.setPurchaseDate(new Date());

        orderBean.setShippingPrice(Double.parseDouble(request.getParameter("shippingPrice")));

        PaymentMethodBean paymentMethodBean = new PaymentMethodBean();
        AddressBean shippingAddressBean = new AddressBean();
        AddressBean billingAddressBean = new AddressBean();

        try {
            paymentMethodBean = new PaymentMethodDAO().doRetriveById(orderBean.getPaymentMethodId());
            shippingAddressBean = new AddressDAO().doRetrieveById(orderBean.getShippingAddressId());
            billingAddressBean = new AddressDAO().doRetrieveById(orderBean.getBillingAddressId());

            new OrdersDAO().doSave(orderBean);
        } catch (SQLException e) {
            ServerLogger.severe(OrderController.class.getName(), "SQLException: " + e.getMessage());
        }

        session.setAttribute("paymentMethod", paymentMethodBean);
        session.setAttribute("shippingAddress", shippingAddressBean);
        session.setAttribute("billingAddress", billingAddressBean);

        session.setAttribute("orderDetails", orderBean);

        request.getRequestDispatcher("/views/secure/orderSummary.jsp").forward(request, response);
    }
}
