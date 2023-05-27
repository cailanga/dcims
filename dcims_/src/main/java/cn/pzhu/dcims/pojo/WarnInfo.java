package cn.pzhu.dcims.pojo;

/**
 * 预警信息
 * @author cailang
 * @create 2021-03-05-12:53
 */
public class WarnInfo {
    //主键id
    private int id;
    //病人编号
    private int patientId;
    //病人信息
    private Patientinfo patientinfo;
    //预警条件编号
    private int conditionNo;
    //预警条件信息
    private WarnCondition warnCondition;
    //预警类型编号
    private int warnTypeNo;
    //预警类型信息
    private WarnType warnType;
    //预警类容
    private String warnContent;
    //预警日期
    private String date;
    //报卡id
    private int reportCardId;
    //报卡信息
    private ReportCard reportCard;
    //预警状态（0：存在，1：已经取消）
    private int status;

    @Override
    public String toString() {
        return "WarnInfo{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", conditionNo=" + conditionNo +
                ", warnCondition=" + warnCondition +
                ", warnTypeNo=" + warnTypeNo +
                ", warnType=" + warnType +
                ", warnContent='" + warnContent + '\'' +
                ", date='" + date + '\'' +
                ", reportCardId=" + reportCardId +
                ", reportCard=" + reportCard +
                ", status=" + status +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReportCardId() {
        return reportCardId;
    }

    public void setReportCardId(int reportCardId) {
        this.reportCardId = reportCardId;
    }

    public ReportCard getReportCard() {
        return reportCard;
    }

    public void setReportCard(ReportCard reportCard) {
        this.reportCard = reportCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Patientinfo getPatientinfo() {
        return patientinfo;
    }

    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;
    }

    public int getConditionNo() {
        return conditionNo;
    }

    public void setConditionNo(int conditionNo) {
        this.conditionNo = conditionNo;
    }

    public WarnCondition getWarnCondition() {
        return warnCondition;
    }

    public void setWarnCondition(WarnCondition warnCondition) {
        this.warnCondition = warnCondition;
    }

    public int getWarnTypeNo() {
        return warnTypeNo;
    }

    public void setWarnTypeNo(int warnTypeNo) {
        this.warnTypeNo = warnTypeNo;
    }

    public WarnType getWarnType() {
        return warnType;
    }

    public void setWarnType(WarnType warnType) {
        this.warnType = warnType;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
