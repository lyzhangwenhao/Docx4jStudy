package com.zzqa.utils;

import com.zzqa.pojo.UnitInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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


    public static List<List<List<List<UnitInfo>>>> loadTest(){
        File file111 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件1测点1.xls");
        List<UnitInfo> list111 = LoadExeclUtil.analysisExcel(file111, UnitInfo.class);

        File file112 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件1测点2.xls");
        List<UnitInfo> list112 = LoadExeclUtil.analysisExcel(file112, UnitInfo.class);

        File file113 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件1测点3.xls");
        List<UnitInfo> list113 = LoadExeclUtil.analysisExcel(file113, UnitInfo.class);

        List<List<UnitInfo>> list11 = new ArrayList<>();
        list11.add(list111);
        list11.add(list112);
        list11.add(list113);


        File file121 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件2测点1.xls");
        List<UnitInfo> list121 = LoadExeclUtil.analysisExcel(file121, UnitInfo.class);

        File file122 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件2测点2.xls");
        List<UnitInfo> list122 = LoadExeclUtil.analysisExcel(file122, UnitInfo.class);

        File file123 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件2测点3.xls");
        List<UnitInfo> list123 = LoadExeclUtil.analysisExcel(file123, UnitInfo.class);

        List<List<UnitInfo>> list12 = new ArrayList<>();
        list12.add(list121);
        list12.add(list122);
        list12.add(list123);



        File file131 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件3测点1.xls");
        List<UnitInfo> list131 = LoadExeclUtil.analysisExcel(file131, UnitInfo.class);

        File file132 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件3测点2.xls");
        List<UnitInfo> list132 = LoadExeclUtil.analysisExcel(file132, UnitInfo.class);

        File file133 = new File("C:\\Users\\Mi_dad\\Desktop\\机组1部件3测点3.xls");
        List<UnitInfo> list133 = LoadExeclUtil.analysisExcel(file133, UnitInfo.class);

        List<List<UnitInfo>> list13 = new ArrayList<>();
        list13.add(list131);
        list13.add(list132);
        list13.add(list133);


        List<List<List<UnitInfo>>> list1 = new ArrayList<>();
        list1.add(list11);
        list1.add(list12);
        list1.add(list13);


        File file211 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件1测点1.xls");
        List<UnitInfo> list211 = LoadExeclUtil.analysisExcel(file211, UnitInfo.class);

        File file212 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件1测点2.xls");
        List<UnitInfo> list212 = LoadExeclUtil.analysisExcel(file212, UnitInfo.class);

        File file213 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件1测点3.xls");
        List<UnitInfo> list213 = LoadExeclUtil.analysisExcel(file213, UnitInfo.class);

        List<List<UnitInfo>> list21 = new ArrayList<>();
        list21.add(list211);
        list21.add(list212);
        list21.add(list213);


        File file221 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件2测点1.xls");
        List<UnitInfo> list221 = LoadExeclUtil.analysisExcel(file221, UnitInfo.class);

        File file222 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件2测点2.xls");
        List<UnitInfo> list222 = LoadExeclUtil.analysisExcel(file222, UnitInfo.class);

        File file223 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件2测点3.xls");
        List<UnitInfo> list223 = LoadExeclUtil.analysisExcel(file223, UnitInfo.class);

        List<List<UnitInfo>> list22 = new ArrayList<>();
        list22.add(list221);
        list22.add(list222);
        list22.add(list223);



        File file231 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件3测点1.xls");
        List<UnitInfo> list231 = LoadExeclUtil.analysisExcel(file231, UnitInfo.class);

        File file232 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件3测点2.xls");
        List<UnitInfo> list232 = LoadExeclUtil.analysisExcel(file232, UnitInfo.class);

        File file233 = new File("C:\\Users\\Mi_dad\\Desktop\\机组2部件3测点3.xls");
        List<UnitInfo> list233 = LoadExeclUtil.analysisExcel(file233, UnitInfo.class);

        List<List<UnitInfo>> list23 = new ArrayList<>();
        list23.add(list231);
        list23.add(list232);
        list23.add(list233);


        List<List<List<UnitInfo>>> list2 = new ArrayList<>();
        list2.add(list21);
        list2.add(list22);
        list2.add(list23);

        List<List<List<List<UnitInfo>>>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);
        return list;
    }



}