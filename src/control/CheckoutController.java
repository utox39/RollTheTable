package control;

import model.bean.*;
import model.dao.AddressDAO;
import model.dao.CartDAO;
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
import java.util.List;

@WebServlet("/secure/checkout")
public class CheckoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");

        if (user == null) {
            response.sendRedirect("views/fallback.jsp");
            return;
        }

        CartBean cartBean = null;
        List<AddressBean> addressBeanList = null;
        List<PaymentMethodBean> paymentMethodBeanList = null;
        try {
            cartBean = new CartDAO().retrieveCart(user);

            addressBeanList = new AddressDAO().doRetrieveAllByCustomerId(user.getCustomerId());

            paymentMethodBeanList = new PaymentMethodDAO().doRetrieveAllByCustomerId(user.getCustomerId());
        } catch (SQLException e) {
            ServerLogger.severe(CheckoutController.class.getName(), "SQLException: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("/views/fallback.jsp");
            return;
        }
        session.setAttribute("cart", cartBean);
        session.setAttribute("addresses", null);
        session.setAttribute("addresses", addressBeanList);
        session.setAttribute("paymentMethods", null);
        session.setAttribute("paymentMethods", paymentMethodBeanList);

        request.getRequestDispatcher("/views/secure/checkout.jsp").forward(request, response);
    }
}
