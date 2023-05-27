package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.service.PatientinfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 病人详细信息相关
 *
 * @author cailang
 * @create 2021-02-03-0:10
 */
@Controller
@RequestMapping("/patient")
@PreAuthorize("hasRole('ROLE_USER')")
public class PatientinfoController {
    @Autowired
    private PatientinfoService patientinfoService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询病人信息
     *
     * @param map 病人基本信息
     * @param req
     * @return
     */
    @RequestMapping("findPatientinfoById")
    @ResponseBody
    public Map findPatientinfoById(@RequestBody Map map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        User user = patientinfoService.findUserByUsername(username);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("user", user);

        //获取当前查询病人的编号
        int id1 = Integer.parseInt((String) map.get("id"));

        //获取病人详细信息
        Patientinfo patientInfo = patientinfoService.findPatientInfoById(id1);
        map1.put("patientInfo", patientInfo);

        return map;
    }

    /**
     * 获取病人检查指标信息
     *
     * @param req
     * @return
     */
    @RequestMapping("findAllPatientTarget")
    @ResponseBody
    public Map findAllPatientTarget(HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        User user = patientinfoService.findUserByUsername(username);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("user", user);

        //获取病人检查指标信息
        List<PatientTarget> patientTargets = patientinfoService.selAllPatientTarget();
        map1.put("patientTargets", patientTargets);
        return map1;
    }

    /**
     * 查询所有病人信息
     *
     * @param page 页数
     * @param size 每页显示数量
     * @param req
     * @return
     */
    @RequestMapping("findAllPatientinfo")
    @ResponseBody
    public Map<String, Object> findAllPatientinfo(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        User user = patientinfoService.findUserByUsername(username);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("user", user);

        //查询所有病人信息
        List<Patientinfo> patientinfos = patientinfoService.findAllPatientinfo(page, size);

        //进行分页处理
        PageInfo pageInfo = new PageInfo(patientinfos);
        map1.put("pageInfo", pageInfo);
        return map1;
    }

    /**
     * 获取病人检查信息
     *
     * @param id    病人编号
     * @param year  年
     * @param month 月
     * @return
     */
    @RequestMapping("findPatientSituationById")
    @ResponseBody
    public List<PatientPTarget> findPatientSituationById(int id, int year, int month) {
        List<PatientPTarget> patientSituations = patientinfoService.findPatientSituationById(id, year + "", "" + month);
        return patientSituations;
    }

    /**
     * 获取病人病例信息
     *
     * @param id 病人编号
     * @return
     */
    @RequestMapping("findAllCaseListById")
    @ResponseBody
    public List<CaseList> findAllCaseListById(int id) {
        List<CaseList> allCaseListById = patientinfoService.findAllCaseListById(id);
        return allCaseListById;
    }


    /**
     * 查询体温数据
     *
     * @param id    病人编号
     * @param year  年
     * @param month 月
     * @return
     */
    @RequestMapping("findTemperaturesById")
    @ResponseBody
    public String[] findTemperaturesById(int id, long year, long month) {
        System.out.println(id + ":" + year + ":" + month);
        String[] temperaturesById = patientinfoService.findTemperaturesById(id, year + "", month + "");
        for (int i = 0; i < temperaturesById.length; i++) {
            if (temperaturesById[i] == null) {
                temperaturesById[i] = 35 + "";
            }
        }
        return temperaturesById;
    }

    /**
     * 病人检查信息查询
     *
     * @param id 病人编号
     * @return
     */
    @RequestMapping("findCheckListById")
    @ResponseBody
    public List<CheckList> findCheckListById(int id) {
        List<CheckList> checkListById = patientinfoService.findCheckListById(id);
        return checkListById;
    }

    /**
     * 检查结果查询
     *
     * @param checkNo 检查编号
     * @return
     */
    @RequestMapping("findCheckListByNo")
    @ResponseBody
    public List<CheckResultList> findCheckResultListByNo(String checkNo) {
        List<CheckResultList> checkResultListByNo = patientinfoService.findCheckResultListByNo(checkNo);
        return checkResultListByNo;
    }

    /**
     * 医嘱查询（查询所有信息）
     *
     * @param id 病人编号
     * @return
     */
    @RequestMapping("findMedicalAdviceById")
    @ResponseBody
    public List<MedicalAdvice> findMedicalAdviceById(int id) {
        List<MedicalAdvice> medicalAdviceById = patientinfoService.findMedicalAdviceById(id);
        return medicalAdviceById;
    }


    /**
     * 查询病人的检查情况
     *
     * @param id    病人编号
     * @param month 月
     * @param year  年
     * @return
     */
    @RequestMapping("findPatientPT")
    public PatientPT findPatientPT(int id, String month, String year) {

        return patientinfoService.findPatientPT(id, month, year);
    }


    /**
     * 病人摘要界面信息
     *
     * @param authentication
     * @param patientId      病人编号
     * @param month          月
     * @param year           年
     * @param req
     * @return
     */
    @RequestMapping("zhaiyao")
    @ResponseBody
    public Map findAllInfoInZhaiYao(Authentication authentication, Integer patientId, String month, String year, HttpServletRequest req) {//@RequestBody Map map
        String username = authentication.getName();
        User user = patientinfoService.findUserByUsername(username);

        Integer id = patientId;
        //确保当前病人ID获取成功
        if (id == null || id.equals(0)) {
            Integer id1 = (Integer) redisTemplate.opsForValue().get("id");
            id = id1;
        } else {
            redisTemplate.opsForValue().set("id", id);
        }
        Map<String, Object> map = new HashMap<>();

        //获取病人信息
        Patientinfo patientInfo = patientinfoService.findPatientInfoById(id);

        //获取病人检查情况情况
        PatientPT patientPT = patientinfoService.findPatientPT(id, month, year);

        //获取病人体温信息
        String[] temperaturesById = patientinfoService.findTemperaturesById(id, year, month);
        for (int i = 0; i < temperaturesById.length; i++) {
            if (temperaturesById[i] == null) {
                temperaturesById[i] = 35 + "";
            }
        }
        map.put("temperatures", temperaturesById);
        map.put("patient", patientInfo);
        map.put("patientPT", patientPT);
        map.put("user", user);
        return map;

    }

