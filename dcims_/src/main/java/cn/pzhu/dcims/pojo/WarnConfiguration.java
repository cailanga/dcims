package cn.pzhu.dcims.pojo;

/**
 * 自动预警配置类
 */
public class WarnConfiguration {
    private Integer id;
    private String warnName;
    private Integer warnCount;
    private Integer departmentNo;

    @Override
    public String toString() {
        return "WarnConfiguration{" +
                "id=" + id +
                ", warnName='" + warnName + '\'' +
                ", warnCount=" + warnCount +
                ", departmentNo=" + departmentNo +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public Integer getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Integer warnCount) {
        this.warnCount = warnCount;
    }

    public Integer getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(Integer departmentNo) {
        this.departmentNo = departmentNo;
    }
}
