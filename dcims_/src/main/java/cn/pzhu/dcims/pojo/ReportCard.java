package cn.pzhu.dcims.pojo;

/**
 * 报卡信息
 * @author cailang
 * @create 2021-03-04-23:30
 */
public class ReportCard {
    //主键id
    private int id;
    //病人id
    private int patientId;
    //科室编号
    private int departmentNo;
    //病人信息
    private Patientinfo patientinfo;
    private String upTime;//上报时间
    private int status;//报卡状态
    private String statusStr;//报卡状态对应的字符串
    private int doctorNo;//上报医生编号
    private Doctor doctor;//上报人信息
    private Doctor MainDoctor;//通过patientinfo找到主治医生
    private int handleUserId;//预警人id
    private int susUserId;//疑似人id
    private int paichuUserId;//排除人员Id
    private User handleUser;//处理人

    private int isLeaveHospital;//是否出院 0：否，1：是
    private String isLeaveHospitalStr;

    private int isCheckedPathogen;//是否检出病原体 0：否，1：是
    private String isCheckedPathogenStr;

    private int isMultiMedical;//是否多重耐药 0：否，1：是
    private String isMultiMedicalStr;

    private int isUseMedical;//是否使用抗菌药物 0：否，1：是
    private String isUseMedicalStr;

    private int isInvade;//是否侵入性操作 0：否，1：是
    private String isInvadeStr;

    private int isOperation;//是否手术 0：否，1：是
    private String isOperationStr;

    private int diagnosisTypeNo;//入院诊断编号
    private DiagnosisType diagnosisType;//诊断类型信息
    private String diagnosis;//诊断类型名称

    private int tisNormal;//体温是否正常 0：否，1：是
    private String tisNormalStr;

    private String warnDate;//预警时间
    private String handleDate;//处理时间

    private int cstatus;//社区状态
    private String cstatusStr;

    private int warnStatus;//预警状态
    private String warnStatusStr;

    private int handleAction;//处理行为  处理操作(0：排除，1：预警，2：疑似爆发)


    private String courseAnalysis;//病程分析

    public int getCstatus() {
        return cstatus;
    }

    public void setCstatus(int cstatus) {
        cstatus = cstatus;
        if(cstatus==0){
            this.cstatusStr="否";
        }else{
            this.cstatusStr="是";
        }
    }

    @Override
    public String toString() {
        return "ReportCard{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", departmentNo=" + departmentNo +
                ", patientinfo=" + patientinfo +
                ", upTime='" + upTime + '\'' +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", doctorNo=" + doctorNo +
                ", doctor=" + doctor +
                ", MainDoctor=" + MainDoctor +
                ", handleUserId=" + handleUserId +
                ", susUserId=" + susUserId +
                ", paichuUserId=" + paichuUserId +
                ", handleUser=" + handleUser +
                ", isLeaveHospital=" + isLeaveHospital +
                ", isLeaveHospitalStr='" + isLeaveHospitalStr + '\'' +
                ", isCheckedPathogen=" + isCheckedPathogen +
                ", isCheckedPathogenStr='" + isCheckedPathogenStr + '\'' +
                ", isMultiMedical=" + isMultiMedical +
                ", isMultiMedicalStr='" + isMultiMedicalStr + '\'' +
                ", isUseMedical=" + isUseMedical +
                ", isUseMedicalStr='" + isUseMedicalStr + '\'' +
                ", isInvade=" + isInvade +
                ", isInvadeStr='" + isInvadeStr + '\'' +
                ", isOperation=" + isOperation +
                ", isOperationStr='" + isOperationStr + '\'' +
                ", diagnosisTypeNo=" + diagnosisTypeNo +
                ", diagnosisType=" + diagnosisType +
                ", diagnosis='" + diagnosis + '\'' +
                ", tisNormal=" + tisNormal +
                ", tisNormalStr='" + tisNormalStr + '\'' +
                ", warnDate='" + warnDate + '\'' +
                ", handleDate='" + handleDate + '\'' +
                ", cstatus=" + cstatus +
                ", cstatusStr='" + cstatusStr + '\'' +
                ", warnStatus=" + warnStatus +
                ", warnStatusStr='" + warnStatusStr + '\'' +
                ", handleAction=" + handleAction +
                ", courseAnalysis='" + courseAnalysis + '\'' +
                '}';
    }

