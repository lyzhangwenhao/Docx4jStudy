package com.zzqa.docx4j2word;

import com.zzqa.pojo.UnitInfo;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName: Docx4j2WordMain
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/3 11:17
 */
public class Docx4j2WordMain {
    public static void main(String[] args) {
        try {
            WordprocessingMLPackage wpMLPackage = WordprocessingMLPackage.createPackage();

            //数据准备
            String reportName = "风电场风电机组";  //项目名称
            Long startTime = 1468166400000L;    //报告开始时间毫秒值
            Long endTime = System.currentTimeMillis();  //报告结束时间毫秒值
            String logoPath = "src\\main\\resources\\images\\logo.png"; //封面logo路径
            String linePath = "src\\main\\resources\\images\\横线.png";   //封面横线路径

            int normalPart = 106;   //正常机组数量
            int warningPart = 20;   //预警机组数量
            int alarmPart = 12; //报警机组数量
            //最后保存的文件名
            String fileName = reportName+getDate(new Date(startTime))+getDate(new Date(endTime))+"检测报告.docx";
            //保存的文件路径
            String targetFilePath = "D:/TestFile/projectTest/"+fileName;
            //PageContent2的数据准备
            List<UnitInfo> unitInfos = createData();
            for (UnitInfo unitInfo:unitInfos){
                System.out.println(unitInfo);
            }



            Cover cover = new Cover();
            //创建封面
            wpMLPackage = cover.createCover(wpMLPackage,reportName,startTime, endTime, logoPath,linePath);
            //添加目录
            AddingTableOfContent.addTableOfContent(wpMLPackage);
            //文件内容1:项目概述
            PageContent1 pageContent1 = new PageContent1();
            pageContent1.createPageContent1(wpMLPackage,reportName,normalPart,warningPart,alarmPart);
            //文件内容2:运行状况
            PageContent2 pageContent2 = new PageContent2();
            pageContent2.createPageContent2(wpMLPackage, unitInfos);
            //文件内容3：震动图谱
            PageContent3 pageContent3 = new PageContent3();
            pageContent3.createPageContent3(wpMLPackage);
            //文件内容4：补充说明
            PageContent4 pageContent4 = new PageContent4();
            pageContent4.createPageContent4(wpMLPackage);

            //保存文件
            wpMLPackage.save(new File(targetFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static List<UnitInfo> createData(){
        List<UnitInfo> unitInfos = new ArrayList<>();
        UnitInfo unitInfo = null;
        int j = 0;
        for (int i=0; i<10;i++){
            unitInfo = new UnitInfo();
            unitInfo.setUnitName((i+1)+"#机组");
            if (j%2==0){
                unitInfo.setUnitPart("发电机前轴承");
                unitInfo.setValve("3g");
                unitInfo.setType("有效值");
                unitInfo.setMaxValue("5g");
                unitInfo.setLevel("预警");
            }else{
                unitInfo.setUnitPart("发电机后轴承");
                unitInfo.setValve("7g");
                unitInfo.setType("峰值");
                unitInfo.setMaxValue("10g");
                unitInfo.setLevel("报警");
            }
            unitInfo.setCount(i+j);
            if (j%2==0){
                i--;
            }
            j++;
            unitInfos.add(unitInfo);
        }
        return unitInfos;
    }
    public static String getDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy年M月d日");
        //TODO 删除输出语句
        System.out.println("转换时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        return sdf.format(date);
    }
}
