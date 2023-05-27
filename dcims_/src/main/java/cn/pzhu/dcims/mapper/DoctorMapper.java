package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.pojo.*;
import org.apache.ibatis.annotations.*;

import javax.print.Doc;
import java.util.List;

@Mapper
public interface DoctorMapper {
    @Select("select * from doctor where username=#{username}")
    Doctor selDoctorByUsernam(String username);
    @Delete("delete from doctor where username=#{username}")
    int delDoctor(String username);
    @Insert("insert into patientInfo(name,age,sex,phone,enterTime,departmentNo,attDoctorNo,bedNumber) values(#{name},#{age},#{sex},#{phone},#{enterTime},#{departmentNo},#{attDoctorNo},#{bedNumber})")
    @Options(useGeneratedKeys =true, keyProperty = "id", keyColumn="id")
    int insPatientInfo(Patientinfo patientinfo);
    @Insert("insert into patient_diagnosisType values(#{diagnosisTypeNo},#{patientId},#{recordDate})")
    Integer insDiagnosisType(PDiagnosis pDiagnosis);
    @Update("update patientInfo set name=#{name},age=#{age},sex=#{sex},phone=#{phone},departmentNo=#{departmentNo},bedNumber=#{bedNumber} where id=#{id}")
    Integer updPatientInfo(Patientinfo patientinfo);
    @Update("update sickBed set pid=#{pid} where bedRoomName=#{bedRoomName} and sickBedNo=#{sickBedNo}")
    Integer updSickBed(SickBed sickBed);
    @Select("select * from patientInfo where attDoctorNo=#{doctorNo}")
    List<Patientinfo> selAllPatientsByDoctorNo(Integer doctorNo);
    @Select("select * from doctor where username=#{username}")
    Doctor selDoctorByUsername(String username);
    @Select("select * from bedRoom where departmentNo=#{departmentNo}")
    List<BedRoom> selBedRoomsByDno(Integer departmentNo);
    @Select("select id  from patientInfo where id=(select max(id) from patientInfo)")
    Integer selPatientMaxId();
    @Select("select count(*) from operation where patientId=#{patientId}")
    Integer selOperationCountsByPid(int patientId);
    @Select("select status from patientInfo where id = #{id}")
    Integer selPStatusByPid(int patientId);
    @Select("select count(*) from checkresult_pathogen crp inner join checkresultlist crl on crl.id=crp.checkresultId  inner join checkList cl on cl.checkNo=crl.checkNo  where cl.patientId=#{patientId}")
    Integer selPathogenByPid(int patientId);
    @Select("select count(*) from patient_patienttarget where patientId=#{patientId} and PTargetNo =15")
    Integer selIsInvadeByPid(int patientId);
    @Select("select count(*) from patient_patienttarget where patientId=#{patientId} and (PTargetNo=12 or PTargetNo=13 or PTargetNo=14) ")
    Integer selIsUseMedicalByPid(int patientId);
    @Select("select count(*) from pathogen po left join checkresult_pathogen crp on po.pathogenNo=crp.pathogenNo left join checkresultlist crl on crl.id=crp.checkresultId  left join checkList cl on cl.checkNo=crl.checkNo  where po.isMultidrug=1 and cl.patientId=#{patientId}")
    Integer selIsMultiMedical(int patientId);
    @Select("select a.value from patient_patienttarget as a where a.patientId=#{patientId} and a.PTargetNo=1 and a.recordTime=(select max(recordTime) from patient_patienttarget as b where b.PTargetNo=1 and a.patientId=b.patientId) ")
    Integer selTisNormalByPid(int patientId);
    @Insert("insert into reportCard(patientId,upTime,doctorNo,courseAnalysis,warnStatus,cstatus,isOperation,isLeaveHospital,isCheckedPathogen,isInvade,diagnosisTypeNo,isMultiMedical,isUseMedical,tisNormal,departmentNo) " +
            "values(#{patientId},#{upTime},#{doctorNo},#{courseAnalysis},#{warnStatus},#{cstatus},#{isOperation},#{isLeaveHospital},#{isCheckedPathogen},#{isInvade},#{diagnosisTypeNo},#{isMultiMedical},#{isUseMedical},#{tisNormal},#{departmentNo})")
    Integer insReportCard(ReportCard reportCard);

    @Select("select * from operation where doctorNo=#{doctorNo}")
    List<Operation> selOperationsByDno(Integer doctorNo);
    @Insert("insert into operation values(default,#{patientId},#{operationName},#{operateTime},#{doctorNo},#{grade})")
    Integer insOperation(Operation operation);
    @Insert("insert into operationFollow values(default,#{patientId},#{departmentNo},#{operationDate},#{operationName},#{doctorNo},#{grade},#{nextDate})")
    Integer insOperationFollow(OperationFollow operationFollow);

