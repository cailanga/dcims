package cn.pzhu.dcims.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 检查结果对象
 * @author cailang
 * @create 2021-02-01-9:51
 */
public class CheckResultList {
    //主键id
    private int id;
    //检查单号
    private String checkNo;
    //出结果时间
    private Date resultTime;
    //出结果时间对应字符串
    private String resultTimeStr;
    //检查项目名称
    private String checkName;
    //检查结果
    private Float checkResult;
    //计量单位
    private String unit;
    //正常范围
    private String range;
    //病原体
    private List<CheckresultPathogen> pathogens;
    //药检结果
    private List<MedicalResult> medicalResults;


    public String getResultTimeStr() {
        return resultTimeStr;
    }

    public List<CheckresultPathogen> getPathogens() {
        return pathogens;
    }

    public void setPathogens(List<CheckresultPathogen> pathogens) {
        this.pathogens = pathogens;
    }

    @Override
    public String toString() {
        return "CheckResultList{" +
                "id=" + id +
                ", checkNo='" + checkNo + '\'' +
                ", resultTime=" + resultTime +
                ", checkName='" + checkName + '\'' +
                ", checkResult=" + checkResult +
                ", unit='" + unit + '\'' +
                ", range='" + range + '\'' +
                ", pathogens=" + pathogens +
                ", medicalResults=" + medicalResults +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<MedicalResult> getMedicalResults() {
        return medicalResults;
    }

    public void setMedicalResults(List<MedicalResult> medicalResults) {
        this.medicalResults = medicalResults;
    }

    public Float getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Float checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public Date getResultTime() {
        return resultTime;
    }

    public void setResultTime(Date resultTime) {
        resultTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultTime);
        this.resultTime = resultTime;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

}
