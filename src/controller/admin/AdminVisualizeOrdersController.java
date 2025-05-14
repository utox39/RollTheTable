package admin;

import model.dao.OrdersDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/secure/orders")
public class AdminVisualizeOrdersController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ordersAttr = "orders";
        request.removeAttribute(ordersAttr);
        try {
            OrdersDAO ordersDAO = new OrdersDAO();
            request.setAttribute(ordersAttr, ordersDAO.doRetrieveAll());
        } catch (SQLException e) {
            ServerLogger.severe(AdminVisualizeOrdersController.class.getName(), "doRetrieveAll SQLException: " + e.getMessage());
            request.setAttribute(ordersAttr, null);
        }
        response.setContentType("text/html");
        request.getRequestDispatcher("/views/admin/secure/orders.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
