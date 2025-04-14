import model.bean.ProductBean;
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

@WebServlet("/product")
public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String productAttr = "product";
        request.removeAttribute(productAttr);

        try {
            ProductDAO productDAO = new ProductDAO();
            ProductBean productBean = productDAO.doRetrieveById(request.getParameter("productId"));
            request.setAttribute(productAttr, productBean);
        } catch (SQLException e) {
            ServerLogger.severe(CatalogController.class.getName(), "doRetriveByKey SQLException: " + e.getMessage());
            request.setAttribute(productAttr, null);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/product.jsp");
        dispatcher.forward(request, response);
    }
}
