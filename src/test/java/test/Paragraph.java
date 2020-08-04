package test;

import java.io.File;
import java.math.BigInteger;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.DocDefaults;
import org.docx4j.wml.DocDefaults.PPrDefault;
import org.docx4j.wml.DocDefaults.RPrDefault;
import org.docx4j.wml.Document;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Ind;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.RStyle;
import org.docx4j.wml.STLineSpacingRule;
import org.docx4j.wml.Style;
import org.docx4j.wml.Style.Name;
import org.docx4j.wml.Style.UiPriority;
import org.docx4j.wml.Styles;
import org.docx4j.wml.Text;
import org.junit.Test;

/**
 * ClassName: Paragraph
 * Description:
 *
 * @author Mi_dad
 * @date 2020/8/4 9:03
 */
public class Paragraph {





    protected ObjectFactory factory = Context.getWmlObjectFactory();

    protected RFonts createRFonts(String ascii, String hAnsi, String eastAsia) {
        RFonts rfonts = factory.createRFonts();
        rfonts.setAscii(ascii);
        rfonts.setHAnsi(hAnsi);
        rfonts.setEastAsia(eastAsia);
        return rfonts;
    }

    protected HpsMeasure createHpsMeasure(int val) {
        HpsMeasure hm = factory.createHpsMeasure();
        hm.setVal(BigInteger.valueOf(val));
        return hm;
    }

    /**
     * 字号11，新宋体/Times New Roman复合字体
     * 1.5倍行距，段前段后各0.5行，首行缩进2字符
     * @return
     */
    public DocDefaults createDocDefaults() {
        DocDefaults defaults = factory.createDocDefaults();

        RPrDefault rprd = factory.createDocDefaultsRPrDefault();
        defaults.setRPrDefault(rprd);
        RPr rpr = factory.createRPr();
        rprd.setRPr(rpr);

        rpr.setRFonts(this.createRFonts("Times New Roman", "Times New Roman", "新宋体"));
        rpr.setSz(this.createHpsMeasure(22));

        PPrDefault pprd = factory.createDocDefaultsPPrDefault();
        defaults.setPPrDefault(pprd);
        PPr ppr = factory.createPPr();
        pprd.setPPr(ppr);

        Ind ind = factory.createPPrBaseInd();
        ind.setFirstLineChars(BigInteger.valueOf(200));
        ppr.setInd(ind);

        Spacing spacing = factory.createPPrBaseSpacing();
        spacing.setBeforeLines(BigInteger.valueOf(50));
        spacing.setAfterLines(BigInteger.valueOf(50));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(360));
        ppr.setSpacing(spacing);

        Jc jc = factory.createJc();
        jc.setVal(JcEnumeration.LEFT);
        ppr.setJc(jc);

        return defaults;
    }

    public Style createParagraphStyle() {
        Style style = factory.createStyle();
        style.setType("paragraph");
        Name name = new Name();
        name.setVal("段落样式示例");
        style.setName(name);
        UiPriority up = factory.createStyleUiPriority();
        up.setVal(BigInteger.valueOf(1));
        style.setUiPriority(up);
        style.setStyleId("paragraph_1");

        RPr rpr = factory.createRPr();
        style.setRPr(rpr);

        rpr.setRFonts(this.createRFonts("Arial", "Arial", "华文仿宋"));
        rpr.setSz(this.createHpsMeasure(18));

        PPr ppr = factory.createPPr();
        style.setPPr(ppr);

        Ind ind = factory.createPPrBaseInd();
        ind.setFirstLine(BigInteger.valueOf(0));
        ppr.setInd(ind);

        Spacing spacing = factory.createPPrBaseSpacing();
        spacing.setBeforeLines(BigInteger.valueOf(0));
        spacing.setAfterLines(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
        ppr.setSpacing(spacing);

        Jc jc = factory.createJc();
        jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);

        return style;
    }

    public Style createCharacterStyle() {
        Style style = factory.createStyle();
        style.setType("character");
        Name name = new Name();
        name.setVal("字体样式示例");
        style.setName(name);
        style.setStyleId("character_1");
        UiPriority up = factory.createStyleUiPriority();
        up.setVal(BigInteger.valueOf(2));
        style.setUiPriority(up);

        RPr rpr = factory.createRPr();
        style.setRPr(rpr);

        rpr.setRFonts(this.createRFonts("Arial Black", "Arial Black", "微软雅黑"));
        rpr.setSz(this.createHpsMeasure(24));

        return style;
    }

    protected P createP(String str) {
        P para = factory.createP();
        R run = factory.createR();
        Text text = factory.createText();
        text.setValue(str);

        run.getContent().add(text);
        para.getContent().add(run);

        run.setRPr(factory.createRPr());
        para.setPPr(factory.createPPr());
        return para;
    }
    @Test
    public void doTest() throws Docx4JException {
        WordprocessingMLPackage pkg = WordprocessingMLPackage.createPackage();

        MainDocumentPart main = pkg.getMainDocumentPart();
        Document doc = main.getContents();
        Body body = doc.getBody();

        StyleDefinitionsPart sdp = main.getStyleDefinitionsPart(true);
        Styles styles = factory.createStyles();
        sdp.setJaxbElement(styles);

        styles.setDocDefaults(this.createDocDefaults());
        styles.getStyle().add(this.createParagraphStyle());
        styles.getStyle().add(this.createCharacterStyle());



        //默认样式
        body.getContent().add(this.createP("The quick brown fox jumps over the lazy dog."));
        body.getContent().add(this.createP("关关雎鸠，在河之洲。窈窕淑女，君子好逑。"));

        //段落样式
        PStyle ps1 = factory.createPPrBasePStyle();
        ps1.setVal("paragraph_1");
        P p11 = this.createP("The quick brown fox jumps over the lazy dog.");
        p11.getPPr().setPStyle(ps1);
        body.getContent().add(p11);
        P p12 = this.createP("关关雎鸠，在河之洲。窈窕淑女，君子好逑。");
        p12.getPPr().setPStyle(ps1);
        body.getContent().add(p12);


        //字体样式
        RStyle rs1 = factory.createRStyle();
        rs1.setVal("character_1");
        P p21 = this.createP("The quick brown fox jumps over the lazy dog.");
        p21.getPPr().setPStyle(ps1);
        ((R)p21.getContent().get(0)).getRPr().setRStyle(rs1);
        body.getContent().add(p21);
        P p22 = this.createP("关关雎鸠，在河之洲。窈窕淑女，君子好逑。");
        p22.getPPr().setPStyle(ps1);
        ((R)p22.getContent().get(0)).getRPr().setRStyle(rs1);
        body.getContent().add(p22);


        pkg.save(new File("D:/TestFile/段落测试.docx"));
    }
}
