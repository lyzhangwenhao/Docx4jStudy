package test;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import java.io.File;
import java.util.List;

/**
 * ClassName: AddingFooter
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/3 10:38
 */
public class AddingAFooter {
    private static WordprocessingMLPackage wordMLPackage;

    static {
        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    private static ObjectFactory factory= Context.getWmlObjectFactory();

    /**
     *  First we create the package and the factory. Then we create the footer part,
     *  which returns a relationship. This relationship is then used to create
     *  a reference. Finally we add some text to the document and save it.
     */
    public static void main (String[] args) throws Docx4JException {
//        wordMLPackage = WordprocessingMLPackage.createPackage();
//        factory = Context.getWmlObjectFactory();

        Relationship relationship = null;
        try {
            relationship = createFooterPart(wordMLPackage,"中自庆安新能源技术有限公司");
        } catch (Exception e) {
            e.printStackTrace();
        }
        createFooterReference(wordMLPackage,relationship);

        wordMLPackage.getMainDocumentPart().addParagraphOfText("Hello Word!");

        wordMLPackage.save(new File("D:/TestFile/文件2.docx") );
    }

    /**
     *  This method creates a footer part and set the package on it. Then we add some
     *  text and add the footer part to the package. Finally we return the
     *  corresponding relationship.
     *
     *  @return
     *  @throws InvalidFormatException
     */
    public static Relationship createFooterPart(WordprocessingMLPackage wordMLPackage ,String content) throws Exception {
        FooterPart footerPart = new FooterPart();

        footerPart.setPackage(wordMLPackage);

        footerPart.setJaxbElement(createFooter(content));

        return wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
    }
    /**
     * 创建包含图片的内容
     *
     * @param word
     * @param sourcePart
     * @param imageFilePath
     * @return
     * @throws Exception
     */
    public static P newImage(WordprocessingMLPackage word,
                             Part sourcePart,
                             String imageFilePath) throws Exception {
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
                .createImagePart(word, sourcePart, new File(imageFilePath));
        //随机数ID
        int id = (int) (Math.random() * 10000);
        //这里的id不重复即可
        Inline inline = imagePart.createImageInline("image", "image", id, id * 2, false);

        ObjectFactory factory = new ObjectFactory();
        Drawing drawing = factory.createDrawing();
        drawing.getAnchorOrInline().add(inline);

        R r = factory.createR();
        r.getContent().add(drawing);

        P p = factory.createP();
        p.getContent().add(r);

        return p;
    }

    /**
     *  First we create a footer, a paragraph, a run and a text. We add the given
     *  given content to the text and add that to the run. The run is then added to
     *  the paragraph, which is in turn added to the footer. Finally we return the
     *  footer.
     *
     *  @param content
     *  @return
     */
    public static Ftr createFooter(String content) {
        Ftr footer = factory.createFtr();
        P paragraph = factory.createP();
        R run = factory.createR();
        Text text = new Text();
        text.setValue(content);
        run.getContent().add(text);
        paragraph.getContent().add(run);
        footer.getContent().add(paragraph);
        return footer;
    }

    /**
     *  First we retrieve the document sections from the package. As we want to add
     *  a footer, we get the last section and take the section properties from it.
     *  The section is always present, but it might not have properties, so we check
     *  if they exist to see if we should create them. If they need to be created,
     *  we do and add them to the main document part and the section.
     *  Then we create a reference to the footer, give it the id of the relationship,
     *  set the type to header/footer reference and add it to the collection of
     *  references to headers and footers in the section properties.
     *
     * @param relationship
     */
    public static void createFooterReference(WordprocessingMLPackage wordMLPackage,Relationship relationship) {
        List<SectionWrapper> sections =
                wordMLPackage.getDocumentModel().getSections();

        SectPr sectionProperties = sections.get(sections.size() - 1).getSectPr();
        // There is always a section wrapper, but it might not contain a sectPr
        if (sectionProperties==null ) {
            sectionProperties = factory.createSectPr();
            wordMLPackage.getMainDocumentPart().addObject(sectionProperties);
            sections.get(sections.size() - 1).setSectPr(sectionProperties);
        }

        FooterReference footerReference = factory.createFooterReference();
        footerReference.setId(relationship.getId());
        footerReference.setType(HdrFtrRef.DEFAULT);
        sectionProperties.getEGHdrFtrReferences().add(footerReference);
    }
}
