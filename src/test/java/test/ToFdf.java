package test;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.junit.Test;

import java.io.File;
import java.io.OutputStream;

/**
 * ClassName: ToFdf
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/14 9:08
 */
public class ToFdf {



    @Test
    public void method(){
        String docxPath = "D:\\AutoExport\\风电场风电机组2016年7月11日2020年8月14日检测报告.docx";
        String pdfPath = "D:\\AutoExport\\风电场风电机组2016年7月11日2020年8月14日检测报告.pdf";
        try {
            convertDocxToPDF(docxPath, pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * docx文档转换为PDF
     *
     * @param docxPath docx文档
     * @param pdfPath PDF文档存储路径
     * @throws Exception 可能为Docx4JException, FileNotFoundException, IOException等
     */
    public static void convertDocxToPDF(String docxPath, String pdfPath) throws Exception {
        OutputStream os = null;
        try {
            WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File(docxPath));
            //Mapper fontMapper = new BestMatchingMapper();
            Mapper fontMapper = new IdentityPlusMapper();
            fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
            fontMapper.put("宋体",PhysicalFonts.get("SimSun"));
            fontMapper.put("微软雅黑",PhysicalFonts.get("Microsoft Yahei"));
            fontMapper.put("黑体",PhysicalFonts.get("SimHei"));
            fontMapper.put("楷体",PhysicalFonts.get("KaiTi"));
            fontMapper.put("新宋体",PhysicalFonts.get("NSimSun"));
            fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
            fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
            fontMapper.put("宋体扩展",PhysicalFonts.get("simsun-extB"));
            fontMapper.put("仿宋",PhysicalFonts.get("FangSong"));
            fontMapper.put("仿宋_GB2312",PhysicalFonts.get("FangSong_GB2312"));
            fontMapper.put("幼圆",PhysicalFonts.get("YouYuan"));
            fontMapper.put("华文宋体",PhysicalFonts.get("STSong"));
            fontMapper.put("华文中宋",PhysicalFonts.get("STZhongsong"));

            //解决宋体（正文）和宋体（标题）的乱码问题
            PhysicalFonts.put("PMingLiU", PhysicalFonts.get("SimSun"));
            PhysicalFonts.put("新細明體", PhysicalFonts.get("SimSun"));

            mlPackage.setFontMapper(fontMapper);

            os = new java.io.FileOutputStream(pdfPath);

            FOSettings foSettings = Docx4J.createFOSettings();
            foSettings.setWmlPackage(mlPackage);
            Docx4J.toFO(foSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            IOUtils.closeQuietly(os);
        }
    }
}
