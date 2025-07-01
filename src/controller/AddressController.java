import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.bean.AddressBean;
import model.bean.CustomerBean;
import model.dao.AddressDAO;
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

@WebServlet("/secure/customer/address")
public class AddressController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");
        int customerId = user.getCustomerId();

        final ObjectMapper mapper = new ObjectMapper();

        AddressBean addressBean = new AddressBean();

        int statusCode = HttpServletResponse.SC_OK;
        Object responseBody = Map.of("status", "success");

        try {
            addressBean = mapper.readValue(request.getInputStream(), AddressBean.class);
        } catch (JsonProcessingException e) {
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
            responseBody = Map.of(
                    "status", "error",
                    "message", "Invalid JSON format"
            );
        } catch (Exception e) {
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            responseBody = Map.of(
                    "status", "error",
                    "message", "Internal server error"
            );
        }

        //! NOTE: the customer ID must be set here, otherwise Jackson will overwrite it with 0
        addressBean.setCustomerId(customerId);

        AddressDAO addressDAO = new AddressDAO();

        try {
           addressDAO.doSave(addressBean);
        } catch (SQLException e) {
            ServerLogger.severe(AddressController.class.getName(), "AddressController doSave SQLException: " + e.getMessage());
            statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        response.setStatus(statusCode);
        try {
            mapper.writeValue(response.getWriter(), responseBody);
        } catch (IOException e) {
            ServerLogger.severe(OrderController.class.getName(), "JSON error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
