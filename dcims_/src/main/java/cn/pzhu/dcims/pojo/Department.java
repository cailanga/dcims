package cn.pzhu.dcims.pojo;

/**
 * 科室
 * @author cailang
 * @create 2021-02-01-10:11
 */
public class Department {
    //科室编号
    private Integer departmentNo;
    //科室名称
    private String departmentName;

    @Override
    public String toString() {
        return "Department{" +
                "departmentNo=" + departmentNo +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

    public Integer getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(Integer departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
