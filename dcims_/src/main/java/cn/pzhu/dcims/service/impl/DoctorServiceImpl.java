package cn.pzhu.dcims.service.impl;


import cn.pzhu.dcims.mapper.DoctorMapper;
import cn.pzhu.dcims.mapper.PatientinfoMapper;
import cn.pzhu.dcims.mapper.UserMapper;
import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    PatientinfoMapper patientinfoMapper;
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public Doctor getDoctorByUsername(String username) {
        return doctorMapper.selDoctorByUsername(username);
    }

    /**
     * 插入病人信息
     * @param patientinfo
     * @return
     */
    @Override
    public Boolean insPatientInfo(Patientinfo patientinfo,PDiagnosis pDiagnosis) {
        int maxId1=doctorMapper.selPatientMaxId()+1;
        patientinfo.setEnterTime(new Date());
        pDiagnosis.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(pDiagnosis.getRecordDate())));
        //未选择病床
        if(patientinfo.getBedNumber()==null||"".equals(patientinfo.getBedNumber())){
            Integer integer1 = doctorMapper.insPatientInfo(patientinfo);
            if(integer1>0){
                pDiagnosis.setPatientId(maxId1);
                doctorMapper.insDiagnosisType(pDiagnosis);
                return true;
            }else{
                return false;
            }
        }
        String bedNumber = patientinfo.getBedNumber();
        String[] split = bedNumber.split("-");
        SickBed sickBed = new SickBed();
        sickBed.setSickBedNo(Integer.parseInt(split[1]));
        sickBed.setBedRoomName(split[0]);
        patientinfo.setId(maxId1);
        sickBed.setPid(patientinfo.getId());
        //更新新病房信息
        Integer integer = doctorMapper.updSickBed(sickBed);
        if(integer>0){
            Integer integer1 = doctorMapper.insPatientInfo(patientinfo);
            if(integer1>0){
                pDiagnosis.setPatientId(maxId1);
                doctorMapper.insDiagnosisType(pDiagnosis);
                return true;
            }
        }
        return false;
    }

    /**
     * 更新病人信息
     * @param patientinfo
     * @return
     */
    @Override
    public Boolean updPatientInfo(Patientinfo patientinfo) {
        Patientinfo patientinfo1 = patientinfoMapper.selPatientInfoById(patientinfo.getId());
        Integer integer = doctorMapper.updPatientInfo(patientinfo);
        if(integer<=0){
            return false;
        }

        if(patientinfo1.getBedNumber().equals(patientinfo.getBedNumber())){//判断病房信息是否修改
            return true;
        }else {
            SickBed sickBed = new SickBed();
            //若没有病床号
            if(patientinfo.getBedNumber()==null||"".equals(patientinfo.getBedNumber())){
                patientinfo1.setId(0);
               patientinfo=patientinfo1;
            }

            String bedNumber = patientinfo.getBedNumber();
            String[] split = bedNumber.split("-");
            sickBed.setBedRoomName(split[0]);
            sickBed.setPid(patientinfo.getId());
            sickBed.setSickBedNo(Integer.parseInt(split[1]));
            //更新病床信息
            Integer integer1 = doctorMapper.updSickBed(sickBed);
            if(integer1>0){
                return true;
            }
        }
        return false;
    }

    /**
     * （根据医生编号）查询所有病人信息
     * @param doctorNo 医生编号
     * @return
     */
    @Override
    public List<Patientinfo> selAllPatietnsByDoctorNo(Integer doctorNo) {
        List<Patientinfo> patientinfos = doctorMapper.selAllPatientsByDoctorNo(doctorNo);
        int i=0;
        for (Patientinfo patientinfo : patientinfos) {
            patientinfo.setDepartment(patientinfoMapper.selDepartmentByNo(patientinfo.getDepartmentNo()));
            patientinfo.setDoctor(patientinfoMapper.selDoctorByNo(patientinfo.getAttDoctorNo()));
            patientinfos.set(i,patientinfo);
            i++;
        }
        return patientinfos;
    }

    /**
     * 查询doctor信息
     * @param username 用户名
     * @return
     */
    @Override
    public Doctor selDoctorByUsername(String username) {
        return doctorMapper.selDoctorByUsername(username);
    }

    /**
     * 通过departmentNo查询病房信息
     * @param departmentNo 科室编号
     * @return
     */
    @Override
    public List<BedRoom> selBedRoomsByDno(Integer departmentNo) {
        return doctorMapper.selBedRoomsByDno(departmentNo);
    }

    /**
     * 查询感染检测信息
     * @param pid 病人编号
     * @return
     */
    @Override
    public List<PatientPTarget> selAllPatientPTargetByPid(Integer pid) {
        List<PatientPTarget> patientPTargets = doctorMapper.selAllPatientPTargetByPid(pid);
        int i=0;
        for (PatientPTarget patientPTarget : patientPTargets) {
            patientPTarget.setPatientinfo(patientinfoMapper.selPatientInfoById(patientPTarget.getPatientId()));
            patientPTarget.setPatientTarget(patientinfoMapper.selPatientTargetByNo(patientPTarget.getpTargetNo()));
            patientPTargets.set(i,patientPTarget);
            i++;
        }
        return patientPTargets;
    }

    /**
     * 插入感染检测信息
     * @param patientPTarget
     * @return
     */
    @Override
    public Boolean insPatientPTarget(PatientPTarget patientPTarget) {
        redisTemplate.opsForList().rightPush("INSPTarget",patientPTarget.getPatientId());
        return doctorMapper.insPatientPTarget(patientPTarget)>0?true:false;
    }

    /**
     * 查询手术信息
     * @param doctorNo 医生编号
     * @return
     */
    @Override
    public List<Operation> selOperationsByDno(int doctorNo) {
        List<Operation> operations = doctorMapper.selOperationsByDno(doctorNo);
        int i=0;
        for (Operation operation : operations) {
            operation.setDoctor(patientinfoMapper.selDoctorByNo(doctorNo));
            operation.setPatientinfo(patientinfoMapper.selPatientInfoById(operation.getPatientId()));
            operations.set(i,operation);
            i++;
        }
        return operations;
    }

    /**
     * 插入手术信息
     * @param operation
     * @return
     */
    @Override
    public Boolean insOperation(Operation operation) {
        redisTemplate.opsForList().rightPush("INSOperation",operation.getPatientId());
        return doctorMapper.insOperation(operation)>0?true:false;
    }

    /**
     * 插入手术回访信息
     * @param operationFollow
     * @return
     */
    @Override
    public Boolean insOperationFollow(OperationFollow operationFollow) {
        int patientId = operationFollow.getPatientId();
        Patientinfo patientinfo = patientinfoMapper.selPatientInfoById(patientId);
        int departmentNo = patientinfo.getDepartmentNo();
        operationFollow.setDepartmentNo(departmentNo);
        return doctorMapper.insOperationFollow(operationFollow)>0?true:false;
    }

    /**
     * 检验pid是否存在
     * @param pid 病人编号
     * @param dno 医生编号
     * @return
     */
    @Override
    public Boolean selPidByPidDno(Integer pid, Integer dno) {
        Integer integer = doctorMapper.selPidByPidDno(pid, dno);
        if(integer==0||integer==null){
            return false;
        }else return true;
    }

    /**
     * 获取所有检验类型信息
     * @return
     */
    @Override
    public List<CheckType> selAllCheckTypes() {
        return doctorMapper.selAllCheckTypes();
    }

    /**
     * 插入检验检查信息
     * @param checkList
     * @return
     */
    @Override
    public Boolean insCheckList(CheckList checkList) {
        String split[] = checkList.getSpecimenName().split(",");
//        String[] split = checkList.getSpecimenName().split("-");
        for (int i = 0; i < split.length; i++) {
            checkList.setSpecimenName(doctorMapper.selCheckTypeByCno(split[i]).getCheckTypeName());
            Integer integer = doctorMapper.selMaxIdFromCheckList();
            String maxIdStr="";
            if(integer<10){
                maxIdStr="000"+integer;
            }else if(integer<100){
                maxIdStr="00"+integer;
            }else if(integer<1000){
                maxIdStr="0"+integer;
            }else {
                maxIdStr=""+integer;
            }
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            String checkNo="CL"+instance.get(Calendar.YEAR)+maxIdStr;
            checkList.setCheckNo(checkNo);
            doctorMapper.insCheckList(checkList);
            doctorMapper.insCheckTypeCheckList(checkList.getCheckNo(),split[i]);
        }
        return true;
    }

    /**
     * 插入检查结果信息
     * @param checkResultList
     * @return
     */
    @Override
    public Boolean insCheckResultList(CheckResultList checkResultList) {
        String checkNo = checkResultList.getCheckNo();
        Integer pid = doctorMapper.selPatientByCno(checkNo);
        redisTemplate.opsForList().rightPush("INSCheckResult",pid);
        return doctorMapper.insCheckResultList(checkResultList)>0?true:false;
    }

    /**
     * 查询检查结果
     * @param checkNo 检查单号
     * @return
     */
    @Override
    public List<CheckResultList> selAllCheckResultList(String checkNo) {
        return doctorMapper.selAllCheckResultListByCno(checkNo);
    }

    /**
     * 查询所有病原体信息
     * @return
     */
    @Override
    public List<Pathogen> selAllPathogens() {
        return doctorMapper.selAllPathogen();
    }

    /**
     * 查询所有检查结果对应的病原体信息
     * @param checkresultId 检查结果id
     * @return
     */
    @Override
    public List<CheckresultPathogen> selAllCPathogen(String checkresultId) {
        List<CheckresultPathogen> checkresultPathogens = doctorMapper.selAllCPathogenByCid(checkresultId);
        if(checkresultPathogens.size()>0){
            int i=0;
            for (CheckresultPathogen checkresultPathogen : checkresultPathogens) {
                checkresultPathogen.setPathogen(doctorMapper.selPathogenByPno(checkresultPathogen.getPathogenNo()+""));
                checkresultPathogens.set(i,checkresultPathogen);
                i++;
            }
        }

        return checkresultPathogens;
    }

    /**
     * 插入检查结果的病原体信息
     * @param checkresultPathogen
     * @return
     */
    @Override
    public Boolean insCPathogen(CheckresultPathogen checkresultPathogen) {
        int checkresultId = checkresultPathogen.getCheckresultId();
        String checkNo = doctorMapper.selCheckNoByCRno(checkresultId);
        Integer pid = doctorMapper.selPatientByCno(checkNo);
        redisTemplate.opsForList().rightPush("INSCheckResultToP",pid);
        return doctorMapper.insCpathogen(checkresultPathogen)>0;
    }

    /**
     * 查询药敏结果
     * @param checkResultId
     * @return
     */
    @Override
    public List<ResultMedical> selAllRMedicalByCid(String checkResultId) {
        List<ResultMedical> resultMedicals = doctorMapper.selAllRMedicalByCid(checkResultId);
        return resultMedicals;
    }

    /**
     * 插入检验结果
     * @param resultMedical
     * @return
     */
    @Override
    public Boolean insRMedical(ResultMedical resultMedical) {
        Integer checkresultId = resultMedical.getCheckresultId();
        String checkNo = doctorMapper.selCheckNoByCRno(checkresultId);
        Integer pid = doctorMapper.selPatientByCno(checkNo);
        redisTemplate.opsForList().rightPush("INSCheckResultToMedical",pid);
        return doctorMapper.insRMedical(resultMedical)>0;
    }

    /**
     * 插入医嘱信息
     * @param medicalAdvice
     * @return
     */
    @Override
    public Boolean insMedicalAdvice(MedicalAdvice medicalAdvice) {
        return doctorMapper.insMedicalAdvice(medicalAdvice)>0;
    }

    /**
     * 更新医嘱信息
     * @param medicalAdvice
     * @return
     */
    @Override
    public Boolean updMedicalAdvice(MedicalAdvice medicalAdvice) {
        return doctorMapper.updMedicalAdvice(medicalAdvice)>0;
    }

    /**
     * 获取所有医嘱类型信息
     * @return
     */
    @Override
    public List<MedicalAdviceType> selAllMedicalAdviceTypes() {
        return doctorMapper.selAllMedicalAdviceTypes();
    }

    @Override
    public List<MedicalAdvice> findAllMedicalAdviceByDco(Integer doctorNo) {
        List<MedicalAdvice> medicalAdvices = doctorMapper.selAllMedicalAdviceByDco(doctorNo);
        List<MedicalAdvice> medicalAdvices1 = new LinkedList<MedicalAdvice>();
        for (MedicalAdvice medicalAdvice : medicalAdvices
        ) {
            medicalAdvice.setMedicalAdviceType(patientinfoMapper.selMedicalAdviceTypeByNo(medicalAdvice.getTypeNo()));
            medicalAdvice.setDoctor(patientinfoMapper.selDoctorByNo(medicalAdvice.getDoctorNo()));
            medicalAdvices1.add(medicalAdvice);
        }
        return medicalAdvices1;
    }

    /**
     * 查询病例信息
     * @param patientId 病人编号
     * @return
     */
    @Override
    public List<CaseList> selAllCaseList(Integer patientId) {
        List<CaseList> caseLists = doctorMapper.selAllCaseLists(patientId);
        int i=0;
        for (CaseList caseList : caseLists) {
            caseList.setPatientinfo(patientinfoMapper.selPatientInfoById(patientId));
            caseLists.set(i,caseList);
            i++;
        }
        return caseLists;
    }

    @Override
    public List<CaseList> findAllCaseListByDco(Integer doctorNo) {
        List<CaseList> caseLists = doctorMapper.selAllCaseListByDco(doctorNo);
        int i=0;
        for (CaseList caseList : caseLists) {
            caseList.setPatientinfo(patientinfoMapper.selPatientInfoById(caseList.getPatientId()));
            caseLists.set(i,caseList);
            i++;
        }
        return caseLists;
    }

    /**
     * 插入病例信息
     * @param caseList
     * @return
     */
    @Override
    public Boolean insCaseList(CaseList caseList) {
        return doctorMapper.insCaseList(caseList)>0;
    }

    /**
     * 在生成报卡前从其他数据自动获取报卡需要的信息
     * @param id 病人ID
     * @return
     */
    @Override
    public ReportCard selReportCard(int id) {

        ReportCard reportCard = new ReportCard();
        reportCard.setWarnStatus(1);
        CountDownLatch latch = new CountDownLatch(7);
        new Thread(){
            @Override
            public void run() {
                Integer integer = doctorMapper.selOperationCountsByPid(id);
                if(integer!=null){
                    reportCard.setIsOperation(integer>0?1:0);
                }
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Integer integer = doctorMapper.selPStatusByPid(id);
                if(integer!=null){
                    reportCard.setIsLeaveHospital(integer);
                }
                latch.countDown();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                Integer integer = doctorMapper.selPathogenByPid(id);
                if(integer!=null){
                    reportCard.setIsCheckedPathogen(integer>0?1:0);
                }

                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Integer integer = doctorMapper.selIsInvadeByPid(id);
                if(integer!=null){
                    reportCard.setIsInvade(integer>0?1:0);
                }

                latch.countDown();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                Integer integer = doctorMapper.selIsMultiMedical(id);
                if(integer!=null){
                    reportCard.setisMultiMedical(integer>0?1:0);
                }
                latch.countDown();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                Integer integer = doctorMapper.selIsUseMedicalByPid(id);
                if(integer!=null){
                    reportCard.setIsUseMedical(integer>0?1:0);
                }
                latch.countDown();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                Integer integer = doctorMapper.selTisNormalByPid(id);
                if(integer!=null){
                    reportCard.setTisNormal(integer>37?1:0);
                }
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("latch error");
        }
        return reportCard;
    }

    /**
     * 插入报卡
     * @param reportCard
     * @return
     */
    @Override
    public Boolean insReportCard(ReportCard reportCard) {
        return doctorMapper.insReportCard(reportCard)>0?true:false;
    }
}
