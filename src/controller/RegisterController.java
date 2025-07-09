import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkEmail = request.getParameter("checkRegistration");
        if (checkEmail != null && !checkEmail.isEmpty()) {
            int statusCode = HttpServletResponse.SC_OK;
            Object responseBody = Map.of("status", "success");

            final ObjectMapper mapper = new ObjectMapper();
            CustomerDAO customerDAO = new CustomerDAO();

            try {
                if (customerDAO.doesEmailExists(checkEmail)) {
                   responseBody = Map.of(
                           "status", "success",
                           "email", "true"
                   );
                } else {
                    responseBody = Map.of(
                            "status", "success",
                            "email", "false"
                    );
                }
            } catch (SQLException e) {
                ServerLogger.severe(RegisterController.class.getName(), "doesEmailExists SQLException: " + e.getMessage());
                statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                responseBody = Map.of(
                        "status", "error",
                        "message", "Internal server error"
                );
            }

            response.setStatus(statusCode);
            response.setContentType("application/json");

            try {
                mapper.writeValue(response.getWriter(), responseBody);
            } catch (IOException e) {
                ServerLogger.severe(RegisterController.class.getName(), "JSON error: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            return;
        }

        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("mismatchedPasswordsError", "");
            request.setAttribute("mismatchedPasswordsError", "Le password non coincidono.");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();

        CustomerBean customerBean = new CustomerBean();
        customerBean.setName(name);
        customerBean.setSurname(surname);
        customerBean.setUsername(username);
        customerBean.setEmail(email);
        customerBean.setPhone(phone);

        String pswHash = HashUtil.hashToHexString(HashUtil.digest(password));
        customerBean.setPswHash(pswHash);

        try {
            customerDAO.doSave(customerBean);
        } catch (SQLException e) {
            ServerLogger.severe(RegisterController.class.getName(), "doRetriveAll SQLException: " + e.getMessage());
            request.setAttribute("registrationError", "");
            request.setAttribute("registrationError", "Ops...qualcosa è andato storto. Ritenta per favore.");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            return;
        }

        customerBean.setPswHash("");
        HttpSession session = request.getSession();

        session.removeAttribute("isAdmin");
        session.setAttribute("isAdmin", false);

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