    public int getSusUserId() {
        return susUserId;
    }

    public void setSusUserId(int susUserId) {
        this.susUserId = susUserId;
    }

    public int getPaichuUserId() {
        return paichuUserId;
    }

    public void setPaichuUserId(int paichuUserId) {
        this.paichuUserId = paichuUserId;
    }

    public int getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(int handleUserId) {
        this.handleUserId = handleUserId;
    }

    public User getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(User handleUser) {
        this.handleUser = handleUser;
    }

    public String getCourseAnalysis() {
        return courseAnalysis;
    }

    public void setCourseAnalysis(String courseAnalysis) {
        this.courseAnalysis = courseAnalysis;
    }

    public int getHandleAction() {
        return handleAction;
    }

    public void setHandleAction(int handleAction) {
        this.handleAction = handleAction;
    }

    public int getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(int departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public String getIsLeaveHospitalStr() {
        return isLeaveHospitalStr;
    }


    public String getIsCheckedPathogenStr() {
        return isCheckedPathogenStr;
    }


    public String getisMultiMedicalStr() {
        return isMultiMedicalStr;
    }


    public String getIsUseMedicalStr() {
        return isUseMedicalStr;
    }


    public String getIsInvadeStr() {
        return isInvadeStr;
    }


    public String getIsOperationStr() {
        return isOperationStr;
    }


    public String getDiagnosis() {
        return diagnosis;
    }


    public String getTisNormalStr() {
        return tisNormalStr;
    }


    public String getCstatusStr() {
        return cstatusStr;
    }


    public String getWarnStatusStr() {
        return warnStatusStr;
    }


    public int getIsLeaveHospital() {
        return isLeaveHospital;
    }

    public void setIsLeaveHospital(int isLeaveHospital) {
        this.isLeaveHospital = isLeaveHospital;
        this.isLeaveHospitalStr = isLeaveHospital==0?"否":"是";
    }

    public int getIsCheckedPathogen() {
        return isCheckedPathogen;
    }

    public void setIsCheckedPathogen(int isCheckedPathogen) {
        this.isCheckedPathogen = isCheckedPathogen;
        this.isCheckedPathogenStr = isCheckedPathogen==0?"否":"是";
    }

    public int getIsInvade() {
        return isInvade;
    }

    public void setIsInvade(int isInvade) {
        this.isInvade = isInvade;
        this.isInvadeStr=isInvade==0?"否":"是";
    }

    public int getDiagnosisTypeNo() {
        return diagnosisTypeNo;
    }

    public void setDiagnosisTypeNo(int diagnosisTypeNo) {
        this.diagnosisTypeNo = diagnosisTypeNo;
    }

    public DiagnosisType getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(DiagnosisType diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public int getTisNormal() {
        return tisNormal;
    }

    public void setTisNormal(int tisNormal) {
        this.tisNormal = tisNormal;
        this.tisNormalStr=tisNormal==0?"否":"是";
    }

    public String getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }


    public int getWarnStatus() {
        return warnStatus;
    }

    public void setWarnStatus(int warnStatus) {
        this.warnStatus = warnStatus;
        if(warnStatus==1){
            this.warnStatusStr="新预警";
        }else{
            this.warnStatusStr="旧预警";
        }
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


    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        this.statusStr=status==0?"未处理":"已处理";
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

    public Doctor getMainDoctor() {
        return MainDoctor;
    }

    public void setMainDoctor(Doctor mainDoctor) {
        MainDoctor = mainDoctor;
    }

    public int getisMultiMedical() {
        return isMultiMedical;
    }

    public void setisMultiMedical(int isMultiMedical) {
        this.isMultiMedical = isMultiMedical;
        if(isMultiMedical==0){
            this.isMultiMedicalStr="否";
        }else {
            this.isMultiMedicalStr="是";
        }
    }

    public int getIsUseMedical() {
        return isUseMedical;
    }

    public void setIsUseMedical(int isUseMedical) {
        this.isUseMedical = isUseMedical;
        if(isUseMedical==0){
            this.isUseMedicalStr="否";
        }else{
            this.isUseMedicalStr="是";
        }
    }


    public int getIsOperation() {
        return isOperation;
    }

    public void setIsOperation(int isOperation) {
        this.isOperation = isOperation;
        if(isOperation==0){
            this.isOperationStr="否";
        }else{
            this.isOperationStr="是";
        }
    }
}
