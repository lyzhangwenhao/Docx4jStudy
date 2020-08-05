package test;

import org.apache.log4j.Logger;
import org.docx4j.jaxb.Context;
import org.docx4j.model.properties.table.tr.TrHeight;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
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
    private ObjectFactory factory = Context.getWmlObjectFactory();

    {
        try {
            wpMLPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void method8(){

        File mkdirPath = new File("D:/chart/pieChart");
        if(!mkdirPath.exists() && !mkdirPath.isDirectory()){
            System.out.println("不存在");
            mkdirPath.mkdirs();
            System.out.println(mkdirPath.getAbsolutePath());
        }else {
            System.out.println("存在");
        }
    }

    @org.junit.Test
    public void method7(){

        Tbl tbl = factory.createTbl();
        //给table添加边框
        addBorders(tbl);
        Tr tr = factory.createTr();
        //单元格居中对齐
        Jc jc = new Jc();
        jc.setVal(JcEnumeration.CENTER);
        TblPr tblPr = tbl.getTblPr();
        tblPr.setJc(jc);
        tbl.setTblPr(tblPr);

        //表格表头
        CTBackground ctBackground = new CTBackground();//-------------------------------------------------------------------
        ctBackground.setColor("#4f81bd");



        addTableTc(tr, "报警机组",1100);
        addTableTc(tr, "机警部件",1100);
        addTableTc(tr, "报警次数", 1100);
        addTableTc(tr, "触发报警门限及类型",2200);
        addTableTc(tr, "最大报警特征值", 1800);
        addTableTc(tr, "报警等级", 1100);
        //将tr添加到table中
        tbl.getContent().add(tr);

        //第一行数据
        Tr dateTr1 = factory.createTr();
        addTableTc(dateTr1, "01#", 1100);
        addTableTc(dateTr1, "发电机前轴承", 1100);
        addTableTc(dateTr1, "8", 1100);
        addTableTc(dateTr1, "3g", 1100);
        addTableTc(dateTr1, "有效值", 1100);
        addTableTc(dateTr1, "5g", 1800);
        addTableTc(dateTr1, "预警", 1100);
        //将tr添加到table中
        tbl.getContent().add(dateTr1);

        //第一行数据
        Tr dateTr2 = factory.createTr();
        addTableTc(dateTr2, "01#", 1100);
        addTableTc(dateTr2, "发电机前轴承", 1100);
        addTableTc(dateTr2, "8", 1100);
        addTableTc(dateTr2, "7g", 1100);
        addTableTc(dateTr2, "峰值", 1100);
        addTableTc(dateTr2, "10g", 1800);
        addTableTc(dateTr2, "预警", 1100);
        //将tr添加到table中
        tbl.getContent().add(dateTr2);

        wpMLPackage.getMainDocumentPart().addObject(tbl);
        try {
            wpMLPackage.save(new File("D:/TestFile/文件测试.docx"));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加TableCell
     * @param tableRow
     * @param content
     */
    public void addTableTc(Tr tableRow,String content, int width){
        Tc tc = factory.createTc();
        //设置宽度
        setCellWidth(tc, width);
        tc.getContent().add(wpMLPackage.getMainDocumentPart().createParagraphOfText(content));

        //设置垂直居中
        TcPr tcPr = tc.getTcPr();
        if (tcPr == null){
            tcPr = new TcPr();
        }
        CTVerticalJc ctVerticalJc = factory.createCTVerticalJc();
        ctVerticalJc.setVal(STVerticalJc.CENTER);
        tcPr.setVAlign(ctVerticalJc);

        tc.setTcPr(tcPr);

        tableRow.getContent().add(tc);
    }
    /**
     *  本方法创建一个单元格属性集对象和一个表格宽度对象. 将给定的宽度设置到宽度对象然后将其添加到
     *  属性集对象. 最后将属性集对象设置到单元格中.
     */
    private static void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }
    /**
     *  本方法为表格添加边框
     */
    private static void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("#95b3d7");
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    @org.junit.Test
    public void method6(){
        try {
            wpMLPackage = WordprocessingMLPackage.createPackage();

            File logo = new File("./resources/images/logo.png");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void method5(){
        //输入毫秒数，转化为日期，用simpleDateFormat  +  Date 方法；
         //直接用SimpleDateFormat格式化 Date对象，即可得到相应格式的日期 字符串。

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        long time3 = 1470212176122L;
        String format = transitionDate(time3);
        System.out.println(format);
    }
    public String transitionDate(Long createTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        simpleDateFormat.applyPattern("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        date.setTime(createTime);
        String format = simpleDateFormat.format(date);
        return format;
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
