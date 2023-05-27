package cn.pzhu.dcims.service.impl;

import cn.pzhu.dcims.mapper.PatientinfoMapper;
import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.service.PatientinfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cailang
 * @create 2021-02-02-21:06
 */
@Service
public class PatientinfoServiceImpl implements PatientinfoService {
    @Resource
    private PatientinfoMapper patientinfoMapper;

    @Resource
    SolrTemplate solrTemplate;

    /**
     * 查询用户
     *
     * @param username 用户名
     * @return
     */
    public User findUserByUsername(String username) {
        return patientinfoMapper.selectByUsername(username);
    }

    /**
     * 查询病人信息
     *
     * @param id 病人编号
     * @return
     */
    public Patientinfo findPatientInfoById(int id){
        Patientinfo patientinfo = patientinfoMapper.selPatientInfoById(id);
       /* final CountDownLatch latch = new CountDownLatch(3); //同步执行
        new Thread() {
            @Override
            public void run() {
                Department department = patientinfoMapper.selDepartmentByNo(patientinfo.getDepartmentNo());
                patientinfo.setDepartment(department);
                latch.countDown();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Doctor doctor = patientinfoMapper.selDoctorByNo(patientinfo.getAttDoctorNo());
                patientinfo.setDoctor(doctor);
                latch.countDown();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                List<String> strings = patientinfoMapper.selDiagnosisTypeNosByPatientId(patientinfo.getId());

                List<DiagnosisType> diagnosisTypes = null;
                if (strings != null && strings.size() != 0) {
                    diagnosisTypes = patientinfoMapper.selDiagnosisTypesByNos(strings);
                    patientinfo.setDiagnosisTypes(diagnosisTypes);
                }
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("error");
        }*/
        List<String> strings = patientinfoMapper.selDiagnosisTypeNosByPatientId(patientinfo.getId());

        List<DiagnosisType> diagnosisTypes = null;
        if (strings != null && strings.size() != 0) {
            diagnosisTypes = patientinfoMapper.selDiagnosisTypesByNos(strings);
            patientinfo.setDiagnosisTypes(diagnosisTypes);
        }
        return patientinfo;
    }

    /**
     * 查询所有病人检查指标
     *
     * @return
     */
    public List<PatientTarget> selAllPatientTarget() {

        return patientinfoMapper.selAllPatientTarget();
    }

    /**
     * 分页查询病人信息
     *
     * @param page 页数
     * @param size 每页显示条数
     * @return
     */
    public List<Patientinfo> findAllPatientinfo(int page, int size) {
        PageHelper.startPage(page, size);
        List<Patientinfo> patientinfos = patientinfoMapper.selAllPatient();
        List<Patientinfo> patientinfos1 = new LinkedList<Patientinfo>();
        for (Patientinfo patientinfo : patientinfos
        ) {
            Department department = patientinfoMapper.selDepartmentByNo(patientinfo.getDepartmentNo());
            Doctor doctor = patientinfoMapper.selDoctorByNo(patientinfo.getAttDoctorNo());
            List<String> strings = patientinfoMapper.selDiagnosisTypeNosByPatientId(patientinfo.getId());
            List<DiagnosisType> diagnosisTypes = patientinfoMapper.selDiagnosisTypesByNos(strings);
            patientinfo.setDepartment(department);
            patientinfo.setDiagnosisTypes(diagnosisTypes);
            patientinfo.setDoctor(doctor);
            patientinfos1.add(patientinfo);
        }

        return patientinfos1;
    }

    /**
     * 查询病人情况
     *
     * @param id    病人编号
     * @param year  年
     * @param month 月
     * @return
     */
    public List<PatientPTarget> findPatientSituationById(int id, String year, String month) {
        Patientinfo patientinfo = patientinfoMapper.selPatientInfoById(id);
        List<PatientPTarget> patientPTargets = patientinfoMapper.selAllPatientPTargetByPid(id);
        List<PatientPTarget> patientPTargets1 = new LinkedList<PatientPTarget>();
        Calendar c = Calendar.getInstance();
        for (PatientPTarget patientPTarget : patientPTargets
        ) {

            c.setTime(patientPTarget.getRecordTime());
            int year2 = c.get(Calendar.YEAR);
            int month2 = c.get(Calendar.MONTH) + 1;
            if ((year2 + "").equals(year) && (month2 + "").equals(month)) {
                PatientTarget patientTarget = patientinfoMapper.selPatientTargetByNo(patientPTarget.getpTargetNo());
                patientPTarget.setPatientinfo(patientinfo);
                patientPTarget.setPatientTarget(patientTarget);
                patientPTargets1.add(patientPTarget);
            }
        }
        return patientPTargets1;
    }

