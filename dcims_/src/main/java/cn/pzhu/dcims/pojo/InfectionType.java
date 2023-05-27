package cn.pzhu.dcims.pojo;

/**
 * 感染类型
 * @author cailang
 * @create 2021-03-04-13:45
 */
public class InfectionType {
    //感染类型编号
    private String infectionTypeNo;
    //感染类型名称
    private String infectionTypeName;

    private Integer id;

    @Override
    public String toString() {
        return "InfectionType{" +
                "infectionTypeNo='" + infectionTypeNo + '\'' +
                ", infectionTypeName='" + infectionTypeName + '\'' +
                ", id=" + id +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfectionTypeNo() {
        return infectionTypeNo;
    }

    public void setInfectionTypeNo(String infectionTypeNo) {
        this.infectionTypeNo = infectionTypeNo;
    }

    public String getInfectionTypeName() {
        return infectionTypeName;
    }

    public void setInfectionTypeName(String infectionTypeName) {
        this.infectionTypeName = infectionTypeName;
    }
}