    @Select("select * from patient_patientTarget where patientId=#{patientId}")
    List<PatientPTarget> selAllPatientPTargetByPid(Integer patientId);
    @Insert("insert into patient_patientTarget values(default,#{patientId},#{pTargetNo},#{status},#{recordTime},#{value},#{unit})")
    Integer insPatientPTarget(PatientPTarget patientPTarget);

    @Select("select id from patientInfo where id=#{patientId} and attDoctorNo=#{doctorNo}")
    Integer selPidByPidDno(Integer patientId,Integer doctorNo);
    @Select("select * from checkType")
    List<CheckType> selAllCheckTypes();
    @Select("select id from checkList where id=(select max(id) from checkList)")
    Integer selMaxIdFromCheckList();
    @Insert("insert into checkList values(default,#{checkNo},#{patientId},#{checkTime},#{remark},#{specimenName})")
    Integer insCheckList(CheckList checkList);
    @Select("select * from checkResultList where checkNo=#{checkNo}")
    List<CheckResultList> selAllCheckResultListByCno(String checkNo);
    @Insert("insert into checkResultList values(default,#{checkNo},#{resultTime},#{checkName},#{checkResult},#{unit},#{range})")
    Integer insCheckResultList(CheckResultList checkResultList);
    @Insert("insert into checkList_checkType values(#{checkNo},#{checkTypeNo})")
    Integer insCheckTypeCheckList(String checkNo,String checkTypeNo);
    @Select("select * from checkType where checkTypeNo=#{checkTypeNo}")
    CheckType selCheckTypeByCno(String checkTypeNo);
    @Select("select * from checkResult_pathogen where checkResultId=#{checkResultId}")
    List<CheckresultPathogen> selAllCPathogenByCid(String checkResultId);
    @Insert("insert into checkResult_pathogen values(#{pathogenNo},#{checkresultId},#{date},#{content})")
    Integer insCpathogen(CheckresultPathogen checkresultPathogen);
    @Select("select * from pathogen")
    List<Pathogen> selAllPathogen();
    @Select("select * from pathogen where pathogenNo=#{pathogenNo}")
    Pathogen selPathogenByPno(String pathogenNo);

    @Select("select * from resultlist_medical where checkResultId=#{checkResultId}")
    List<ResultMedical> selAllRMedicalByCid(String checkResultId);
    @Insert("insert into resultlist_medical values(#{checkresultId},#{medicalName},#{result})")
    Integer insRMedical(ResultMedical resultMedical);

    @Insert("insert into medicalAdvice values(default,#{patientId},#{typeNo},#{startTime},#{endTime},#{endExcuteTime},#{medicalAdviceName},#{cause},#{goal},#{signall},#{unit},#{frequency},#{route},#{doctorNo},#{antibiotic},#{instruction},#{remark},#{update_date})")
    Integer insMedicalAdvice(MedicalAdvice medicalAdvice);
    @Update("update medicalAdvice set typeNo=#{typeNo},startTime=#{startTime},endTime=#{endTime},endExcuteTime=#{endExcuteTime},medicalAdviceName=#{medicalAdviceName},cause=#{cause},goal=#{goal},signall=#{signall},unit=#{unit},frequency=#{frequency},route=#{route},antibiotic=#{antibiotic},instruction=#{instruction},remark=#{remark},update_date=#{update_date}  where id=#{id}")
    Integer updMedicalAdvice(MedicalAdvice medicalAdvice);
    @Select("select * from medicalAdviceType")
    List<MedicalAdviceType> selAllMedicalAdviceTypes();

    @Select("select * from caseList where patientId=#{patientId}")
    List<CaseList> selAllCaseLists(Integer patientId);
    @Insert("insert into caseList values(default,#{patientId},#{recordTime},#{caseName},#{content})")
    Integer insCaseList(CaseList caseList);

    //根据检验单编号查询病人ID
    @Select("select patientId from checkList where checkNo=#{checkNo}")
    Integer selPatientByCno(String checkNo);
    //根据检验结果编号查询检验单ID
    @Select("select checkNo from checkResultList where id=#{checkresultId}")
    String selCheckNoByCRno(int checkresultId);
    //获取病人诊断类型
    @Select("select diagnosisTypeNo from patient_diagnosisType where patientId = #{patientId}")
    Integer selDnoByPid(Integer patientId);
    //获取病人科室编号
    @Select("select departmentNo from PatientInfo where id = #{patientId}")
    Integer selDepartNoByPid(Integer patientId);
    //根据医生编号获取医嘱信息
    @Select("select * from medicalAdvice where doctorNo=#{doctorNo}")
    List<MedicalAdvice> selAllMedicalAdviceByDco(Integer doctorNo);
    //根据医生编号获取病历信息
    @Select("select * from caseList where patientId in (select id from patientInfo where attDoctorNo=#{doctorNo})")
    List<CaseList> selAllCaseListByDco(Integer doctorNo);
}
