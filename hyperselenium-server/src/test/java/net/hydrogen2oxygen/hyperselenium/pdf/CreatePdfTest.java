package net.hydrogen2oxygen.hyperselenium.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.Test;

import java.io.IOException;

public class CreatePdfTest {

    @Test
    public void testPdfGeneration() {

        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            float currentY = PDRectangle.A4.getUpperRightY() - 40;

            // Add text
            addText(doc, page, font, 12, 30, currentY, "hello world");
            currentY -= 14;
            addText(doc, page, font, 12, 30, currentY, "a new line");
            currentY -= 14;

            // Add image
            PDImageXObject pdImage = PDImageXObject.createFromFile("docs/protocol.PNG", doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
            float scale = 0.5f;
            currentY -= pdImage.getHeight() / 2;
            contentStream.drawImage(pdImage, 30, currentY, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
            contentStream.close();


            doc.save("target/test.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addText(PDDocument doc, PDPage page, PDFont font, float fontSize, float x, float y, String text) throws IOException {
        PDPageContentStream contents = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contents.beginText();
        contents.setFont(font, fontSize);
        contents.newLineAtOffset(x, y);
        contents.showText(text);
        contents.endText();
        contents.close();
    }
}
