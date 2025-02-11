package hr.mipro;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java -jar TextExtractor.jar <fileName>");
            System.out.println("Supported extensions: pdf, doc, docx");
            System.out.println("Please provide a filename.");
            return;
        }

        String fileName = args[0];
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Usage: java -jar TextExtractor.jar <fileName>");
            System.out.println("Supported extensions: pdf, doc, docx");
            System.out.println("File does not exist: " + fileName); //Added filename for clarity
            return;
        }

        TextExtractor extractor = new TextExtractor(fileName);
        String txt = extractor.toText();
        System.out.println(txt);
    }
}