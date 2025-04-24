import model.bean.CartBean;
import model.bean.CustomerBean;
import model.bean.ProductBean;
import model.dao.CartDAO;
import model.dao.CustomerDAO;
import model.dao.ProductDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

// sample class created to debug the routing functionality
@WebServlet("/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        ProductBean productBean;
        CartBean cartBean;
        HttpSession session = request.getSession();
        String productId = request.getParameter("productId");
        boolean logged = false;

        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");

        try {
            if (user != null && user.getCustomerId() == new CustomerDAO().doRetriveById(user.getCustomerId()).getCustomerId()) {
                logged = true;
            }

            if (op != null && op.equals("add")) {
                productBean = new ProductDAO().doRetrieveById(productId);

                if (session.getAttribute("cart") == null) {
                    cartBean = new CartBean();
                } else {
                    cartBean = (CartBean) session.getAttribute("cart");
                }

                cartBean.add(productBean);
                session.setAttribute("cart", cartBean);

                if (logged) {
                    new CartDAO().addToCart(user, cartBean);
                }
            } else if (op != null && op.equals("remove")) {
                productBean = new ProductDAO().doRetrieveById(productId);
                cartBean = (CartBean) session.getAttribute("cart");
                cartBean.remove(productBean);

                if (logged) {
                    new CartDAO().removeFromCart(cartBean, productBean);
                }
            }
        } catch (SQLException e) {
            ServerLogger.severe(CartController.class.getName(), "CartController SQLException: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
