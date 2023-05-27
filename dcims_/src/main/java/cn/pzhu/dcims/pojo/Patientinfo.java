package cn.pzhu.dcims.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 病人信息
 * @author cailang
 * @create 2021-02-01-8:56
 */
public class Patientinfo {
    //主键id
    private Integer id;
    //头像地址
    private String imagePath;
    //名字
    private String name;
    //年龄
    private Integer age;
    //性别
    private String sex;
    //手机号
    private String phone;
    //入院日期
    private Date enterTime;
    //入院日期对应的字符串
    private String enterTimeStr;
    //出院时间
    private Date outTime;
    //出院日期对应的字符串
    private String outTimeStr;
    //科室编号
    private int departmentNo;
    //科室信息
    private Department department;
    //主治医生编号
    private int attDoctorNo;
    //主治医生信息
    private Doctor doctor;
    //病床编号
    private String bedNumber;
    //诊断类型
    private List<DiagnosisType> diagnosisTypes;
    //是否出院标识
    private int status;

    @Override
    public String toString() {
        return "Patientinfo{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", enterTime=" + enterTime +
                ", enterTimeStr='" + enterTimeStr + '\'' +
                ", outTime=" + outTime +
                ", outTimeStr='" + outTimeStr + '\'' +
                ", departmentNo=" + departmentNo +
                ", department=" + department +
                ", attDoctorNo=" + attDoctorNo +
                ", doctor=" + doctor +
                ", bedNumber=" + bedNumber +
                ", diagnosisTypes=" + diagnosisTypes +
                ", status=" + status +
                '}';
    }

    public String getEnterTimeStr() {
        return enterTimeStr;
    }

    public String getOutTimeStr() {
        return outTimeStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(int departmentNo) {
        this.departmentNo = departmentNo;
    }

    public int getAttDoctorNo() {
        return attDoctorNo;
    }

    public void setAttDoctorNo(int attdoctorNo) {
        this.attDoctorNo = attdoctorNo;
    }

    public List<DiagnosisType> getDiagnosisTypes() {
        return diagnosisTypes;
    }

    public void setDiagnosisTypes(List<DiagnosisType> diagnosisTypes) {
        this.diagnosisTypes = diagnosisTypes;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(enterTime);
        this.enterTime = enterTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(outTime);
        this.outTime = outTime;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }
}
