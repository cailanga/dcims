package cn.pzhu.dcims.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

//手术
public class Operation {
    //主键
    private int id;
    //病人id
    private int patientId;
    //病人信息
    private Patientinfo patientinfo;
    //手术时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date operateTime;
    //手术名称
    private String operationName;
    //对应字符串
    private String operationTimeStr;
    //手术医生编号
    private int doctorNo;
    //医生信息
    private Doctor doctor;
    //切口等级
    private int grade;

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", operateTime=" + operateTime +
                ", operationName='" + operationName + '\'' +
                ", operationTimeStr='" + operationTimeStr + '\'' +
                ", doctorNo=" + doctorNo +
                ", doctor=" + doctor +
                ", grade=" + grade +
                '}';
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operationTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operateTime);
        this.operateTime = operateTime;
    }

    public String getOperationTimeStr() {
        return operationTimeStr;
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
}
