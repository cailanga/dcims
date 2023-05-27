package cn.pzhu.dcims.pojo;

/**
 * 诊断类型
 * @author cailang
 * @create 2021-02-01-10:19
 */
public class DiagnosisType {
    //诊断类型编号
    private Integer diagnosisTypeNo;
    //诊断类型名称
    private String diagnosisTypeName;

    @Override
    public String toString() {
        return "DiagnosisType{" +
                "diagnosisTypeNo=" + diagnosisTypeNo +
                ", diagnosisTypeName='" + diagnosisTypeName + '\'' +
                '}';
    }


    public Integer getDiagnosisTypeNo() {
        return diagnosisTypeNo;
    }

    public void setDiagnosisTypeNo(Integer diagnosisTypeNo) {
        this.diagnosisTypeNo = diagnosisTypeNo;
    }

    public String getDiagnosisTypeName() {
        return diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName) {
        this.diagnosisTypeName = diagnosisTypeName;
    }
}
