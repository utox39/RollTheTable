package util;

import model.bean.CustomerBean;
import model.bean.InvoiceBean;
import model.bean.OrdersBean;
import model.bean.ProductBean;
import model.dao.CustomerDAO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Map;

public class InvoicePDFGenerator {

    public static ByteArrayOutputStream generateInvoicePDF(InvoiceBean invoice, OrdersBean order) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            CustomerBean customer = new CustomerDAO().doRetriveById(invoice.getCustomerId());

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // HEADER AZIENDA
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 790);
                contentStream.showText("RollTheTable s.p.a");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 10);
                writeText(contentStream, "Via Giovanni Paolo II,132", 50, 775);
                writeText(contentStream, "84084 Fisciano (SA) – Italia", 50, 760);
                writeText(contentStream, "P.IVA: 12345678901", 50, 745);
                writeText(contentStream, "Tel: +39 00 0000000 – Email: info@RollTheTable.com", 50, 730);

                contentStream.moveTo(50, 720);
                contentStream.lineTo(550, 720);
                contentStream.stroke();

                // FATTURA INFO
                writeTextBold(contentStream, "FATTURA N° " + invoice.getPurchaseId(), 50, 705);
                writeText(contentStream, "Data: " + order.getPurchaseDate(), 50, 690);

                // INFO CLIENTE
                writeTextBold(contentStream, "Cliente:", 50, 670);
                writeText(contentStream, customer.getName() + " " + customer.getSurname(), 50, 655);
                writeText(contentStream, "Email: " + customer.getEmail(), 50, 640);
                writeText(contentStream, "Telefono: " + customer.getPhone(), 50, 625);

                // TABLE
                float y = 600;
                writeRow(contentStream, y, "Codice", "Descrizione", "Qtà", "Prezzo unitario", "Totale");

                y -= 15;
                BigDecimal grandTotal = BigDecimal.ZERO;

                for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> entry : order.getProducts().entrySet()) {
                    ProductBean product = entry.getKey();
                    OrdersBean.PurchaseInfo info = entry.getValue();

                    BigDecimal lineTotal = BigDecimal.valueOf(info.getPriceAtPurchase())
                            .multiply(BigDecimal.valueOf(info.getQuantity()))
                            .setScale(2, RoundingMode.HALF_UP);

                    grandTotal = grandTotal.add(lineTotal);

                    writeRow(contentStream, y,
                            product.getProductId(),
                            product.getName(),
                            String.valueOf(info.getQuantity()),
                            "€ " + String.format("%.2f", info.getPriceAtPurchase()),
                            "€ " + String.format("%.2f", lineTotal));

                    y -= 15;
                }

                // TOTALE E IVA
                y -= 20;
                BigDecimal ivaAmount = grandTotal.multiply(invoice.getIva())
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                BigDecimal totalWithoutIva = grandTotal.subtract(ivaAmount);

                writeText(contentStream, "Totale senza IVA: € " + String.format("%.2f", totalWithoutIva), 400, y);
                y -= 15;
                writeText(contentStream, "IVA (" + invoice.getIva() + "%): € " + String.format("%.2f", ivaAmount), 400, y);
                y -= 15;
                writeTextBold(contentStream, "Totale Fattura: € " + String.format("%.2f", grandTotal), 400, y);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream;

        } catch (SQLException e) {
            ServerLogger.severe(InvoicePDFGenerator.class.getName(), "InvoicePDFGenerator SQLException: " + e.getMessage());
        }
        return null;
    }

    private static void writeText(PDPageContentStream cs, String text, float x, float y) throws IOException {
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA, 10);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private static void writeTextBold(PDPageContentStream cs, String text, float x, float y) throws IOException {
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private static void writeRow(PDPageContentStream cs, float y, String... columns) throws IOException {
        float[] xOffsets = {50, 120, 300, 380, 450};
        for (int i = 0; i < columns.length && i < xOffsets.length; i++) {
            writeText(cs, columns[i], xOffsets[i], y);
        }
    }
}
