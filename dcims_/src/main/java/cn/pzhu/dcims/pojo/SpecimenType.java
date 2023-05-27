package cn.pzhu.dcims.pojo;

/**
 * 标本
 * @author cailang
 * @create 2021-02-01-9:41
 */
public class SpecimenType {
    //标本编号
    private Integer specimenNo;
    //标本名称
    private String specimenName;

    @Override
    public String toString() {
        return "SpecimenType{" +
                "specimenNo=" + specimenNo +
                ", specimenName='" + specimenName + '\'' +
                '}';
    }

    public Integer getSpecimenNo() {
        return specimenNo;
    }

    public void setSpecimenNo(Integer specimenNo) {
        this.specimenNo = specimenNo;
    }

    public String getSpecimenName() {
        return specimenName;
    }

    public void setSpecimenName(String specimenName) {
        this.specimenName = specimenName;
    }
}
