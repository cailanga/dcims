package cn.pzhu.dcims.pojo;

/**
 * 科室统计信息
 * @author cailang
 * @create 2021-03-07-16:12
 */
public class ReportTable {

    private Department department;//科室信息
    private int total;//在院人数
    private int upCount;//上报人数
    private int susCount;//疑似爆发人数
    private int elimiCount;//排除人数
    private int warnCount;//预警人数


    @Override
    public String toString() {
        return "ReportTable{" +
                "department=" + department +
                ", total=" + total +
                ", upCount=" + upCount +
                ", susCount=" + susCount +
                ", elimiCount=" + elimiCount +
                ", warnCount=" + warnCount +
                '}';
    }

    public int getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }

    public Department getDepartment() {
        return department;
    }

    public int getElimiCount() {
        return elimiCount;
    }

    public void setElimiCount(int elimiCount) {
        this.elimiCount = elimiCount;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getSusCount() {
        return susCount;
    }

    public void setSusCount(int susCount) {
        this.susCount = susCount;
    }

}
