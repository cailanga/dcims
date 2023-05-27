package cn.pzhu.dcims.pojo;

/**
 * 医嘱类型
 * @author cailang
 * @create 2021-02-01-10:30
 */
public class MedicalAdviceType {
    //医嘱类型编号
    private Integer medicalAdviceNo;
    //医嘱类型名称
    private String medicalAdviceName;

    @Override
    public String toString() {
        return "MedicalAdviceType{" +
                "medicalAdviceNo=" + medicalAdviceNo +
                ", medicalAdviceName='" + medicalAdviceName + '\'' +
                '}';
    }

    public Integer getMedicalAdviceNo() {
        return medicalAdviceNo;
    }

    public void setMedicalAdviceNo(Integer medicalAdviceNo) {
        this.medicalAdviceNo = medicalAdviceNo;
    }

    public String getMedicalAdviceName() {
        return medicalAdviceName;
    }

    public void setMedicalAdviceName(String medicalAdviceName) {
        this.medicalAdviceName = medicalAdviceName;
    }
}
