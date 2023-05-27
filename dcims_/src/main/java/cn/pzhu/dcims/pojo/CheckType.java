package cn.pzhu.dcims.pojo;


/**
 * 检查类型对象
 * @author cailang
 * @create 2021-02-01-9:38
 */
public class CheckType {
    //检查类型编号
    private Integer checkTypeNo;
    //检查类型名称
    private String checkTypeName;

    @Override
    public String toString() {
        return "CheckType{" +
                "checkTypeNo=" + checkTypeNo +
                ", checkTypeName='" + checkTypeName + '\'' +
                '}';
    }


    public Integer getCheckTypeNo() {
        return checkTypeNo;
    }

    public void setCheckTypeNo(Integer checkTypeNo) {
        this.checkTypeNo = checkTypeNo;
    }

    public String getCheckTypeName() {
        return checkTypeName;
    }

    public void setCheckTypeName(String checkTypeName) {
        this.checkTypeName = checkTypeName;
    }

}
