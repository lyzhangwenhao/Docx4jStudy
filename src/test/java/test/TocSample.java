package test;


import org.docx4j.Docx4jProperties;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.toc.TocGenerator;

/**
 * ClassName: TocSample
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/13 8:41
 */
public class TocSample {


    static boolean update = false;

    public static void main(String[] args) throws Exception{

        String input_DOCX = System.getProperty("user.dir") + "/sample-docs/toc.docx";

        // Load input_template.docx
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        documentPart.addStyledParagraphOfText("Heading1", "Heading 1");
        documentPart.addStyledParagraphOfText("Heading2", "Heading 2");

        TocGenerator tocGenerator = new TocGenerator(wordMLPackage);

        // If you want to automatically fix any broken bookmarks
        Docx4jProperties.setProperty("docx4j.toc.BookmarksIntegrity.remediate", true);

//        	Toc.setTocHeadingText("Sumário");
        tocGenerator.generateToc(true);
        tocGenerator.updateToc(true); // including page numbers

        wordMLPackage.save(new java.io.File("D:/AutoExport/OUT_TocUpdateDemo.docx") );

    }
}
