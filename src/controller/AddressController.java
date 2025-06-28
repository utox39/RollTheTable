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

@WebServlet("/customer/address")
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

        String addressType = request.getParameter("address-type");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String streetName = request.getParameter("street-name");
        String streetNumber = request.getParameter("street-number");
        String postcode = request.getParameter("postcode");
        String city = request.getParameter("city");
        String province = request.getParameter("province");
        String country = request.getParameter("country");
        String notes = request.getParameter("notes");

        AddressBean addressBean = new AddressBean();
        addressBean.setCustomerId(customerId);
        addressBean.setType(addressType);
        addressBean.setName(name);
        addressBean.setSurname(surname);
        addressBean.setStreetName(streetName);
        addressBean.setStreetNumber(streetNumber);
        addressBean.setPostcode(postcode);
        addressBean.setCity(city);
        addressBean.setProvince(province);
        addressBean.setCountry(country);
        addressBean.setNotes(notes);

        AddressDAO addressDAO = new AddressDAO();

        try {
           addressDAO.doSave(addressBean);
        } catch (SQLException e) {
            ServerLogger.severe(AddressController.class.getName(), "AddressController doSave SQLException: " + e.getMessage());
        }
    }
}
