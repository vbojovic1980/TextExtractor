package hr.mipro;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TextExtractor {

    private String fileName;
    public TextExtractor() {
    }
    public TextExtractor(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String toText() throws Exception {
        if (!new File(fileName).exists())
            throw new Exception("File doesn't exist: " + fileName);
        String[] parts = fileName.toString().split("\\.");
        String extension = (parts.length > 1) ? parts[parts.length - 1] : "";
        String txt = "";
        switch (extension) {
            case "pdf":
                txt = pdf2text(fileName);
                break;
            case "doc":
                txt = doc2text(fileName);
                break;
            case "docx":
                txt = docx2text(fileName);
                break;
        }
        return txt;
    }
    public String pdf2text(String pdfPath){
        StringBuilder text = new StringBuilder();
        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            if (!document.isEncrypted()) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                text.append(pdfStripper.getText(document));
            } else {
                System.out.println("The document is encrypted and cannot be processed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString().trim();
    }
    public String doc2text(String docPath){
        StringBuilder text = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(docPath);
             HWPFDocument document = new HWPFDocument(fis);
             WordExtractor extractor = new WordExtractor(document)) {
            text.append(extractor.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString().trim();
    }
    public String docx2text(String docxPath) throws Exception {
        StringBuilder text = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(docxPath);
             XWPFDocument document = new XWPFDocument(fis)) {

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString().trim();
    }
}
