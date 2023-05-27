package cn.pzhu.dcims.pojo;

/**
 * 疑似爆发信息
 * @author cailang
 * @create 2021-03-04-22:22
 */
public class SusOutbreak {
    //主键id
    private int id;
    //科室编号
    private int departmentNo;
    //科室信息
    private Department department;
    //例次数
    private int licount;
    //爆发类型编号
    private int outBreakTypeNo;
    //爆发类型信息
    private OutbreakType outbreakType;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //名称
    private String name;
    //备注
    private String remark;
    //报卡id
    private int reportCardId;
    //病人编号
    private int patientId;

    @Override
    public String toString() {
        return "SusOutbreak{" +
                "id=" + id +
                ", departmentNo=" + departmentNo +
                ", department=" + department +
                ", licount=" + licount +
                ", outBreakTypeNo=" + outBreakTypeNo +
                ", outbreakType=" + outbreakType +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", reportCardId=" + reportCardId +
                ", patientId=" + patientId +
                '}';
    }

    public int getReportCardId() {
        return reportCardId;
    }

    public void setReportCardId(int reportCardId) {
        this.reportCardId = reportCardId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(int departmentNo) {
        this.departmentNo = departmentNo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getLicount() {
        return licount;
    }

    public void setLicount(int licount) {
        this.licount = licount;
    }

    public int getOutBreakTypeNo() {
        return outBreakTypeNo;
    }

    public void setOutBreakTypeNo(int outBreakTypeNo) {
        this.outBreakTypeNo = outBreakTypeNo;
    }

    public OutbreakType getOutbreakType() {
        return outbreakType;
    }

    public void setOutbreakType(OutbreakType outbreakType) {
        this.outbreakType = outbreakType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
