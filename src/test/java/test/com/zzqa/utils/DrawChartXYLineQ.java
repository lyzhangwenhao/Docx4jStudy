package test.com.zzqa.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ClassName: DrawChartLine
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/6 9:24
 */
public class DrawChartXYLineQ {
    /**
     * 创建JFreeChart Line Chart（折线图）
     */
    public static void main(String[] args) {
        ChartUtil.setChartTheme();
        // 步骤1：创建CategoryDataset对象（准备数据）
        XYDataset dataset = createDataset();
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

    public static JFreeChart createChart(XYDataset xyDataset) {
//      创建JFreeChart对象
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("包络图X", // 标题
                "s", // categoryAxisLabel （category轴，横轴，X轴标签）
                "g", // valueAxisLabel（value轴，纵轴，Y轴的标签）
                xyDataset// dataset
                , true, // legend
                false, // tooltips
                false); // URLs
        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        XYPlot plot = (XYPlot)jfreechart.getPlot();
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);

        DateAxis domainAxis = (DateAxis)plot.getDomainAxis();
        domainAxis.setPositiveArrowVisible(true);
        domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
        domainAxis.setMinorTickCount(8);

        // 设置Y轴
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15));
        //纵轴的箭头
        rangeAxis.setPositiveArrowVisible(true);
//        rangeAxis.setUpperMargin(0.0);//上边距,防止最大的一个数据靠近了坐标轴。

        XYItemRenderer renderer = plot.getRenderer();
        //设置线条颜色
        renderer.setSeriesPaint(0, Color.BLUE);

        return jfreechart;
    }

    /**
     * 创建CategoryDataset对象
     *
     */
    public static CategoryDataset createDataset1() {
        String[] rowKeys = {""};
        String dataX = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\发电机转速趋势图X.txt");
        String[] colKeys = dataX.split(",");
        String dataY = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\发电机转速趋势图Y.txt");
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
    /**
     * 创建CategoryDataset对象
     *
     */
    public static XYDataset createDataset() {
        XYSeries first = new XYSeries("first");
        String dataX = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\趋势图X.txt");
        String[] colKeys = dataX.split(",");
        String dataY = LoadDataUtils.ReadFile("C:\\Users\\Mi_dad\\Desktop\\趋势图Y.txt");
        String[] split = dataY.split(",");
        for (int i=0;i<colKeys.length;i++){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy/MM/dd HH:mm");
            try {
                first.add(simpleDateFormat.parse(colKeys[i]).getTime(),Double.parseDouble(split[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(first);
        return xySeriesCollection;
    }
    public static void setDomainAxis(CategoryAxis domainAxis,int max){
        domainAxis.setTickLabelFont(new Font("Times New Roman", Font.ITALIC, 12));
        domainAxis.setLabelFont(new Font("Times New Roman", Font.ITALIC, 12));
        domainAxis.setTickMarksVisible(true);  //用于显示X轴标尺
        domainAxis.setTickLabelsVisible(true); //用于显示X轴标尺值
        int j = 1;
        for(int i = 0; i<max; i++)
        {
            //设置0-max显示10个刻度，可自选
            if((i>10||i==0) && i%(max/10) ==0)
//            if(i==j*(i/9))
            {
                domainAxis.setTickLabelPaint(Integer.toString(i), Color.BLUE);
                j++;
            }else{
                domainAxis.setTickLabelPaint(Integer.toString(i), Color.white);
            }
        }
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);  //设置X轴45度
    }





}
