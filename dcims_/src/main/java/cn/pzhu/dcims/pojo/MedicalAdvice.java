package cn.pzhu.dcims.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 医嘱
 * @author cailang
 * @create 2021-02-01-10:21
 */
//与solr核心库对应关联
@SolrDocument(solrCoreName = "dcimsCore")
public class MedicalAdvice implements Serializable {

    @Id                //标注此字段为唯一主键
    @Field             //标注域名，默认与类属性名一致
    private Integer id;//主键id
    @Field
    private Integer patientId;//病人编号
    private Patientinfo patientinfo;//病人信息
    @Field
    private Integer typeNo;//医嘱类型编号
    private MedicalAdviceType medicalAdviceType;//医嘱类型信息

    @Field
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;//开嘱时间
    private String startTimeStr;//开嘱时间对应的字符串

    @Field
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;//停嘱时间
    private String endTimeStr;//停嘱时间对应字符串

    @Field
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endExcuteTime;//最后执行时间
    private String endExcuteTimeStr;//对应时间的字符串
    @Field
    private String medicalAdviceName;//医嘱名称
    @Field
    private String cause;//用药原因
    private String goal;//用药目的
    private String unit;//计量单位
    private float signall;//单次剂量
    @Field
    private String frequency;//使用频次
    @Field
    private String route;//用药途径
    @Field
    private Integer doctorNo;//开嘱医生编号
    @Field
    private String doctorName;//开嘱医生名字
    private Doctor doctor;//开嘱医生信息
    private Integer antibiotic;//是否使用抗生素标识
    private String antibioticStr;//是否使用抗生素对应字符串
    @Field
    private String instruction;//医嘱说明
    @Field
    private String remark;//备注
    @Field
    private String mtname;//医嘱名称

    private Date update_date;//更新时间
    private String update_dateStr;//对应的字符串

    @Override
    public String toString() {
        return "MedicalAdvice{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", typeNo=" + typeNo +
                ", medicalAdviceType=" + medicalAdviceType +
                ", startTime=" + startTime +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", endTime=" + endTime +
                ", endTimeStr='" + endTimeStr + '\'' +
                ", endExcuteTime=" + endExcuteTime +
                ", endExcuteTimeStr='" + endExcuteTimeStr + '\'' +
                ", medicalAdviceName='" + medicalAdviceName + '\'' +
                ", cause='" + cause + '\'' +
                ", goal='" + goal + '\'' +
                ", unit='" + unit + '\'' +
                ", signall=" + signall +
                ", frequency='" + frequency + '\'' +
                ", route='" + route + '\'' +
                ", doctorNo=" + doctorNo +
                ", doctorName='" + doctorName + '\'' +
                ", doctor=" + doctor +
                ", antibiotic=" + antibiotic +
                ", antibioticStr='" + antibioticStr + '\'' +
                ", instruction='" + instruction + '\'' +
                ", remark='" + remark + '\'' +
                ", mtname='" + mtname + '\'' +
                ", update_date=" + update_date +
                ", update_dateStr='" + update_dateStr + '\'' +
                '}';
    }

    public String getUpdate_dateStr() {
        return update_dateStr;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(update_date);
        this.update_date = update_date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getMtname() {
        return mtname;
    }

    public void setMtname(String mtname) {
        this.mtname = mtname;
    }

    public String getAntibioticStr() {
        return antibioticStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public String getEndExcuteTimeStr() {
        return endExcuteTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public void setEndExcuteTimeStr(String endExcuteTimeStr) {
        this.endExcuteTimeStr = endExcuteTimeStr;
    }

    public void setAntibioticStr(String antibioticStr) {
        this.antibioticStr = antibioticStr;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public Integer getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(Integer doctorNo) {
        this.doctorNo = doctorNo;
    }

    public Patientinfo getPatientinfo() {
        return patientinfo;
    }

    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MedicalAdviceType getMedicalAdviceType() {
        return medicalAdviceType;
    }

    public void setMedicalAdviceType(MedicalAdviceType medicalAdviceType) {
        this.medicalAdviceType = medicalAdviceType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime);
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime);
        this.endTime = endTime;
    }

    public Date getEndExcuteTime() {
        return endExcuteTime;
    }

    public void setEndExcuteTime(Date endExcuteTime) {
        this.endExcuteTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endExcuteTime);
        this.endExcuteTime = endExcuteTime;
    }

    public String getMedicalAdviceName() {
        return medicalAdviceName;
    }

    public void setMedicalAdviceName(String medicalAdviceName) {
        this.medicalAdviceName = medicalAdviceName;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getSignall() {
        return signall;
    }

    public void setSignall(float signal) {
        this.signall = signal;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Integer getAntibiotic() {
        return antibiotic;
    }

    public void setAntibiotic(Integer antibiotic) {
        if(antibiotic==1)this.antibioticStr="是";
        this.antibiotic = antibiotic;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
