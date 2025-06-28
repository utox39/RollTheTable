import model.bean.CustomerBean;
import model.bean.PaymentMethodBean;
import model.dao.PaymentMethodDAO;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

@WebServlet("/secure/customer/payment-methods")
public class PaymentMethodController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        CustomerBean user = (CustomerBean) session.getAttribute("loggedUser");
        int customerId = user.getCustomerId();

        String owner =  request.getParameter("owner");
        String cardNumber = request.getParameter("card-number");
        String expirationDate = request.getParameter("expiration-date");

        String[] dateParts = expirationDate.split("/");
        int month = Integer.parseInt(dateParts[0]);
        int year = Integer.parseInt(dateParts[1]);
        LocalDate localDate = YearMonth.of(year, month).atDay(1);
        Date parsedDate = Date.valueOf(localDate);

        PaymentMethodBean paymentMethodBean = new PaymentMethodBean();
        paymentMethodBean.setCustomerId(customerId);
        paymentMethodBean.setCardNumber(cardNumber);
        paymentMethodBean.setOwner(owner);
        paymentMethodBean.setExpireDate(parsedDate);

        PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();

        try {
           paymentMethodDAO.doSave(paymentMethodBean);
        } catch (SQLException e) {
            ServerLogger.severe(PaymentMethodController.class.getName(), "PaymentMethodController doSave SQLException: " + e.getMessage());
        }
    }
}
