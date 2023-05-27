package cn.pzhu.dcims.pojo;

/**
 * 手术信息
 * @author cailang
 * @create 2021-03-28-18:59
 */
public class OperateInfo {
    //主键id
    private int id;
    //手术名称
    private int count;
    //对应人数
    private String infoName;

    @Override
    public String toString() {
        return "OperateInfo{" +
                "id=" + id +
                ", count=" + count +
                ", infoName='" + infoName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }
}
