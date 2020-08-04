package test;

import java.awt.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.*;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;

/**
 * ClassName: DrawChart
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/4 14:44
 */
public class DrawChartPie {
        /**
         * @param args
         */
        public static void main(String[] args)
        {
            //设置数据源
            PieDataset mDataset = GetDataset(17,10,6);
            //建立图表
//            JFreeChart mChart = ChartFactory.createPieChart3D("项目进度分布", mDataset, true, true, false);
            JFreeChart mChart = ChartFactory.createPieChart("项目进度分布",mDataset);

            //设置图表标题
            mChart.setTitle(new TextTitle("机组运行状况", new Font("黑体",Font.CENTER_BASELINE, 20)));
            //设置Legend字体
            mChart.getLegend().setItemFont(new Font("宋体", Font.ROMAN_BASELINE, 15));

            PiePlot mPiePlot = (PiePlot) mChart.getPlot();
            //设置背景色
            mPiePlot.setBackgroundPaint(ChartColor.WHITE);
            //数据区边框隐藏
            mPiePlot.setOutlineVisible(false);


            //以默认方式显示百分比
            //mPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
            // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
            mPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
            // 底部图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
            mPiePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})"));
            //设置饼图标签字体
            mPiePlot.setLabelFont(new Font("宋体", Font.PLAIN, 12));
            //内置对象显示图表
//            ChartFrame mChartFrame = new ChartFrame("项目状态分布", mChart);
//            mChartFrame.pack();
//            mChartFrame.setVisible(true);

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("D:/TestFile/项目状态分布2D.jpg");
                ChartUtilities.writeChartAsJPEG(fos, mChart, 475, 300);
                fos.close();
                System.out.println("完成！");
            } catch (Exception e) {
                e.printStackTrace();
            }

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