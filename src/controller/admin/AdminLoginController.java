package admin;

import model.bean.AdminBean;
import model.dao.AdminDAO;
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

@WebServlet("/admin/login")
public class AdminLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("/views/admin/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String pswHash = HashUtil.hashToHexString(HashUtil.digest(password));
        AdminDAO adminDAO = new AdminDAO();

        try {
            AdminBean adminBean = adminDAO.authenticate(login, pswHash);
            if (adminBean != null) {
                adminBean.setPswHash("");
                HttpSession session = request.getSession();
                session.removeAttribute("loggedAdmin");
                session.invalidate();
                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("loggedAdmin", adminBean);
                newSession.setAttribute("isAdmin", true);
            } else {
                request.setAttribute("loginError", "");
                request.setAttribute("loginError", "Username o Password incorrette.");
                request.getRequestDispatcher("/views/admin/login.jsp").forward(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/catalog");
        } catch (SQLException e) {
            ServerLogger.severe(AdminLoginController.class.getName(), "CustomerDao authenticate SQLException: " + e.getMessage());
            request.setAttribute("loginError", "");
            request.setAttribute("loginError", "Username o Password incorrette.");
            request.getRequestDispatcher("/views/admin/login.jsp").forward(request, response);
        } catch (IOException e) {
            ServerLogger.severe(AdminLoginController.class.getName(), "IOException: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.setAttribute("loginError", "Ops...qualcosa è andato storto. Ritenta per favore.");
            request.getRequestDispatcher("/views/admin/login.jsp").forward(request, response);
        }
    }
}
