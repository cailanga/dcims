package cn.pzhu.dcims.pojo;

/**
 * ICU监测
 * @author cailang
 * @create 2021-03-04-13:41
 */
public class ICUCheck {
    //主键id
    private int id;
    //科室编号
    private int departmentNo;
    //科室信息
    private Department department;
//    private String date;
    //年
    private String year;
    //月
    private String month;
    //日
    private String day;
    //感染类型
    private String type;
    //感染信息
    private InfectionType infectionType;
    //人数
    private int count;

    @Override
    public String toString() {
        return "ICUCheck{" +
                "id=" + id +
                ", departmentNo=" + departmentNo +
                ", department=" + department +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", type='" + type + '\'' +
                ", infectionType=" + infectionType +
                ", count=" + count +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(int departmentNo) {
        this.departmentNo = departmentNo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InfectionType getInfectionType() {
        return infectionType;
    }

    public void setInfectionType(InfectionType infectionType) {
        this.infectionType = infectionType;
    }

}
