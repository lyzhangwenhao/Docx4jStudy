package test;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: CreateTable
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/5 10:20
 */
public class CreateTable {
    private WordprocessingMLPackage wpMLPackage;
    private ObjectFactory factory = Context.getWmlObjectFactory();

    {
        try {
            wpMLPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
    @Test
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
        addTableTc(tr, "报警机组",1100,true,"black");
        addTableTc(tr, "报警部件",1500,true,"black");
        addTableTc(tr, "报警次数", 1100,true,"black");
        addTableTc(tr, "触发报警门限及类型",2200,true,"black");
        addTableTc(tr, "最大报警特征值", 1800,true,"black");
        addTableTc(tr, "报警等级", 1100,true,"black");
        //将tr添加到table中
        tbl.getContent().add(tr);

        //第一行数据
        Tr dateTr1 = factory.createTr();
        addTableTc(dateTr1, "01#", 1100,false,"black");
        addTableTc(dateTr1, "发电机前轴承", 1500,false,"black");
        addTableTc(dateTr1, "8", 1100,false,"black");
        addTableTc(dateTr1, "3g", 1100,false,"black");
        addTableTc(dateTr1, "有效值", 1100,false,"black");
        addTableTc(dateTr1, "5g", 1800,false,"black");
        addTableTc(dateTr1, "预警", 1100,true,"#92d050");
        //将tr添加到table中
        tbl.getContent().add(dateTr1);

        //第二行数据
        Tr dateTr2 = factory.createTr();
        addTableTc(dateTr2, "01#", 1100,false,"black");
        addTableTc(dateTr2, "发电机前轴承", 1500,false,"black");
        addTableTc(dateTr2, "8", 1100,false,"black");
        addTableTc(dateTr2, "7g", 1100,false,"black");
        addTableTc(dateTr2, "峰值", 1100,false,"black");
        addTableTc(dateTr2, "10g", 1800,false,"black");
        addTableTc(dateTr2, "报警", 1100,true,"#ff0000");
        //将tr添加到table中
        tbl.getContent().add(dateTr2);


        wpMLPackage.getMainDocumentPart().addObject(tbl);
        try {
            wpMLPackage.save(new File("D:/TestFile/tableTest/"+System.currentTimeMillis()+".docx"));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加TableCell
     * @param tableRow
     * @param content
     */
    public void addTableTc(Tr tableRow,String content, int width,boolean isBold,String color){
        Tc tc = factory.createTc();
        P p = factory.createP();
        R r = factory.createR();
        Text text = factory.createText();

        //设置宽度
        setCellWidth(tc, width);
        //生成段落添加到单元格中
        text.setValue(content);
        r.getContent().add(text);
        //设置字体颜色，加粗
        RPr rPr = factory.createRPr();
        setTcFontColor(rPr,isBold, color);
        //设置字体
        RFonts rFonts = new RFonts();
        rFonts.setEastAsia("宋体");
        rPr.setRFonts(rFonts);
        //设置字体大小
        HpsMeasure fontSize = new HpsMeasure();
        fontSize.setVal(new BigInteger("20"));
        rPr.setSzCs(fontSize);
        rPr.setSz(fontSize);
        //将样式添加到段落中
        r.getContent().add(rPr);
        p.getContent().add(r);
        tc.getContent().add(p);
        //设置垂直居中
        setTcVAlign(tc, STVerticalJc.CENTER);
        //设置水平居中
        setTcJcAlign(tc, JcEnumeration.CENTER);
        //去除段后格式
        PPr pPr = p.getPPr();
        if (pPr==null){
            pPr = factory.createPPr();
        }
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing==null){
            spacing = new PPrBase.Spacing();
        }
        spacing.setAfter(new BigInteger("0"));
        pPr.setSpacing(spacing);
        p.setPPr(pPr);

        tableRow.getContent().add(tc);
    }

    /**
     * 设置字体大小
     * @param rPr
     * @param size
     */
    public void setFontSize(RPr rPr,String size){
        HpsMeasure fontSize = new HpsMeasure();
        fontSize.setVal(new BigInteger(size));
        rPr.setSzCs(fontSize);
        rPr.setSz(fontSize);
    }

    /**
     * 设置字体
     * @param rPr
     * @param font
     */
    public void setFont(RPr rPr,String font){
        RFonts rFonts = new RFonts();
        rFonts.setEastAsia(font);
        rPr.setRFonts(rFonts);
    }

    /**
     * 设置字体颜色，加粗
     * @param rPr
     * @param color
     */
    public void setTcFontColor(RPr rPr,boolean isBold,String color){
        if (color != null) {
            Color c = new Color();
            c.setVal(color);
            rPr.setColor(c);
        }
        if (isBold){
            //设置加粗
            BooleanDefaultTrue booleanDefaultTrue = factory.createBooleanDefaultTrue();
            booleanDefaultTrue.setVal(Boolean.TRUE);
            rPr.setB(booleanDefaultTrue);
        }
    }
    /**
     *  本方法创建一个单元格属性集对象和一个表格宽度对象. 将给定的宽度设置到宽度对象然后将其添加到
     *  属性集对象. 最后将属性集对象设置到单元格中.
     */
    private void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }
    /**
     *  本方法为表格添加边框
     */
    private void addBorders(Tbl table) {
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
    public TcPr getTcPr(Tc tc) {
        TcPr tcPr = tc.getTcPr();
        if (tcPr == null) {
            tcPr = new TcPr();
            tc.setTcPr(tcPr);
        }
        return tcPr;
    }
    /**
     * @Description: 设置单元格垂直对齐方式
     */
    public void setTcVAlign(Tc tc, STVerticalJc vAlignType) {
        if (vAlignType != null) {
            TcPr tcPr = getTcPr(tc);
            CTVerticalJc vAlign = new CTVerticalJc();
            vAlign.setVal(vAlignType);
            tcPr.setVAlign(vAlign);
        }
    }
    /**
     * @Description: 设置单元格水平对齐方式
     */
    public void setTcJcAlign(Tc tc, JcEnumeration jcType) {
        if (jcType != null) {
            List<P> pList = getTcAllP(tc);
            for (P p : pList) {
                setParaJcAlign(p, jcType);
            }
        }
    }
    public List<P> getTcAllP(Tc tc) {
        List<Object> objList = getAllElementFromObject(tc, P.class);
        List<P> pList = new ArrayList<P>();
        if (objList == null) {
            return pList;
        }
        for (Object obj : objList) {
            if (obj instanceof P) {
                P p = (P) obj;
                pList.add(p);
            }
        }
        return pList;
    }
    /**
     * @Description:得到指定类型的元素
     */
    public static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }

    /**
     * @Description: 设置段落水平对齐方式
     */
    public void setParaJcAlign(P paragraph, JcEnumeration hAlign) {
        if (hAlign != null) {
            PPr pprop = paragraph.getPPr();
            if (pprop == null) {
                pprop = new PPr();
                paragraph.setPPr(pprop);
            }
            Jc align = new Jc();
            align.setVal(hAlign);
            pprop.setJc(align);
        }
    }
}
