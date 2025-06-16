package admin;

import model.bean.ProductBean;
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

@WebServlet("/admin/secure/manageProducts")
public class AdminManageProducts extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String op = request.getParameter("op");
        ProductBean productBean;
        ProductDAO productDAO = new ProductDAO();
        HttpSession session = request.getSession();
        String productId = request.getParameter("id");

        if (op == null) {
            return;
        }

        try {
            if (op.equals("add")) {
                // get params
                // build bean
                // productDAO.doSave(productbean)
            }
            if (op.equals("edit")) {
                // get params
                // check params
                // build bean
                // productDAO.doUpdate(productbean)
            }
            if (op.equals("delete")) {
                int id = Integer.parseInt(productId);
                productDAO.doDelete(id);
            }
        } catch (SQLException e) {
            ServerLogger.severe(AdminManageProducts.class.getName(), "AdminManageProducts SQLException: " + e.getMessage());
        } catch (NumberFormatException e) {

            ServerLogger.severe(AdminManageProducts.class.getName(), "AdminManageProducts NumberFormatException: " + e.getMessage());
        }
    }
}
