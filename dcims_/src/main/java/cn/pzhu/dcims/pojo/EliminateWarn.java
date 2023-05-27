package cn.pzhu.dcims.pojo;

/**
 * 排除信息
 * @author cailang
 * @create 2021-03-05-13:01
 */
public class EliminateWarn {
    //主键id
    private int id;
    //病人编号
    private int patientId;
    //病人信息
    private Patientinfo patientinfo;
    //排除时间
    private String date;
    //排除内容
    private String content;
    //医生编号
    private int doctorNo;
    //医生信息
    private Doctor doctor;
    //当前操作人员id
    private int userId;
    //当前操作人员信息
    private User user;
    //排除原因
    private String cause;
    //报卡id
    private int reportCardId;
    //报卡信息
    private ReportCard reportCard;

    @Override
    public String toString() {
        return "EliminateWarn{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientinfo=" + patientinfo +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", doctorNo=" + doctorNo +
                ", doctor=" + doctor +
                ", userId=" + userId +
                ", user=" + user +
                ", cause='" + cause + '\'' +
                ", reportCardId=" + reportCardId +
                ", reportCard=" + reportCard +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Patientinfo getPatientinfo() {
        return patientinfo;
    }

    public void setPatientinfo(Patientinfo patientinfo) {
        this.patientinfo = patientinfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(int doctorNo) {
        this.doctorNo = doctorNo;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
