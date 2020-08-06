package com.zzqa.docx4j2word;

import com.zzqa.utils.Docx4jUtil;
import com.zzqa.utils.DrawChartLineUtil;
import com.zzqa.utils.LoadDataUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ObjectFactory;

import javax.print.Doc;
import java.io.File;

/**
 * ClassName: PageConten3
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/6 14:48
 */
public class PageContent3 {
    private ObjectFactory factory = new ObjectFactory();

    public void createPageContent3(WordprocessingMLPackage wpMLPackage) {
        try {
            //添加标题三：震动图谱
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading1", "3 震动图谱");

            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading2", "3.1 报警机组");
            wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("Heading3", ".3.1.1 01#机组");
            //添加四种图谱
            //1、趋势图
            //2、波形图
            String dataX2 = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\波形图X.txt");
            String[] colKeys2 = dataX2.split(",");
            String dataY2 = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\波形图Y.txt");
            double[][] data2 = string2DoubleArray(dataY2);
            File imageFile2 = DrawChartLineUtil.getImageFile("01#机组发电机前轴", "s", "g", "波值", colKeys2, data2);

            byte[] bytes2 = Docx4jUtil.convertImageToByteArray(imageFile2);
            Docx4jUtil.addImageToPackage(wpMLPackage, bytes2);
            Docx4jUtil.addTableTitle(wpMLPackage,"图3.1.1-2 01#机组发电机前轴波形图");

            //3、频谱图
            String dataX3 = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\频谱图X.txt");
            String[] colKeys3 = dataX3.split(",");
            String dataY3 = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\频谱图Y.txt");
            double[][] data3 = string2DoubleArray(dataY3);
            File imageFile3 = DrawChartLineUtil.getImageFile("01#机组发电机前轴", "Hz", "g", "频率", colKeys3, data3);

            byte[] bytes3 = Docx4jUtil.convertImageToByteArray(imageFile3);
            Docx4jUtil.addImageToPackage(wpMLPackage, bytes3);
            Docx4jUtil.addTableTitle(wpMLPackage,"图3.1.1-3 01#机组发电机前轴频谱图");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //4、包络图

        wpMLPackage.getMainDocumentPart().addParagraphOfText("Footer");
        wpMLPackage.getMainDocumentPart().addStyledParagraphOfText("ListNumber", "内容");
        //下一页
//        Docx4jUtil.addNextPage(wpMLPackage);
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
/*
    String[] rowKeys = {""};
    String dataX = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\波形图X.txt");
    String[] colKeys = dataX.split(",");

    String dataY = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\波形图Y.txt");
    String[] split = dataY.split(",");
    double[] dataTemp = new double[split.length];
        for (int i=0;i<split.length;i++){
        if (split[i]!=null){
            dataTemp[i] = Double.parseDouble(split[i]);
        }
    }
    double[][] data = {dataTemp};
*/
}
