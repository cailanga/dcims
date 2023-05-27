package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.pojo.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cailang
 * @create 2021-02-02-13:36
 */
@Mapper
public interface PatientinfoMapper {
    @Select("select * from user where username=#{param1}")
    User selectByUsername(String username);

    @Select("select * from patientinfo where id=#{id}")
    @Results(id="selPatientInfoById",value = {
            @Result(property = "attDoctorNo",column = "attDoctorNo"),
            @Result(property = "departmentNo",column = "departmentNo"),
            @Result(property = "department",column = "departmentNo",one = @One(select = "cn.pzhu.dcims.mapper.PatientinfoMapper.selDepartmentByDNo",fetchType = FetchType.EAGER)),
            @Result(property = "doctor",column = "attDoctorNo",one = @One(select = "cn.pzhu.dcims.mapper.PatientinfoMapper.selDoctorByDNo",fetchType = FetchType.EAGER)),
    })
    Patientinfo selPatientInfoById(int id);
    @Select("select * from department where departmentNo=#{departmentNo}")
    Department selDepartmentByDNo();
    @Select("select * from doctor where doctorNo=#{attDoctorNo}")
    Doctor selDoctorByDNo(@Param("attDoctorNo") int attDoctorNo);

    @Select("select * from patientinfo")
    List<Patientinfo> selAllPatient();

    @Select("select * from caselist where patientId=#{id}")
    List<CaseList> selCaseListByPId(int id);

    @Select("select * from patientTarget order by pTargetNo asc ")
    ArrayList<PatientTarget> selAllPatientTarget();//查询所有病人情况判断指标

    @Select("select * from patient_patienttarget where patientId=#{id}")
    List<PatientPTarget> selAllPatientPTargetByPid(int id);//查询一个病人所有情况

    @Select("select * from checklist where patientId=#{id}")
    List<CheckList> selCheckListByPid(int id);
    @Select("select * from checklist where patientId in (select id from patientInfo where attDoctorNo=#{doctorNo})")
    List<CheckList> selCheckListByDco(int doctorNo);

    @Select("select * from checkresultlist where checkNo=#{param1}")
    @Results(id="selCheckResultListByCheckNo",value = {
            @Result(property = "pathogens",column = "id",many=@Many(select="cn.pzhu.dcims.mapper.PatientinfoMapper.selCheckresultPathogens",fetchType= FetchType.EAGER)),
            @Result(property = "medicalResults",column = "id",many=@Many(select="cn.pzhu.dcims.mapper.PatientinfoMapper.selResultlist_medicals",fetchType= FetchType.EAGER)),
    })
    List<CheckResultList> selCheckResultListByCheckNo(String checkNo);

    @Select("select * from resultlist_medical where checkresultId = #{id}")
    List<MedicalResult> selResultlist_medicals();

    @Select("select * from checkresult_pathogen where checkresultId = #{id}")
    @Results(id = "selCheckresultPathogens",value = {
            @Result(property = "pathogen",column = "pathogenNo",one=@One(select = "cn.pzhu.dcims.mapper.PatientinfoMapper.selPathogen",fetchType = FetchType.DEFAULT))
    })
    List<CheckresultPathogen> selCheckresultPathogens(@Param("id") int id);

    @Select("select * from pathogen where pathogenNo =#{pathogen}")
    Pathogen selPathogen();


    @Select("select * from patient_patienttarget where patientId=#{id} and pTargetNo=1")
    List<PatientPTarget> selAllTempValueByPid(int id);

    @Select("select * from medicaladvice where patientId=#{id}")
    List<MedicalAdvice> selAllMAByPid(int id);//查询医嘱

    @Select("select * from checktype where checkTypeNo=#{checkTypeNo}")
    CheckType selCheckTypeByCheckTypeNo(int checkTypeNo);

    @Select("select checkTypeNo from checklist_checktype where checkNo=#{checkNo}")
    List<String> selCheckTypeNosByCheckNo(String checkNo);

    @Select("<script> " +
            "select * from checktype where checkTypeNo in " +
            "<foreach collection='list' index='index' item='item' open='(' separator=',' close=')'>" +
            "#{item}" + " </foreach>"
            +"</script> ")
    List<CheckType> selCheckTypeByCheckTypeNos(List<String> checkTypeNos);

    @Select("<script> " +
            "select * from pathogen where pathogenNo in "+
            "<foreach collection='list' index='index' item='item' open='(' separator=',' close=')'>" +
            "#{item}" + " </foreach>"
            +"</script>")
    List<Pathogen> selPathogensByPathogenNos(List<String> pathogenNos);
    @Select("select * from pathogen where pathogenNo=#{pathogenNo}")
    Pathogen selPathogenByNo(String pathogenNo);

    @Select("select * from medicaladvicetype where medicalAdviceNo=#{medicalAdviceNo}")
    MedicalAdviceType selMedicalAdviceTypeByNo(int typeNo);

    @Select("select diagnosisTypeNo from patient_diagnosistype where patientId=#{patientId}")
    List<String> selDiagnosisTypeNosByPatientId(int id);

    @Select("<script> " +
            "select * from diagnosistype where diagnosisTypeNo in "+
            "<foreach collection='list' index='index' item='item' open='(' separator=',' close=')'>" +
            "#{item}"
            + " </foreach>"
            +"</script> ")
    List<DiagnosisType> selDiagnosisTypesByNos(List<String> diagnosisTypeNos);

    @Select("select * from diagnosistype")
    List<DiagnosisType> selectAllDiagnosisType();

    @Select("select * from doctor where doctorNo=#{doctorNo}")
    Doctor selDoctorByNo(int doctorNo);

    @Select("select * from patientTarget where pTargetNo=#{pTargetNo}")
    PatientTarget selPatientTargetByNo(int pTargetNo);//查询病人情况判断指标

    @Select("select * from department where departmentNo=#{departmentNo}")
    Department selDepartmentByNo(int departmentNo);

    @Select("select * from specimentype where specimenNo=#{specimenNo}")
    SpecimenType selSpecimenTypeByNo(int specimenNo);

    @Select("select * from checkresult_pathogen where checkresultId=#{id}")
    List<CheckresultPathogen> selCheckresultPathogenByid(int id);

    @Select("select * from resultlist_medical where checkresultId=#{id}")
    List<MedicalResult> selMedicalResultById(int id);
}
