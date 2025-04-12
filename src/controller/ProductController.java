import model.dao.ProductDAO;
import util.ServerLogger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String productsAttr = "products";
        request.removeAttribute(productsAttr);
        try {
            ProductDAO productDAO = new ProductDAO();
            request.setAttribute(productsAttr, productDAO.doRetriveAll());
        } catch (SQLException e) {
            ServerLogger.severe(ProductController.class.getName(), "doRetriveAll SQLException: " + e.getMessage());
            request.setAttribute(productsAttr, null);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/products.jsp");
        dispatcher.forward(request, response);
    }
}
