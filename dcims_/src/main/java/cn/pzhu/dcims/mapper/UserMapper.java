package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Update("update user set name=#{name} where username =#{username}")
    int updUserByUsername(User user);
    @Insert("insert into user(id,username,password,name,regTime) values (default ,#{username},#{password},#{name},#{regTime})")
    int insertUsers(User user);
    @Delete("delete from user where id=#{id}")
    int delUser(int id);

    User selectUsers(String username, String password);
    @Select("select * from user where username=#{param1}")
    User selectByUsername(String username);
    @Select("select * from user where id=#{param1}")
    User selectById(int id);

    @Select("select * from patientInfo")
    List<Patientinfo> selAllPatientInfo();

    @Select("select * from ICUCheck where departmentNo=#{departmentNo}")
    List<ICUCheck> selAllICUCheckByDNo(int departmentNo);

    @Select("select * from ICUCheck where departmentNo=#{param1} and type=#{param2}")
    List<ICUCheck> selAllICUCheckByDNoAndType(int departmentNo, String type);

    @Select("select * from ICUCheck")
    List<ICUCheck> selAllICUCheck();

    @Select("<script>"+"select * from icucheck where 1=1" +
            "        <if test=\"year!=null and year!='0'\">" +
            "            and year=#{year}" +
            "        </if>" +
            "        <if test=\"month!=null and month!=0\">" +
            "            and month=#{month}" +
            "        </if>" +
            "        <if test=\"day!=null and day!=0\">" +
            "            and day=#{day}" +
            "        </if>" +
            "        <if test=\"type!=null and type!='' and type!=0\">" +
            "            and type=#{type}" +
            "        </if>" +
            "        <if test=\"departmentNo!=null and departmentNo!='' and departmentNo!=0\">" +
            "            and departmentNo=#{departmentNo}" +
            "        </if>"+
            "</script>")
    List<ICUCheck> selAllICUCheckByYMDDNOT(@Param("departmentNo") int departmentNo, @Param("type") String type, @Param("year") String year, @Param("month") String month, @Param("day") String day);

    @Select("<script>"+"select * from patientinfo where 1=1" +
            "        <if test=\"departmentNo!=null and departmentNo!=0\">" +
            "            and departmentNo=#{departmentNo}" +
            "        </if>"+
            "</script>")
    List<Patientinfo> selPatientByDno(@Param("departmentNo") int dno);

    @Select("select * from department")
    List<Department> selAllDepartMent();

    @Select("select * from infectiontype")
    List<InfectionType> selAllInfectionType();

    @Select("select * from InfectionType where infectionTypeNo=#{param1}")
    InfectionType selInfectionTypeByNo(String typeNo);

    @Select("select * from patient_patienttarget")
    List<PatientPTarget> selAllPPT();

    @Select("select * from checkList")
    List<CheckList> selAllCheckList();

    @Select("select * from incisionGrade")
    List<IncisionGrade> selAllIncisinGade();

    @Select("select * from operationFollow")
    List<OperationFollow> selAllOperation();

    @Select("select * from operationFollow where grade=#{param1} and departmentNo=#{param2}")
    List<OperationFollow> selOperationByGradeAndDno(int grade, int departmentNo);

    @Select("select * from operationFollow where grade=#{param1}")
    List<OperationFollow> selOperationByGrade(int grade);

    @Select("select * from operationFollow where departmentNo=#{departmentNo}")
    List<OperationFollow> selOperationByDepartmentNo(int departmentNo);

    @Select("select * from outbreaktype")
    List<OutbreakType> selAllOutBreakType();

    @Select("select * from outbreaktype where outBreakTypeNo=#{param1}")
    OutbreakType selAllOutBreakTypeByNo(int no);

    @Select("select * from SusOutbreak where departmentNo=#{param1} and outBreakTypeNo=#{param2} and liCount=#{param3} and status=0")
    List<SusOutbreak> selAllSusOutbreak(int departmentNo, int outbreakTypeNo, int liCount);

    @Select("select * from SusOutbreak where departmentNo=#{param1} and liCount=#{param2} and status=0")
    List<SusOutbreak> selAllSusOutbreakByDno(int departmentNo, int liCount);

    @Select("select * from SusOutbreak where liCount=#{param1} and status=0")
    List<SusOutbreak> selAllSusOutbreakByliCount(int liCount);

    @Select("select * from SusOutbreak where outBreakTypeNo=#{param1} and liCount=#{param2} and status=0")
    List<SusOutbreak> selAllSusOutbreakByOutNo(int outbreakTyNo, int liCount);

    @Select("select * from reportCard")
    List<ReportCard> selAllReportCard();

    @Select("<script>" +
            "select * from reportCard where 1=1" +
            "<if test='startDate!=null and endDate!=null'>" +
            "   and upTime &gt;=#{startDate} and upTime &lt;=#{endDate}" +
            "</if>" +
            "</script>")
    List<ReportCard> selAllReportCardByDate(Date startDate,Date endDate);

    @Select("select * from reportCard where departmentNo=#{departmentNo}")
    List<ReportCard> selAllReportCardByDNo(int departmentNo);
    @Select("<script>" +
            "select * from reportCard where 1=1" +
            "<if test='departmentNo!=null and departmentNo!=0'>" +
            "   and departmentNo =#{departmentNo}" +
            "</if>" +
            "<if test='startDate!=null and endDate!=null'>" +
            "   and upTime &gt;=#{startDate} and upTime &lt;=#{endDate}" +
            "</if>" +
            "</script>")
    List<ReportCard> selAllReportCardByDNoDate(int departmentNo,Date startDate,Date endDate);

    @Select("select count(distinct patientId) from reportCard where isLeaveHospital=0 and departmentNo=#{param1}")
    int selPCountIsInHospitalByDno(int departmentNo);

    @Select("select * from diagnosisType where diagnosisTypeNo=#{param1}")
    DiagnosisType selDiagnosisTypeByNo(int diagnosisTyNo);

    @Update("update reportCard set status=1,warnStatus=2,handleAction=#{param2},handleDate=#{param3},handleUserId=#{param4} where id=#{param1}")
    int updReportCard(int id, int handleAction, String handleDate, int userId);

    @Select("select * from warncondition where conditionNo=#{param1}")
    WarnCondition selWarnConditionByNo(int conditionNo);

    @Select("<script>select count(*) from warninfo where conditionNo=#{conditionNo} " +
            "        <if test='departmentNo!=null and departmentNo!=0'>" +
            "            and departmentNo=#{departmentNo}" +
            "        </if>" +
            "</script>")
    int selWarnCountFromWarnInfo(@Param("conditionNo") int conditionNo, @Param("departmentNo") int departmentNo);

    @Select("select * from warncondition")
    List<WarnCondition> selAllWarnCondition();
    @Select("select * from warnType")
    List<WarnType> selAllWarnTypes();
    @Insert("insert into warninfo values(default,#{patientId},#{conditionNo},#{warnTypeNo},#{warnContent},#{date},#{reportCardId},#{status},#{departmentNo})")
    Integer insWarnInfos(WarnInfo warnInfo);
    @Update("update reportCard set handleUserId=#{userId},handleAction=1,handleDate=#{handleDate},warnDate=#{handleDate} where id = #{rid}")
    Integer updReportCardHandleUser(int userId,int rid,String handleDate);
    @Update("update reportCard set susUserId=#{userId},handleDate=#{date},handleAction=2 where id = #{rid}")
    Integer updReportCardSusUser(int userId,int rid,Date date);
    @Update("update reportCard set paichuUserId=#{userId},handleAction=0 where id = #{rid}")
    Integer updReportCardPaichuUser(int userId,int rid);

    @Select("select * from warnType where warnTypeNo=#{param1}")
    WarnType selWarnTypeByNo(int warnTypeNo);

    @Select("select * from eliminatewarn where patientId=#{param1}")
    List<EliminateWarn> selEliminateByPid(int patientId);

    @Select("select * from eliminatewarn where reportCardId=#{reportCardId}")
    List<EliminateWarn> selEliminateByRid(int reportCardId);

    @Select("select * from warninfo where patientId=#{param1}")
    List<WarnInfo> selWarnInfoByPid(int patientId);

    @Select("select * from warninfo where reportCardId=#{param1}")
    List<WarnInfo> selWarnInfoByRid(int reportCardId);

    @Select("select * from susOutbreak where patientId=#{param1}")
    List<SusOutbreak> selSusOutbreakByPid(int patientId);

    //查询某科室的报卡疑似，预警，排除人数（目前在院的人）
    @Select("select count(*) from (select patientId from reportCard where departmentNo=#{param1} and handleAction=#{param2} and isLeaveHospital=0 group by patientId order by max(upTime) desc) as a")
    int selPCountFromReportCardByDno(int departmentNo, int handleAction);

    //查询某科室的报卡疑似，预警，排除信息（只查每个病人的最新报卡信息）
    @Select("<script>" +
            "select * from reportCard as a where 1=1 " +
            "<if test='departmentNo!=null and departmentNo!=0'>" +
            "   and departmentNo=#{departmentNo}" +
            "</if>" +
            "<if test='startDate!=null and endDate!=null'>" +
            "   and upTime &gt;=#{startDate} and upTime &lt;=#{endDate}" +
            "</if>" +
            "<if test='handleAction!=null and handleAction!=-1'>" +
            "   and handleAction=#{handleAction}" +
            "</if>" +
            " and isLeaveHospital=0 " +
            "and upTime=" +
            "(select max(r_b.upTime) from reportcard as r_b " +
            "where a.patientId=r_b.patientId)" +
            "</script>")
    List<ReportCard> selReportCardsByDnoHa(int departmentNo, int handleAction,Date startDate,Date endDate);
    //查询某科室的报卡疑似，预警，排除信息（只查每个病人的最新报卡信息）查数量
    @Select("<script>" +
            "select count(*) from reportCard as a where 1=1 " +
            "<if test='departmentNo!=null and departmentNo!=0'>" +
            "   and departmentNo=#{departmentNo}" +
            "</if>" +
            "<if test='handleAction!=null and handleAction!=-1'>" +
            "   and handleAction=#{handleAction}" +
            "</if>" +
            " and isLeaveHospital=0 " +
            "and upTime=" +
            "(select max(r_b.upTime) from reportcard as r_b " +
            "where a.patientId=r_b.patientId)" +
            "</script>")
    Integer selReportCardsByDnoHaCount(int departmentNo, int handleAction);
    //查询某科室的报卡疑似，预警，排除信息（只查每个病人的最新报卡信息）查数量
    @Select("<script>" +
            "select count(*) from reportCard as a where 1=1 " +
            "<if test='departmentNo!=null and departmentNo!=0'>" +
            "   and departmentNo=#{departmentNo}" +
            "</if>" +
            "<if test='handleAction!=null and handleAction!=-1'>" +
            "   and handleAction=#{handleAction}" +
            "</if>" +
            " and isLeaveHospital=0 " +
            "and upTime=" +
            "(select max(r_b.upTime) from reportcard as r_b " +
            "where a.patientId=r_b.patientId" +
            "<if test='year>0 and year!=0'>" +
            "   and Year(upTime)=#{year}" +
            "</if>" +
            "<if test='month>0 and month!=0'>" +
            "   and month(upTime)=#{month}" +
            "</if>" +
            ")" +
            "</script>")
    Integer selReportCardsByDnoHaCount1(int departmentNo, int handleAction,int year,int month);
    //查询某科室的报卡疑似，预警，排除信息
    @Select("<script>" +
            "select * from reportCard as a where 1=1 " +
            "<if test='diagnosisTypeNo!=null and diagnosisTypeNo!=0'>" +
            "   and diagnosisTypeNo=#{diagnosisTypeNo}" +
            "</if>" +
            "<if test='start!=null and end!=null'>" +
            "   and upTime &gt;=#{start} and upTime &lt;=#{end}" +
            "</if>" +
            "<if test='handleAction!=null and handleAction!=-1'>" +
            "   and handleAction=#{handleAction}" +
            "</if>" +
            " and isLeaveHospital=0 " +
            "and upTime=" +
            "(select max(r_b.upTime) from reportCard as r_b " +
            "where a.patientId=r_b.patientId)" +
            "</script>")
    List<ReportCard> selReportCardsByDTnoHa(int diagnosisTypeNo, int handleAction,Date start,Date end);
    //查询某科室的报卡疑似，预警，排除信息（只查每个病人的最新报卡信息）
    @Select("<script>" +
            "select * from reportCard where 1=1 " +
            "<if test='diagnosisTypeNo!=null and diagnosisTypeNo!=0'>" +
            "   and diagnosisTypeNo=#{diagnosisTypeNo}" +
            "</if>" +
            "<if test='start!=null  and end!=null'>" +
            "   and upTime &gt;=#{start} and upTime &lt;=#{end}" +
            "</if>" +
            "<if test='handleAction!=null and handleAction!=-1'>" +
            "   and handleAction=#{handleAction}" +
            "</if>" +
            " and isLeaveHospital=0"+
            "</script>")
    List<ReportCard> selReportCardsByDTnoHas(int diagnosisTypeNo, int handleAction,Date start,Date end);

    @Insert("insert into eliminatewarn (patientId, date, content, userId, cause,reportCardId) values (#{patientId},#{date},#{content},#{userId},#{cause},#{reportCardId})")
    int insEliminateWarn(EliminateWarn eliminateWarn);//排除

    @Insert("insert into warninfo (patientId, conditionNo, warnTypeNo, warnContent, date,reportCardId) values (#{patientId},#{conditionNo},#{warnTypeNo},#{warnContent},#{date},#{reportCardId})")
    int insWarnInfo(WarnInfo warnInfo);//添加预警

    @Insert("insert into susoutbreak (departmentNo, liCount, outBreakTypeNo, startDate, endDate, name, remark,reportCardId,patientId) values (#{departmentNo},#{liCount},#{outBreakTypeNo},#{startDate},#{endDate},#{name},#{remark},#{reportCardId},#{patientId})")
    int insSusOutbreak(SusOutbreak susOutbreak);//添加疑似

    @Select("select count(*) from patientinfo where departmentNo=#{param1} and status=0")
    int selPCountFromPatientInfoByDno(int departmentNo);

    //查询体征信息
    @Select("select * from tizhenginfo")
    List<TiZhengInfo> selAllTiZheng();

    @Select("select * from shengwuinfo")
    List<ShengWuInfo> selAllshengwuinfo();

    @Select("select * from operateinfo")
    List<OperateInfo> selAlloperateinfo();

    @Select("select * from department_bqtype where departmentNo=#{param1}")
    List<DepartmentBqType> selAllDepartmentBqType(int departmentNo);


    @Select("select * from diagnosistype")
    List<DiagnosisType> selectAllDiagnosisType();

    @Select("select * from reportCard where diagnosisTypeNo=#{diagnosisTypeNo}")
    List<ReportCard> selAllReportCardByDType(int diagnosisTypeNo);

    @Select("<script>" +
            "select * from operation where 1=1 " +
            "<if test='grade!=0'>"+
            "and grade=#{grade}" +
            "</if>"+
            "<if test='patientId!=0'>"+
            "and patientId=#{patientId}" +
            "</if>"+
            "</script>")
    List<Operation> getOperationsByGAndPid(int grade,int patientId);


    //根据科室查询病人数量
    @Select("<script>" +
            "select count(*) from patientinfo where departmentNo =#{departmentNo} and status=0 " +
            "<if test='year!=0'>"+
            "and Year(enterTime) &lt;= #{year}" +
            "</if>"+
            "<if test='month!=0'>"+
            "and month(enterTime) &lt;= #{month}" +
            "</if>"+
            "</script>")
    Integer selCountByDno(int departmentNo,int year,int month);

    //根据年龄段查询
    @Select("select count(*) from patientinfo where age>=#{minAge} and age<=#{maxAge} and Year(enterTime)=#{year}")
    Integer selPCountByAgeRange(int minAge,int maxAge,int year);

    //根据科室名查科室信息
    @Select("select * from department where departmentName=#{departmentName}")
    Department selDepartmentByName(String departmentName);

    //查询病人ID，根据科室和状态
    //查询某科室的报卡疑似，预警，排除信息（只查每个病人的最新报卡信息）查数量
    @Select("<script>" +
            "select patientId from reportCard as a where 1=1 " +
            "<if test='departmentNo!=null and departmentNo!=0'>" +
            "   and departmentNo=#{departmentNo}" +
            "</if>" +
            "<if test='handleAction!=null and handleAction!=-1'>" +
            "   and handleAction=#{handleAction}" +
            "</if>" +
            " and isLeaveHospital=0 " +
            "and upTime=" +
            "(select max(r_b.upTime) from reportcard as r_b " +
            "where a.patientId=r_b.patientId" +
            "<if test='year>0 and year!=0'>" +
            "   and Year(upTime)=#{year}" +
            "</if>" +
            "<if test='month>0 and month!=0'>" +
            "   and month(upTime)=#{month}" +
            "</if>" +
            ")" +
            "</script>")
    List<Integer> selPatientIds(int departmentNo, int handleAction,int year,int month);

    //根据病人id数组查询病人信息
    @Select("<script>" +
            "select * from patientinfo " +
            "          where id in " +
            "          <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">  \n" +
            "            #{item}  \n" +
            "        </foreach>\n" +
            "</script>")
    List<Patientinfo> selPatientsByIds(List<Integer> list);
    //根据病人id数组查询未感染病人信息
    @Select("<script>" +
            "select * from patientinfo " +
            "          where id not in " +
            "          <foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">  \n" +
            "            #{item}  \n" +
            "        </foreach>\n" +
            "</script>")
    List<Patientinfo> selPatientsByIds1(List<Integer> list);
//    Year(recordTime)=#{year} and month(recordTime)=#{month} and
    @Select("<script>" +
            "select count(*) from patient_patienttarget where pTargetNo=#{pTargetNo} " +
            "<if test='status!=-1'>"+
            " and status=#{status}"+
            "</if>"+
            "<if test='year!=0'>"+
            " and Year(recordTime)=#{year}"+
            "</if>"+
            "<if test='month!=0'>"+
            " and month(recordTime)=#{month}"+
            "</if>"+
            "</script>")
    Integer selAllPatientPTarget(String year,String month,int pTargetNo,int status);//查询数量

    @Select("select count(*) from patientinfo where status=0")
    Integer selPatientCounts();
    @Select("select count(*) from doctor")
    Integer selDoctorCounts();
    @Select("select count(*) from department")
    Integer selDepartmentCounts();
}
