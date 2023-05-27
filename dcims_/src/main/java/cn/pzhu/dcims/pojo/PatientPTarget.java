package cn.pzhu.dcims.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 病人检查情况
 * @author cailang
 * @create 2021-02-01-10:56
 */
public class PatientPTarget {

    //主键id
    private Integer id;
    //病人编号
    private Integer patientId;
    //病人信息
    private Patientinfo patientinfo;
    //病人检测类型编号
    private Integer pTargetNo;
    //病人检测类型信息
    private PatientTarget patientTarget;
    //当前监测是否正常
    private Integer status;
    //记录时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date recordTime;
    //对应字符串
    private String recordTimeStr;
    //检测类型对应的值
    private String value;
    //检测指标对应的单位
    private String unit;

    @Override
    public String toString() {
        return "PatientPTarget{" +
                "patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", pTargetNo=" + pTargetNo +
                ", patientTarget=" + patientTarget +
                ", id=" + id +
                ", status=" + status +
                ", recordTime=" + recordTime +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }

    public String getRecordTimeStr() {
        return recordTimeStr;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getpTargetNo() {
        return pTargetNo;
    }

    public void setpTargetNo(Integer pTargetNo) {
        this.pTargetNo = pTargetNo;
    }

    public Patientinfo getPatientinfo() {
        return patientinfo;
    }

    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;
    }

    public PatientTarget getPatientTarget() {
        return patientTarget;
    }

    public void setPatientTarget(PatientTarget patientTarget) {
        this.patientTarget = patientTarget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recordTime);
        this.recordTime = recordTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