    /**
     * 查询病例信息
     *
     * @param id 病人编号
     * @return
     */
//    @Cacheable(value = "CaseList", key = "#id") //将查询信息缓存到redis
    public List<CaseList> findAllCaseListById(int id) {
        return patientinfoMapper.selCaseListByPId(id);
    }

    /**
     * 查询体温信息
     *
     * @param id    病人编号
     * @param year  年
     * @param month 月
     * @return
     */
    public String[] findTemperaturesById(int id, String year, String month) {
        //若没有传递年月，则选择最近一个月的信息

        Map<String, String> maxYM = getMaxYM(id, month, year);
        month = maxYM.get("month");
        year = maxYM.get("year");
        System.out.println("year:" + year);
        System.out.println("month:" + month);
        List<PatientPTarget> patientPTargets = patientinfoMapper.selAllTempValueByPid(id);
        int year1 = Integer.parseInt(year);//将字符串转为int类型
        int month1 = Integer.parseInt(month);
        Calendar c = Calendar.getInstance();
        c.set(year1, month1, 0);//设置时间
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        String[] strings = new String[dayOfMonth];
        for (PatientPTarget patientPTarget : patientPTargets
        ) {
            c.setTime(patientPTarget.getRecordTime());
            int year2 = c.get(Calendar.YEAR);
            int month2 = c.get(Calendar.MONTH) + 1;
            //比较时间
            if ((year2 + "").equals(year) && (month2 + "").equals(month)) {
                int date = c.get(Calendar.DATE);
                strings[date] = patientPTarget.getValue() == null ? "0" : patientPTarget.getValue();
            }
        }
        return strings;
    }

    /**
     * 查询检查信息
     *
     * @param id 病人编号
     * @return
     */
//    @Cacheable(value = "checkList",key = "#id")//将查询信息缓存到redis
    public List<CheckList> findCheckListById(int id) {
        List<CheckList> checkLists = patientinfoMapper.selCheckListByPid(id);
        List<CheckList> checkLists1 = new LinkedList<CheckList>();
        for (CheckList checklist : checkLists
        ) {
            List<String> strings = patientinfoMapper.selCheckTypeNosByCheckNo(checklist.getCheckNo());
            List<CheckType> checkTypes = patientinfoMapper.selCheckTypeByCheckTypeNos(strings);

            checklist.setCheckTypes(checkTypes);
            checkLists1.add(checklist);
        }
        return checkLists1;
    }

    @Override
    public List<CheckList> findCheckListByIdByDco(int doctorNo) {
        List<CheckList> checkLists = patientinfoMapper.selCheckListByDco(doctorNo);
        List<CheckList> checkLists1 = new LinkedList<CheckList>();
        for (CheckList checklist : checkLists
        ) {
            List<String> strings = patientinfoMapper.selCheckTypeNosByCheckNo(checklist.getCheckNo());
            List<CheckType> checkTypes = patientinfoMapper.selCheckTypeByCheckTypeNos(strings);

            checklist.setCheckTypes(checkTypes);
            checkLists1.add(checklist);
        }
        return checkLists1;
    }

    /**
     * 查询检查结过信息
     *
     * @param checkNo
     * @return
     */
//    @Cacheable(value = "checkResultList", key = "#checkNo")//将查询信息缓存到redis
    public List<CheckResultList> findCheckResultListByNo(String checkNo) {
        List<CheckResultList> checkResultLists = patientinfoMapper.selCheckResultListByCheckNo(checkNo);
        /*List<CheckResultList> checkResultLists1 = new LinkedList<CheckResultList>();
        for (CheckResultList checkResultList : checkResultLists
        ) {//遍历检查结果
            //查询检查结果对应的病原体
            List<CheckresultPathogen> checkresultPathogens = patientinfoMapper.selCheckresultPathogenByid(checkResultList.getId());
            List<CheckresultPathogen> checkresultPathogens1 = new LinkedList<CheckresultPathogen>();
            for (CheckresultPathogen checkresultPathogen : checkresultPathogens) {
                Pathogen pathogen = patientinfoMapper.selPathogenByNo(checkresultPathogen.getPathogenNo() + "");
                checkresultPathogen.setPathogen(pathogen);
                checkresultPathogen.setCheckNo(checkResultList.getCheckNo());
                checkresultPathogens1.add(checkresultPathogen);
            }
            List<MedicalResult> medicalResults = patientinfoMapper.selMedicalResultById(checkResultList.getId());
            checkResultList.setMedicalResults(medicalResults);
            checkResultList.setPathogens(checkresultPathogens1);
            checkResultLists1.add(checkResultList);
        }*/
        return checkResultLists;
    }

