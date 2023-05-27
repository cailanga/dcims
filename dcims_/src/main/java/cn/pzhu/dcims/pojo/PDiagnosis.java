package cn.pzhu.dcims.pojo;

/**
 * 病人与其对应的诊断类型
 * @author cailang
 * @create 2021-03-04-21:42
 */
public class PDiagnosis {
    //诊断类型编号
    private int diagnosisTypeNo;
    //病人编号
    private int patientId;
    //病人信息
    private Patientinfo patientinfo;
    //检测出疾病的日期
    private String recordDate;

    @Override
    public String toString() {
        return "PDiagnosis{" +
                "diagnosisTypeNo=" + diagnosisTypeNo +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", Date='" + recordDate + '\'' +
                '}';
    }

    public int getDiagnosisTypeNo() {
        return diagnosisTypeNo;
    }

    public void setDiagnosisTypeNo(int diagnosisTypeNo) {
        this.diagnosisTypeNo = diagnosisTypeNo;
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

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
