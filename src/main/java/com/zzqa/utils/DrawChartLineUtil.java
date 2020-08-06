package com.zzqa.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ClassName: DrawChartLine
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/6 9:24
 */
public class DrawChartLineUtil {

    /**
     * 传入数据生成折线图
     * @param title 机组名称：一般为机组+测点
     * @param xUnit x轴单位
     * @param yUnit y轴单位
     * @param rowKey 折线所属类别
     * @param colKeys 折线x轴数据
     * @param data 与x轴对应的y轴数据
     * @return 返回一个File对象
     */
    public static File getImageFile(String title,String xUnit,String yUnit,
                                    String rowKey,String[] colKeys,double[][] data){
        ChartUtil.setChartTheme();
        // 步骤1：创建CategoryDataset对象（准备数据）
        String[]rowKeys={rowKey};
        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
        // 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
        JFreeChart freeChart = createChart(dataset,title,xUnit,yUnit);
        //步骤3：将JFreeChart对象保存为一个文件
        BufferedImage bufferedImage = freeChart.createBufferedImage(550, 250);
        //确定图表文件存在的位置，如果文件夹路径不存在则创建一个
        File mkdirPath = new File("D:/chart/lineChart");
        if(!mkdirPath.exists() && !mkdirPath.isDirectory()){
            mkdirPath.mkdirs();
        }

        //折线图路径
        String lineFilePath = mkdirPath.getAbsolutePath()+"/"+System.currentTimeMillis() +title+".png";
        File file = new File(lineFilePath);
        try {
            ImageIO.write(bufferedImage, "png",file);
            // TODO 删除输出
            System.out.println(title+"折线图完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    // 根据CategoryDataset创建JFreeChart对象

    /**
     * 根据CategoryDataset创建JFreeChart对象
     * @param categoryDataset 生成的CategoryDataset对象
     * @param title 机组+测点
     * @param xUnit x轴单位
     * @param yUnit y轴单位
     * @return 返回一个JFreeChart对象
     */
    private static JFreeChart createChart(CategoryDataset categoryDataset,String title,String xUnit,String yUnit) {
        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart(title, // 标题
                xUnit, // categoryAxisLabel （category轴，横轴，X轴标签）
                yUnit, // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDataset, // dataset
                PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs
        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot)jfreechart.getPlot();
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);
        //设置X轴距离两端距离
        CategoryAxis domainAxis = plot.getDomainAxis();
        // 设置距离图片左端距离
        domainAxis.setLowerMargin(0.0);
        // 设置距离图片右端距离
        domainAxis.setUpperMargin(0.0);

        //设置最大行数（X轴显示）
//        domainAxis.setMaximumCategoryLabelLines(1);

        // 设置Y轴
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15));
//        rangeAxis.setUpperMargin(0.0);//上边距,防止最大的一个数据靠近了坐标轴。

        // 其他设置 参考 CategoryPlot类
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setItemLabelsVisible(false);
        renderer.setBaseShapesVisible(false); // series 点（即数据点）可见
        renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
        renderer.setUseSeriesOffset(true); // 设置偏移量
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setSeriesPaint(0, Color.blue);//改变线条颜色
        renderer.setSeriesStroke(0, new BasicStroke(0.5f));
        return jfreechart;
    }
}