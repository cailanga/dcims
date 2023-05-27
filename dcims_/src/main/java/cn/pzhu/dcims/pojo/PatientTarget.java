package cn.pzhu.dcims.pojo;

/**
 * 检测指标
 * @author cailang
 * @create 2021-02-01-10:33
 */
public class PatientTarget {
    //指标编号
    private Integer pTargetNo;
    //指标名称
    private String pTargetName;

    @Override
    public String toString() {
        return "PatientTarget{" +
                "pTargetNo=" + pTargetNo +
                ", pTargetName='" + pTargetName + '\'' +
                '}';
    }

    public Integer getpTargetNo() {
        return pTargetNo;
    }

    public void setpTargetNo(Integer pTargetNo) {
        this.pTargetNo = pTargetNo;
    }

    public String getpTargetName() {
        return pTargetName;
    }

    public void setpTargetName(String pTargetName) {
        this.pTargetName = pTargetName;
    }
}
