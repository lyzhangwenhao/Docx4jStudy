package com.zzqa.docx4j2word;

import com.zzqa.utils.Docx4jUtil;
import org.apache.commons.lang.StringUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import com.zzqa.utils.DrawChartPieUtil;

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
 * @author 张文豪
 * @date 2020/8/3 11:19
 */
public class PageContent1 {
    private ObjectFactory objectFactory = new ObjectFactory();

    public void createPageContent1(WordprocessingMLPackage wpMLPackage,
                                                      String proName, int normalPart, int warningPart, int alarmPart){
        try {
            //TODO 页脚和页眉没做完，目前达不到要求，抽空继续做，先将核心内容完成
            Relationship relationship = AddingAFooter.createFooterPart(wpMLPackage,"◆ 版权所有 © 2018-2020 浙江中自庆安新能源技术有限公司\r" +
                    "◆ 我们保留本文档和信息的全部所有权利。未经明示授权，严禁复制、使用或披露给第三方。");
            AddingAFooter.createFooterReference(wpMLPackage,relationship);

            Relationship headerPart = AddingAHeader.createHeaderPart(wpMLPackage, "咨询电话：4000093668-7 \r" + "网站:www.windit.com.cn ");
            AddingAHeader.createHeaderReference(wpMLPackage, headerPart);

            //添加标题一：项目概述
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading1", "1 项目概述");
            //文本内容
            String titleContent1 = proName+"项目共有"+(normalPart+warningPart+alarmPart)+
                    "台机组，每台机组配置一套在线状态监测系统，用于监测传动链部件运行情况，本期CMS监测系统监测结果为：当前正常机组"
                    +normalPart+"台，预警机组"+warningPart+"台，报警机组"+alarmPart+"台。";
            addParagraph(wpMLPackage, titleContent1);

            //根据数据生成饼状图
            File pieImage = DrawChartPieUtil.getImageFile(proName, normalPart, warningPart, alarmPart);
            byte[] pieImageBytes = Docx4jUtil.convertImageToByteArray(pieImage);
            Docx4jUtil.addImageToPackage(wpMLPackage, pieImageBytes);
            //表格标题
            Docx4jUtil.addTableTitle(wpMLPackage, "图1.1 机组状态统计饼状图");

            //下一页
//            Docx4jUtil.addNextPage(wpMLPackage);
            //TODO 删除输出语句
            System.out.println("PageContent Success......");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加正文段落
     * @param wpMLPackage
     * @param content
     */
    public void addParagraph(WordprocessingMLPackage wpMLPackage,String content){
        P p = objectFactory.createP();
        R r = objectFactory.createR();
        PPr pPr = p.getPPr();
        if (pPr==null){
            pPr = objectFactory.createPPr();
        }
        //缩进2字符
        PPrBase.Ind ind = pPr.getInd();
        if (ind==null){
            ind = objectFactory.createPPrBaseInd();
        }
        ind.setFirstLineChars(BigInteger.valueOf(200));
        pPr.setInd(ind);
        //设置行距1.5倍
        PPrBase.Spacing spacing = pPr.getSpacing();
        if (spacing==null){
            spacing = objectFactory.createPPrBaseSpacing();
        }
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(360));
        //段前段后
//        spacing.setBeforeLines(BigInteger.valueOf(50));
//        spacing.setAfterLines(BigInteger.valueOf(50));

        Text text = objectFactory.createText();
        text.setValue(content);
        r.getContent().add(text);

        pPr.setSpacing(spacing);
        p.getContent().add(r);
        p.setPPr(pPr);
        wpMLPackage.getMainDocumentPart().addObject(p);
    }
}
