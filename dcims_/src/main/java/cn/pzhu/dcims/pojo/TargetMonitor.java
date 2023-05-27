package cn.pzhu.dcims.pojo;

import java.util.List;

/**
 * 目标监测漏报信息
 * @author cailang
 * @create 2021-03-04-19:07
 */
public class TargetMonitor {
    //主键id
    private int id;
    //病人编号
    private int patientId;
    //病人信息
    private Patientinfo patientinfo;
    //检查单编号
    private String checkNo;
    //检测单信息
    private CheckList checkList;
    //检测结果信息
    private List<CheckResultList> checkResultLists;
    //科室编号
    private int departmentNo;
    //送检科室信息
    private Department department;

    @Override
    public String toString() {
        return "TargetMonitor{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", checkNo='" + checkNo + '\'' +
                ", checkList=" + checkList +
                ", checkResultLists=" + checkResultLists +
                ", departmentNo=" + departmentNo +
                ", department=" + department +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Patientinfo getPatientinfo() {
        return patientinfo;
    }

    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public List<CheckResultList> getCheckResultLists() {
        return checkResultLists;
    }

    public void setCheckResultLists(List<CheckResultList> checkResultLists) {
        this.checkResultLists = checkResultLists;
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
}
