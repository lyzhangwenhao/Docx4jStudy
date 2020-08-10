package com.zzqa.docx4j2word;

import com.zzqa.pojo.UnitInfo;
import com.zzqa.utils.Docx4jUtil;
import com.zzqa.utils.DrawChartLineUtil;
import com.zzqa.utils.DrawChartLineUtilQ;
import com.zzqa.utils.LoadDataUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ObjectFactory;

import javax.print.Doc;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: PageConten3
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/6 14:48
 */
public class PageContent3 {
    private ObjectFactory factory = new ObjectFactory();

    /**
     * 生成预警/报警机组的四种图谱
     * @param wpMLPackage 传入的wpMLPackage对象
     * @param unitInfos 数据
     * @param mac_export 正常机组是否导出，1为导出
     */
    public void createPageContent3(WordprocessingMLPackage wpMLPackage, List<UnitInfo> unitInfos,int mac_export) {
        try {
            //添加标题三：震动图谱
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading1", "3 震动图谱");


            //三种报警等级
            List<UnitInfo> unitInfos1 = new ArrayList<>();  //报警机组数据
            List<UnitInfo> unitInfos2 = new ArrayList<>();  //预警机组数据
            List<UnitInfo> unitInfos3 = new ArrayList<>();  //正常机组数据
            //将数据分离，分别分为报警，预警，正常
            UnitInfo unitInfo = new UnitInfo();
            if (unitInfos!=null){
                for (int i=0; i<unitInfos.size(); i++){
                    unitInfo = unitInfos.get(i);
                    if ("报警".equals(unitInfo.getLevel())){
                        unitInfos1.add(unitInfo);
                    }else if ("预警".equals(unitInfo.getLevel())){
                        unitInfos2.add(unitInfo);
                    }else {     //正常机组
                        unitInfos3.add(unitInfo);
                    }
                }
            }
            //添加四种图谱
            //注意，1、图表数据必须是根据X轴排好序的，(x,y)一一对应，不然图像不对
            //      2、y轴的数据必须为double类型，所以，字符串中不能包含任何不能转换为double类型的数据
            //报警
            addImageContent(wpMLPackage, unitInfos1, 1);
            //预警
            addImageContent(wpMLPackage, unitInfos2, 2);
            //正常
            if (mac_export==1){
                addImageContent(wpMLPackage, unitInfos3, 3);
            }
            //TODO 删除输出语句
            System.out.println("PageContent3 Success......");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //4、包络图

        //下一页
        Docx4jUtil.addNextPage(wpMLPackage);
    }

    /**
     * 添加四种图谱到wpMLPackage中
     * @param wpMLPackage
     * @param unitInfos
     * @param flag 标记预警等级
     * @throws Exception
     */
    private void addImageContent(WordprocessingMLPackage wpMLPackage,List<UnitInfo> unitInfos ,int flag) throws Exception {
        String index = "3.1";
        String title = "报警机组";
        if (flag == 1){
             index = "3.1";
             title = "报警机组";
        }else if (flag == 2){
            index = "3.2";
            title = "预警机组";
        }else if (flag == 3){
            index = "3.3";
            title = "正常机组";
        }
        if (unitInfos!=null && unitInfos.size()!=0){
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading2", index + " " +title);
            UnitInfo unitInfo = new UnitInfo();
            for (int i=0; i<unitInfos.size(); i++){
                unitInfo = unitInfos.get(i);
                wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading3", index + "."+ (i+1) + " " +unitInfo.getUnitName());
                //1、趋势图
                File imageFile1 = DrawChartLineUtilQ.getImageFile(unitInfo.getUnitName() + unitInfo.getUnitPart(), "t(s)", "g", "趋势图线", unitInfo.getColKeys1(), unitInfo.getData1());
                byte[] bytes1 = Docx4jUtil.convertImageToByteArray(imageFile1);
                Docx4jUtil.addImageToPackage(wpMLPackage, bytes1);
                deleteImageFile(imageFile1);
                Docx4jUtil.addTableTitle(wpMLPackage,
                        "图"+index+"."+ (i+1) + "-1 " +
                                unitInfo.getUnitName() + "-" + unitInfo.getUnitPart()+"趋势图(X轴数据从"+unitInfo.getColKeys1()[0]+"到"+unitInfo.getColKeys1()[unitInfo.getColKeys1().length-1]+")");

                //2、波形图
                File imageFile2 = DrawChartLineUtil.getImageFile(unitInfo.getUnitName() + unitInfo.getUnitPart(), "t(s)", "g", "波线图线", unitInfo.getColKeys2(), unitInfo.getData2());
                byte[] bytes2 = Docx4jUtil.convertImageToByteArray(imageFile2);
                Docx4jUtil.addImageToPackage(wpMLPackage, bytes2);
                deleteImageFile(imageFile2);
                Docx4jUtil.addTableTitle(wpMLPackage,
                        "图"+index+"."+ (i+1) + "-2 " +
                                unitInfo.getUnitName() + "-" + unitInfo.getUnitPart()+"波形图(X轴数据从"+unitInfo.getColKeys2()[0]+"到"+unitInfo.getColKeys2()[unitInfo.getColKeys2().length-1]+")");

                //3、频谱图
                File imageFile3 = DrawChartLineUtil.getImageFile(unitInfo.getUnitName() + unitInfo.getUnitPart(), "t(s)", "g", "频谱图线", unitInfo.getColKeys3(), unitInfo.getData3());
                byte[] bytes3 = Docx4jUtil.convertImageToByteArray(imageFile3);
                Docx4jUtil.addImageToPackage(wpMLPackage, bytes3);
                deleteImageFile(imageFile3);
                Docx4jUtil.addTableTitle(wpMLPackage,
                        "图"+index+"."+ (i+1) + "-3 " +
                                unitInfo.getUnitName() + "-" + unitInfo.getUnitPart()+"频谱图(X轴数据从"+unitInfo.getColKeys3()[0]+"到"+unitInfo.getColKeys3()[unitInfo.getColKeys3().length-1]+")");

                //4、包络图
                File imageFile4 = DrawChartLineUtil.getImageFile(unitInfo.getUnitName() + unitInfo.getUnitPart(), "t(s)", "g", "包络图线", unitInfo.getColKeys4(), unitInfo.getData4());
                byte[] bytes4 = Docx4jUtil.convertImageToByteArray(imageFile4);
                Docx4jUtil.addImageToPackage(wpMLPackage, bytes4);
                deleteImageFile(imageFile4);
                Docx4jUtil.addTableTitle(wpMLPackage,
                        "图"+index+"."+ (i+1) + "-4 " +
                                unitInfo.getUnitName() + "-" + unitInfo.getUnitPart()+"包络图(X轴数据从"+unitInfo.getColKeys4()[0]+"到"+unitInfo.getColKeys4()[unitInfo.getColKeys4().length-1]+")");

            }
        }
    }

    /**
     * 删除已经添加进文档中的图片
     * @param imageFile
     */
    private void deleteImageFile(File imageFile){
        if (imageFile != null && imageFile.exists()){
            imageFile.delete();
        }
    }

    /**
     * 将数据转换为double数组
     *
     * @param dataY
     * @return
     */
    private double[][] string2DoubleArray(String dataY) {
        String[] split = dataY.split(",");
        double[] dataTemp = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            if (split[i] != null) {
                dataTemp[i] = Double.parseDouble(split[i]);
            }
        }
        double[][] data = {dataTemp};
        return data;
    }
}
