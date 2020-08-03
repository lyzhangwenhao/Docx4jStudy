package test;

import org.apache.commons.lang.StringUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: PageContent
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/3 11:19
 */
public class PageContent {
    private ObjectFactory objectFactory = new ObjectFactory();

    public WordprocessingMLPackage createPageContent(WordprocessingMLPackage wpMLPackage){
        try {
            Relationship relationship = AddingAFooter.createFooterPart(wpMLPackage,"浙江中自庆安新能源技术有限公司");
            AddingAFooter.createFooterReference(wpMLPackage,relationship);

            //下一页
            addNextPage(wpMLPackage);
            wpMLPackage.getMainDocumentPart().addParagraphOfText("第二页");
            addNextPage(wpMLPackage);
            wpMLPackage.getMainDocumentPart().addParagraphOfText("第三页");

//            wpMLPackage.save(new File("D:/TestFile/文件1.docx"));
            System.out.println("PageContent Success......");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wpMLPackage;
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
     * 插入下一页
     * @param wpMLPackage
     */
    public void addNextPage(WordprocessingMLPackage wpMLPackage){
//        ObjectFactory objectFactory = new ObjectFactory();

//        P para = objectFactory.createP();
//        SectPr sectPr = objectFactory.createSectPr();
//        PPr pPr = objectFactory.createPPr();
//        SectPr.Type sectPrType = objectFactory.createSectPrType();
//
//        sectPrType.setVal("nextPage");
//        sectPr.setType(sectPrType);
//        pPr.setSectPr(sectPr);
//        para.setPPr(pPr);
//        wpMLPackage.getMainDocumentPart().addObject(para);

        Br breakBr = new Br();
        breakBr.setType(STBrType.PAGE);
        P paragraph = objectFactory.createP();
        paragraph.getContent().add(breakBr);
        wpMLPackage.getMainDocumentPart().getContent().add(paragraph);

    }


    /**
     * 添加换行符
     * @param wpMLPackage
     * @param brNum 换行数量
     */
    public void addBr(WordprocessingMLPackage wpMLPackage, int brNum){
//        ObjectFactory objectFactory = new ObjectFactory();
        P para = objectFactory.createP();
        R run = objectFactory.createR();
        Br br = new Br();
        //循环插入
        for (int i=0; i<brNum; i++){
            run.getContent().add(br);
        }
        para.getContent().add(run);
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
    public void addImageToPackage(WordprocessingMLPackage wpMLPackage,byte [] bytes) throws Exception{
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
    public P addInlineImageToParagraph(Inline inline){
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
    public byte[] convertImageToByteArray(File file) throws Exception {
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

    public String getNowDate(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
//        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        sdf.applyPattern("yyyy年 MM月 dd日");
        Date date = new Date();// 获取当前时间
        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        return sdf.format(date);
    }
}