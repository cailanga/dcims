package cn.pzhu.dcims.service.impl;

import cn.pzhu.dcims.mapper.AdminMapper;
import cn.pzhu.dcims.mapper.DoctorMapper;
import cn.pzhu.dcims.mapper.PatientinfoMapper;
import cn.pzhu.dcims.mapper.UserMapper;
import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.service.AdminService;
import cn.pzhu.dcims.service.PatientinfoService;
import cn.pzhu.dcims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    PatientinfoMapper patientinfoMapper;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 根据role查询用户
     * @param role (0：未审核管理员，1：已审核管理员，2：超级管理员，3：医生)
     * @return
     */
    @Override
    public List<User> findUsersByRole(Integer role) {
        return adminMapper.getUsersByRole(role);
    }

    /**
     * 审核用户注册
     * @param id 用户id
     * @return
     */
    @Override
    public Boolean updRole(Integer id) {
        int index=adminMapper.updateRole(id,new Date());
        if(index>0){
            return true;
        }
        return false;
    }

    /**
     * 修改密码
     * @param password
     * @param id
     * @return
     */
    @Override
    public Boolean updPassword(String password,Integer id) {
        //密码加密后修改到数据库
        int index=adminMapper.updatePasssword(new BCryptPasswordEncoder().encode(password),id);
        if(index>0){
            return true;
        }
        return false;
    }

    /**
     * 查询所有医生信息
     * @return
     */
    @Override
    public List<Doctor> selAllDoctor() {
        return adminMapper.selAllDoctor();
    }

    /**
     * 插入医生信息
     * @param doctor
     * @param user
     * @return
     */
    @Override
    public Boolean insDoctor(Doctor doctor,User user) {
        //插入医生对应的用户信息
        int index=adminMapper.insUser(user);
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        //生成doctorNo
        Integer maxId = adminMapper.selMaxId()+1;
        String maxIdStr="";
        if(maxId<10){
            maxIdStr="000"+maxId;
        }else if(maxId<100){
            maxIdStr="00"+maxId;
        }else if(maxId<1000){
            maxIdStr="0"+maxId;
        }else {
            maxIdStr=""+maxId;
        }
        int string = Integer.parseInt(
                ""
                        + instance.get(Calendar.YEAR)
                        + ((instance.get(Calendar.MONTH) + 1) < 10 ?
                        "0" + (instance.get(Calendar.MONTH) + 1) : (instance.get(Calendar.MONTH) + 1))
                        +maxIdStr);
        doctor.setDoctorNo(string);
        //插入医生信息
        int index1= adminMapper.insDoctor(doctor);
        if(index+index1>=2){
            return true;
        }
        return false;
    }

    /**
     * 更新医生信息
     * @param doctor
     * @param user
     * @return
     */
    @Override
    public Boolean updDoctor(Doctor doctor,User user) {
        //更新医生对应的用户信息
        int index=adminMapper.updUser(user);
        //更新医生信息
        Integer index1 = adminMapper.updDoctor(doctor);
        return index+index1>=2?true:false;
    }

    /**
     * 删除医生信息
     * @param doctor
     * @return
     */
    @Override
    public Boolean delDoctor(Doctor doctor) {
        int index =0;
        try {
            index= doctorMapper.delDoctor(doctor.getUsername());
            User user = userMapper.selectByUsername(doctor.getUsername());
            userMapper.delUser(user.getId());
        }catch (Exception e){

        }finally {
            return index>0?true:false;
        }

    }

    /**
     * 查询所有诊断信息
     * @return
     */
    @Override
    public List<DiagnosisType> selAllDiagnosisType() {
        return adminMapper.selAllDiagnosisType();
    }

    /**
     * 插入诊断信息
     * @param diagnosisType
     * @return
     */
    @Override
    public Boolean insDiagnosisType(DiagnosisType diagnosisType) {

        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        //生成maxDiagnosisTypeNo
        Integer maxDiagnosisTypeNo = adminMapper.selMaxDiagnosisTypeId()+1;
        String maxDiagnosisTypeNoStr="";
        if(maxDiagnosisTypeNo<10){
            maxDiagnosisTypeNoStr="000"+maxDiagnosisTypeNo;
        }else if(maxDiagnosisTypeNo<100){
            maxDiagnosisTypeNoStr="00"+maxDiagnosisTypeNo;
        }else if(maxDiagnosisTypeNo<1000){
            maxDiagnosisTypeNoStr="0"+maxDiagnosisTypeNo;
        }else {
            maxDiagnosisTypeNoStr=""+maxDiagnosisTypeNo;
        }
        int string = Integer.parseInt(
                ""
                        + instance.get(Calendar.YEAR)
                        +maxDiagnosisTypeNoStr);
        diagnosisType.setDiagnosisTypeNo(string);
        //插入诊断信息
        int index1= adminMapper.insDiagnosisType(diagnosisType);
        if(index1>0){
            return true;
        }
        return false;
    }

    /**
     * 修改诊断类型信息
     * @param diagnosisType
     * @return
     */
    @Override
    public Boolean updDiagnosisType(DiagnosisType diagnosisType) {
        Integer index = adminMapper.updDiagnosisType(diagnosisType);
        return index>0?true:false;
    }

    /**
     * 查询所有病床信息
     * @return
     */
    @Override
    public List<BedRoom> selAllBedRooms() {
        List<BedRoom> bedRooms = adminMapper.selAllBedRooms();
        int i=0;
        for (BedRoom bedRoom : bedRooms) {
            Department department = patientinfoMapper.selDepartmentByNo(Integer.parseInt(bedRoom.getDepartmentNo()));
            bedRoom.setDepartment(department);
            bedRooms.set(i,bedRoom);
            i++;
        }
        return bedRooms;
    }

    /**
     * 插入病房信息
     * @param bedRoom
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insBedRoom(BedRoom bedRoom) {
        Integer index = adminMapper.insBedRoom(bedRoom);
        Integer index1=0;
        for(int i=1;i<=bedRoom.getBedCount();i++){
            SickBed sickBed=new SickBed();
            sickBed.setBedRoomName(bedRoom.getBedRoomName());
            sickBed.setSickBedNo(i);
            index1+= adminMapper.insSickBed(sickBed);
        }
        if(index+index1>=1+bedRoom.getBedCount())
        {
            return true;
        }
        return false;
    }

    /**
     * 更新病房信息
     * @param bedRoom
     * @return
     */
    @Override
    public Boolean updBedRoom(BedRoom bedRoom,String lastBedRoomName) {
        if(bedRoom.getBedRoomName().equals(lastBedRoomName)){
            return adminMapper.updBedRoom(bedRoom)>0?true:false;
        }
        int index=adminMapper.updSickBed(bedRoom.getBedRoomName(),lastBedRoomName);
        Integer integer = adminMapper.updBedRoom(bedRoom);
        if(index+integer>=2){
            return true;
        }
        return false;
    }

    /**
     * 查询病床信息
     * @param bedRoomName 病房编号
     * @return
     */
    @Override
    public List<SickBed> selSickBedByBedRoomName(String bedRoomName) {
        return adminMapper.selSickBedByBedRoomName(bedRoomName);
    }

    /**
     * 插入感染监测信息
     * @param infectionType
     * @return
     */
    @Override
    public Boolean insInfectionType(InfectionType infectionType) {
        infectionType.setInfectionTypeNo(""+(adminMapper.selInfectionTypeMaxId()+1));
        System.out.println(infectionType);
        return adminMapper.insInfectionType(infectionType)>0?true:false;
    }

    /**
     * 更新感染监测信息
     * @param infectionType
     * @return
     */
    @Override
    public Boolean updInfectionType(InfectionType infectionType) {

        return adminMapper.updInfectionType(infectionType)>0?true:false;
    }

    /**
     * 查询所有爆发类型信息
     * @return
     */
    @Override
    public List<OutbreakType> selAllOutbreakType() {
        return adminMapper.selAllOutbreakType();
    }

    /**
     * 更新爆发类型信息
     * @param outbreakType
     * @return
     */
    @Override
    public Boolean updOutbreakType(OutbreakType outbreakType) {
        return adminMapper.updOutBreakType(outbreakType)>0?true:false;
    }

    /**
     * 插入爆发类型信息
     * @param outbreakType
     * @return
     */
    @Override
    public Boolean insOutBreakType(OutbreakType outbreakType) {
        outbreakType.setOutBreakTypeNo(adminMapper.selOutbreakTypeMaxId()+1);
        return adminMapper.insOutBreakType(outbreakType)>0?true:false;
    }

    /**
     * 查询所有检验类型
     * @return
     */
    @Override
    public List<CheckType> selAllCheckTypes() {
        return adminMapper.selAllCheckTypes();
    }

    /**
     * 更新检验类型信息
     * @param checkType
     * @return
     */
    @Override
    public Boolean insCheckType(CheckType checkType) {
        return adminMapper.insCheckType(checkType)>0?true:false;
    }

    /**
     * 插入检验类型信息
     * @param checkType
     * @return
     */
    @Override
    public Boolean updCheckType(CheckType checkType) {
        return adminMapper.updCheckType(checkType)>0?true:false;
    }

    /**
     * 插入科室信息
     * @param department
     * @return
     */
    @Override
    public Boolean insDepartment(Department department) {
        return adminMapper.insDepartment(department)>0?true:false;
    }

    /**
     * 更新科室信息
     * @param department
     * @return
     */
    @Override
    public Boolean updDepartment(Department department) {
        return adminMapper.updDepartment(department)>0?true:false;
    }
}
