package util;

import model.bean.InvoiceBean;
import model.bean.OrdersBean;
import model.bean.ProductBean;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class InvoicePDFGenerator {

    public static ByteArrayOutputStream generateInvoicePDF(InvoiceBean invoice, OrdersBean order) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Fattura n°" + invoice.getPurchaseId());
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 10);
                float y = 730;

                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                contentStream.showText("Cliente ID: " + invoice.getCustomerId());
                contentStream.endText();

                y -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y);
                contentStream.showText("Data acquisto: " + order.getPurchaseDate());
                contentStream.endText();

                // Table Header
                y -= 40;
                writeRow(contentStream, y, "Codice", "Descrizione", "Qtà", "Prezzo", "Totale");

                // Table rows
                for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> entry : order.getProducts().entrySet()) {
                    ProductBean product = entry.getKey();
                    OrdersBean.PurchaseInfo info = entry.getValue();
                    y -= 15;

                    String total = BigDecimal.valueOf(info.getPriceAtPurchase())
                            .multiply(BigDecimal.valueOf(info.getQuantity()))
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .toPlainString();

                    writeRow(contentStream, y,
                            product.getProductId(),
                            product.getName(),
                            String.valueOf(info.getQuantity()),
                            String.format("€%.2f", info.getPriceAtPurchase()),
                            "€" + total);
                }

                // Totals
                y -= 40;
                writeText(contentStream, "Totale imponibile: €" + invoice.getTaxable(), 400, y);
                y -= 15;
                writeText(contentStream, "IVA (" + invoice.getIva() + "%): €" + invoice.getTax(), 400, y);
                y -= 15;
                writeText(contentStream, "Totale Fattura: €" +
                        invoice.getTaxable().add(invoice.getTax()), 400, y);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream;
        }
    }

    private static void writeText(PDPageContentStream cs, String text, float x, float y) throws IOException {
        cs.beginText();
        cs.setFont(PDType1Font.HELVETICA, 10);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private static void writeRow(PDPageContentStream cs, float y, String... columns) throws IOException {
        float[] xOffsets = {50, 120, 300, 380, 450};
        for (int i = 0; i < columns.length; i++) {
            writeText(cs, columns[i], xOffsets[i], y);
        }
    }
}
