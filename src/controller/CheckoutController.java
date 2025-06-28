import model.bean.AddressBean;
import model.bean.CartBean;
import model.bean.CustomerBean;
import model.dao.AddressDAO;
import model.dao.CartDAO;
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

        CartBean cartBean = null;
        List<AddressBean> addressBeanList = null;
        try {
            cartBean = new CartDAO().retrieveCart(user);

            addressBeanList = new AddressDAO().doRetrieveAllByCustomerId(user.getCustomerId());
        } catch (SQLException e) {
            ServerLogger.severe(CheckoutController.class.getName(), "SQLException: " + e.getMessage());
        }
        session.setAttribute("cart", cartBean);
        session.setAttribute("addresses", null);
        session.setAttribute("addresses", addressBeanList);

        request.getRequestDispatcher("/views/secure/checkout.jsp").forward(request, response);
    }
}
