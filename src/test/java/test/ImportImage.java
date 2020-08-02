package test;

import org.apache.commons.lang.StringUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ClassName: Test2
 * Description:
 *
 * @author Mi_dad
 * @date 2020/7/31 14:30
 */
public class ImportImage {
    private WordprocessingMLPackage wpMLPackage;

    @Test
    public void method1(){
        try {
            wpMLPackage = WordprocessingMLPackage.createPackage();

            //logo图片插入
            File logo = new File("src/test/resources/images/logo.png");
            byte[] logoBytes = convertImageToByteArray(logo);
            addImageToPackage(wpMLPackage, logoBytes);

            //横线插入
            File linePng = new File("src/test/resources/images/横线.png");
            byte[] linePngBytes = convertImageToByteArray(linePng);
            addImageToPackage(wpMLPackage, linePngBytes);

            //加入换行
            addBr(wpMLPackage,1);

            //封面标题
            String largeTitlePart1 = "XXXX风电场风电机组";
            String largeTitlePart2 = "2019年7月31日-2020年7月30日";
            String largeTitlePart3 = "监测报告";
            addTitleWordToPackage(wpMLPackage, largeTitlePart1);
            addTitleWordToPackage(wpMLPackage, largeTitlePart2);
            addTitleWordToPackage(wpMLPackage, largeTitlePart3);

            //插入换行符
            addBr(wpMLPackage,2);
            //横线插入
            addImageToPackage(wpMLPackage, linePngBytes);
            //插入换行符
            addBr(wpMLPackage,1);

            //向封面插入小标题
            addTitleLittle(wpMLPackage,"浙江中自庆安新能源技术有限公司");
            addTitleLittle(wpMLPackage,"检测与诊断中心");
            addTitleLittle(wpMLPackage,"联系电话：4000093668-7");
            addTitleLittle(wpMLPackage,"邮箱：service@windit.com.cn");
            addTitleLittle(wpMLPackage,getNowDate());

            //下一页
            addNextPage(wpMLPackage);
            //页眉
            HeaderPart header = createHeader(wpMLPackage);
            ObjectFactory factory = new ObjectFactory();
            P p = factory.createP();
            R r = factory.createR();
            Text text = new Text();
            text.setValue("这是一个页眉");
            r.getContent().add(text);
            p.getContent().add(r);
            header.getContent().add(p);
            header.getContent().add(newImage(wpMLPackage, header, "src/test/resources/images/logo.png"));
            wpMLPackage.getMainDocumentPart().addObject(header);


            wpMLPackage.save(new File("D:\\00_工作\\Docx4j学习\\文件1.docx"));
            System.out.println("Success......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HeaderPart createHeader(WordprocessingMLPackage wpMLPackage){
        HeaderPart headerPart = null;
        try {
            headerPart = new HeaderPart();

            Relationship relationship = wpMLPackage.getMainDocumentPart().addTargetPart(headerPart);
            createHeaderReference(wpMLPackage,relationship);
            return headerPart;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return headerPart;
    }
    public void createHeaderReference(WordprocessingMLPackage wpMLPackage,Relationship relationship){
        List<SectionWrapper> sections = wpMLPackage.getDocumentModel().getSections();
        ObjectFactory factory = new ObjectFactory();

        SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
        if (sectPr==null){
            sectPr = factory.createSectPr();
            wpMLPackage.getMainDocumentPart().addObject(sectPr);
            sections.get(sections.size()-1).setSectPr(sectPr);
        }

        HeaderReference headerReference = factory.createHeaderReference();
        headerReference.setId(relationship.getId());
        headerReference.setType(HdrFtrRef.DEFAULT);
        sectPr.getEGHdrFtrReferences().add(headerReference);
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
        ObjectFactory objectFactory = new ObjectFactory();

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
     * 插入封面小标题
     * @param wpMLPackage
     * @param word
     */
    public void addTitleLittle(WordprocessingMLPackage wpMLPackage,String word){
        ObjectFactory factory = new ObjectFactory();
        P para = factory.createP();
        R run = factory.createR();
        Text text = factory.createText();
        //添加标题内容
        text.setValue(word);
        run.getContent().add(text);
        //设置居中格式
        PPr pPr = factory.createPPr();
        Jc jc = pPr.getJc();
        if (jc==null){
            jc = new Jc();
        }
        jc.setVal(JcEnumeration.CENTER);

        RPr rPr = factory.createRPr();
        //设置颜色
        Color color = new Color();
        color.setVal("#0070c0");
        rPr.setColor(color);
        //设置字体大小
        HpsMeasure fontSize = new HpsMeasure();
        fontSize.setVal(new BigInteger("28"));
        rPr.setSzCs(fontSize);
        rPr.setSz(fontSize);

        //设置加粗
        BooleanDefaultTrue booleanDefaultTrue = factory.createBooleanDefaultTrue();
        booleanDefaultTrue.setVal(Boolean.TRUE);
        rPr.setB(booleanDefaultTrue);

        //设置字体
        RFonts rFonts = new RFonts();
        rFonts.setEastAsia("宋体");
        rPr.setRFonts(rFonts);


        //将格式和内容添加进para
        pPr.setJc(jc);
        run.getContent().add(rPr);
        para.setPPr(pPr);
        para.getContent().add(run);

        wpMLPackage.getMainDocumentPart().addObject(para);

    }
    /**
     * 添加换行符
     * @param wpMLPackage
     * @param brNum 换行数量
     */
    public void addBr(WordprocessingMLPackage wpMLPackage, int brNum){
        ObjectFactory fac = new ObjectFactory();
        P para = fac.createP();
        R run = fac.createR();
        Br br = new Br();
        //循环插入
        for (int i=0; i<brNum; i++){
            run.getContent().add(br);
        }
        para.getContent().add(run);
        wpMLPackage.getMainDocumentPart().addObject(para);
    }


    /**
     * 封面标题
     * @param wpMLPackage
     * @param word 标题内容
     */
    public void addTitleWordToPackage(WordprocessingMLPackage wpMLPackage, String word){
        ObjectFactory factory = new ObjectFactory();
        P para = factory.createP();
        R run = factory.createR();
        Text text = factory.createText();
        text.setValue(word);
        PPr pPr = factory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null){
            jc = new Jc();
        }
        //设置居中
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);
        //设置字体大小
        RPr rPr = new RPr();
        rPr.setB(Context.getWmlObjectFactory().createBooleanDefaultTrue());
        setFontSize(rPr, "50");
        run.setRPr(rPr);


        //将内容添加
        run.getContent().add(text);
        para.getContent().add(run);
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
        ObjectFactory factory = new ObjectFactory();
        P p = factory.createP();
        R run = factory.createR();
        p.getContent().add(run);
        PPr pPr = factory.createPPr();
        Jc jc = pPr.getJc();
        if (jc == null){
            jc = new Jc();
        }
        //设置居中
        jc.setVal(JcEnumeration.CENTER);
        pPr.setJc(jc);
        p.setPPr(pPr);
        Drawing drawing = factory.createDrawing();
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
