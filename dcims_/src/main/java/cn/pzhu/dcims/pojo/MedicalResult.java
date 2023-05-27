package cn.pzhu.dcims.pojo;

/**
 * 用药结果
 * @author cailang
 * @create 2021-03-04-11:32
 */
public class MedicalResult {
    //检查结果id
    private int checkresultId;
    //药物名称
    private String medicalName;
    //药敏结果
    private String result;

    @Override
    public String toString() {
        return "MedicalResult{" +
                "checkresultId=" + checkresultId +
                ", medicalName='" + medicalName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public int getCheckresultId() {
        return checkresultId;
    }

    public void setCheckresultId(int checkresultId) {
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
