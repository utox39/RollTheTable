package admin;

import model.dao.OrdersDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/admin/secure/orders")
public class AdminVisualizeOrdersController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ordersAttr = "orders";
        request.removeAttribute(ordersAttr);

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        String customerId = request.getParameter("customerId");

        boolean retrieveByDate = false;
        boolean retrieveByCustomerId = customerId != null;

        Date startDate = null;
        Date endDate = null;

        if ((!retrieveByCustomerId) && (startDateStr != null) && (!startDateStr.isEmpty())) {
            startDate = Date.valueOf(startDateStr);
            retrieveByDate = true;
        }

        if (retrieveByDate && (endDateStr != null) && (!endDateStr.isEmpty())) {
            endDate = Date.valueOf(endDateStr);
        } else {
            retrieveByDate = false;
        }

        try {
            OrdersDAO ordersDAO = new OrdersDAO();

            if (retrieveByDate) {
                System.out.println("retrieveByDate");
                request.setAttribute(ordersAttr, ordersDAO.doRetrieveOrdersByDateRange(startDate, endDate));
            } else if (retrieveByCustomerId) {
                System.out.println("retrieveByCustomerId");
                request.setAttribute(ordersAttr, ordersDAO.doRetrieveAllByCustomerId(Integer.parseInt(customerId)));
            } else {
                System.out.println("retrieveAll");
                request.setAttribute(ordersAttr, ordersDAO.doRetrieveAll());
            }

        } catch (SQLException e) {
            ServerLogger.severe(AdminVisualizeOrdersController.class.getName(),"SQLException: " + e.getMessage());
            request.setAttribute(ordersAttr, null);
        }
        response.setContentType("text/html");
        request.getRequestDispatcher("/views/admin/secure/orders.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
