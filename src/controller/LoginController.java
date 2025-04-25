import model.bean.CustomerBean;
import model.dao.CustomerDAO;
import util.HashUtil;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String pswHash = HashUtil.hashToHexString(HashUtil.digest(password));
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            CustomerBean customerBean = customerDAO.authenticate(login, pswHash);
            if (customerBean != null) {
                customerBean.setPswHash("");
                HttpSession session = request.getSession();
                session.removeAttribute("loggedUser");
                session.invalidate();
                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("loggedUser", customerBean);
            } else {
                request.setAttribute("loginError", "");
                request.setAttribute("loginError", "Username o Password incorrette.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/");
        } catch (SQLException e) {
            ServerLogger.severe(LoginController.class.getName(), "CustomerDao authenticate SQLException: " + e.getMessage());
            request.setAttribute("loginError", "");
            request.setAttribute("loginError", "Username o Password incorrette.");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        } catch (IOException e) {
            ServerLogger.severe(LoginController.class.getName(), "IOException: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("loginError", "Ops...qualcosa è andato storto. Ritenta per favore.");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}
