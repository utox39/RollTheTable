package control;

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
import java.util.Map;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
        if ((isAdmin != null) && (isAdmin)) {
            return;
        }

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

            if (op != null && (op.equals("add") || op.equals("increase"))) {
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
            } else if (op != null && op.equals("get")) {
                if (logged) {
                    cartBean = new CartDAO().retrieveCart(user);
                    session.setAttribute("cart", cartBean);
                }

                request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
            } else if (op != null && op.equals("decrease")) {
                productBean = new ProductDAO().doRetrieveById(productId);
                cartBean = (CartBean) session.getAttribute("cart");
                int actualQuantity = cartBean.getCart().entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().getProductId().equals(productBean.getProductId()))
                        .map(Map.Entry::getValue)
                        .findFirst().orElse(0);

                int newQuantity = 0;
                if ((actualQuantity - 1) == 0) {
                    cartBean.remove(productBean);
                } else {
                    cartBean.decreaseQuantity(productBean);
                    newQuantity = actualQuantity - 1;
                }

                if (logged) {
                    CartDAO cartDAO = new CartDAO();
                    if ((actualQuantity - 1) == 0) {
                        cartDAO.removeFromCart(cartBean, productBean);
                    } else {
                        cartDAO.doUpdateQuantity(cartBean, productBean, newQuantity);
                    }
                    session.setAttribute("cart", cartBean);
                }
            } else {
                response.sendRedirect("views/fallback.jsp");
            }
        } catch (SQLException e) {
            ServerLogger.severe(CartController.class.getName(), "CartController SQLException: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
