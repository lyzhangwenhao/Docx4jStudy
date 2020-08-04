package test.com.zzqa.utils;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * ClassName: DrawChartPieUtil
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/4 16:39
 */
public class DrawChartPieUtil {
    public static File getImageFile(String proName,int normalPort,int warningPort,int alarmPort,String filePath)
    {
        //设置数据源
        PieDataset mDataset = GetDataset(normalPort,warningPort,alarmPort);
        //建立图表
        JFreeChart mChart = ChartFactory.createPieChart(proName,mDataset);

        //设置图表标题
        mChart.setTitle(new TextTitle(proName, new Font("黑体",Font.CENTER_BASELINE, 20)));
        //设置Legend字体
        mChart.getLegend().setItemFont(new Font("宋体", Font.ROMAN_BASELINE, 15));

        PiePlot mPiePlot = (PiePlot) mChart.getPlot();
        //设置背景色
        mPiePlot.setBackgroundPaint(ChartColor.WHITE);
        //数据区边框隐藏
        mPiePlot.setOutlineVisible(false);

        mPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        // 底部图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
        mPiePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
        //设置饼图标签字体
        mPiePlot.setLabelFont(new Font("宋体", Font.PLAIN, 12));

        BufferedImage bufferedImage = mChart.createBufferedImage(475, 300);

        FileOutputStream fos = null;
        File file = new File(filePath);

        try {
            ImageIO.write(bufferedImage, "png", file);
            System.out.println("完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }
    public static PieDataset GetDataset(int normalPort,int warningPort,int alarmPort)
    {
        DefaultPieDataset mDataset = new DefaultPieDataset();
        mDataset.setValue(" 报警机组", new Double(alarmPort));
        mDataset.setValue(" 预警机组", new Double(warningPort));
        mDataset.setValue(" 正常机组", new Double(normalPort));
        return mDataset;
    }
}
