package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzqa.pojo.UnitInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 * ClassName: LoadExecl
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/19 10:10
 */
public class LoadExeclUtil {

    /**
     * 将excel解析为指定的对象集合 <br>
     * 例如： 要导入的excel格式为 <br>
     * 第1行：  | id   | username | password　|(与对象的字段对应)<br>
     * 第2行：  |1     | 小周　　 | 123456    |<br>
     * 第3行：  |2     | 老王　　 | 123456    |<br>
     * 调用： analysisExcel(file,User.class);
     * @param file-----要解析的excel文件
     * @param c--------指定的对象类型
     * @throws IOException
     * @return---------对象集合
     */
    public static <T> List<T> analysisExcel(File file,Class<T> c) {
        List<T> list = new ArrayList<T>();
        InputStream inputStream = null;
        String fileName = null;
        Workbook wb = null;
        try{
            inputStream = new FileInputStream(file);
            fileName = file.getName();
            if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")){
                //如果是2003版本
                if(fileName.endsWith(".xls")){
                    //1.先解析文件
                    POIFSFileSystem fs = new POIFSFileSystem(inputStream);
                    wb = new HSSFWorkbook(fs);
                }else if( fileName.endsWith(".xlsx")){//如果是2007以上版本
                    wb = new XSSFWorkbook(inputStream);
                }else{
                    return null;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        //获取第一行（标题行）
        Row row1 = sheet.getRow(0);
        //总列数
        int colNum = row1.getPhysicalNumberOfCells();
        //总行数
        int rowNum = sheet.getLastRowNum();
        //将标题行一一放入数组
        String[] titles = new String[colNum];
        for(int i = 0 ; i < colNum ; i++){
            titles[i] = row1.getCell(i).getStringCellValue();
        }
        //获取指定对象所有的字段
        Field fields[] = c.getDeclaredFields();
        Map<String,Field> fieldMap = new HashMap<String, Field>();
        for (int i = 0; i < fields.length; i++) {
            fieldMap.put(fields[i].getName(), fields[i]);
        }
        //使用反射机制，将值存入对应对象中
        try {
            for (int i = 1; i < rowNum+1; i++) {
                T t =c.newInstance();
                for (int j = 0; j < titles.length; j++) {
                    //当excel中有这个字段
                    if(fieldMap.containsKey(titles[j])){
                        String fieldName = titles[j];
                        String methodName = "set" + fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                        //调用该字段对应的set方法
                        Class cc = fieldMap.get(titles[j]).getType();
                        Method method = c.getMethod(methodName, cc);
                        String value = String.valueOf(sheet.getRow(i).getCell(j));
                        method.invoke(t, parseValue(value, cc));
                    }
                }
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将字符串转化为指定类型的对象
     * @param s----要转化的字符串
     * @param c----目标对象类型
     * @return
     */
    private static Object parseValue(String s,Class c){
        Object obj = null;
        String className = c.getName();
        //excel中的数字解析之后可能末尾会有.0，需要去除
        if(s.endsWith(".0")) s = s.substring(0, s.length()-2);

        if(className.equals("java.lang.Integer")){ //Integer
            obj =  new Integer(s);
        }else if(className.equals("int")){ //int
            obj = (int)Integer.parseInt(s);
        }else if(className.equals("java.lang.String")){ //String
            obj = s;
        }else if(className.equals("java.lang.Double")){ //Double
            obj = new Double(s);
        }else if(className.equals("double")){ //double
            obj = (double)new Double(s);
        }else if(className.equals("java.lang.Float")){ //Float
            obj = new Float(s);
        }else if(className.equals("float")){ //float
            obj = (float)new Float(s);
        }else if(className.equals("java.util.Date")){ //Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                obj = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(className.equals("long")){ //long
            obj = Long.parseLong(s);
        }else if(className.equals("java.util.Long")){ //Long
            obj = new Long(s);
        }
        return obj;
    }


    @Test
    public void loadTest(){
        File file11 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1测点1.xls");
        List<UnitInfo> list11 = LoadExeclUtil.analysisExcel(file11, UnitInfo.class);
        File file12 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1测点2.xls");
        List<UnitInfo> list12 = LoadExeclUtil.analysisExcel(file12, UnitInfo.class);
        File file13 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1测点3.xls");
        List<UnitInfo> list13 = LoadExeclUtil.analysisExcel(file13, UnitInfo.class);


        File file21 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2测点1.xls");
        List<UnitInfo> list21 = LoadExeclUtil.analysisExcel(file21, UnitInfo.class);
        File file22 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2测点2.xls");
        List<UnitInfo> list22 = LoadExeclUtil.analysisExcel(file22, UnitInfo.class);
        File file23 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2测点3.xls");
        List<UnitInfo> list23 = LoadExeclUtil.analysisExcel(file23, UnitInfo.class);


        List<List<UnitInfo>> list1 = new ArrayList<>();
        list1.add(list11);
        list1.add(list12);
        list1.add(list13);

        List<List<UnitInfo>> list2 = new ArrayList<>();
        list2.add(list21);
        list2.add(list22);
        list2.add(list23);

        List<List<List<UnitInfo>>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);
        System.out.println(list.get(0).size()+"-----------"+list.get(1).size());
        list.forEach(s->s.forEach(m->m.forEach(u-> System.out.println(u.toString()))));
    }

    @Test
    public void loadTest2(){
        File file = new File("C:\\Users\\Mi_dad\\Desktop\\机组2测点3.xls");
        List<UnitInfo> list = LoadExeclUtil.analysisExcel(file, UnitInfo.class);
        list.forEach(l-> System.out.println(l.toString()));
    }



}