package cn.pzhu.dcims.service;

import cn.pzhu.dcims.pojo.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface DoctorService {
    //获取医生信息
    Doctor getDoctorByUsername(String username);
    //插入病人信息
    Boolean insPatientInfo(Patientinfo patientinfo,PDiagnosis pDiagnosis);
    //修改病人信息
    Boolean updPatientInfo(Patientinfo patientinfo);
    //查询当前登录医生相关的病人信息
    List<Patientinfo> selAllPatietnsByDoctorNo(Integer doctorNo);
    //通过username获取doctor信息
    Doctor selDoctorByUsername(String username);
    //通过departmentNo查询病房信息
    List<BedRoom> selBedRoomsByDno(Integer departmentNo);
    //查询感染检测信息
    List<PatientPTarget> selAllPatientPTargetByPid(Integer pid);
    //插入感染检测信息
    Boolean insPatientPTarget(PatientPTarget patientPTarget);

    //查询手术信息
    List<Operation> selOperationsByDno(int doctorNo);
    //插入手术信息
    Boolean insOperation(Operation operation);
    //插入手术回访信息
    Boolean insOperationFollow(OperationFollow operationFollow);

    //验证pid是否存在
    Boolean selPidByPidDno(Integer pid,Integer dno);
    //获取所有检验类型
    List<CheckType> selAllCheckTypes();
    //插入检验检查信息
    Boolean insCheckList(CheckList checkList);
    //插入检验结果信息
    Boolean insCheckResultList(CheckResultList checkResultList);
    //获取所有检查结果信息
    List<CheckResultList> selAllCheckResultList(String checkNo);
    //查询所有病原体信息
    List<Pathogen> selAllPathogens();
    //查询所有检查结果对应的病原体信息
    List<CheckresultPathogen> selAllCPathogen(String checkresultId);
    //插入检查结果的病原体信息
    Boolean insCPathogen(CheckresultPathogen checkresultPathogen);

    //查询药敏结果
    List<ResultMedical> selAllRMedicalByCid(String checkResultId);
    //插入药敏结果
    Boolean insRMedical(ResultMedical resultMedical);

    //插入医嘱信息
    Boolean insMedicalAdvice(MedicalAdvice medicalAdvice);
    //更新医嘱信息
    Boolean updMedicalAdvice(MedicalAdvice medicalAdvice);
    //获取所有医嘱类型
    List<MedicalAdviceType> selAllMedicalAdviceTypes();
    //通过医生编号获取医嘱信息
    List<MedicalAdvice> findAllMedicalAdviceByDco(Integer doctorNo);

    //查询病例信息
    List<CaseList> selAllCaseList(Integer patientId);
    //通过医生编号获取病例信息
    List<CaseList> findAllCaseListByDco(Integer doctorNo);
    //插入病例信息
    Boolean insCaseList(CaseList caseList);

    //在生成报卡前从其他数据自动获取报卡需要的信息
    ReportCard selReportCard(int id);
    //插入报卡
    Boolean insReportCard(ReportCard reportCard);

}
