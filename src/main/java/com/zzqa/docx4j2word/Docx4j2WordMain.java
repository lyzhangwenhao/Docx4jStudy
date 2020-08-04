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
            Cover cover = new Cover();
            //创建封面
            String reportName = "风电场风电机组";
            Long startTime = 1468166400000L;
            Long endTime = 1487088000000L;
            String logoPath = "src\\main\\resources\\images\\logo.png";
            String linePath = "src\\main\\resources\\images\\横线.png";
            wpMLPackage = cover.createCover(wpMLPackage,reportName,startTime, endTime, logoPath,linePath);

            //文章内容
            PageContent pageContent = new PageContent();
            wpMLPackage = pageContent.createPageContent(wpMLPackage);

            //保存文件
            String fileName = reportName+getDate(new Date(startTime))+getDate(new Date(endTime))+"检测报告.docx";
            String filePath = "D:/TestFile/"+fileName;
            wpMLPackage.save(new File(filePath));

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
