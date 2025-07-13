package control;

import model.bean.*;
import model.dao.InvoiceDAO;
import model.dao.OrdersDAO;
import util.InvoicePDFGenerator;
import util.ServerLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/secure/generate-invoice")
public class GenerateInvoiceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String orderIdParam = request.getParameter("orderId");
        InvoiceBean invoice = null;
        OrdersBean order = null;

        try {
            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                // ✅ 1) Get invoice by purchase ID from DB if orderId is provided
                int orderId = Integer.parseInt(orderIdParam);
                InvoiceDAO invoiceDAO = new InvoiceDAO();
                invoice = invoiceDAO.doRetrieveByPurchaseId(orderId);

                if (invoice == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "No invoice found for order ID: " + orderId);
                    return;
                }

                // Retrieve the order details for PDF generation
                OrdersDAO ordersDAO = new OrdersDAO();
                order = ordersDAO.doRetriveById(orderId);

                if (order == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "No order found for ID: " + orderId);
                    return;
                }

            } else {
                // ✅ 2) Fallback: Get invoice info from session if no orderId parameter
                HttpSession session = request.getSession(false);
                if (session == null) {
                    response.sendRedirect("views/login.jsp");
                    return;
                }

                order = (OrdersBean) session.getAttribute("orderDetails");
                invoice = (InvoiceBean) session.getAttribute("invoice"); // you might store invoice in session too

                if (order == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing order in session.");
                    return;
                }

                if (invoice == null) {
                    // If the invoice isn’t stored in session, try to fetch it by order ID
                    InvoiceDAO invoiceDAO = new InvoiceDAO();
                    invoice = invoiceDAO.doRetrieveByPurchaseId(order.getOrderId());

                    if (invoice == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "No invoice found for this session order.");
                        return;
                    }
                }
            }

            // ✅ 3) Generate PDF from invoice + order
            byte[] pdfBytes = InvoicePDFGenerator.generateInvoicePDF(invoice, order).toByteArray();

            // ✅ 4) Return PDF to user
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Fattura_" + invoice.getInvoiceId() + ".pdf");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();

        } catch (NumberFormatException e) {
            ServerLogger.severe(GenerateInvoiceController.class.getName(), "Invalid orderId parameter: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid orderId format.");
        } catch (SQLException e) {
            ServerLogger.severe(GenerateInvoiceController.class.getName(), "Database error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("views/fallback.jsp");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
