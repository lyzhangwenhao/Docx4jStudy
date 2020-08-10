package test;

import org.apache.log4j.Logger;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;

import java.io.File;

/**
 * ClassName: Test
 * Description:
 *
 * @author Mi_dad
 * @date 2020/7/28 9:22
 */
public class TestJava {
    public static Logger logger = Logger.getLogger(TestJava.class);
    public static WordprocessingMLPackage wpMLPackage;
    public static void main(String[] args) {
        //增加标题，段落
//        method1();

        //读取文佳后新加内容
//        method2();
        method3("method3内容元素");
    }


    public static void method3(String simpleText){
        try {
            wpMLPackage = WordprocessingMLPackage.load(new File("D:/TestFile/test1.docx"));

            ObjectFactory factory = Context.getWmlObjectFactory();
            P para = factory.createP();

            if (simpleText!=null){
                Text text = factory.createText();
                text.setValue(simpleText);
                R run = factory.createR();
                run.getContent().add(text);
                para.getContent().add(run);
            }

            wpMLPackage.getMainDocumentPart().getContent().add(para);
            wpMLPackage.save(new File("D:/TestFile/test1.docx"));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法二：利用工厂类进行添加段落，步骤一般是：(1)创建R;(2)将内容元素B加到R中;(3)将R增加到A元素中;(4)将A元素加到mainDocumentPart内容中。
     */
    public static void method2(){
        try {
            wpMLPackage = WordprocessingMLPackage.load(new File("D:/TestFile/test1.docx"));

            wpMLPackage.getMainDocumentPart().addParagraphOfText("新加的内容");
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "新加的标题");
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle", "新加的小标题");
            wpMLPackage.save(new File("D:/TestFile/test1.docx"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法一：直接利用wordprocessingMLPackage进行添加
     */
    public static void method1(){
        try {
            wpMLPackage = WordprocessingMLPackage.createPackage();

            wpMLPackage.getMainDocumentPart().addParagraphOfText("Hello world!");
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "Hello world2");
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Subtitle", "a subtitle");
            //存为新的文件
            wpMLPackage.save(new File("D:/TestFile/test1.docx"));

//            Docx4J.save(wordprocessingMLPackage, new File("D:/TestFile/test2.docx"), 1);

        } catch (Exception e) {
            logger.error("createDocx error:InvalidFormatException",e);
        }
    }


}
