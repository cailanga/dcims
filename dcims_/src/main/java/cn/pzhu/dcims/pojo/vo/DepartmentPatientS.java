package cn.pzhu.dcims.pojo.vo;

import cn.pzhu.dcims.pojo.Department;

import java.util.ArrayList;

/**
 * 显示每个科室的疑似，排除，预警人数
 */
public class DepartmentPatientS {
    private Department department;
    private ArrayList<Integer> counts;//依次存放疑似感染人数，排除感染人数，预警人数，未感染人数。

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ArrayList<Integer> getCounts() {
        return counts;
    }

    public void setCounts(ArrayList<Integer> counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "DepartmentPatientS{" +
                "department=" + department +
                ", counts=" + counts +
                '}';
    }
}
