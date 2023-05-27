package cn.pzhu.dcims.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 检查单对象
 * @author cailang
 * @create 2021-02-01-9:32
 */
public class CheckList {
    //主键id
    private Integer id;
    //检查单号
    private String checkNo;
    //病人编号
    private Integer patientId;
    //检查的时间
    private Date checkTime;
    //检查时间对应的字符串
    private String checkTimeStr;
    //备注
    private String remark;
    //检查类型
    private List<CheckType> checkTypes;
    //检查类型对应的字符串
    private String checkTypeStr;
    //标本名称
    private String specimenName;

    public String getCheckTypeStr() {
        return checkTypeStr;
    }

    public String getCheckTimeStr() {
        return checkTimeStr;
    }

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "id=" + id +
                ", checkNo='" + checkNo + '\'' +
                ", patientId=" + patientId +
                ", checkTime=" + checkTime +
                ", remark='" + remark + '\'' +
                ", checkTypes=" + checkTypes +
                ", specimenName='" + specimenName + '\'' +
                '}';
    }

    public List<CheckType> getCheckTypes() {
        return checkTypes;
    }

    public void setCheckTypes(List<CheckType> checkTypes) {
        this.checkTypeStr="";
        for (int i=0;i<checkTypes.size();i++){
            CheckType checkType=checkTypes.get(i);
            if(i<checkTypes.size()-1)
            this.checkTypeStr+=checkType.getCheckTypeName()+"+";
            else this.checkTypeStr+=checkType.getCheckTypeName();
        }
        this.checkTypes = checkTypes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(checkTime);
        this.checkTime = checkTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
