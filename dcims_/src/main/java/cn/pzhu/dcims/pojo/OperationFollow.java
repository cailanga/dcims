package cn.pzhu.dcims.pojo;

import java.util.List;

/**
 * 手术回访信息
 * @author cailang
 * @create 2021-03-04-20:54
 */
public class OperationFollow {
    //主键id
    private int id;
    //病人编号
    private int patientId;
    //病人信息
    private Patientinfo patientinfo;
    //科室编号
    private int departmentNo;
    //科室信息
    private Department department;
    //手术日期
    private String operationDate;
    //手术名称
    private String operationName;
    //诊断类型信息
    private List<DiagnosisType> diagnosisTypes;
    //医生编号
    private int doctorNo;
    //医生信息
    private Doctor doctor;
    //切口等级
    private int grade;
    //下次回访日期
    private String nextDate;

    @Override
    public String toString() {
        return "OperationFollow{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", departmentNo=" + departmentNo +
                ", department=" + department +
                ", operationDate='" + operationDate + '\'' +
                ", operationName='" + operationName + '\'' +
                ", diagnosisTypes=" + diagnosisTypes +
                ", doctorNo=" + doctorNo +
                ", doctor=" + doctor +
                ", grade=" + grade +
                ", nextDate='" + nextDate + '\'' +
                '}';
    }

    public List<DiagnosisType> getDiagnosisTypes() {
        return diagnosisTypes;
    }

    public void setDiagnosisTypes(List<DiagnosisType> diagnosisTypes) {
        this.diagnosisTypes = diagnosisTypes;
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

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public int getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(int doctorNo) {
        this.doctorNo = doctorNo;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }
}
