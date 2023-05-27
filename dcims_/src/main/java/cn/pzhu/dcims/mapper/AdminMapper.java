package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from user where role=#{role}")
    List<User> getUsersByRole(Integer role);

    @Update("update user set role=1,checkTime=#{date} where id=#{id}")
    Integer updateRole(Integer id, Date date);

    @Update("update user set password=#{password} where id=#{id}")
    Integer updatePasssword(String password,Integer id);

    @Select("select * from doctor")
    List<Doctor> selAllDoctor();

    @Insert("insert into doctor(id,doctorNo,doctorName,phone,age,sex,username) values(default,#{doctorNo},#{doctorName},#{phone},#{age},#{sex},#{username})")
    Integer insDoctor(Doctor doctor);

    @Insert("insert into user(id,username,password,role,name,regTime,checkTime,checkID) values(default,#{username},#{password},#{role},#{name},#{regTime},#{checkTime},#{checkID})")
    Integer insUser(User user);

    @Update("update user set name=#{name} where id=#{id}")
    Integer updUser(User user);

    @Update("update doctor set doctorName=#{doctorName},phone=#{phone},age=#{age},sex=#{sex} where id=#{id}")
    Integer updDoctor(Doctor doctor);

//    查询数据中最大的id
    @Select("select id  from doctor where id=(select max(id) from doctor)")
    Integer selMaxId();

    @Select("select * from diagnosisType")
    List<DiagnosisType> selAllDiagnosisType();

    @Insert("insert into diagnosisType values(default,#{diagnosisTypeNo},#{diagnosisTypeName})")
    Integer insDiagnosisType(DiagnosisType diagnosisType);

    @Update("update diagnosisType set diagnosisTypeName=#{diagnosisTypeName} where diagnosisTypeNo=#{diagnosisTypeNo}")
    Integer updDiagnosisType(DiagnosisType diagnosisType);
    //    查询数据中最大的id
    @Select("select id  from diagnosisType where id=(select max(id) from diagnosisType)")
    Integer selMaxDiagnosisTypeId();

    @Select("select * from bedRoom")
    List<BedRoom> selAllBedRooms();
    @Insert("insert into bedRoom values(default,#{bedRoomName},#{departmentNo},#{bedCount})")
    Integer insBedRoom(BedRoom bedRoom);

    @Update("update bedRoom set bedRoomName=#{bedRoomName},departmentNo=#{departmentNo},bedCount=#{bedCount} where id=#{id}")
    Integer updBedRoom(BedRoom bedRoom);
    @Insert("insert into sickBed(id,sickBedNo,bedRoomName) values(default,#{sickBedNo},#{bedRoomName})")
    Integer insSickBed(SickBed sickBed);
    @Select("select * from sickBed where bedRoomName=#{bedRoomName}")
    List<SickBed> selSickBedByBedRoomName(String bedRoomName);
    @Update("update sickBed set bedRoomName=#{bedRoomName} where bedRoomName=#{lastBedRoomName}")
    Integer updSickBed(String bedRoomName,String lastBedRoomName);

    @Insert("insert into infectionType values(default,#{infectionTypeNo},#{infectionTypeName})")
    Integer insInfectionType(InfectionType infectionType);
    @Update("update infectionType set infectionTypeName=#{infectionTypeName} where infectionTypeNo=#{infectionTypeNo}")
    Integer updInfectionType(InfectionType infectionType);
    @Select("select id  from infectionType where id=(select max(id) from infectionType)")
    Integer selInfectionTypeMaxId();

    @Insert("insert into outBreakType values(default,#{outBreakTypeNo},#{outBreakTypeName})")
    Integer insOutBreakType(OutbreakType outBreakType);
    @Update("update outBreakType set outbreaktypeName=#{outBreakTypeName} where id=#{id}")
    Integer updOutBreakType(OutbreakType outbreakType);
    @Select("select * from outbreakType")
    List<OutbreakType> selAllOutbreakType();
    @Select("select id  from outbreakType where id=(select max(id) from outbreakType)")
    Integer selOutbreakTypeMaxId();

    @Select("select * from checkType")
    List<CheckType> selAllCheckTypes();
    @Insert("insert into checkType values(default,#{checkTypeName})")
    Integer insCheckType(CheckType checkType);
    @Update("update checkType set checkTypeName=#{checkTypeName} where checkTypeNo=#{checkTypeNo}")
    Integer updCheckType(CheckType checkType);

    @Insert("insert into department values(default,#{departmentName})")
    Integer insDepartment(Department department);
    @Update("update department set departmentName=#{departmentName} where departmentNo=#{departmentNo}")
    Integer updDepartment(Department department);

}