    /**
     * 获取病人病例信息
     *
     * @param authentication
     * @param patientId      病人编号
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("bingli")
    @ResponseBody
    public Map findAllInfoInBingli(Authentication authentication, Integer patientId, HttpServletRequest request, HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        final CountDownLatch latch = new CountDownLatch(3); //同步执行
        new Thread() {
            @Override
            public void run() {
                String username = authentication.getName();
                User user = patientinfoService.findUserByUsername(username);
                map.put("user", user);
                latch.countDown();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Integer id = (Integer) redisTemplate.opsForValue().get("id");
                //获取病人病例信息
                List<CaseList> allCaseListById = patientinfoService.findAllCaseListById(id);
                map.put("caseList", allCaseListById);
                latch.countDown();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Integer id = (Integer) redisTemplate.opsForValue().get("id");
                //获取病人信息
                Patientinfo patientInfo = patientinfoService.findPatientInfoById(id);
                map.put("patient", patientInfo);
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("error");
        }
        return map;
    }

    /**
     * 病人体温单
     *
     * @param authentication
     * @param request
     * @param month          月
     * @param year           日
     * @param req
     * @return
     */
    @RequestMapping("tiwendan")
    @ResponseBody
    public Map<String, Object> findAllInfoInTiWenDan(Authentication authentication, HttpServletRequest request, String month, String year, HttpServletRequest req) {
        String username = authentication.getName();
        User user = patientinfoService.findUserByUsername(username);

        Map<String, Object> map = new HashMap<String, Object>();
        Integer patientId = (Integer) redisTemplate.opsForValue().get("id");
        //若没有传递年月，则选择最近一个月的信息
        Map<String, String> maxYM = patientinfoService.getMaxYM(patientId, month, year);
        month = maxYM.get("month");
        year = maxYM.get("year");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), 0);
        //获取选择的当前日期的体温数据
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Patientinfo patientInfo = patientinfoService.findPatientInfoById(patientId);
        String[] temperaturesById = patientinfoService.findTemperaturesById(patientId, year, month);
        for (int i = 0; i < temperaturesById.length; i++) {
            if (temperaturesById[i] == null) {
                temperaturesById[i] = 35 + "";
            }
        }
        map.put("patient", patientInfo);
        map.put("temperatures", temperaturesById);
        map.put("days", days);
        map.put("month", month);
        map.put("year", year);
        map.put("user", user);
        return map;
    }

    /**
     * 医嘱查询
     *
     * @param authentication
     * @param request
     * @return
     */
    @RequestMapping("yizhu")
    @ResponseBody
    public Map<String, Object> findAllInfoInYiZhu(Authentication authentication, HttpServletRequest request) {
        String username = authentication.getName();
        User user = patientinfoService.findUserByUsername(username);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer patientId = (Integer) redisTemplate.opsForValue().get("id");

        //获取当前查询的病人信息
        Patientinfo patientInfo = patientinfoService.findPatientInfoById(patientId);
        map.put("patient", patientInfo);
        //获取医嘱信息
        List<MedicalAdvice> medicalAdviceById = patientinfoService.findMedicalAdviceById(patientId);
        map.put("medicalAdvice", medicalAdviceById);
        map.put("user", user);
        return map;
    }

    /**
     * 医嘱搜索
     *
     * @param authentication
     * @param keyword        搜索关键词
     * @param request
     * @return
     */
    @RequestMapping("yizhuSearch")
    @ResponseBody
    public Map<String, Object> findAllInfoInYiZhuSearch(Authentication authentication, String keyword, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer patientId = (Integer) redisTemplate.opsForValue().get("id");
        //根据关键词进行查找
        List<MedicalAdvice> medicalAdviceById = patientinfoService.findMeicalAdviceByKeyW(patientId + "", keyword);
        map.put("medicalAdvice", medicalAdviceById);
        return map;
    }

    /**
     * 病人检查信息查询
     *
     * @param authentication
     * @param request
     * @return
     */
    @RequestMapping("jianyanjieguo")
    @ResponseBody
    public Map findAllInfoJianyanjieguo(Authentication authentication, HttpServletRequest request) {
        String username = authentication.getName();
        User user = patientinfoService.findUserByUsername(username);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer patientId = (Integer) redisTemplate.opsForValue().get("id");

        //获取当前查询的病人信息
        Patientinfo patientInfo = patientinfoService.findPatientInfoById(patientId);

        //获取检查结果信息
        List<CheckList> checkListById = patientinfoService.findCheckListById(patientId);
        map.put("patient", patientInfo);
        map.put("checkList", checkListById);
        map.put("user", user);
        return map;
    }

    /**
     * 获取检查结果
     *
     * @param checkNo 检查编号
     * @param request
     * @return
     */
    @RequestMapping("checkResultList")
    @ResponseBody
    public List<CheckResultList> findAllCheckResultList(String checkNo, HttpServletRequest request) {
        List<CheckResultList> checkResultList = patientinfoService.findCheckResultListByNo(checkNo);
        return checkResultList;
    }

}
