package cn.pzhu.dcims.pojo;

import java.util.List;
import java.util.Map;

/**
 * ICU监测表格统计信息
 * @author cailang
 * @create 2021-03-04-13:57
 */
public class ICUCheckShow {
    //查询的当前年份
    private String year;
    //查询当前年份的前一年
    private String preyear;
    //当前月份
    private String month;
    //当前月份的前一个月
    private String premonth;
    //当前日期
    private String day;
    //当前日期前一天
    private String preDay;
    //科室编号
    private int departmentNo;
    //所有科室信息
    private List<Department> departments;
    //感染类型
    private String type;
    //所有感染类型，以及对应的人数
    private Map<InfectionType,List<Integer>> infectionTypes;
    private int total1;//总人数
    private int lastTotal;//去年同期总人数
    private int preTotal;//当前日期前一个日期总人数
    private String title;//标题
    private String content1;//显示内容1
    private String content2;//显示内容2
    private String tableTitle2;//表格标题1
    private String tableTitle3;//表格标题2
/*    private String huxiji;
    private String niaodaochaguan;
    private String zhongxinjingmai;*/
    /*private int total;//总器械使用次数
    private Map<InfectionType,Integer> infectionTypeIntegerMap;//当前查询的科室不同感染对应的发病人数
    private Map<InfectionType,Integer> preinfectionTypeIntegerMap;//去年同期查询的科室不同感染对应的发病人数
    */
    private Integer count;//发生疾控次数
    private Integer lastCount;//去年同期发生疾控次数

    @Override
    public String toString() {
        return "ICUCheckShow{" +
                "year='" + year + '\'' +
                ", preyear='" + preyear + '\'' +
                ", month='" + month + '\'' +
                ", premonth='" + premonth + '\'' +
                ", day='" + day + '\'' +
                ", preDay='" + preDay + '\'' +
                ", departmentNo=" + departmentNo +
                ", departments=" + departments +
                ", type='" + type + '\'' +
                ", infectionTypes=" + infectionTypes +
                ", total1=" + total1 +
                ", lastTotal=" + lastTotal +
                ", preTotal=" + preTotal +
                ", title='" + title + '\'' +
                ", content1='" + content1 + '\'' +
                ", content2='" + content2 + '\'' +
                ", tableTitle2='" + tableTitle2 + '\'' +
                ", tableTitle3='" + tableTitle3 + '\'' +
                ", count=" + count +
                ", lastCount=" + lastCount +
                '}';
    }

    public int getPreTotal() {
        return preTotal;
    }

    public void setPreTotal(int preTotal) {
        this.preTotal = preTotal;
    }

    public String getTableTitle2() {
        return tableTitle2;
    }

    public void setTableTitle2(String tableTitle2) {
        this.tableTitle2 = tableTitle2;
    }

    public String getTableTitle3() {
        return tableTitle3;
    }

    public void setTableTitle3(String tableTitle3) {
        this.tableTitle3 = tableTitle3;
    }

    public Integer getLastCount() {
        return lastCount;
    }

    public void setLastCount(Integer lastCount) {
        this.lastCount = lastCount;
    }

    public int getLastTotal() {
        return lastTotal;
    }

    public void setLastTotal(int lastTotal) {
        this.lastTotal = lastTotal;
    }

    public int getTotal1() {
        return total1;
    }

    public void setTotal1(int total1) {
        this.total1 = total1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getPreDay() {
        return preDay;
    }

    public void setPreDay(String preDay) {
        this.preDay = preDay;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPreyear() {
        return preyear;
    }

    public void setPreyear(String preyear) {
        this.preyear = preyear;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPremonth() {
        return premonth;
    }

    public void setPremonth(String premonth) {
        this.premonth = premonth;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(int departmentNo) {
        this.departmentNo = departmentNo;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<InfectionType, List<Integer>> getInfectionTypes() {
        return infectionTypes;
    }

    public void setInfectionTypes(Map<InfectionType,List<Integer>> infectionTypes) {
        this.infectionTypes = infectionTypes;
    }
}
