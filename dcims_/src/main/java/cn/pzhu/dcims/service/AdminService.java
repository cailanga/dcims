package cn.pzhu.dcims.service;

import cn.pzhu.dcims.pojo.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminService {
    //根据条件查询用户(0：未审核管理员，1：已审核管理员，2：超级管理员，3：医生)
    List<User> findUsersByRole(Integer role);

    //审核用户注册
    Boolean updRole(Integer id);
    //修改密码
    Boolean updPassword(String password,Integer id);

   //查询所有医生信息
    List<Doctor> selAllDoctor();
    //插入医生信息
    Boolean insDoctor(Doctor doctor,User user);

    //更新医生信息
    Boolean updDoctor(Doctor doctor,User user);
    //删除医生信息
    Boolean delDoctor(Doctor doctor);

    //查询所有诊断类型信息
    List<DiagnosisType> selAllDiagnosisType();
    //插入诊断类型信息
    Boolean insDiagnosisType(DiagnosisType diagnosisType);

    //更新诊断类型信息
    Boolean updDiagnosisType(DiagnosisType diagnosisType);

    //查询所有病房信息
    List<BedRoom> selAllBedRooms();
    //插入病房信息
    Boolean insBedRoom(BedRoom bedRoom);
    //修改病房信息
    Boolean updBedRoom(BedRoom bedRoom,String lastBedRoomName);
    //查询病床信息
    List<SickBed> selSickBedByBedRoomName(String bedRoomName);

    //插入感染监测信息
    Boolean insInfectionType(InfectionType infectionType);
    //更新感染监测信息
    Boolean updInfectionType(InfectionType infectionType);

    //查询所有爆发类型信息
    List<OutbreakType> selAllOutbreakType();
    //更新爆发类型信息
    Boolean updOutbreakType(OutbreakType outbreakType);
    //插入爆发类型信息
    Boolean insOutBreakType(OutbreakType outbreakType);

    //查询所有检验类型
    List<CheckType> selAllCheckTypes();
    //插入检验类型
    Boolean insCheckType(CheckType checkType);
    //更新检验类型
    Boolean updCheckType(CheckType checkType);

    //插入科室信息
    Boolean insDepartment(Department department);
    //更新科室信息
    Boolean updDepartment(Department department);
}
