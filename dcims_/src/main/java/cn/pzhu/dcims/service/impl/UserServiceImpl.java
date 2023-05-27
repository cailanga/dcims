package cn.pzhu.dcims.service.impl;

import cn.pzhu.dcims.mapper.AdminMapper;
import cn.pzhu.dcims.mapper.DoctorMapper;
import cn.pzhu.dcims.mapper.PatientinfoMapper;
import cn.pzhu.dcims.mapper.UserMapper;
import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.pojo.vo.ByAgeInfo;
import cn.pzhu.dcims.pojo.vo.DepartmentPatientS;
import cn.pzhu.dcims.pojo.vo.PatientPTInfo;
import cn.pzhu.dcims.service.PatientinfoService;
import cn.pzhu.dcims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper usersMapper;
    @Autowired
    private PatientinfoMapper patientinfoMapper;
    @Autowired
    PatientinfoService patientinfoService;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 插入用户信息
     *
     * @param user
     * @return
     */
    public int insUsers(User user) {
        //对密码进行加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return usersMapper.insertUsers(user);
    }

    /**
     * 根据条件查询用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public User selUsersByNamePassword(String username, String password) {
        return usersMapper.selectUsers(username, password);
    }

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    public User selUsersByName(String username) {
        return usersMapper.selectByUsername(username);
    }

    /**
     * 查询所有的用户信息
     *
     * @return
     */
    public List<Patientinfo> findAllPatientInfo() {
        return usersMapper.selAllPatientInfo();
    }

    /**
     * 查询所有科室信息
     *
     * @return
     */
    public List<Department> findAllDepartment() {
        return usersMapper.selAllDepartMent();
    }

    /**
     * 查询所有感染类型
     *
     * @return
     */
    public List<InfectionType> findAllInfection() {
        return usersMapper.selAllInfectionType();
    }

    /**
     * 查询ICU监测信息
     *
     * @param departmentNo 科室编号
     * @param type         感染类型
     * @param year         年
     * @param month        月
     * @param day          日
     * @return
     */
    public ICUCheckShow findICUCheckShow(int departmentNo, String type, String year, String month, String day) {
        String type1 = type;
        year = year == null ? "2021" : year;
        int year1 = Integer.parseInt(year);
        int month1 = month == null || "".equals(month) ? 0 : Integer.parseInt(month);
        int day1 = day == null || "".equals(day) ? 0 : Integer.parseInt(day);
        ICUCheckShow icuCheckShow = new ICUCheckShow();
        icuCheckShow.setMonth(month1 + "");
        icuCheckShow.setYear(year);
        icuCheckShow.setPreyear(Integer.parseInt(year) - 1 + "");
        icuCheckShow.setType(type);
        icuCheckShow.setDay(day1 + "");
        icuCheckShow.setDepartmentNo(departmentNo);
        List<Department> departments = new ArrayList<Department>();
        List<InfectionType> infectionTypes = new ArrayList<InfectionType>();
        String title = "";
        String content1 = "";
        String content2 = "";
        Calendar calendar = Calendar.getInstance();
        int count = 0;//查档查询条件的人数统计
        int lastCount = 0;//去年同期在院人数
        int precount = 0;//当前条件前一个日期的人数统计
        List<Patientinfo> patientinfos = usersMapper.selPatientByDno(departmentNo);
        for (Patientinfo patientinfo : patientinfos) {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Date enterTime = patientinfo.getEnterTime();
            calendar1.setTime(enterTime);
            Date outTime = patientinfo.getOutTime();
            if (outTime == null) {
                outTime = new Date();
            }
            calendar2.setTime(outTime);
            if (month1 == 0) {//未传递月份信息，就只按年份查询
                calendar.set(year1, month1, day1 + 1);
                if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR)) {
                    count++;//统计人数
                }
                calendar.set(year1 - 1, month1, day1);
                if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR)) {
                    precount++;
                }
                calendar.set(year1 - 1, month1, day1);//去年同期
                if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR)) {
                    lastCount++;
                }
            } else if (day1 == 0) {//未传递当前日信息，就只按年份月份查询
                if (month1 == 1) {//月份等于1的话，前一个月的信息还是按该月信息显示
                    calendar.set(year1, month1 - 1, day1 + 1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH)) {
                        count++;
                    }
                    calendar.set(year1, month1 - 1, day1 + 1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH)) {
                        precount++;
                    }
                    calendar.set(year1 - 1, month1 - 1, day1 + 1);//去年同期
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH)) {
                        lastCount++;
                    }
                } else {
                    calendar.set(year1, month1 - 1, day1 + 1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH)) {
                        count++;
                    }
                    calendar.set(year1, month1 - 2, day1 + 1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH)) {
                        precount++;
                    }
                    calendar.set(year1 - 1, month1 - 1, day1 + 1);//去年同期
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH)) {
                        lastCount++;
                    }
                }
            } else {
                if (day1 == 1) {
                    calendar.set(year1, month1 - 1, day1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) && calendar2.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)) {
                        count++;
                    }
                    calendar.set(year1, month1 - 1, day1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) && calendar2.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)) {
                        precount++;
                    }
                    calendar.set(year1 - 1, month1 - 1, day1);//去年同期
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) && calendar2.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)) {
                        lastCount++;
                    }
                } else {
                    calendar.set(year1, month1 - 1, day1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) && calendar2.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)) {
                        count++;
                    }
                    calendar.set(year1, month1 - 1, day1 - 1);
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) && calendar2.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)) {
                        precount++;
                    }
                    calendar.set(year1 - 1, month1 - 1, day1);//去年同期
                    if (calendar1.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR) && calendar2.get(Calendar.YEAR) >= calendar.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) && calendar2.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) <= calendar.get(Calendar.DAY_OF_MONTH) && calendar2.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)) {
                        lastCount++;
                    }
                }

            }

        }

        if ("0".equals(type) || type == null) {//查询全部感染类型信息
            infectionTypes = usersMapper.selAllInfectionType();
        } else {//根据感染类型查询
            InfectionType infectionType = usersMapper.selInfectionTypeByNo(type);
            infectionTypes.add(infectionType);
        }
        int jikong = 0;//病例疾控次数(当前)
        int lastjikong = 0;//去年同期病例疾控次数
        String tableTitle2 = "";
        String tableTitle3 = "";
        Map<InfectionType, List<Integer>> infectionTypeListMap = new HashMap<InfectionType, List<Integer>>();
        for (InfectionType infectionType : infectionTypes) {//遍历感染类型
            List<Integer> integers = new ArrayList<Integer>();//0对应前一次，1对应当前查询，2对应去年同期的人数
            type = infectionType.getInfectionTypeNo();
            if (month1 == 0) {//只按年查询
                //当前查询年份
                tableTitle2 = year1 - 1 + "年";
                tableTitle3 = year1 + "年";
                List<ICUCheck> icuChecks = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month, day);
                int total = 0;
                for (ICUCheck icuCheck : icuChecks) {
                    total += icuCheck.getCount();
                    if (icuCheck.getType().equals("3")) {
                        jikong += icuCheck.getCount();
                    }
                }
                //当前查询年份的前一年
                List<ICUCheck> icuChecks1 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 - 1 + "", month, day);
                int total1 = 0;
                for (ICUCheck icuCheck : icuChecks1) {
                    total1 += icuCheck.getCount();
                }
                //当前查询年份的去年同期
                List<ICUCheck> icuChecks2 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 - 1 + "", month, day);
                int total2 = 0;
                for (ICUCheck icuCheck : icuChecks2) {
                    total2 += icuCheck.getCount();
                    if (icuCheck.getType().equals("3")) {
                        lastjikong += icuCheck.getCount();
                    }
                }
                integers.add(total1);
                integers.add(total);
                integers.add(total2);

            } else if (day1 == 0) {
                //查询当前年份当前月
                if (month1 == 1) {
                    tableTitle2 = year1 + "年" + (month1) + "月";
                } else {
                    tableTitle2 = year1 + "年" + (month1 - 1) + "月";
                }

                tableTitle3 = year1 + "年" + month1 + "月";
                List<ICUCheck> icuChecks = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month1 + "", day);
                int total = 0;
                for (ICUCheck icuCheck : icuChecks) {
                    total += icuCheck.getCount();
                    if (icuCheck.getType().equals("3")) {
                        jikong += icuCheck.getCount();
                    }
                }
                //当前查询年份的前一月
                List<ICUCheck> icuChecks1;
                int total1 = 0;
                if (month1 == 1) {

                    icuChecks1 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month1 + "", day);
                } else {
                    icuChecks1 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month1 - 1 + "", day);
                }
                for (ICUCheck icuCheck : icuChecks1) {
                    total1 += icuCheck.getCount();
                }
                //当前查询年份当前月的去年同期
                List<ICUCheck> icuChecks2 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 - 1 + "", month, day);
                int total2 = 0;
                for (ICUCheck icuCheck : icuChecks2) {
                    total2 += icuCheck.getCount();
                    if (icuCheck.getType().equals("3")) {
                        lastjikong += icuCheck.getCount();
                    }
                }
                integers.add(total1);
                integers.add(total);
                integers.add(total2);
            } else {
                //查询当前年份当前月日
                if (day1 == 1) {
                    tableTitle2 = year1 + "年" + (month1) + "月" + (day1) + "日";
                } else {
                    tableTitle2 = year1 + "年" + (month1) + "月" + (day1 - 1) + "日";
                }
                tableTitle3 = year1 + "年" + month1 + "月" + day1 + "日";
                List<ICUCheck> icuChecks = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month1 + "", day);
                int total = 0;
                for (ICUCheck icuCheck : icuChecks) {
                    total += icuCheck.getCount();
                    if (icuCheck.getType().equals("3")) {
                        jikong += icuCheck.getCount();
                    }
                }
                //当前查询时间的前一日
                List<ICUCheck> icuChecks1;
                int total1 = 0;
                if (day1 == 1) {
                    icuChecks1 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month1 + "", day1 + "");
                } else {
                    icuChecks1 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 + "", month1 + "", day1 - 1 + "");
                }
                for (ICUCheck icuCheck : icuChecks1) {
                    total1 += icuCheck.getCount();
                }
                //当前查询年份当前月日的去年同期
                List<ICUCheck> icuChecks2 = usersMapper.selAllICUCheckByYMDDNOT(departmentNo, type, year1 - 1 + "", month, day);
                int total2 = 0;
                for (ICUCheck icuCheck : icuChecks2) {
                    total2 += icuCheck.getCount();
                    if (icuCheck.getType().equals("3")) {
                        lastjikong += icuCheck.getCount();
                    }
                }
                integers.add(total1);
                integers.add(total);
                integers.add(total2);
            }
            infectionTypeListMap.put(infectionType, integers);
        }
        if (departmentNo == 0) {
            content1 += "统计条件:全部科室  ";
            if (month1 == 0) {
                title += year1 + "年全部科室监控指标统计榜";
                String str = (count - lastCount) > 0 ? "增加" : "减少";
                String str1 = (jikong - lastjikong) > 0 ? "增加" : "减少";
                content2 += year1 + "年全部科室住院病人" + count + "人,较去年同期(" + lastCount + ")" + str + Math.abs(count - lastCount) + "人。发生疾控" + jikong + "例次，较去年同期(" + lastjikong + ")" + str1 + Math.abs(jikong - lastjikong) + "例次";
            } else if (day1 == 0) {
                title += year1 + "年" + month1 + "月全部科室监控指标统计榜";
                String str = (count - lastCount) > 0 ? "增加" : "减少";
                String str1 = (jikong - lastjikong) > 0 ? "增加" : "减少";
                content2 += year1 + "年" + month1 + "月全部科室住院病人" + count + "人,较去年同期(" + lastCount + ")" + str + Math.abs(count - lastCount) + "人。发生疾控" + jikong + "例次，较去年同期(" + lastjikong + ")" + str1 + Math.abs(jikong - lastjikong) + "例次";
            } else {
                title += year1 + "年" + month1 + "月" + day1 + "日全部科室监控指标统计榜";
                String str = (count - lastCount) > 0 ? "增加" : "减少";
                String str1 = (jikong - lastjikong) > 0 ? "增加" : "减少";
                content2 += year1 + "年" + month1 + "月" + day1 + "全部科室住院病人" + count + "人,较去年同期(" + lastCount + ")" + str + Math.abs(count - lastCount) + "人。发生疾控" + jikong + "例次，较去年同期(" + lastjikong + ")" + str1 + Math.abs(jikong - lastjikong) + "例次";
            }
        } else {
            Department department = patientinfoMapper.selDepartmentByNo(departmentNo);
            content1 += "统计条件:" + department.getDepartmentName() + "  ";
            if (month1 == 0) {
                title += year1 + "年" + department.getDepartmentName() + "监控指标统计榜";
                String str = (count - lastCount) > 0 ? "增加" : "减少";
                String str1 = (jikong - lastjikong) > 0 ? "增加" : "减少";
                content2 += year1 + "年" + department.getDepartmentName() + "住院病人" + count + "人,较去年同期(" + lastCount + ")" + str + Math.abs(count - lastCount) + "人。发生疾控" + jikong + "例次，较去年同期(" + lastjikong + ")" + str1 + Math.abs(jikong - lastjikong) + "例次";
            } else if (day1 == 0) {
                title += year1 + "年" + month1 + "月" + department.getDepartmentName() + "监控指标统计榜";
                String str = (count - lastCount) > 0 ? "增加" : "减少";
                String str1 = (jikong - lastjikong) > 0 ? "增加" : "减少";
                content2 += year1 + "年" + month1 + "月" + department.getDepartmentName() + "住院病人" + count + "人,较去年同期(" + lastCount + ")" + str + Math.abs(count - lastCount) + "人。发生疾控" + jikong + "例次，较去年同期(" + lastjikong + ")" + str1 + Math.abs(jikong - lastjikong) + "例次";
            } else {
                title += year1 + "年" + month1 + "月" + day1 + "日" + department.getDepartmentName() + "监控指标统计榜";
                String str = (count - lastCount) > 0 ? "增加" : "减少";
                String str1 = (jikong - lastjikong) > 0 ? "增加" : "减少";
                content2 += year1 + "年" + month1 + "月" + day1 + "日" + department.getDepartmentName() + "住院病人" + count + "人,较去年同期(" + lastCount + ")" + str + Math.abs(count - lastCount) + "人。发生疾控" + jikong + "例次，较去年同期(" + lastjikong + ")" + str1 + Math.abs(jikong - lastjikong) + "例次";
            }
        }
        if ("0".equals(type1) || type1 == null) {
            content1 += "感染类型:全部感染";
        } else {
            InfectionType infectionType = usersMapper.selInfectionTypeByNo(type1);
            content1 += "感染类型:" + infectionType.getInfectionTypeName();
        }

        icuCheckShow.setCount(jikong);
        icuCheckShow.setLastCount(lastjikong);
        icuCheckShow.setTotal1(count);
        icuCheckShow.setPreTotal(precount);
        icuCheckShow.setLastTotal(lastCount);
        icuCheckShow.setTitle(title);
        icuCheckShow.setContent1(content1);
        icuCheckShow.setContent2(content2);
        icuCheckShow.setInfectionTypes(infectionTypeListMap);
        icuCheckShow.setTableTitle2(tableTitle2);
        icuCheckShow.setTableTitle3(tableTitle3);

        return icuCheckShow;
    }

    /**
     * 目标检测漏报查询
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<TargetMonitor> findTargetMonitor(String startDate, String endDate) {
        List<TargetMonitor> targetMonitors = new LinkedList<TargetMonitor>();
        List<CheckList> checkLists = usersMapper.selAllCheckList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sd1 = df.parse(startDate);
            Date sd2 = df.parse(endDate);
            for (CheckList checkList : checkLists) {//遍历病人所有检查信息
                String format = df.format(checkList.getCheckTime());
                Date date = df.parse(format);
                if (date.after(sd1) || date.equals(sd1)) {//比较时间
                    List<CheckResultList> checkResultLists = patientinfoService.findCheckResultListByNo(checkList.getCheckNo());
                    List<CheckResultList> checkResultLists1 = new LinkedList<CheckResultList>();
                    TargetMonitor targetMonitor = new TargetMonitor();
                    for (CheckResultList checkResultList : checkResultLists) {
                        String format1 = df.format(checkResultList.getResultTime());
                        Date date1 = df.parse(format1);
                        if (date1.before(sd2) || date1.equals(sd2)) {
                            checkResultLists1.add(checkResultList);
                        }
                    }
                    if (checkResultLists1.size() > 0) {
                        targetMonitor.setCheckList(checkList);
                        Patientinfo patientinfo = patientinfoMapper.selPatientInfoById(checkList.getPatientId());
                        Department department = patientinfoMapper.selDepartmentByNo(patientinfo.getDepartmentNo());
                        targetMonitor.setDepartment(department);
                        targetMonitor.setDepartmentNo(department.getDepartmentNo());
                        targetMonitor.setPatientinfo(patientinfo);
                        targetMonitor.setPatientId(checkList.getPatientId());
                        targetMonitor.setCheckResultLists(checkResultLists1);
                        targetMonitors.add(targetMonitor);
                    }


                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetMonitors;
    }

    /**
     * 查询所有切口等级
     *
     * @return
     */
    public List<IncisionGrade> findAllIncisionGrade() {
        return usersMapper.selAllIncisinGade();
    }

    /**
     * 查询手术回访信息
     *
     * @param grade      切口等级
     * @param department 科室
     * @return
     */
    public List<OperationFollow> findOperationFByGradeAndDno(int grade, int department) {
        List<OperationFollow> operationFollows = new LinkedList<OperationFollow>();
        List<OperationFollow> operationFollows1;
        if (grade == 0 && department == 0) {
            operationFollows1 = usersMapper.selAllOperation();
        } else if (grade == 0) {
            operationFollows1 = usersMapper.selOperationByDepartmentNo(department);
        } else if (department == 0) {
            operationFollows1 = usersMapper.selOperationByGrade(grade);
        } else {
            operationFollows1 = usersMapper.selOperationByGradeAndDno(grade, department);
        }
        for (OperationFollow operationFollow : operationFollows1
        ) {
            operationFollow.setPatientinfo(patientinfoMapper.selPatientInfoById(operationFollow.getPatientId()));
            operationFollow.setDepartment(patientinfoMapper.selDepartmentByNo(operationFollow.getDepartmentNo()));
            operationFollow.setDoctor(patientinfoMapper.selDoctorByNo(operationFollow.getDoctorNo()));
            List<String> strings = patientinfoMapper.selDiagnosisTypeNosByPatientId(operationFollow.getPatientId());
            operationFollow.setDiagnosisTypes(patientinfoMapper.selDiagnosisTypesByNos(strings));
            operationFollows.add(operationFollow);
        }
        return operationFollows;
    }

    /**
     * 查询疑似爆发信息
     *
     * @param departmentNo   科室编号
     * @param outbreakTypeNo 爆发类型编号
     * @param liCount        例次数
     * @return
     */
    public List<SusOutbreak> findSusOutbreak(int departmentNo, int outbreakTypeNo, int liCount) {
        List<SusOutbreak> susOutbreaks;
        List<SusOutbreak> susOutbreaks1 = new LinkedList<SusOutbreak>();
        if (outbreakTypeNo == 0 && departmentNo == 0) {
            susOutbreaks = usersMapper.selAllSusOutbreakByliCount(liCount);
        } else if (outbreakTypeNo == 0) {
            susOutbreaks = usersMapper.selAllSusOutbreakByDno(departmentNo, liCount);
        } else if (departmentNo == 0) {
            susOutbreaks = usersMapper.selAllSusOutbreakByOutNo(outbreakTypeNo, liCount);
        } else {
            susOutbreaks = usersMapper.selAllSusOutbreak(departmentNo, outbreakTypeNo, liCount);
        }
        for (SusOutbreak susOutbreak : susOutbreaks) {
            susOutbreak.setDepartment(patientinfoMapper.selDepartmentByNo(susOutbreak.getDepartmentNo()));
            susOutbreak.setOutbreakType(usersMapper.selAllOutBreakTypeByNo(susOutbreak.getOutBreakTypeNo()));
            susOutbreaks1.add(susOutbreak);
        }
        return susOutbreaks1;
    }

    /**
     * 查询所有爆发类型
     *
     * @return
     */
    public List<OutbreakType> findAllOutbreakType() {
        return usersMapper.selAllOutBreakType();
    }

    /**
     * 查询报卡信息
     *
     * @param departmentNo 科室编号
     * @return
     */
    public List<ReportCard> findAllReportCard(int departmentNo,Date startDate,Date endDate) {
        List<ReportCard> reportCards = new LinkedList<ReportCard>();
        List<ReportCard> reportCards1;
        if (departmentNo == 0) {//查询所有科室的报卡
            reportCards1 = usersMapper.selAllReportCardByDate(startDate,endDate);
        } else {
            reportCards1 = usersMapper.selAllReportCardByDNoDate(departmentNo,startDate,endDate);
        }

        for (ReportCard reportCard : reportCards1) {
            reportCard.setPatientinfo(patientinfoMapper.selPatientInfoById(reportCard.getPatientId()));
            reportCard.setDiagnosisType(usersMapper.selDiagnosisTypeByNo(reportCard.getDiagnosisTypeNo()));
            reportCard.setDoctor(patientinfoMapper.selDoctorByNo(reportCard.getDoctorNo()));
            reportCard.setHandleUser(usersMapper.selectById(reportCard.getHandleUserId()));
            reportCard.setMainDoctor(patientinfoMapper.selDoctorByNo(reportCard.getPatientinfo().getAttDoctorNo()));
            reportCards.add(reportCard);
        }
        return reportCards;
    }

    /**
     * 根据科室号，报卡处理状态查询每个病人最近一次提交的报卡
     *
     * @param departmentNo 科室号
     * @param handleAction 报卡处理状态
     * @return
     */
    @Override
    public List<ReportCard> findAllReportCardByDnoHa(int departmentNo, int handleAction,Date startDate,Date endDate) {
        List<ReportCard> reportCards = new LinkedList<ReportCard>();
        List<ReportCard> reportCards1;
        reportCards1 = usersMapper.selReportCardsByDnoHa(departmentNo, handleAction,startDate,endDate);

        for (ReportCard reportCard : reportCards1) {
            reportCard.setPatientinfo(patientinfoMapper.selPatientInfoById(reportCard.getPatientId()));
            reportCard.setDiagnosisType(usersMapper.selDiagnosisTypeByNo(reportCard.getDiagnosisTypeNo()));
            reportCard.setDoctor(patientinfoMapper.selDoctorByNo(reportCard.getDoctorNo()));
            reportCard.setHandleUser(usersMapper.selectById(reportCard.getHandleUserId()));
            reportCard.setMainDoctor(patientinfoMapper.selDoctorByNo(reportCard.getPatientinfo().getAttDoctorNo()));
            reportCards.add(reportCard);
        }
        return reportCards;
    }

    /**
     * 根据诊断类型，报卡处理状态查询每个病人最近一次提交的报卡
     *
     * @param diagnosisTypeNo 科室号
     * @param handleAction    报卡处理状态
     * @return
     */
    @Override
    public List<ReportCard> findAllReportCardByDTnoHa(int diagnosisTypeNo, int handleAction,Date start,Date end) {
        List<ReportCard> reportCards = new LinkedList<ReportCard>();
        List<ReportCard> reportCards1;
        reportCards1 = usersMapper.selReportCardsByDTnoHa(diagnosisTypeNo, handleAction,start,end);

        for (ReportCard reportCard : reportCards1) {
            reportCard.setPatientinfo(patientinfoMapper.selPatientInfoById(reportCard.getPatientId()));
            reportCard.setDiagnosisType(usersMapper.selDiagnosisTypeByNo(reportCard.getDiagnosisTypeNo()));
            reportCard.setDoctor(patientinfoMapper.selDoctorByNo(reportCard.getDoctorNo()));
            reportCard.setHandleUser(usersMapper.selectById(reportCard.getHandleUserId()));
            reportCard.setMainDoctor(patientinfoMapper.selDoctorByNo(reportCard.getPatientinfo().getAttDoctorNo()));
            reportCards.add(reportCard);
        }
        return reportCards;
    }

    /**
     * 根据诊断类型，报卡处理状态查询所有报卡
     *
     * @param diagnosisTypeNo 科室号
     * @param handleAction    报卡处理状态
     * @return
     */
    @Override
    public List<ReportCard> findAllReportCardByDTnoHas(int diagnosisTypeNo, int handleAction, Date start,Date end) {
        List<ReportCard> reportCards = new LinkedList<ReportCard>();
        List<ReportCard> reportCards1;
        reportCards1 = usersMapper.selReportCardsByDTnoHas(diagnosisTypeNo, handleAction,start,end);

        for (ReportCard reportCard : reportCards1) {
            reportCard.setPatientinfo(patientinfoMapper.selPatientInfoById(reportCard.getPatientId()));
            reportCard.setDiagnosisType(usersMapper.selDiagnosisTypeByNo(reportCard.getDiagnosisTypeNo()));
            reportCard.setDoctor(patientinfoMapper.selDoctorByNo(reportCard.getDoctorNo()));
            reportCard.setHandleUser(usersMapper.selectById(reportCard.getHandleUserId()));
            reportCard.setMainDoctor(patientinfoMapper.selDoctorByNo(reportCard.getPatientinfo().getAttDoctorNo()));
            reportCards.add(reportCard);
        }
        return reportCards;
    }

    /**
     * 插入疑似爆发信息
     *
     * @param susOutbreak 疑似爆发信息
     * @param username    用户名
     * @return
     */
    public boolean insSusOutbreak(SusOutbreak susOutbreak, String username) {
        susOutbreak.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (usersMapper.insSusOutbreak(susOutbreak) > 0) {
            User user = usersMapper.selectByUsername(username);
            //更新报卡状态
            int index = usersMapper.updReportCard(susOutbreak.getPatientId(), 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), user.getId());
            if (index > 0) return true;
        }
        return false;
    }

    /**
     * 插入警告信息
     *
     * @param warnInfo 警告信息
     * @param username 用户名
     * @return
     */
    public boolean insWarnInfo(WarnInfo warnInfo, String username) {
        warnInfo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (usersMapper.insWarnInfo(warnInfo) > 0) {
            User user = usersMapper.selectByUsername(username);
            //更新报卡状态
            int index = usersMapper.updReportCard(warnInfo.getPatientId(), 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), user.getId());
            if (index > 0)
                return true;
        }
        return false;
    }

    /**
     * 插入排除信息
     *
     * @param eliminateWarn 排除信息
     * @param username      用户名
     * @return
     */
    @Transactional
    public boolean insEliminateWarn(EliminateWarn eliminateWarn, String username) {
        eliminateWarn.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (usersMapper.insEliminateWarn(eliminateWarn) > 0) {
            User user = usersMapper.selectByUsername(username);
            //更新报卡状态
            int index = usersMapper.updReportCard(eliminateWarn.getReportCardId(), 0, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), user.getId());
            int index1 = usersMapper.updReportCardPaichuUser(user.getId(), eliminateWarn.getReportCardId());
            if (index + index1 > 1)
                return true;
        }
        return false;
    }

    /**
     * 查询警告信息
     *
     * @param reportCardId 报卡编号
     * @return
     */
    public List<WarnInfo> findWarnInfoByRid(int reportCardId) {
        List<WarnInfo> warnInfoList = new LinkedList<WarnInfo>();
        List<WarnInfo> warnInfoList1 = usersMapper.selWarnInfoByRid(reportCardId);
        for (WarnInfo warnInfo : warnInfoList1) {
            warnInfo.setPatientinfo(patientinfoMapper.selPatientInfoById(warnInfo.getPatientId()));
            warnInfo.setWarnCondition(usersMapper.selWarnConditionByNo(warnInfo.getConditionNo()));
            warnInfo.setWarnType(usersMapper.selWarnTypeByNo(warnInfo.getWarnTypeNo()));
            warnInfoList.add(warnInfo);
        }
        return warnInfoList;
    }

    /**
     * 查询排除信息
     *
     * @param reportCardId 报卡编号
     * @return
     */
    public List<EliminateWarn> findEliminateWarn(int reportCardId) {
        List<EliminateWarn> eliminateWarns = new LinkedList<EliminateWarn>();
        List<EliminateWarn> eliminateWarns1 = usersMapper.selEliminateByRid(reportCardId);
        for (EliminateWarn eliminateWarn : eliminateWarns1) {
            eliminateWarn.setPatientinfo(patientinfoMapper.selPatientInfoById(eliminateWarn.getPatientId()));
//            eliminateWarn.setDoctor(patientinfoMapper.selDoctorByNo(eliminateWarn.getDoctorNo()));
            eliminateWarn.setUser(usersMapper.selectById(eliminateWarn.getUserId()));
            eliminateWarns.add(eliminateWarn);
        }
        return eliminateWarns;
    }

    /**
     * 查询科室统计信息
     *
     * @param departmentNo
     * @return
     */
    public List<ReportTable> findReportTable(int departmentNo) {
        List<ReportTable> reportTables = new LinkedList<ReportTable>();
        if (departmentNo == 0) {//查询所有科室
            List<Department> departments = usersMapper.selAllDepartMent();
            for (Department department : departments) {
                ReportTable reportTable = new ReportTable();
                int total = usersMapper.selPCountFromPatientInfoByDno(department.getDepartmentNo());
                int upCount = usersMapper.selPCountIsInHospitalByDno(department.getDepartmentNo());
                int susCount = usersMapper.selPCountFromReportCardByDno(department.getDepartmentNo(), 2);
                int warnCount = usersMapper.selPCountFromReportCardByDno(department.getDepartmentNo(), 1);
                int elimiCount = usersMapper.selPCountFromReportCardByDno(department.getDepartmentNo(), 0);
                reportTable.setDepartment(department);
                reportTable.setElimiCount(elimiCount);
                reportTable.setSusCount(susCount);
                reportTable.setTotal(total);
                reportTable.setUpCount(upCount);
                reportTable.setWarnCount(warnCount);
                reportTables.add(reportTable);
            }
        } else {
            Department department = patientinfoMapper.selDepartmentByNo(departmentNo);
            ReportTable reportTable = new ReportTable();
            int total = usersMapper.selPCountFromPatientInfoByDno(department.getDepartmentNo());
            int upCount = usersMapper.selPCountIsInHospitalByDno(department.getDepartmentNo());
            int susCount = usersMapper.selPCountFromReportCardByDno(department.getDepartmentNo(), 2);
            int warnCount = usersMapper.selPCountFromReportCardByDno(department.getDepartmentNo(), 1);
            int elimiCount = usersMapper.selPCountFromReportCardByDno(department.getDepartmentNo(), 0);
            reportTable.setDepartment(department);
            reportTable.setElimiCount(elimiCount);
            reportTable.setSusCount(susCount);
            reportTable.setTotal(total);
            reportTable.setUpCount(upCount);
            reportTable.setWarnCount(warnCount);
            reportTables.add(reportTable);
        }
        return reportTables;
    }

    /**
     * 更新报卡信息
     *
     * @param id           报卡id
     * @param handleAction 处理行为
     * @param username     用户名
     * @return
     */
    public Boolean updReportCard(int id, int handleAction, String username) {
        User user = usersMapper.selectByUsername(username);
        int index = usersMapper.updReportCard(id, handleAction, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), user.getId());
        return index > 0 ? true : false;
    }

    /**
     * 查询警告条件以及对应人数
     *
     * @param departmentNo
     * @return
     */
    public List<WarnContditionToCount> findWarnTypeToCount(int departmentNo) {
        List<WarnContditionToCount> warnContditionToCounts = new LinkedList<WarnContditionToCount>();

        List<WarnCondition> warnConditions = usersMapper.selAllWarnCondition();
        for (WarnCondition warnCondition : warnConditions) {
            WarnContditionToCount warnContditionToCount = new WarnContditionToCount();
            warnContditionToCount.setWarnCondition(warnCondition);
            int count = usersMapper.selWarnCountFromWarnInfo(warnCondition.getConditionNo(), departmentNo);
            warnContditionToCount.setCount(count);
            warnContditionToCounts.add(warnContditionToCount);
        }

        return warnContditionToCounts;
    }

    /**
     * 获取所有警告类型信息
     *
     * @return
     */
    @Override
    public List<WarnType> findAllWarnTypes() {
        return usersMapper.selAllWarnTypes();
    }

    /**
     * 获取警告条件信息
     *
     * @return
     */
    @Override
    public List<WarnCondition> findAllWarnconditions() {
        return usersMapper.selAllWarnCondition();
    }

    /**
     * 对报卡进行预警
     *
     * @param username 操作人员 用户名
     * @param warnInfo 预警信息
     * @return
     */
    @Override
    @Transactional
    public Boolean warn(String username, WarnInfo warnInfo) {
        int reportCardId = warnInfo.getReportCardId();
        redisTemplate.opsForList().rightPush("warnReportCard:",warnInfo.getReportCardId());
        warnInfo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        User user = usersMapper.selectByUsername(username);
        int index1 = usersMapper.insWarnInfo(warnInfo);
        Integer index = usersMapper.updReportCardHandleUser(user.getId(), warnInfo.getReportCardId(), warnInfo.getDate());
        if (index + index1 > 1) {
            return true;
        }
        return false;
    }

    /**
     * 对报卡进行怀疑
     *
     * @param username     操作人员 用户名
     * @param reportCardId 报卡id
     * @return
     */
    @Override
    public Boolean sus(String username, int reportCardId) {
        User user = usersMapper.selectByUsername(username);
        Integer index = usersMapper.updReportCardSusUser(user.getId(), reportCardId, new Date());
        return index > 0 ? true : false;
    }

    /**
     * 查询体征信息
     *
     * @return
     */
    public List<TiZhengInfo> findAllTiZheng() {
        return usersMapper.selAllTiZheng();
    }

    /**
     * 查询生物信息
     *
     * @return
     */
    public List<ShengWuInfo> findAllshengwuinfo() {
        return usersMapper.selAllshengwuinfo();
    }

    /**
     * 查询手术信息
     *
     * @return
     */
    public List<OperateInfo> findAlloperateinfo() {
        return usersMapper.selAlloperateinfo();
    }

    /**
     * 查询科室各种类型以及对应人数
     *
     * @return
     */
    public List<DepartmentBQ> findAllDepartmentBqType() {
        List<DepartmentBQ> list = new ArrayList<DepartmentBQ>();
        List<Department> departments = usersMapper.selAllDepartMent();

        for (Department department : departments) {
            int total = 0;
            DepartmentBQ departmentBQ = new DepartmentBQ();
            departmentBQ.setDepartment(department);
            List<DepartmentBqType> departmentBqTypes = usersMapper.selAllDepartmentBqType(department.getDepartmentNo());
            for (DepartmentBqType departmentBqType : departmentBqTypes) {
                total += departmentBqType.getCount();
            }
            departmentBQ.setList(departmentBqTypes);
            departmentBQ.setTaotals(total);
            list.add(departmentBQ);
        }
        return list;
    }

    /**
     * 查询所有诊断类型
     *
     * @return
     */
    @Override
    public List<DiagnosisType> sellAllDiagnosisTypes() {
        return usersMapper.selectAllDiagnosisType();
    }

    /**
     * 查询报卡信息
     *
     * @param diagnosisTypeNo 诊断类型
     * @return
     */
    @Override
    public List<ReportCard> selAllreportcardByDTypeNo(int diagnosisTypeNo) {
        List<ReportCard> reportCards1 = usersMapper.selAllReportCardByDType(diagnosisTypeNo);
        List<ReportCard> reportCards = new ArrayList<>();
        for (ReportCard reportCard : reportCards1) {
            reportCard.setPatientinfo(patientinfoMapper.selPatientInfoById(reportCard.getPatientId()));
            reportCard.setDiagnosisType(usersMapper.selDiagnosisTypeByNo(reportCard.getDiagnosisTypeNo()));
            reportCard.setDoctor(patientinfoMapper.selDoctorByNo(reportCard.getDoctorNo()));
            if (reportCard.getHandleUserId() != 0) {
                User user = usersMapper.selectById(reportCard.getHandleUserId());
                reportCard.setHandleUser(user);
            } else if (reportCard.getPaichuUserId() != 0) {
                User user = usersMapper.selectById(reportCard.getPaichuUserId());
                reportCard.setHandleUser(user);
            } else if (reportCard.getSusUserId() != 0) {
                User user = usersMapper.selectById(reportCard.getSusUserId());
                reportCard.setHandleUser(user);
            }
            reportCard.setMainDoctor(patientinfoMapper.selDoctorByNo(reportCard.getPatientinfo().getAttDoctorNo()));
            reportCards.add(reportCard);
        }
        return reportCards;
    }

    @Override
    public List<Operation> findOperationsByGAndPid(int grade, int patientId) {
        List<Operation> operations = usersMapper.getOperationsByGAndPid(grade, patientId);
        int i = 0;
        for (Operation operation : operations) {
            operation.setDoctor(patientinfoMapper.selDoctorByNo(operation.getDoctorNo()));
            operation.setPatientinfo(patientinfoMapper.selPatientInfoById(operation.getPatientId()));
            operations.set(i, operation);
            i++;
        }
        return operations;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public Boolean updateUser(User user) {
        return usersMapper.updUserByUsername(user) > 0 ? true : false;
    }

    /**
     * 删除用户
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public Boolean delUser(User user) {
        int index = 0;
        try {
            if (user.getRole() == 3) {
                index = doctorMapper.delDoctor(user.getUsername());
                usersMapper.delUser(user.getId());
            } else {
                index = usersMapper.delUser(user.getId());
            }
        } catch (Exception e) {

        } finally {
            return index > 0 ? true : false;
        }
    }

    /**
     * 根据科室查询人数
     *
     * @param departmentNo
     * @return
     */
    @Override
    public Integer findPatientSByDno(int departmentNo) {
        return null;
    }

    //查询各科室所存在的不同感染状态病人数量
    @Override
    public List<DepartmentPatientS> findDepartmentPatientS(int year, int month) {
        List<Department> departments = usersMapper.selAllDepartMent();
        List<DepartmentPatientS> departmentPatientS = new ArrayList<>();
        for (Department department : departments) {
            DepartmentPatientS departmentPatientS1 = new DepartmentPatientS();
            departmentPatientS1.setDepartment(department);
            ArrayList<Integer> counts = new ArrayList<Integer>();
            int susCount = usersMapper.selReportCardsByDnoHaCount1(department.getDepartmentNo(), 2, year, month);
            int warnCount = usersMapper.selReportCardsByDnoHaCount1(department.getDepartmentNo(), 1, year, month);
            int elimiCount = usersMapper.selReportCardsByDnoHaCount1(department.getDepartmentNo(), 0, year, month);
            int no_handle = usersMapper.selReportCardsByDnoHaCount1(department.getDepartmentNo(), 3, year, month);
            counts.add(susCount);
            counts.add(elimiCount);
            counts.add(warnCount);
            counts.add(no_handle);
            departmentPatientS1.setCounts(counts);
            departmentPatientS.add(departmentPatientS1);
        }
        return departmentPatientS;
    }

    /**
     * 查询病人年龄分布
     *
     * @return
     */
    @Override
    public List<ByAgeInfo> findAgeInfo(int year) {
        int integer = usersMapper.selPCountByAgeRange(0, 10, year);
        int integer1 = usersMapper.selPCountByAgeRange(11, 20, year);
        int integer2 = usersMapper.selPCountByAgeRange(21, 30, year);
        int integer3 = usersMapper.selPCountByAgeRange(31, 50, year);
        int integer4 = usersMapper.selPCountByAgeRange(51, 150, year);
        ArrayList<ByAgeInfo> byAgeInfos = new ArrayList<>();
        ByAgeInfo byAgeInfo = new ByAgeInfo();
        byAgeInfo.setCounts(integer);
        byAgeInfo.setDescription("10岁及以下");
        byAgeInfos.add(byAgeInfo);
        ByAgeInfo byAgeInfo1 = new ByAgeInfo();
        byAgeInfo1.setCounts(integer1);
        byAgeInfo1.setDescription("11岁~20岁");
        byAgeInfos.add(byAgeInfo1);
        ByAgeInfo byAgeInfo2 = new ByAgeInfo();
        byAgeInfo2.setCounts(integer2);
        byAgeInfo2.setDescription("21岁~30岁");
        byAgeInfos.add(byAgeInfo2);
        ByAgeInfo byAgeInfo3 = new ByAgeInfo();
        byAgeInfo3.setCounts(integer3);
        byAgeInfo3.setDescription("31岁~50岁");
        byAgeInfos.add(byAgeInfo3);
        ByAgeInfo byAgeInfo4 = new ByAgeInfo();
        byAgeInfo4.setCounts(integer4);
        byAgeInfo4.setDescription("50岁及以上");
        byAgeInfos.add(byAgeInfo4);
        return byAgeInfos;
    }

    @Override
    public List<Patientinfo> findPatiensByDNameAndType(String departmentName, String type, int year, int month) {
        Department department = usersMapper.selDepartmentByName(departmentName);
        if (type.equals("疑似感染")) {
            List<Integer> integers = usersMapper.selPatientIds(department.getDepartmentNo(), 2, year, month);
            if (integers.size() == 0) {
                return new ArrayList<>();
            }
            List<Patientinfo> patientinfos = usersMapper.selPatientsByIds(integers);
            return patientinfos;
        } else if (type.equals("排除感染")) {
            List<Integer> integers = usersMapper.selPatientIds(department.getDepartmentNo(), 0, year, month);
            if (integers.size() == 0) {
                return new ArrayList<>();
            }
            List<Patientinfo> patientinfos = usersMapper.selPatientsByIds(integers);
            return patientinfos;
        } else if (type.equals("感染预警")) {
            List<Integer> integers = usersMapper.selPatientIds(department.getDepartmentNo(), 1, year, month);
            if (integers.size() == 0) {
                return new ArrayList<>();
            }
            List<Patientinfo> patientinfos = usersMapper.selPatientsByIds(integers);
            return patientinfos;
        } else if (type.equals("未处理")) {
            List<Integer> integers = usersMapper.selPatientIds(department.getDepartmentNo(), 3, year, month);
            if (integers.size() == 0) {
                return new ArrayList<>();
            }
//            List<Integer> mergedList = Stream.of(integers1, integers2, integers).flatMap(Collection::stream).collect(Collectors.toList());
//            List<Patientinfo> patientinfos = usersMapper.selPatientsByIds1(mergedList);
            return usersMapper.selPatientsByIds(integers);
        }

        return null;
    }

    @Override
    public List<PatientPTInfo> findPatientPT(String year, String month, int status) {

        ArrayList<PatientPTInfo> patientPTInfos = new ArrayList<>();
        ArrayList<PatientTarget> patientTargets = patientinfoMapper.selAllPatientTarget();
        int year1 = Integer.parseInt(year);//将字符串转为int类型
        int month1 = Integer.parseInt(month);
        Calendar c = Calendar.getInstance();
        c.set(year1, month1, 0);//设置时间
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        Map<PatientTarget, PatientPTarget[]> map = new LinkedHashMap<PatientTarget, PatientPTarget[]>();
        Map<PatientTarget, Integer> map1 = new LinkedHashMap<PatientTarget, Integer>();
        int index = 0;

        for (PatientTarget patientTarget : patientTargets) {
            Integer integer = patientTarget.getpTargetNo();
            Integer integer1 = usersMapper.selAllPatientPTarget(year, month, integer, status);
            PatientPTInfo patientPTInfo = new PatientPTInfo();
            patientPTInfo.setCount(integer1);
            patientPTInfo.setName(patientTarget.getpTargetName());
            patientPTInfos.add(patientPTInfo);
        }

        return patientPTInfos;
    }

    @Override
    public Integer findPatientsCount() {
        return usersMapper.selPatientCounts();
    }

    @Override
    public Integer findDoctorsCount() {
        return usersMapper.selDoctorCounts();
    }

    @Override
    public Integer findDepartmentCount() {
        return usersMapper.selDepartmentCounts();
    }


    /**
     * SpringSecurity提供的登录验证
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println(username);
            User users = usersMapper.selectByUsername(username);
            System.out.println(users);
            if (users == null) {
                return null;
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            //根据用户角色标识添加用户授权信息
            if (users.getRole() == 1) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            } else if (users.getRole() == 2) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else if (users.getRole() == 3) {
                authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
            }

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), authorities);
            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
