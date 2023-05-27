package cn.pzhu.dcims.service;

import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.pojo.vo.ByAgeInfo;
import cn.pzhu.dcims.pojo.vo.DepartmentPatientS;
import cn.pzhu.dcims.pojo.vo.PatientPTInfo;
import org.apache.zookeeper.Op;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.List;

public interface UserService extends UserDetailsService {
    int insUsers(User users);
    User selUsersByNamePassword(String username, String password);
    User selUsersByName(String username);

    List<Patientinfo> findAllPatientInfo();

    List<Department> findAllDepartment();

    List<InfectionType> findAllInfection();

    ICUCheckShow findICUCheckShow(int departmentNo, String type, String year, String month, String day);

    List<TargetMonitor> findTargetMonitor(String startDate, String endDate);

    List<IncisionGrade> findAllIncisionGrade();

    List<OperationFollow> findOperationFByGradeAndDno(int grade, int department);

    List<SusOutbreak> findSusOutbreak(int departmentNo, int outbreakTypeNo, int liCount);

    List<OutbreakType> findAllOutbreakType();

    List<ReportCard> findAllReportCard(int departmentNo,Date startDate,Date endDate);
    List<ReportCard> findAllReportCardByDnoHa(int departmentNo,int handleAction,Date startDate,Date endDate);
    List<ReportCard> findAllReportCardByDTnoHa(int diagnosisTypeNo, int handleAction, Date start,Date end);

    List<ReportCard> findAllReportCardByDTnoHas(int diagnosisTypeNo, int handleAction,Date start,Date end);

    boolean insSusOutbreak(SusOutbreak susOutbreak, String username);

    boolean insWarnInfo(WarnInfo warnInfo, String username);

    boolean insEliminateWarn(EliminateWarn eliminateWarn, String username);

    List<WarnInfo> findWarnInfoByRid(int PatientId);

    List<EliminateWarn> findEliminateWarn(int reportCardId);

    List<ReportTable> findReportTable(int departmentNo);
    Boolean updReportCard(int id, int handleAction, String username);

    List<WarnContditionToCount> findWarnTypeToCount(int departmentNo);

    List<WarnType> findAllWarnTypes();
    List<WarnCondition> findAllWarnconditions();
    Boolean warn(String username,WarnInfo warnInfo);
    Boolean sus(String username,int reportCardId);

    //查询体征信息
    List<TiZhengInfo> findAllTiZheng();

    List<ShengWuInfo> findAllshengwuinfo();

    List<OperateInfo> findAlloperateinfo();

    List<DepartmentBQ> findAllDepartmentBqType();



    List<DiagnosisType> sellAllDiagnosisTypes();
    //根据诊断类型查询报卡

    List<ReportCard> selAllreportcardByDTypeNo(int diagnosisTypeNo);

    List<Operation> findOperationsByGAndPid(int grade,int patientId);

    //更新用户信息
    Boolean updateUser(User user);
    //删除用户
    Boolean delUser(User user);

    //查询科室的人数
    Integer findPatientSByDno(int departmentNo);
    //查询各科室所存在的不同感染状态病人数量
    List<DepartmentPatientS> findDepartmentPatientS(int year,int month);
    //查询不同年龄段病人情况
    List<ByAgeInfo> findAgeInfo(int year);
    //根据科室名和感染状态查勋病人信息
    List<Patientinfo> findPatiensByDNameAndType(String departmentName,String type,int year,int month);
    //根据年月获取检测信息
    List<PatientPTInfo> findPatientPT(String year, String month,int status);

    //病人数量查询
    Integer findPatientsCount();
    Integer findDoctorsCount();
    Integer findDepartmentCount();
}
