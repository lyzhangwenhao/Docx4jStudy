package test;

import com.zzqa.pojo.UnitInfo;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ClassName: StreamStudy
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/12 9:51
 */
public class StreamStudy {
    private Logger logger = Logger.getLogger("logger");
    @Test
    public void method1(){
        List<String> strings = Arrays.asList("hell", "hello", "1", "", "3", "zhang");
        int[] arr = {12,21,3};
        System.out.println(strings.size());

        System.out.println(strings.stream().count());

        System.out.println(arr.length);
        Stream<Integer> stream = Stream.of(12, 21, 3);
        ArrayList<Integer> collect = stream.collect(Collectors.toCollection(ArrayList::new));
        collect.stream().filter(s->s<20).forEach(s-> System.out.println(s));
    }

    @Test
    public void method2(){
        UnitInfo unitInfo1 = new UnitInfo();
        unitInfo1.setUnitName("03#机组");
        unitInfo1.setUnitPart("发动机测点");
        unitInfo1.setCount(3);

        UnitInfo unitInfo2 = new UnitInfo();
        unitInfo2.setUnitName("04#机组");
        unitInfo2.setUnitPart("发动机测点");
        unitInfo2.setCount(4);

        UnitInfo unitInfo3 = new UnitInfo();
        unitInfo3.setUnitName("05#机组");
        unitInfo3.setUnitPart("发动机测点");
        unitInfo3.setCount(5);

        UnitInfo unitInfo4 = new UnitInfo();
        unitInfo4.setUnitName("01#机组");
        unitInfo4.setUnitPart("电动机");
        unitInfo4.setCount(1);

        UnitInfo unitInfo5 = new UnitInfo();
        unitInfo5.setUnitName("01#机组");
        unitInfo5.setUnitPart("发电机111");
        unitInfo5.setCount(11);

        UnitInfo unitInfo6 = new UnitInfo();
        unitInfo6.setUnitName("02#机组");
        unitInfo6.setUnitPart("2");
        unitInfo6.setCount(21);

        UnitInfo unitInfo7 = new UnitInfo();
        unitInfo7.setUnitName("04#机组");
        unitInfo7.setUnitPart("电动机测点");
        unitInfo7.setCount(5);

        UnitInfo unitInfo8 = new UnitInfo();
        unitInfo8.setUnitName("02#机组");
        unitInfo8.setUnitPart("1");
        unitInfo8.setCount(21);

        UnitInfo unitInfo9 = new UnitInfo();
        unitInfo9.setUnitName("06#机组");
        unitInfo9.setUnitPart("风叶测点");
        unitInfo9.setCount(6);


        List<UnitInfo> list = new ArrayList<>();
        list.add(unitInfo1);
        list.add(unitInfo2);
        list.add(unitInfo3);
        list.add(unitInfo4);
        list.add(unitInfo5);
        list.add(unitInfo6);
        list.add(unitInfo7);
        list.add(unitInfo8);
        list.add(unitInfo9);

//        list.forEach(s-> logger.info(s.toString()));
//        System.out.println("---------------------------------------------------------");
//        list.stream().sorted(Comparator.comparing(UnitInfo::getUnitName)).forEach(s-> logger.info(s.toString()));
//
//        System.out.println("----------------------------------------------------------");
//        Collections.sort(list, Comparator.comparing(UnitInfo::getUnitName));
//        list.forEach(s-> logger.info(s.toString()));

//        System.out.println("----------------------------------------------------------");
        List<UnitInfo> collect = list.stream().sorted(Comparator.comparing(UnitInfo::getUnitName).thenComparing(UnitInfo::getCount, Comparator.reverseOrder())).collect(Collectors.toList());
        collect.forEach(s->logger.info(s.toString()));
    }

}
