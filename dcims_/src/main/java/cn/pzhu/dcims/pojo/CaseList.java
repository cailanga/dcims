package cn.pzhu.dcims.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 病例对象
 * @author cailang
 * @create 2021-02-01-9:11
 */
public class CaseList {
    //主键id
    private Integer id;
    //病人编号
    private Integer patientId;
    //病人信息
    private Patientinfo patientinfo;
    //记录时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date recordTime;
    //记录时间对应的字符串
    private String recordTimeStr;
    //病例名称
    private String caseName;
    //病例内容
    private String content;

    public String getRecordTimeStr() {
        return recordTimeStr;
    }

    @Override
    public String toString() {
        return "CaseList{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", recordTime=" + recordTime +
                ", recordTimeStr='" + recordTimeStr + '\'' +
                ", caseName='" + caseName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Patientinfo getPatientinfo() {
        return patientinfo;
    }

    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recordTime);
        this.recordTime = recordTime;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
