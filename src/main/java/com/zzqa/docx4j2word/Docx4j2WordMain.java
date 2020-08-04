package com.zzqa.docx4j2word;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: Docx4j2WordMain
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/3 11:17
 */
public class Docx4j2WordMain {
    public static void main(String[] args) {
        try {
            WordprocessingMLPackage wpMLPackage = WordprocessingMLPackage.createPackage();

            //数据
            String reportName = "风电场风电机组";
            Long startTime = 1468166400000L;
            Long endTime = 1487088000000L;
            String logoPath = "src\\main\\resources\\images\\logo.png";
            String linePath = "src\\main\\resources\\images\\横线.png";

            int normalPort = 106;
            int warningPort = 20;
            int alarmPort = 12;
            String pieFilePath = "D:/TestFile/images/"+new String(Long.toString(System.currentTimeMillis()))+reportName+".png";
            //最后保存的文件名
            String fileName = reportName+getDate(new Date(startTime))+getDate(new Date(endTime))+"检测报告.docx";
            //保存的文件路径
            String targetFilePath = "D:/TestFile/"+fileName;

            Cover cover = new Cover();
            //创建封面
            wpMLPackage = cover.createCover(wpMLPackage,reportName,startTime, endTime, logoPath,linePath);
            //文章内容1:项目概述
            PageContent1 pageContent = new PageContent1();
            wpMLPackage = pageContent.createPageContent(wpMLPackage,reportName,normalPort,warningPort,alarmPort,pieFilePath);

            //保存文件
            wpMLPackage.save(new File(targetFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy年M月d日");
        System.out.println("转换时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        return sdf.format(date);
    }
}
