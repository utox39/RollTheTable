import model.bean.ProductBean;
import model.dao.ProductDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomepageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String latestProductsAttr = "latestProducts";
        final String discountedProductsAttr = "discountedProducts";
        final String popularProductsAttr = "popularProducts";
        final String acclaimedProductsAttr = "acclaimedProducts";

        List<ProductBean> latestProducts;
        List<ProductBean> discountedProducts;
        List<ProductBean> popularProducts;

        request.removeAttribute(latestProductsAttr);
        request.removeAttribute(discountedProductsAttr);
        request.removeAttribute(popularProductsAttr);
        request.removeAttribute(acclaimedProductsAttr);

        try {
            ProductDAO productDAO = new ProductDAO();
            latestProducts = productDAO.retrieveLatestProducts();
            discountedProducts = productDAO.retrieveDiscountedProducts();
            popularProducts = productDAO.retrieveMostPurchasedProducts();

            request.setAttribute(latestProductsAttr, latestProducts);
            request.setAttribute(discountedProductsAttr, discountedProducts);
            request.setAttribute(popularProductsAttr, popularProducts);
            request.setAttribute(acclaimedProductsAttr, popularProducts);
        } catch (SQLException e) {
            ServerLogger.severe(HomepageController.class.getName(), "ProductDAO SQLException: " + e.getMessage());

            request.setAttribute(latestProductsAttr, null);
            request.setAttribute(discountedProductsAttr, null);
            request.setAttribute(popularProductsAttr, null);
            request.setAttribute(acclaimedProductsAttr, null);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("views/fallback.jsp");
            return;
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
