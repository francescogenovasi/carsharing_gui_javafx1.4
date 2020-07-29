package it.unisalento.pps1920.carsharing.util;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;

import java.io.IOException;
import java.util.ArrayList;

public class PdfHelper {

    private static PdfHelper instance;

    public static synchronized PdfHelper getInstance() {
        if(instance == null)
            instance = new PdfHelper();
        return instance;
    }

    public void creaPdf(ArrayList<String> testopdf, int num) {
        //Creating PDF document object, Creates an empty PDF document.
        PDDocument document = new PDDocument();

        /*Creates a new PDPage instance for embedding, with a size of U.S. 8.5 x 11 inches. 215.9 x 27.94 centimetri. A4*/
        PDPage page = new PDPage();
        //Adding the blank page to the document
        document.addPage(page);

        try {
            /*PDPageContentStream(PDDocument document, PDPage sourcePage)
            Create a new PDPage content stream.*/
            PDPageContentStream contenutopdf = new PDPageContentStream(document, page);

            //Begin the Content stream
            /*Begin some text operations.*/
            contenutopdf.beginText();

            //Setting the font to the Content stream
            /*void setFont(PDFont font, float fontSize)
            Set the font and font size to draw text with*/
            contenutopdf.setFont(PDType1Font.TIMES_ROMAN, 12);

            //Setting the position for the line
            /*void newLineAtOffset(float tx, float ty)
            The Td operator.
            I PDF usano un classico sistema di coordinate cartesiane con x crescente da sinistra
            a destra e y crescente dal basso verso l'alto*/

            contenutopdf.newLineAtOffset(25, 792-25);//792-25, 580 max "a"

            //String text = "This is the sample document and we are adding content to it.";

            //Adding text in the form of string
            /*void showText(String text)
            Shows the given text at the location specified by the current text matrix*/
            //contenutopdf.showText(testopdf);
            for(String text : testopdf) {
                //Adding text in the form of string
                contenutopdf.showText(text);
                contenutopdf.newLineAtOffset(0, -25);
            }

            //Ending the content stream
            /*void endText()
            End some text operations.*/
            contenutopdf.endText();

            //System.out.println("Content added");

            //Closing the content stream
            /*void close()
            Close the content stream.*/
            contenutopdf.close();

            //Saving the document
            /*void save(String fileName)
            Save the document to a file.*/
            document.save("/Users/francescogenovasi/IdeaProjects/pdf_creati/" + num + ".pdf"); //todo cambiare percorso se si cambia macchina

            //Closing the document
            /* void close()
            This will close the underlying COSDocument object.*/
            document.close();

            //System.out.println("PDF created");

        } catch (IOException e) {
            e.printStackTrace();

            //System.out.println("PDF NOT created");
        }
    }

    public void creaPdfAdmin(ArrayList<String> testopdf) {
        //Creating PDF document object, Creates an empty PDF document.
        PDDocument document = new PDDocument();

        /*Creates a new PDPage instance for embedding, with a size of U.S. 8.5 x 11 inches. 215.9 x 27.94 centimetri. A4*/
        PDPage page = new PDPage();
        //Adding the blank page to the document
        document.addPage(page);

        try {
            /*PDPageContentStream(PDDocument document, PDPage sourcePage)
            Create a new PDPage content stream.*/
            PDPageContentStream contenutopdf = new PDPageContentStream(document, page);

            //Begin the Content stream
            /*Begin some text operations.*/
            contenutopdf.beginText();

            //Setting the font to the Content stream
            /*void setFont(PDFont font, float fontSize)
            Set the font and font size to draw text with*/
            contenutopdf.setFont(PDType1Font.TIMES_ROMAN, 12);

            //Setting the position for the line
            /*void newLineAtOffset(float tx, float ty)
            The Td operator.
            I PDF usano un classico sistema di coordinate cartesiane con x crescente da sinistra
            a destra e y crescente dal basso verso l'alto*/

            contenutopdf.newLineAtOffset(25, 792-25);//792-25, 580 max "a"

            //String text = "This is the sample document and we are adding content to it.";

            //Adding text in the form of string
            /*void showText(String text)
            Shows the given text at the location specified by the current text matrix*/
            //contenutopdf.showText(testopdf);
            for(String text : testopdf) {
                //Adding text in the form of string
                contenutopdf.showText(text);
                contenutopdf.newLineAtOffset(0, -25);
            }

            //Ending the content stream
            /*void endText()
            End some text operations.*/
            contenutopdf.endText();

            //System.out.println("Content added");

            //Closing the content stream
            /*void close()
            Close the content stream.*/
            contenutopdf.close();

            //Saving the document
            /*void save(String fileName)
            Save the document to a file.*/
            document.save("/Users/francescogenovasi/IdeaProjects/pdf_creati/admin.pdf"); //todo cambiare percorso se si cambia macchina

            //Closing the document
            /* void close()
            This will close the underlying COSDocument object.*/
            document.close();

            //System.out.println("PDF created");

        } catch (IOException e) {
            e.printStackTrace();

            //System.out.println("PDF NOT created");
        }
    }

}
