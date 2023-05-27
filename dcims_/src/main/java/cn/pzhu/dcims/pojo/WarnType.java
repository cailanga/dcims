package cn.pzhu.dcims.pojo;

/**
 * 预警类型
 * @author cailang
 * @create 2021-03-05-12:49
 */
public class WarnType {
    //主键id
    private int id;
    //预警类型编号
    private int warnTypeNo;
    //预警类型名称
    private String warnTypeName;

    @Override
    public String toString() {
        return "WarnType{" +
                "id=" + id +
                ", warnTypeNo=" + warnTypeNo +
                ", warnTypeName='" + warnTypeName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarnTypeNo() {
        return warnTypeNo;
    }

    public void setWarnTypeNo(int warnTypeNo) {
        this.warnTypeNo = warnTypeNo;
    }

    public String getWarnTypeName() {
        return warnTypeName;
    }

    public void setWarnTypeName(String warnTypeName) {
        this.warnTypeName = warnTypeName;
    }
}
