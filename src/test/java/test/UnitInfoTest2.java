package test;

import com.zzqa.pojo.UnitInfo;
import com.zzqa.utils.LoadDataUtils;
import com.zzqa.utils.LoadExeclUtil;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * ClassName: UnitInfoTest2
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/19 17:42
 */
public class UnitInfoTest2 {
    /**
     * 数据准备
     *
     * @return
     */
    public static List<List<List<List<UnitInfo>>>> createData() {
        //趋势图
        String dataX1 = LoadDataUtils.ReadFile("C:/Users/Mi_dad/Desktop/趋势图X.txt");
        String[] colKeys = dataX1.split(",");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy/MM/dd HH:mm");
        long [] colKeys1 = new long[colKeys.length];
        for (int i=0; i<colKeys.length; i++){
            try {
                Date parse = simpleDateFormat.parse(colKeys[i]);
                colKeys1[i] = parse.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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

        List<List<List<List<UnitInfo>>>> lists = LoadExeclUtil.loadTest();
        lists.forEach(list1->list1.forEach(list2->list2.forEach(list3->list3.forEach(unitInfo -> {
            unitInfo.setColKeys1(colKeys1);
            unitInfo.setColKeys2(string2Double(colKeys2));
            unitInfo.setColKeys3(string2Double(colKeys3));
            unitInfo.setColKeys4(string2Double(colKeys4));

            unitInfo.setData1(data1);
            unitInfo.setData2(data2);
            unitInfo.setData3(data3);
            unitInfo.setData4(data4);
        }))));

        return lists;
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

    /**
     * String类型转换为Double类型
     * @param colKeys
     * @return
     */
    public static double[] string2Double(String[] colKeys){
        double [] colKeys1 = new double[colKeys.length];
        for (int i=0; i<colKeys.length; i++){
            colKeys1[i] = Double.parseDouble(colKeys[i]);
        }
        return colKeys1;
    }

    @Test
    public void method(){
        List<List<List<List<UnitInfo>>>> unitInfos = createData();
        //三种报警等级
        List<UnitInfo> unitInfos1 = new ArrayList<>();  //报警机组数据
        List<UnitInfo> unitInfos2 = new ArrayList<>();  //预警机组数据
        List<UnitInfo> unitInfos3 = new ArrayList<>();  //正常机组数据
        //将数据分离，分别分为报警，预警，正常
        if (unitInfos != null) {
            for (List<List<List<UnitInfo>>> list1 : unitInfos) {
                for (List<List<UnitInfo>> list2 : list1) {
                    for (List<UnitInfo> list3 : list2) {
                        for (UnitInfo u : list3) {
                            if ("报警".equals(u.getLevel())) {
                                unitInfos1.add(u);
                            } else if ("预警".equals(u.getLevel())) {
                                unitInfos2.add(u);
                            } else {
                                unitInfos3.add(u);
                            }
                        }
                    }
                }
            }
        }
        for (UnitInfo unitInfo : unitInfos1) {
            System.out.println(unitInfo.toString());
        }



        System.out.println("----------------------");
        Map<String, List<UnitInfo>> collect = unitInfos1.stream().collect(Collectors.groupingBy(u->u.getUnitName()+"-"+u.getUnitPart()+"-"+u.getUnitStation()));
        Set<Map.Entry<String, List<UnitInfo>>> entries = collect.entrySet();


        Collector<Map.Entry<String, List<UnitInfo>>, ?, Map<String, List<UnitInfo>>> entryMapCollector = Collectors.toMap(x -> x.getKey(), x -> x.getValue());
        Map<String, List<UnitInfo>> collect1 = entries.stream().sorted(Map.Entry.<String, List<UnitInfo>>comparingByKey()).collect(entryMapCollector);
        for (Map.Entry<String, List<UnitInfo>> entry : entries) {
            System.out.println(entry.getKey()+"---"+entry.getValue().toString());
        }
        System.out.println("-----------------------");
//        Set<Map.Entry<String, List<UnitInfo>>> entries1 = collect1.entrySet();
//        for (Map.Entry<String, List<UnitInfo>> stringListEntry : entries1) {
//            System.out.println(stringListEntry.getKey()+"------"+stringListEntry.getValue());
//        }

        for (String key:collect1.keySet()){
            System.out.println(key+"--"+collect.get(key));
        }


    }
}
