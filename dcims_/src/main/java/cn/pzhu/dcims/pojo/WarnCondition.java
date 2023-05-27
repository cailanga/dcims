package cn.pzhu.dcims.pojo;

/**
 * 预警条件
 * @author cailang
 * @create 2021-03-05-12:51
 */
public class WarnCondition {
    //主键id
    private int id;
    //条件编号
    private int conditionNo;
    //条件名称
    private String conditionName;

    @Override
    public String toString() {
        return "WarnCondition{" +
                "id=" + id +
                ", conditionNo=" + conditionNo +
                ", conditionName='" + conditionName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConditionNo() {
        return conditionNo;
    }

    public void setConditionNo(int conditionNo) {
        this.conditionNo = conditionNo;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}
