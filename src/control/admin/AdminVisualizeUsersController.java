package admin;

import model.dao.CustomerDAO;
import util.ServerLogger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/admin/secure/users")
public class AdminVisualizeUsersController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            request.setAttribute("customers", customerDAO.doRetrieveAll());
        } catch (SQLException e) {
            ServerLogger.severe(AdminVisualizeUsersController.class.getName(), "doRetrieveAll SQLException:" + e.getMessage() );
            request.setAttribute("customers", null);
        }
        request.getRequestDispatcher("/views/admin/secure/users.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {}
}
