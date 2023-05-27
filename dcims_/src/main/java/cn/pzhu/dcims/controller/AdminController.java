package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.service.AdminService;
import cn.pzhu.dcims.service.UserService;
import cn.pzhu.dcims.service.impl.AdminServiceImpl;
import cn.pzhu.dcims.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@ResponseBody
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    /**
     * 审核用户信息
     * @param authentication
     * @param id  用户id
     * @return
     */
    @RequestMapping("check")
    public Map checkUser(Authentication authentication, Integer id){

        Map<String,Object> map=new HashMap<String, Object>();
        //审核用户
        Boolean flag = adminService.updRole(id);
        //查询未审核信息
        List<User> users = adminService.findUsersByRole(0);
        map.put("flag",flag);
        map.put("users",users);
        return map;
    }

    /**
     * 根据role查询用户细信息
     * @param authentication
     * @param role 角色标识(0：未审核管理员，1：已审核管理员，2：超级管理员，3：医生)
     * @return
     */
    @RequestMapping("getUsers")
    public Map getUsers(Authentication authentication,Integer role){
        //获取登录用户姓名
        String username = authentication.getName();

        //查找当前用户完整信息
        User user = userService.selUsersByName(username);
        //根据条件查询用户信息(0：未审核管理员，1：已审核管理员，2：超级管理员，3：医生)
        List<User> users = adminService.findUsersByRole(role);

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("user",user);
        map.put("users",users);
        return map;
    }

    /**
     * 删除用户
     * @param user 用户
     * @return
     */
    @RequestMapping("delUser")
    @ResponseBody
    public Map delUser(User user){
        Boolean flag = userService.delUser(user);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("delDoctor")
    @ResponseBody
    public Map delDoctor(Doctor doctor){
        Boolean flag = adminService.delDoctor(doctor);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("flag",flag);
        return map;
    }

    /**
     * 管理员修改密码
     * @param password 密码
     * @param id 用户id
     * @param role 用户角色标识
     * @return
     */
    @RequestMapping("updatePwd")
    public Map updPassword(String password,Integer id,Integer role){
        Map<String,Object> map=new HashMap<String, Object>();
        //更新用户密码
        Boolean flag = adminService.updPassword(password,id);
        //查询用户信息信息
        List<User> users = adminService.findUsersByRole(role);
        map.put("flag",flag);
        map.put("users",users);
        return map;
    }

    /**
     * 查询所有医生信息
     * @return
     */
    @RequestMapping("findAllDoctors")
    public Map findAllDoctor(){
        Map map=new HashMap();
        map.put("doctors",adminService.selAllDoctor());
        return map;
    }

    /**
     * 插入医生信息
     * @param doctor
     * @param user
     * @return
     */
    @RequestMapping("insDoctor")
    public Map insDoctor(Authentication authentication,Doctor doctor,User user){
        //获取登录用户姓名
        String username = authentication.getName();
        User user1 = userService.selUsersByName(username);
        user.setName(doctor.getDoctorName());
        user.setRegTime(new Date());

        user.setCheckTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        user.setRole(3);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        //插入医生信息和用户信息
        user.setCheckID(user1.getId());
        Boolean aBoolean = adminService.insDoctor(doctor, user);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",aBoolean);
        return map;
    }

    /**
     * 更新医生信息
     * @param doctor
     * @param user
     * @return
     */
    @RequestMapping("updDoctor")
    public Map updDoctor(Doctor doctor,User user,String lastUsername){

        //通过未修改前的username查询用户信息获取到id
        User user1 = userService.selUsersByName(lastUsername);
        user.setId(user1.getId());
        user.setName(doctor.getDoctorName());
        Boolean aBoolean = adminService.updDoctor(doctor, user);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",aBoolean);
        return map;
    }


    /**
     * 查询所有诊断信息
     * @return
     */
    @RequestMapping("findAllDiagnosisTypes")
    public Map findAllDiagnosisType(){
        Map map=new HashMap();
        map.put("diagnosisTypes",adminService.selAllDiagnosisType());
        return map;
    }
    /**
     * 插入诊断信息
     * @param authentication
     * @param diagnosisType
     * @return
     */
    @RequestMapping("insDiagnosisType")
    public Map insDiagnosisType(Authentication authentication, DiagnosisType diagnosisType){

        Boolean aBoolean = adminService.insDiagnosisType(diagnosisType);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",aBoolean);
        return map;
    }

    /**
     * 更新诊断信息
     * @param diagnosisType
     * @return
     */
    @RequestMapping("updDiagnosisType")
    public Map updDiagnosisType(DiagnosisType diagnosisType){
        Boolean aBoolean = adminService.updDiagnosisType(diagnosisType);
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",aBoolean);
        return map;
    }

    /**
     * 查询所有病房信息
     * @return
     */
    @RequestMapping("findAllBedRooms")
    public Map findAllBedRooms(){
        Map<String, Object> map = new HashMap<>();
//        查询所有病房信息
        List<BedRoom> bedRooms = adminService.selAllBedRooms();
        //查询所有科室信息
        List<Department> departments = userService.findAllDepartment();
        map.put("bedRooms",bedRooms);
        map.put("departments",departments);
        return map;
    }

    /**
     * 插入病房信息
     * @param bedRoom
     * @return
     */
    @RequestMapping("insBedRoom")
    public Map insBedRoom(BedRoom bedRoom){
        Boolean flag = adminService.insBedRoom(bedRoom);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",flag);
        return map;
    }
    /**
     * 修改病房信息
     * @param bedRoom
     * @return
     */
    @RequestMapping("updBedRoom")
    public Map updBedRoom(BedRoom bedRoom,String lastBedRoomName){
        Boolean flag = adminService.updBedRoom(bedRoom,lastBedRoomName);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",flag);
        return map;
    }

    /**
     * 查询病床信息
     * @param bedRoomName 病房号
     * @return
     */
    @RequestMapping("selSickBeds")
    public Map findSickBeds(String bedRoomName){
        Map<String, Object> map = new HashMap<>();
        List<SickBed> sickBeds = adminService.selSickBedByBedRoomName(bedRoomName);
        map.put("sickBeds",sickBeds);
        return map;
    }

    /**
     * 查询所有感染检测类型信息
     * @return
     */
    @RequestMapping("findAllInfectionTypes")
    public Map findAllInfectionTypes(){
        Map<String, Object> map = new HashMap<>();
        List<InfectionType> infections = userService.findAllInfection();
        map.put("infectionTypes",infections);
        return map;
    }

    /**
     * 插入感染检测类型
     * @param infectionType
     * @return
     */
    @RequestMapping("insInfectionType")
    public Map insInfectionType(InfectionType infectionType){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.insInfectionType(infectionType);
        map.put("flag",flag);
        return map;
    }

    /**
     * 更新感染监测信息
     * @param infectionType
     * @return
     */
    @RequestMapping("updInfectionType")
    public Map updInfectionType(InfectionType infectionType){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.updInfectionType(infectionType);
        map.put("flag",flag);
        return map;
    }

    /**
     * 查询所有爆发类型信息
     * @return
     */
    @RequestMapping("findAllOutbreakTypes")
    public Map findAllOutbreakTypes(){
        Map<String, Object> map = new HashMap<>();
        List<OutbreakType> outbreakTypes = adminService.selAllOutbreakType();
        map.put("outbreakTypes",outbreakTypes);
        return map;
    }

    /**
     * 插入爆发类型信息
     * @param outbreakType
     * @return
     */
    @RequestMapping("insOutbreakType")
    public Map insOutbreakType(OutbreakType outbreakType){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.insOutBreakType(outbreakType);
        map.put("flag",flag);
        return map;
    }

    /**
     * 更新爆发类型信息
     * @param outbreakType
     * @return
     */
    @RequestMapping("updOutbreakType")
    public Map updOutbreakType(OutbreakType outbreakType){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.updOutbreakType(outbreakType);
        map.put("flag",flag);
        return map;
    }

    /**
     * 查询所有检验类型
     * @return
     */
    @RequestMapping("findAllCheckTypes")
    public Map findAllCheckTypes(){
        Map<String, Object> map = new HashMap<>();
        List<CheckType> checkTypes = adminService.selAllCheckTypes();
        map.put("CheckTypes",checkTypes);
        return map;
    }

    /**
     * 插入检验类型
     * @param checkType
     * @return
     */
    @RequestMapping("insCheckType")
    public Map insCheckType(CheckType checkType){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.insCheckType(checkType);
        map.put("flag",flag);
        return map;
    }

    /**
     * 查询所有科室信息
     * @return
     */
    @RequestMapping("findAllDepartments")
    public Map findAllDepartments(){
        Map<String, Object> map = new HashMap<>();
        List<Department> departments = userService.findAllDepartment();
        map.put("Departments",departments);
        return map;
    }
    /**
     * 更新检验类型
     * @param checkType
     * @return
     */
    @RequestMapping("updCheckType")
    public Map updCheckType(CheckType checkType){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.updCheckType(checkType);
        map.put("flag",flag);
        return map;
    }

    /**
     * 插入科室信息
     * @param department
     * @return
     */
    @RequestMapping("insDepartment")
    public Map insDepartment(Department department){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.insDepartment(department);
        map.put("flag",flag);
        return map;
    }

    /**
     * 更新科室信息
     * @param department
     * @return
     */
    @RequestMapping("updDepartment")
    public Map updDepartment(Department department){
        Map<String, Object> map = new HashMap<>();
        Boolean flag = adminService.updDepartment(department);
        map.put("flag",flag);
        return map;
    }

}
