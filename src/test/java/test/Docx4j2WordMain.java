package test;

import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;

/**
 * ClassName: Docx4j2WordMain
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/3 11:17
 */
public class Docx4j2WordMain {
    public static void main(String[] args) {
        try {
            WordprocessingMLPackage wpMLPackage = WordprocessingMLPackage.createPackage();
            Cover cover = new Cover();
            //创建封面
            wpMLPackage = cover.createCover(wpMLPackage);

            //文章内容
            PageContent pageContent = new PageContent();
            wpMLPackage = pageContent.createPageContent(wpMLPackage);

            //保存文件
            wpMLPackage.save(new File("D:/TestFile/文件1.docx"));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
