package cn.pzhu.dcims.pojo;

/**
 * 爆发类型
 * @author cailang
 * @create 2021-03-04-22:25
 */
public class OutbreakType {
    //主键id
    private int id;
    //爆发类型编号
    private int outBreakTypeNo;
    //爆发类型名称
    private String outBreakTypeName;

    @Override
    public String toString() {
        return "OutbreakType{" +
                "id=" + id +
                ", outBreakTypeNo=" + outBreakTypeNo +
                ", outBreakTypeName='" + outBreakTypeName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOutBreakTypeNo() {
        return outBreakTypeNo;
    }

    public void setOutBreakTypeNo(int outBreakTypeNo) {
        this.outBreakTypeNo = outBreakTypeNo;
    }

    public String getOutBreakTypeName() {
        return outBreakTypeName;
    }

    public void setOutBreakTypeName(String outBreakTypeName) {
        this.outBreakTypeName = outBreakTypeName;
    }
}
