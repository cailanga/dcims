package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.service.AdminService;
import cn.pzhu.dcims.service.DoctorService;
import cn.pzhu.dcims.service.PatientinfoService;
import cn.pzhu.dcims.service.UserService;
import com.alibaba.fastjson.JSON;
import javafx.collections.ObservableMap;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("doctor")
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@ResponseBody
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @Autowired
    UserService userService;
    @Autowired
    PatientinfoService patientinfoService;
    @Autowired
    AdminService adminService;

    /**
     * 更新医生信息
     * @param doctor
     * @return
     */
    @RequestMapping("updDoctor")
    public Map updDoctor(Doctor doctor){
        User user = new User();
        //通过未修改前的username查询用户信息获取到id
        User user1 = userService.selUsersByName(doctor.getUsername());
        user.setId(user1.getId());
        user.setName(doctor.getDoctorName());
        Boolean aBoolean = adminService.updDoctor(doctor, user);
        Map<String, Object> map = new HashMap<>();
        map.put("flag",aBoolean);
        return map;
    }
    @RequestMapping("getDoctor")
    public Map getDoctor(String username){

        Doctor doctor = doctorService.getDoctorByUsername(username);
        Map<String, Object> map = new HashMap<>();
        map.put("doctor",doctor);
        return map;
    }

    /**
     * 获取所有病人信息
     *
     * @return
     */
    @RequestMapping("findAllPatientInfos")
    public Map findAllPatientInfo(Authentication authentication) {
        //获取医生信息
        String name = authentication.getName();
        User user = userService.selUsersByName(name);
        Doctor doctor = doctorService.selDoctorByUsername(name);
        Map map = new HashMap<String, Object>();
        List<Department> departments = userService.findAllDepartment();
        map.put("departments", departments);
        //获取病人信息
        List<Patientinfo> patientInfos = doctorService.selAllPatietnsByDoctorNo(doctor.getDoctorNo());
        map.put("PatientInfos", patientInfos);
        return map;
    }

    /**
     * 插入病人信息
     *
     * @param patientinfo
     * @return
     */
    @RequestMapping("insPatientInfo")
    public Map insPatientInfo(Authentication authentication, Patientinfo patientinfo, PDiagnosis pDiagnosis) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        patientinfo.setAttDoctorNo(doctor.getDoctorNo());
        Map map = new HashMap<String, Object>();
        Boolean flag = doctorService.insPatientInfo(patientinfo, pDiagnosis);
        map.put("flag", flag);
        return map;
    }

    /**
     * 获取所有诊断类型
     *
     * @return
     */
    @RequestMapping("findAllDiagnosisTypes")
    public Map findAllDiagnosisTypes() {
        Map map = new HashMap<String, Object>();
        List<DiagnosisType> diagnosisTypes = userService.sellAllDiagnosisTypes();
        map.put("diagnosisTypes", diagnosisTypes);
        return map;
    }

    /**
     * 修改病人信息
     *
     * @param patientinfo
     * @return
     */
    @RequestMapping("updPatientInfo")
    public Map updPatientInfo(Patientinfo patientinfo) {
        Map map = new HashMap<String, Object>();
        Boolean flag = doctorService.updPatientInfo(patientinfo);
        List<Department> departments = userService.findAllDepartment();
        map.put("flag", flag);
        map.put("departments", departments);
        return map;
    }

    /**
     * 在生成报卡之前从其他数据自动获取报卡中该有的数据进行显示
     *
     * @param id 病人ID
     * @return
     */
    @RequestMapping("findReportCardInfoByPId")
    public Map findReportCardInfo(int id) {
        ReportCard reportCard = doctorService.selReportCard(id);
        Patientinfo patientInfo = patientinfoService.findPatientInfoById(id);
        reportCard.setPatientinfo(patientInfo);
        reportCard.setPatientId(patientInfo.getId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("reportCard", reportCard);
        return map;
    }

    @RequestMapping("createReportCard")
    public Map createReportCard(Authentication authentication,@RequestBody Map map) {

        ReportCard reportCard = JSON.parseObject(JSON.toJSONString(map), ReportCard.class);

        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        reportCard.setDoctorNo(doctor.getDoctorNo());
        Patientinfo patientinfo = reportCard.getPatientinfo();
        System.out.println(patientinfo);
        reportCard.setDiagnosisTypeNo((patientinfo.getDiagnosisTypes()).get(0).getDiagnosisTypeNo());
        reportCard.setDepartmentNo(patientinfo.getDepartmentNo());
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        reportCard.setUpTime(dateFormat.format(date));
        Boolean flag = doctorService.insReportCard(reportCard);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("flag", flag);
        return map1;
    }

    /**
     * 根据科室查询病房
     *
     * @param departmentNo 科室编号
     * @return
     */
    @RequestMapping("findBedRoomsByDno")
    public Map findBedRoomsByDno(Integer departmentNo) {
        Map map = new HashMap<String, Object>();
        List<BedRoom> bedRooms = doctorService.selBedRoomsByDno(departmentNo);
        map.put("bedRooms", bedRooms);
        return map;
    }

    /**
     * 根据病房查询病床信息
     *
     * @param bedRoomName 病房号
     * @return
     */
    @RequestMapping("findSickBedsByBedRoom")
    public Map findSickBedsByBedRoom(String bedRoomName) {
        Map map = new HashMap<String, Object>();
        List<SickBed> sickBeds = adminService.selSickBedByBedRoomName(bedRoomName);
        map.put("sickBeds", sickBeds);
        return map;
    }

    /**
     * 查询感染检测信息
     *
     * @param patientId 病人编号
     * @return
     */
    @RequestMapping("findAllPatientPTargetsByPid")
    public Map findAllPatientPTargetsByPid(Integer patientId) {
        Map map = new HashMap<String, Object>();
        List<PatientPTarget> patientPTargets = doctorService.selAllPatientPTargetByPid(patientId);
        map.put("patientPTargets", patientPTargets);
        return map;
    }

    /**
     * 插入感染信息
     *
     * @param patientPTarget
     * @return
     */
    @RequestMapping("insPatientPTarget")
    public Map insPatientPTarget(PatientPTarget patientPTarget) {
        Map map = new HashMap<String, Object>();
        Boolean flag = doctorService.insPatientPTarget(patientPTarget);
        map.put("flag", flag);
        return map;
    }

    /**
     * 获取感染检测类型
     *
     * @return
     */
    @RequestMapping("findAllPatientTargets")
    public Map findAllPatientTargets() {
        Map map = new HashMap<String, Object>();
        List<PatientTarget> patientTargets = patientinfoService.selAllPatientTarget();
        map.put("patientTargets", patientTargets);
        return map;
    }

    /**
     * 查询手术信息
     *
     * @return
     */
    @RequestMapping("findAllOperationsByDno")
    public Map findAllOperationsByDno(Authentication authentication) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        List<Operation> operations = doctorService.selOperationsByDno(doctor.getDoctorNo());
        Map map = new HashMap<String, Object>();
        map.put("operations", operations);
        return map;
    }

    /**
     * 插入手术信息
     *
     * @param operation
     * @return
     */
    @RequestMapping("insOperation")
    public Map insOperation(Authentication authentication, Operation operation) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        operation.setDoctorNo(doctor.getDoctorNo());
        Map map = new HashMap<String, Object>();
        Boolean flag = doctorService.insOperation(operation);
        map.put("flag", flag);
        return map;
    }

    /**
     * 插入手术回访信息
     *
     * @param operationFollow
     * @return
     */
    @RequestMapping("insOperationFollow")
    public Map insOperationFollow(Authentication authentication, OperationFollow operationFollow) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        operationFollow.setDoctorNo(doctor.getDoctorNo());
        Map map = new HashMap<String, Object>();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(operationFollow.getNextDate()));
        operationFollow.setNextDate(format);
        Boolean flag = doctorService.insOperationFollow(operationFollow);
        map.put("flag", flag);
        return map;
    }

    /**
     * 获取检验检查信息
     *
     * @param patientId 病人编号
     * @return
     */
    @RequestMapping("findAllCheckListsByPid")
    public Map findAllCheckListsByPid(Authentication authentication, String patientId) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        Map map = new HashMap<String, Object>();
        if ("".equals(patientId)){
            List<CheckList> checkListByIdByDco = patientinfoService.findCheckListByIdByDco(doctor.getDoctorNo());
            map.put("checkLists", checkListByIdByDco);
            map.put("flag",true);
        }else {
            Boolean flag = false;
            int patientId1 = Integer.parseInt(patientId);
            try {//判断是否存在pid
                flag = doctorService.selPidByPidDno(patientId1, doctor.getDoctorNo());
            } catch (Exception e) {
                if (!flag) {
                    map.put("flag", flag);
                    return map;
                }
            }
            if (!flag) {
                map.put("flag", flag);
                return map;
            }
            List<CheckList> checkLists = patientinfoService.findCheckListById(patientId1);
            map.put("checkLists", checkLists);
        }
        return map;
    }

    /**
     * 获取所有检验类型
     *
     * @return
     */
    @RequestMapping("findAllCheckTypes")
    public Map findAllCheckTypes() {
        Map map = new HashMap<String, Object>();
        List<CheckType> checkTypes = doctorService.selAllCheckTypes();
        map.put("checkTypes", checkTypes);
        return map;
    }

    /**
     * 插入检查信息
     *
     * @param checkList
     * @return
     */
    @RequestMapping("insCheckList")
    public Map insCheckList(Authentication authentication, CheckList checkList) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        Map map = new HashMap<String, Object>();
        Boolean flag = false;
        try {//判断是否存在pid
            flag = doctorService.selPidByPidDno(checkList.getPatientId(), doctor.getDoctorNo());
        } catch (Exception e) {
            if (!flag) {
                map.put("flag", flag);
                return map;
            }
        }
        if (!flag) {
            map.put("flag", flag);
            return map;
        }
        flag = doctorService.insCheckList(checkList);
        map.put("flag", flag);
        return map;
    }

    /**
     * 插入检查结果信息
     *
     * @param checkResultList
     * @return
     */
    @RequestMapping("insCheckResultList")
    public Map insCheckResultList(CheckResultList checkResultList) {
        Map map = new HashMap<String, Object>();
        Boolean flag = doctorService.insCheckResultList(checkResultList);
        map.put("flag", flag);
        return map;
    }

    /**
     * 获取检查结果信息
     *
     * @param checkNo 检验单号
     * @return
     */
    @RequestMapping("findAllCheckResultListsByCno")
    public Map findAllCheckResultListsByCno(String checkNo) {
        Map map = new HashMap<String, Object>();
        List<CheckResultList> checkResultLists = doctorService.selAllCheckResultList(checkNo);
        map.put("checkResultLists", checkResultLists);
        return map;
    }

    /**
     * 查询所有检查结果对应的病原体信息
     *
     * @param checkresultId 检查结果id
     * @return
     */
    @RequestMapping("findAllCheckPathogens")
    public Map findAllCheckPathogens(String checkresultId) {

        Map map = new HashMap<String, Object>();
        List<CheckresultPathogen> checkresultPathogens = doctorService.selAllCPathogen(checkresultId);
        map.put("checkPathogens", checkresultPathogens);
        return map;
    }

    /**
     * 插入检查结果的病原体信息
     *
     * @param checkresultPathogen
     * @return
     */
    @RequestMapping("insCheckPathogen")
    public Map insCheckPathogen(CheckresultPathogen checkresultPathogen) {
        Map map = new HashMap<String, Object>();
        String date = checkresultPathogen.getDate();
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date));
        checkresultPathogen.setDate(format);
        Boolean flag = doctorService.insCPathogen(checkresultPathogen);
        map.put("flag", flag);
        return map;
    }

    /**
     * 获取所有病原体信息
     *
     * @return
     */
    @RequestMapping("findAllPathogens")
    public Map findAllPathogens() {
        Map map = new HashMap<String, Object>();
        List<Pathogen> pathogens = doctorService.selAllPathogens();
        map.put("pathogens", pathogens);
        return map;
    }


    /**
     * 查询所有检查结果对应的药敏结果信息
     *
     * @param checkresultId 检查结果id
     * @return
     */
    @RequestMapping("findAllResultMedicals")
    public Map findAllResultMedicals(String checkresultId) {

        Map map = new HashMap<String, Object>();
        List<ResultMedical> resultMedicals = doctorService.selAllRMedicalByCid(checkresultId);
        map.put("resultMedicals", resultMedicals);
        return map;
    }

    /**
     * 插入检查结果的药敏结果信息
     *
     * @param resultMedical
     * @return
     */
    @RequestMapping("insResultMedical")
    public Map insResultMedical(ResultMedical resultMedical) {
        Map map = new HashMap<String, Object>();
        Boolean flag = doctorService.insRMedical(resultMedical);
        map.put("flag", flag);
        return map;
    }

    /**
     * 查询医嘱信息
     *
     * @param patientId 病人编号
     * @return
     */
    @RequestMapping("findAllMedicalAdvicesByPid")
    public Map findAllMedicalAdvicesByPid(Authentication authentication, String patientId) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        Map map = new HashMap<String, Object>();
        if ("".equals(patientId)) {
            List<MedicalAdvice> allMedicalAdviceByDco = doctorService.findAllMedicalAdviceByDco(doctor.getDoctorNo());
            map.put("medicalAdvices", allMedicalAdviceByDco);
            map.put("flag", true);
        }else {
            Integer patientId1=Integer.parseInt(patientId);
            Boolean flag = false;
            try {//判断是否存在pid
                flag = doctorService.selPidByPidDno(patientId1, doctor.getDoctorNo());
            } catch (Exception e) {
                if (!flag) {
                    map.put("flag", flag);
                    return map;
                }
            }
            if (!flag) {
                map.put("flag", flag);
                return map;
            }
            List<MedicalAdvice> medicalAdvices = patientinfoService.findMedicalAdviceById(patientId1);
            map.put("medicalAdvices", medicalAdvices);
        }
        return map;
    }

    /**
     * 获取所有医嘱类型信息
     *
     * @return
     */
    @RequestMapping("findAllMedicalAdviceTypes")
    public Map findAllMedicalAdviceTypes() {
        Map map = new HashMap<String, Object>();
        List<MedicalAdviceType> medicalAdviceTypes = doctorService.selAllMedicalAdviceTypes();
        map.put("medicalAdviceTypes", medicalAdviceTypes);
        return map;
    }

    /**
     * 插入医嘱信息
     *
     * @param medicalAdvice
     * @return
     */
    @RequestMapping("insMedicalAdvice")
    public Map insMedicalAdvice(Authentication authentication, MedicalAdvice medicalAdvice) {
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        Map map = new HashMap<String, Object>();
        Boolean flag = false;
        try {//判断是否存在pid
            flag = doctorService.selPidByPidDno(medicalAdvice.getPatientId(), doctor.getDoctorNo());
        } catch (Exception e) {
            if (!flag) {
                map.put("flag", flag);
                return map;
            }
        }
        if (!flag) {
            map.put("flag", flag);
            return map;
        }
        medicalAdvice.setDoctorNo(doctor.getDoctorNo());
        medicalAdvice.setUpdate_date(new Date());
        flag = doctorService.insMedicalAdvice(medicalAdvice);
        map.put("flag", flag);
        return map;
    }

    /**
     * 更新医嘱信息
     *
     * @param medicalAdvice
     * @return
     */
    @RequestMapping("updMedicalAdvice")
    public Map updMedicalAdvice(MedicalAdvice medicalAdvice) {
        Map map = new HashMap<String, Object>();
        medicalAdvice.setUpdate_date(new Date());
        Boolean flag = doctorService.updMedicalAdvice(medicalAdvice);
        map.put("flag", flag);
        return map;
    }

    /**
     * 查询病例信息
     *
     * @param patientId 病人编号
     * @return
     */
    @RequestMapping("findAllCaseListsByPid")
    public Map findAllCaseListsByPid(Authentication authentication, String patientId) {
        Map map = new HashMap<String, Object>();
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        if("".equals(patientId)){
            List<CaseList> allCaseListByDco = doctorService.findAllCaseListByDco(doctor.getDoctorNo());
            map.put("caseLists", allCaseListByDco);
            map.put("flag", true);
        }else {
            Boolean flag = false;
            Integer patientId1=Integer.parseInt(patientId);
            try {//判断是否存在pid
                flag = doctorService.selPidByPidDno(patientId1, doctor.getDoctorNo());
            } catch (Exception e) {
                if (!flag) {
                    map.put("flag", flag);
                    return map;
                }
            }
            if (!flag) {
                map.put("flag", flag);
                return map;
            }
            List<CaseList> caseLists = doctorService.selAllCaseList(patientId1);
            map.put("caseLists", caseLists);
        }
        return map;
    }

    /**
     * 插入病例信息
     *
     * @param caseList
     * @return
     */
    @RequestMapping("insCaseList")
    public Map insCaseList(Authentication authentication, CaseList caseList) {
        Map map = new HashMap<String, Object>();
        //获取医生信息
        String name = authentication.getName();
        Doctor doctor = doctorService.selDoctorByUsername(name);
        Boolean flag = false;
        try {//判断是否存在pid
            flag = doctorService.selPidByPidDno(caseList.getPatientId(), doctor.getDoctorNo());
        } catch (Exception e) {
            if (!flag) {
                map.put("flag", flag);
                return map;
            }
        }
        if (!flag) {
            map.put("flag", flag);
            return map;
        }
        flag = doctorService.insCaseList(caseList);
        map.put("flag", flag);
        return map;
    }


}
