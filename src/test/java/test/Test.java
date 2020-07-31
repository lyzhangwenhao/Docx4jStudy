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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: Test
 * Description:
 *
 * @author Mi_dad
 * @date 2020/7/28 9:22
 */
public class Test {
    private Logger logger = Logger.getLogger(Test.class);
    private WordprocessingMLPackage wpMLPackage;

    @org.junit.Test
    public void method6(){

    }

    @org.junit.Test
    public void method5(){
        //输入毫秒数，转化为日期，用simpleDateFormat  +  Date 方法；
         //直接用SimpleDateFormat格式化 Date对象，即可得到相应格式的日期 字符串。

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        long time3 = 1470212176122L;

        Date date2 = new Date();
        date2.setTime(time3);
        System.out.println(simpleDateFormat.format(date2));
    }


    @org.junit.Test
    public void method4(){
        try {
            wpMLPackage = WordprocessingMLPackage.load(new File("D:/TestFile/test1.docx"));
            ObjectFactory factory = Context.getWmlObjectFactory();

            P para = factory.createP();

            Text text = factory.createText();
            text.setValue("这是method4测试内容");
            R r = factory.createR();
            r.getContent().add(text);
            para.getContent().add(r);

            String contentType = wpMLPackage.getContentType();
            logger.info("contentType -> "+contentType);

            wpMLPackage.getMainDocumentPart().getContent().add(para);
            wpMLPackage.save(new File("D:/TestFile/test1.docx"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 方法二：利用工厂类进行添加段落，步骤一般是：(1)创建R;(2)将内容元素B加到R中;(3)将R增加到A元素中;(4)将A元素加到mainDocumentPart内容中。
     */
    @org.junit.Test
    public  void method3(){
        String simpleText = "neirong";
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


    @org.junit.Test
    public void method2(){
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
    @org.junit.Test
    public void method1(){
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
