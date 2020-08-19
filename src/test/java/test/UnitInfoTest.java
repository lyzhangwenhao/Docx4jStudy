package test;

import com.tom.pojo.UnitInfo;
import com.zzqa.utils.LoadDataUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: UnitInfoTest
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/18 16:50
 */
public class UnitInfoTest {


    @Test
    public void test(){
        List<List<List<UnitInfo>>> data = createData();
        data.stream().forEach(l->{
            l.stream().forEach(s->{
                s.stream().forEach(p->{
                    System.out.println(p.toString());
                });
            });
        });
    }


    public List<List<List<UnitInfo>>> createData() {

        //趋势图
        String dataX1 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/趋势图X.txt");
        String[] colKeys1 = dataX1.split(",");
        String dataY1 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/趋势图Y.txt");
        double[][] data1 = string2DoubleArray(dataY1);
        //波形图
        String dataX2 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/波形图X.txt");
        String[] colKeys2 = dataX2.split(",");
        String dataY2 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/波形图Y.txt");
        double[][] data2 = string2DoubleArray(dataY2);
        //频谱图
        String dataX3 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/频谱图X.txt");
        String[] colKeys3 = dataX3.split(",");
        String dataY3 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/频谱图Y.txt");
        double[][] data3 = string2DoubleArray(dataY3);
        //包络图
        String dataX4 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/包络图X.txt");
        String[] colKeys4 = dataX4.split(",");
        String dataY4 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/包络图Y.txt");
        double[][] data4 = string2DoubleArray(dataY4);

        //创建集合
        //该集合为表格总数据
        List<List<List<UnitInfo>>> lllist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            //该集合为每组相同机组数据
            List<List<UnitInfo>> llist = new ArrayList<>();
            //控制机组相同

            for (int j = 0; j < 5; j++) {
                //该集合为机组相同的情况下测点相同的数据
                List<UnitInfo> list = new ArrayList<>();

                for (int k = 0; k < 3; k++) {
                    UnitInfo ui = new UnitInfo();
                    ui.setUnitName(i + "#机组");//控制测点相同
                    ui.setUnitPart(j + "#测点");
                    ui.setValve("7g");
                    ui.setType("峰值");
                    ui.setMaxValue("10g");
                    ui.setLevel("正常");
                    ui.setCount(i + j + k);
                    ui.setColKeys1(colKeys1);
                    ui.setColKeys2(colKeys2);
                    ui.setColKeys3(colKeys3);
                    ui.setColKeys4(colKeys4);
                    ui.setData1(data1);
                    ui.setData2(data2);
                    ui.setData3(data3);
                    ui.setData4(data4);
                    list.add(ui);
                    llist.add(list);
                    lllist.add(llist);
                }
            }
        }
        return lllist;
    }

    /**
     * 将数据转换为double数组
     *
     * @param dataY
     * @return
     */
    private static double[][] string2DoubleArray(String dataY) {
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
