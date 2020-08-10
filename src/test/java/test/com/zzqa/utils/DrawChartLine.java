package test.com.zzqa.utils;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 * ClassName: DrawChartLine
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/6 9:24
 */
public class DrawChartLine {
    /**
     * 创建JFreeChart Line Chart（折线图）
     */
    public static void main(String[] args) {
        ChartUtil.setChartTheme();
        // 步骤1：创建CategoryDataset对象（准备数据）
        CategoryDataset dataset = createDataset();
        // 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
        JFreeChart freeChart = createChart(dataset);
        // 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
        saveAsFile(freeChart, "D:\\line.png", 800, 500);
    }

    // 保存为文件
    public static void saveAsFile(JFreeChart chart, String outputPath,
                                  int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);
            // 保存为PNG
             ChartUtilities.writeChartAsPNG(out, chart, weight, height);
            // 保存为JPEG
//            ChartUtilities.writeChartAsJPEG(out, chart, 600, 400);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    // 根据CategoryDataset创建JFreeChart对象
    public static JFreeChart createChart(CategoryDataset categoryDataset) {
        // 创建JFreeChart对象：ChartFactory.createLineChart
        JFreeChart jfreechart = ChartFactory.createLineChart("波形图", // 标题
                "s", // categoryAxisLabel （category轴，横轴，X轴标签）
                "g", // valueAxisLabel（value轴，纵轴，Y轴的标签）
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
        //隐藏坐标轴标尺
//        domainAxis.setTickLabelsVisible(false);


//        setDomainAxis(domainAxis, 1000);

//        domainAxis.setTickLabelPaint( Integer.toString(20),Color.blue);



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

    /**
     * 创建CategoryDataset对象
     *
     */
    public static CategoryDataset createDataset() {
        String[] rowKeys = {""};
        String dataX = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\包络图X.txt");
        String[] colKeys = dataX.split(",");
        String dataY = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\包络图Y.txt");
        String[] split = dataY.split(",");
        double[] dataTemp = new double[split.length];
        for (int i=0;i<split.length;i++){
            if (split[i]!=null){
                dataTemp[i] = Double.parseDouble(split[i]);
            }
        }
        double[][] data = {dataTemp};
        // 或者使用类似以下代码
        // DefaultCategoryDataset categoryDataset = new
        // DefaultCategoryDataset();
        // categoryDataset.addValue(10, "rowKey", "colKey");
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
    }

    public static void setDomainAxis(CategoryAxis domainAxis,int max){
        domainAxis.setTickLabelFont(new Font("Times New Roman", Font.ITALIC, 12));
        domainAxis.setLabelFont(new Font("Times New Roman", Font.ITALIC, 12));
        domainAxis.setTickMarksVisible(true);  //用于显示X轴标尺
        domainAxis.setTickLabelsVisible(true); //用于显示X轴标尺值
        for(int i = 0; i<max; i++)
        {
            //设置0-max显示10个刻度，可自选
            if(i%(max/10) ==0)
            {
                domainAxis.setTickLabelPaint(Integer.toString(i), Color.black);
            }else{
                domainAxis.setTickLabelPaint(Integer.toString(i), Color.white);
            }
        }
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);  //设置X轴45度
    }





}
