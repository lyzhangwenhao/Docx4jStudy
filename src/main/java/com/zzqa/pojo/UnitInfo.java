package com.zzqa.pojo;

/**
 * ClassName: UnitInfo
 * Description:
 *
 * @author 张文豪
 * @date 2020/8/5 13:57
 */
public class UnitInfo {
    //报警机组	报警部件	报警次数	触发报警门限及类型	最大报警特征值	报警等级
    private String unitName;    //报警机组
    private String unitPart;    //报警部件
    private int count;  //报警次数
    private String valve;   //触发门限
    private String type;    //类型
    private String maxValue;    //最大报警特征值
    private String level;   //报警等级

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitPart() {
        return unitPart;
    }

    public void setUnitPart(String unitPart) {
        this.unitPart = unitPart;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getValve() {
        return valve;
    }

    public void setValve(String valve) {
        this.valve = valve;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UnitInfo{" +
                "unitName='" + unitName + '\'' +
                ", unitPart='" + unitPart + '\'' +
                ", count=" + count +
                ", valve='" + valve + '\'' +
                ", type='" + type + '\'' +
                ", maxValue='" + maxValue + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
