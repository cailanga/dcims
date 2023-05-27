package cn.pzhu.dcims.pojo;

//药检结果
public class ResultMedical {
    //检查结果单号
    private Integer checkresultId;
    //药物名称
    private String medicalName;
    //药敏结果
    private String result;

    @Override
    public String toString() {
        return "ResultMedical{" +
                "checkresultId=" + checkresultId +
                ", medicalName='" + medicalName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public Integer getCheckresultId() {
        return checkresultId;
    }

    public void setCheckresultId(Integer checkresultId) {
        this.checkresultId = checkresultId;
    }

    public String getMedicalName() {
        return medicalName;
    }

    public void setMedicalName(String medicalName) {
        this.medicalName = medicalName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
