import model.bean.*;
import model.dao.InvoiceDAO;
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
import java.util.Map;

// TODO: change route to /secure/generate-invoice (?)
@WebServlet("/generate-invoice")
public class GenerateInvoiceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session not found.");
            return;
        }

        OrdersBean order = (OrdersBean) session.getAttribute("orderDetails");
        PaymentMethodBean paymentMethod = (PaymentMethodBean) session.getAttribute("paymentMethod");
        AddressBean shippingAddress = (AddressBean) session.getAttribute("shippingAddress");
        AddressBean billingAddress = (AddressBean) session.getAttribute("billingAddress");
        CustomerBean customer = (CustomerBean) session.getAttribute("loggedUser");

        if (order == null || customer == null || paymentMethod == null || shippingAddress == null || billingAddress == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing session attributes for invoice generation.");
            return;
        }

        InvoiceBean invoice = new InvoiceBean();

        int invoiceId = 0;

        try {
            // === Calculate invoice amounts ===
            final BigDecimal ivaRate = new BigDecimal("22");
            BigDecimal taxableTotal = BigDecimal.ZERO;

            for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> entry : order.getProducts().entrySet()) {
                BigDecimal unitPrice = BigDecimal.valueOf(entry.getValue().getPriceAtPurchase());
                int quantity = entry.getValue().getQuantity();
                taxableTotal = taxableTotal.add(unitPrice.multiply(BigDecimal.valueOf(quantity)));
            }

            BigDecimal taxAmount = taxableTotal.multiply(ivaRate).divide(new BigDecimal("100"));
            BigDecimal totalWithIva = taxableTotal.add(taxAmount);

            // === Populate invoice bean ===
            invoice.setCustomerId(customer.getCustomerId());
            invoice.setPurchaseId(order.getOrderId());
            invoice.setTaxable(taxableTotal);
            invoice.setTax(taxAmount);
            invoice.setIva(ivaRate);
            invoice.setNotes("Fattura generata automaticamente");

            // === Save invoice ===
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceId = invoiceDAO.doSave(invoice); // assumes it sets the invoice ID (e.g. auto-incremented)
        } catch (SQLException e) {
            ServerLogger.severe(GenerateInvoiceController.class.getName(), "SQLException: " + e.getMessage());
        }

        // === Generate PDF ===
        byte[] pdfBytes = InvoicePDFGenerator.generateInvoicePDF(invoice, order).toByteArray();

        // === Return PDF response ===
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Fattura_" + invoiceId + ".pdf");
        response.setContentLength(pdfBytes.length);
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }
}
