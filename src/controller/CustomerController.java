import model.bean.CustomerBean;
import model.bean.OrdersBean;
import model.dao.CustomerDAO;
import model.dao.OrdersDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/secure/customer")
public class CustomerController extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CustomerBean customer = (CustomerBean) session.getAttribute("loggedUser");
        int customerId = customer.getCustomerId();

        List<OrdersBean> ordersList;

        try {
            ordersList = new OrdersDAO().doRetrieveAllByCustomerId(customerId);
            req.setAttribute("ordersList", ordersList);
        } catch (SQLException e) {
            ServerLogger.severe(LoginController.class.getName(), " doRetrieveAllByCustomerId SQLException: " + e.getMessage());
        }

        req.getRequestDispatcher("/views/secure/customerProfile.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerBean customer = (CustomerBean) req.getSession().getAttribute("loggedUser");
        CustomerDAO customerDAO = new CustomerDAO();

        customer.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
        customer.setUsername(req.getParameter("username"));
        customer.setName(req.getParameter("name"));
        customer.setSurname(req.getParameter("surname"));
        customer.setEmail(req.getParameter("email"));
        customer.setPhone(req.getParameter("phone"));

        try {
            customerDAO.doUpdate(customer);
        } catch (SQLException e) {
            ServerLogger.severe(CustomerController.class.getName(), "doUpdate SQLException: " + e.getMessage());
        }

        req.getRequestDispatcher("/views/secure/customerProfile.jsp").forward(req, resp);

    }

}
