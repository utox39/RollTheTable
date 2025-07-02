package util;

import model.bean.InvoiceBean;
import model.bean.OrdersBean;
import model.bean.ProductBean;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Map;

public class InvoicePDFGenerator {

    public static File generateInvoicePDF(InvoiceBean invoice, OrdersBean order, String outputDir) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream content = new PDPageContentStream(document, page);
        content.setFont(PDType1Font.HELVETICA_BOLD, 12);

        // Header
        content.beginText();
        content.newLineAtOffset(50, 750);
        content.showText("Fattura Differita");
        content.endText();

        // Invoice details
        content.setFont(PDType1Font.HELVETICA, 10);
        writeText(content, "Cliente ID: " + invoice.getCustomerId(), 50, 730);
        writeText(content, "Ordine n°: " + invoice.getPurchaseId(), 50, 715);
        writeText(content, "Data Documento: " + new SimpleDateFormat("dd/MM/yyyy").format(order.getPurchaseDate()), 50, 700);
        writeText(content, "Numero Documento: F-" + invoice.getInvoiceId(), 50, 685);
        writeText(content, "Note: " + (invoice.getNotes() != null ? invoice.getNotes() : "-"), 50, 670);

        // Table headers
        float startY = 640;
        writeTableHeader(content, startY);

        // Table content
        float rowY = startY - 20;
        for (Map.Entry<ProductBean, OrdersBean.PurchaseInfo> entry : order.getProducts().entrySet()) {
            ProductBean product = entry.getKey();
            OrdersBean.PurchaseInfo info = entry.getValue();

            String[] row = {
                    String.valueOf(product.getProductId()),
                    product.getName(),
                    String.valueOf(info.getQuantity()),
                    "pz",
                    String.format("%.2f", info.getPriceAtPurchase()),
                    String.format("%.0f%%", invoice.getIva())
            };

            writeTableRow(content, row, rowY);
            rowY -= 15;
        }

        // Totals
        writeText(content, "Totale imponibile: € " + invoice.getTaxable().setScale(2, RoundingMode.HALF_UP), 400, 200);
        writeText(content, "IVA " + invoice.getIva() + "%: € " + invoice.getTax().setScale(2, RoundingMode.HALF_UP), 400, 185);
        writeText(content, "Totale Fattura: € " + invoice.getTaxable().add(invoice.getTax()).setScale(2, RoundingMode.HALF_UP), 400, 170);

        content.close();

        // Save to file
        Path outputPath = Path.of(outputDir, "invoice_" + invoice.getInvoiceId() + ".pdf");
        Files.createDirectories(outputPath.getParent());
        document.save(outputPath.toFile());
        document.close();

        return outputPath.toFile();
    }

    private static void writeText(PDPageContentStream content, String text, float x, float y) throws IOException {
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 10);
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
    }

    private static void writeTableHeader(PDPageContentStream content, float y) throws IOException {
        writeText(content, "Codice", 50, y);
        writeText(content, "Descrizione", 120, y);
        writeText(content, "Qtà", 300, y);
        writeText(content, "UM", 340, y);
        writeText(content, "Prezzo", 380, y);
        writeText(content, "IVA", 450, y);
    }

    private static void writeTableRow(PDPageContentStream content, String[] row, float y) throws IOException {
        writeText(content, row[0], 50, y);
        writeText(content, row[1], 120, y);
        writeText(content, row[2], 300, y);
        writeText(content, row[3], 340, y);
        writeText(content, row[4], 380, y);
        writeText(content, row[5], 450, y);
    }
}
