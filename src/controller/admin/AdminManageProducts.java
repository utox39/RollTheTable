package admin;

import model.bean.ProductBean;
import model.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/admin/secure/manageProducts")
public class AdminManageProducts extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        ProductBean productBean;
        ProductDAO productDAO = new ProductDAO();
        HttpSession session = request.getSession();
        String productId = request.getParameter("productId");


       /*try {

       } catch (SQLException e) {
           ServerLogger.severe(AdminManageProducts.class.getName(), "AdminManageProducts SQLException: " + e.getMessage());
       }*/
    }
}
