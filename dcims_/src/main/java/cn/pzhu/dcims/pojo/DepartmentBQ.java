package cn.pzhu.dcims.pojo;

import java.util.List;

/**
 * 科室统计信息
 * @author cailang
 * @create 2021-03-28-19:15
 */
public class DepartmentBQ {
    //科室编号
    private int departmentNo;
    //科室
    private Department department;
    //科室各个类型的病人统计
    private List<DepartmentBqType> list;
    //总人数
    private int taotals;

    @Override
    public String toString() {
        return "DepartmentBQ{" +
                "departmentNo=" + departmentNo +
                ", department=" + department +
                ", list=" + list +
                ", taotals=" + taotals +
                '}';
    }

    public int getTaotals() {
        return taotals;
    }

    public void setTaotals(int taotals) {
        this.taotals = taotals;
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

    public List<DepartmentBqType> getList() {
        return list;
    }

    public void setList(List<DepartmentBqType> list) {
        this.list = list;
    }
}