    /**
     * 查询医嘱信息
     *
     * @param id 病人编号
     * @return
     */
//    @Cacheable(value = "MedicalAdvice",key = "#id")//将查询信息缓存到redis
    public List<MedicalAdvice> findMedicalAdviceById(int id) {
        List<MedicalAdvice> medicalAdvices = patientinfoMapper.selAllMAByPid(id);
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
     * 根据关键词查询医嘱信息
     *
     * @param id      病人编号
     * @param keyword 关键词
     * @return
     */
    public List<MedicalAdvice> findMeicalAdviceByKeyW(String id, String keyword) {
        if (solrTemplate == null) {
            return patientinfoMapper.selAllMAByPid(Integer.parseInt(id));
        }
        if ("".equals(keyword) || keyword == null) {

            Query query = new SimpleQuery();
            Criteria criteria = new Criteria("*:*");
            query.addCriteria(criteria);
            //通过solr查询
            ScoredPage<MedicalAdvice> medicalAdvices = solrTemplate.queryForPage("dcimsCore", query, MedicalAdvice.class);
            List<MedicalAdvice> medicalAdvices1 = new ArrayList<MedicalAdvice>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (MedicalAdvice medicalAdvice : medicalAdvices) {
                MedicalAdviceType medicalAdviceType = patientinfoMapper.selMedicalAdviceTypeByNo(medicalAdvice.getTypeNo());
                medicalAdvice.setMedicalAdviceType(medicalAdviceType);
                Doctor doctor = patientinfoMapper.selDoctorByNo(medicalAdvice.getDoctorNo());
                medicalAdvice.setDoctor(doctor);
                medicalAdvice.setStartTimeStr(simpleDateFormat.format(medicalAdvice.getStartTime()));
                medicalAdvice.setEndTimeStr(simpleDateFormat.format(medicalAdvice.getEndTime()));
                medicalAdvice.setEndExcuteTimeStr(simpleDateFormat.format(medicalAdvice.getEndExcuteTime()));
                medicalAdvice.setAntibioticStr(medicalAdvice.getAntibiotic() == 0 ? "否" : "是");
                medicalAdvices1.add(medicalAdvice);
            }
            return medicalAdvices1;
        }

        Criteria criteria = Criteria.where("medical").is(keyword);
        criteria.and("id").is(id);
        HighlightQuery query = new SimpleHighlightQuery(criteria);
        //高亮
        HighlightOptions highlightOptions = new HighlightOptions();
        // 添加高亮字段
        highlightOptions.addField("medicalAdviceName");
        highlightOptions.addField("route");
        highlightOptions.addField("patientId");
        highlightOptions.addField("cause");
        highlightOptions.addField("frequency");
        highlightOptions.addField("remark");
        highlightOptions.addField("instruction");
        highlightOptions.addField("mtname");
        highlightOptions.addField("doctorName");
        highlightOptions.addField("goal");
        // 添加高亮标签前缀
        highlightOptions.setSimplePrefix("<font color='red'>");
        // 添加高亮标签后缀
        highlightOptions.setSimplePostfix("</font>");
        // 把高亮对象添加到查询中
        query.setHighlightOptions(highlightOptions);
        HighlightPage<MedicalAdvice> page = solrTemplate.queryForHighlightPage("dcimsCore", query, MedicalAdvice.class);
//        List<MedicalAdvice> content = page.getContent();
        List<MedicalAdvice> content1 = new ArrayList<MedicalAdvice>();
        List<HighlightEntry<MedicalAdvice>> content = page.getHighlighted();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (HighlightEntry<MedicalAdvice> medicalAdviceHighlightEntry : content) {
            MedicalAdvice medicalAdvice = medicalAdviceHighlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = medicalAdviceHighlightEntry.getHighlights();
//            System.out.println(highlights.get(0).getSnipplets());
            for (HighlightEntry.Highlight highlight : highlights) {
                String string = "";
                List<String> snipplets = highlight.getSnipplets();
                for (String str : snipplets) {
                    string += str;
                }
                if (highlight.getField().getName().equals("medicalAdviceName")) {
                    medicalAdvice.setMedicalAdviceName(string);
                } else if (highlight.getField().getName().equals("patientId")) {
                    medicalAdvice.setPatientId(Integer.parseInt(string));
                } else if (highlight.getField().getName().equals("cause")) {
                    medicalAdvice.setCause(string);
                } else if (highlight.getField().getName().equals("frequency")) {
                    medicalAdvice.setFrequency(string);
                } else if (highlight.getField().getName().equals("route")) {
                    medicalAdvice.setRoute(string);
                } else if (highlight.getField().getName().equals("remark")) {
                    medicalAdvice.setRemark(string);
                } else if (highlight.getField().getName().equals("instruction")) {
                    medicalAdvice.setInstruction(string);
                } else if (highlight.getField().getName().equals("mtname")) {
                    medicalAdvice.setMtname(string);
                } else if (highlight.getField().getName().equals("doctorName")) {
                    medicalAdvice.setDoctorName(string);
                } else if (highlight.getField().getName().equals("goal")) {
                    medicalAdvice.setGoal(string);
                }

            }

            MedicalAdviceType medicalAdviceType = patientinfoMapper.selMedicalAdviceTypeByNo(medicalAdvice.getTypeNo());
            medicalAdviceType.setMedicalAdviceName(medicalAdvice.getMtname());
            medicalAdvice.setMedicalAdviceType(medicalAdviceType);
            Doctor doctor = patientinfoMapper.selDoctorByNo(medicalAdvice.getDoctorNo());
            if(doctor!=null){
                doctor.setDoctorName(medicalAdvice.getDoctorName());
            }

            medicalAdvice.setDoctor(doctor);
            medicalAdvice.setStartTimeStr(simpleDateFormat.format(medicalAdvice.getStartTime()));
            medicalAdvice.setEndTimeStr(simpleDateFormat.format(medicalAdvice.getEndTime()));
            medicalAdvice.setEndExcuteTimeStr(simpleDateFormat.format(medicalAdvice.getEndExcuteTime()));
            medicalAdvice.setAntibioticStr(medicalAdvice.getAntibiotic() == 0 ? "否" : "是");
            System.out.println(medicalAdvice);
            content1.add(medicalAdvice);
        }

        return content1;
    }

    /**
     * 获取最大的年月
     *
     * @param patientId 病人编号
     * @param month     月
     * @param year      年
     * @return
     */
    public Map<String, String> getMaxYM(int patientId, String month, String year) {
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
        if ("".equals(month) || month == null) {
            List<PatientPTarget> list = patientinfoMapper.selAllPatientPTargetByPid(patientId);
            long year1 = 0;
            long month1 = 0;
            Calendar cal = Calendar.getInstance();

            for (PatientPTarget key : list) {
                cal.setTime(key.getRecordTime());
                int year2 = cal.get(Calendar.YEAR);
                if (year2 > year1) {
                    year1 = year2;
                }
            }
            for (PatientPTarget key : list) {
                cal.setTime(key.getRecordTime());
                int year2 = cal.get(Calendar.YEAR);
                int month2 = cal.get(Calendar.MONTH) + 1;
                if (year2 == year1 && month2 > month1) {

                    month1 = month2;
                }
            }
            year = year1 + "";
            month = month1 + "";
        }
        stringStringHashMap.put("month", month);
        stringStringHashMap.put("year", year);
        return stringStringHashMap;
    }

    /**
     * 查询病人检查情况
     *
     * @param patientId 病人编号
     * @param month     月
     * @param year      年
     * @return
     */
    public PatientPT findPatientPT(int patientId, String month, String year) {

        //若没有传递年月，则选择最近一个月的信息
        Map<String, String> maxYM = getMaxYM(patientId, month, year);
        month = maxYM.get("month");
        year = maxYM.get("year");
        PatientPT patientDiagnosisTypes = new PatientPT();
        patientDiagnosisTypes.setPatientId(patientId);
        patientDiagnosisTypes.setMonth(month);
        patientDiagnosisTypes.setYear(year);
        List<PatientPTarget> list = patientinfoMapper.selAllPatientPTargetByPid(patientId);
        System.out.println(list);
        List<PatientTarget> patientTargets = patientinfoMapper.selAllPatientTarget();
        int year1 = Integer.parseInt(year);//将字符串转为int类型
        int month1 = Integer.parseInt(month);
        Calendar c = Calendar.getInstance();
        c.set(year1, month1, 0);//设置时间
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        Map<PatientTarget, PatientPTarget[]> map = new LinkedHashMap<PatientTarget, PatientPTarget[]>();
        Map<PatientTarget, Integer> map1 = new LinkedHashMap<PatientTarget, Integer>();
        patientDiagnosisTypes.setDays(days);
        int index = 0;

        for (PatientTarget patientTarget : patientTargets) {
            PatientPTarget[] patientPTargets = new PatientPTarget[days];
            index = 0;
            for (PatientPTarget key : list) {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(key.getRecordTime());
                int year2 = c1.get(Calendar.YEAR);
                int month2 = c1.get(Calendar.MONTH) + 1;
                if (key.getpTargetNo().equals(patientTarget.getpTargetNo()) && year.equals(year2 + "") && month.equals(month2 + "")) {
                    index++;
                    patientPTargets[c1.get(Calendar.DATE) - 1] = key;
                }

            }
            map1.put(patientTarget, index);
            map.put(patientTarget, patientPTargets);
        }
        patientDiagnosisTypes.setMap(map);
        patientDiagnosisTypes.setPtoDays(map1);
        return patientDiagnosisTypes;
    }
}
