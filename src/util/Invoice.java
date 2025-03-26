package util;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;

// NOTE: Sample code to create invoices
// TODO: refactor

class Invoice {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            // Creazione della pagina
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Creazione del contenuto della fattura
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Intestazione
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Fattura Differita");
            contentStream.endText();

            // Dati intestazione
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            writeText(contentStream, "Anna Bianchi", 50, 730);
            writeText(contentStream, "Via delle Frasche 3", 50, 715);
            writeText(contentStream, "52011 Bibbiena (AR), Italia", 50, 700);

            writeText(contentStream, "P.IVA: 3477472234", 390, 730);
            writeText(contentStream, "Modalità di pagamento: 120 giorni fine mese ", 390, 715);
            writeText(contentStream, "Numero Documento: 0003-2017", 390, 700);
            writeText(contentStream, "Data Documento: 15/03/2017", 390, 685);

            // Tabella intestazione
            float startY = 650;
            writeTableHeader(contentStream, startY);

            // Dati della fattura
            String[][] data = {
                    {"5311", "ACESIA chiave combinata 13", "1", "Pz", "3.69", "22%"},
                    {"5314", "ACESIA chiave combinata 17", "2", "Pz", "8.52", "22%"},
                    {"600031", "Acetone 99% 1L", "2", "Pz", "4.76", "22%"},
                    {"41700114", "Abitare interno bianco 2.14L", "1", "Pz", "28.69", "22%"},
            };

            float rowY = startY - 20;
            for (String[] row : data) {
                writeTableRow(contentStream, row, rowY);
                rowY -= 15;
            }

            // Totale fattura
            writeText(contentStream, "Totale imponibile: € 90.10", 400, 200);
            writeText(contentStream, "IVA 22%: € 19.82", 400, 185);
            writeText(contentStream, "Totale Fattura: € 109.92", 400, 170);

            contentStream.close();

            // Salvataggio PDF
            document.save("fattura_apache.pdf");
            System.out.println("Fattura generata con successo: fattura_apache.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per scrivere del testo
    private static void writeText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    // Metodo per scrivere l'intestazione della tabella
    private static void writeTableHeader(PDPageContentStream contentStream, float y) throws IOException {
        writeText(contentStream, "Codice", 50, y);
        writeText(contentStream, "Descrizione", 120, y);
        writeText(contentStream, "Qtà", 300, y);
        writeText(contentStream, "UM", 340, y);
        writeText(contentStream, "Prezzo", 380, y);
        writeText(contentStream, "IVA", 450, y);
    }

    // Metodo per scrivere una riga della tabella
    private static void writeTableRow(PDPageContentStream contentStream, String[] row, float y) throws IOException {
        writeText(contentStream, row[0], 50, y);
        writeText(contentStream, row[1], 120, y);
        writeText(contentStream, row[2], 300, y);
        writeText(contentStream, row[3], 340, y);
        writeText(contentStream, row[4], 380, y);
        writeText(contentStream, row[5], 450, y);
    }
}
