package com.zzqa.utils;

import org.apache.commons.lang.StringUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: Docx4jUtil
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/5 9:37
 */
public class Docx4jUtil {
    private static ObjectFactory objectFactory = new ObjectFactory();

    /**
     * 插入下一页
     * @param wpMLPackage
     */
    public static void addNextPage(WordprocessingMLPackage wpMLPackage){
        P para = objectFactory.createP();
        SectPr sectPr = objectFactory.createSectPr();
        PPr pPr = objectFactory.createPPr();
        SectPr.Type sectPrType = objectFactory.createSectPrType();

        sectPrType.setVal("nextPage");
        sectPr.setType(sectPrType);
        pPr.setSectPr(sectPr);
        para.setPPr(pPr);
        wpMLPackage.getMainDocumentPart().addObject(para);
    }

    /**
     * @Description: 设置字体大小
     */
    public static void setFontSize(RPr runProperties, String fontSize) {
        if (StringUtils.isNotBlank(fontSize)) {
            HpsMeasure size = new HpsMeasure();
            size.setVal(new BigInteger(fontSize));
            runProperties.setSz(size);
            runProperties.setSzCs(size);
        }
    }

    /**
     * 添加图片到文档中
     * @param wpMLPackage
     * @param bytes
     * @throws Exception
     */
    public static void addImageToPackage(WordprocessingMLPackage wpMLPackage,byte [] bytes) throws Exception{
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wpMLPackage, bytes);
        int docPrId = 1;
        int cNvPrId = 2;
        Inline imageInline = imagePart.createImageInline("Filename hint", "Alternative", docPrId, cNvPrId, false);
        P p = addInlineImageToParagraph(imageInline);

        wpMLPackage.getMainDocumentPart().addObject(p);

    }

    /**
     * 添加内连对象到Paragraph中
     * @param inline
     * @return
     */
    private static P addInlineImageToParagraph(Inline inline){
        //添加内联对象到一个段落中
//        ObjectFactory objectFactory = new ObjectFactory();
        P p = objectFactory.createP();
        R run = objectFactory.createR();
        p.getContent().add(run);
        PPr pPr = objectFactory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null){
            jc = new Jc();
        }
        //设置居中
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);
        p.setPPr(pPr);
        Drawing drawing = objectFactory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);
        return p;
    }

    /**
     * 转换图片为byte数组
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] convertImageToByteArray(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE){
            System.out.println("File too large!!");
        }
        byte [] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        while (offset<bytes.length && (numRead = is.read(bytes,offset,bytes.length-offset)) >=0){
            offset += numRead;
        }
        //确认所有的字节都被读取
        if (offset < bytes.length){
            System.out.println("Could not completely read file"+file.getName());
        }
        is.close();
        return bytes;
    }
    /**
     * 添加图片标题
     * @param wpMLPackage
     * @param title
     */
    public static void addTableTitle(WordprocessingMLPackage wpMLPackage,String title){
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = objectFactory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null){
            jc = new Jc();
        }
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);

        Text text = objectFactory.createText();
        text.setValue(title);
        r.getContent().add(text);

        p.getContent().add(r);
        p.setPPr(pPr);
        wpMLPackage.getMainDocumentPart().addObject(p);
    }

    /**
     * 设置标题为黑色(未完成)
     * @return
     */
    public R setTitleStyle(){
        R r = objectFactory.createR();
        Color color = new Color();
        color.setVal("#000000");
        RPr rPr = objectFactory.createRPr();
        rPr.setColor(color);
        r.getContent().add(rPr);
        return r;
    }

    /**
     * 时间转换
     * @param date
     * @return
     */
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy年M月d日");
        //TODO 删除输出语句
        System.out.println("转换时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        return sdf.format(date);
    }
}
