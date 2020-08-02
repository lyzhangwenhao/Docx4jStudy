package test;

import com.tom.pojo.File_path;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ClassName: FilePathTest
 * Description:
 *
 * @author Mi_dad
 * @date 2020/7/31 11:20
 */
public class FilePathTest {
    @Test
    public void method(){
        File_path file_path1 = new File_path();
        File_path file_path2 = new File_path();
        File_path file_path3 = new File_path();
        File_path file_path4 = new File_path();

        file_path1.setForeign_id(1);
        file_path2.setForeign_id(1);
        file_path3.setForeign_id(1);
        file_path4.setForeign_id(1);

        file_path1.setType(1);
        file_path2.setType(1);
        file_path3.setType(1);
        file_path4.setType(1);

        file_path1.setFile_name("文件1.txt");
        file_path2.setFile_name("文件1.txt");
        file_path3.setFile_name("文件1.txt");
        file_path4.setFile_name("文件12.txt");

        file_path1.setPath_name("123.txt");
        file_path2.setPath_name("123.txt");
        file_path3.setPath_name("123.txt");
        file_path4.setPath_name("123.txt");

        file_path1.setSize_String("1");
        file_path2.setSize_String("1");
        file_path3.setSize_String("2");
        file_path4.setSize_String("1");

        file_path1.setFile_type(1);
        file_path2.setFile_type(1);
        file_path3.setFile_type(1);
        file_path4.setFile_type(1);


        List<File_path> list = new ArrayList<>();
        list.add(file_path1);
        list.add(file_path2);
        list.add(file_path3);
        list.add(file_path4);


        for (File_path l:list){
            System.out.println(l);
        }
        System.out.println("---------------");
        Set<File_path> set = new LinkedHashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);

        for (File_path l:list){
            System.out.println(l);
        }


    }

}
