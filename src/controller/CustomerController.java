import model.bean.CustomerBean;
import model.dao.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/secure/customer")
public class CustomerController extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }

}
