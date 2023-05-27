package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.mapper.UserMapper;
import cn.pzhu.dcims.pojo.*;
import cn.pzhu.dcims.pojo.vo.PatientPTInfo;
import cn.pzhu.dcims.service.AdminService;
import cn.pzhu.dcims.service.PatientinfoService;
import cn.pzhu.dcims.service.UserService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 用户操作
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    PatientinfoService patientinfoService;
    @Autowired
    RedisTemplate redisTemplate;


    /*@RequestMapping("login")
    @ResponseBody
    public String login(){
        System.out.println("登陆成功");
        return "success";
    }*/
/*
    @RequestMapping("loginFail")
    public String loginFail(HttpServletRequest req){
        System.out.println("登录失败");
        req.getSession().setAttribute("error", "账户或密码错误");
        return "redirect:/pages/login.html";
    }*/
    /**
     * 登录验证码验证
     *
     * @param checkCode
     * @return
     */
    @RequestMapping("loginCheckCode")
    @ResponseBody
    public String loginCheckCode(String checkCode) {
        //从redis中获取验证码字符串
        String code = (String) redisTemplate.opsForValue().get("code");
        //判断输入验证码是否正确
        if ("".equals(checkCode) || checkCode == null) {
            return "null";
        } else if (!checkCode.equals(code)) {
            return "error";
        }
        return "success";
    }

    /**
     * 注册用户
     *
     * @param user       存有用户信息的用户对象
     * @param checkCode  输入的验证码
     * @param rePassword 第二次确认的密码
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public String register(User user, String checkCode, String rePassword) {
        //从redis获取验证码字符串
        String code = (String) redisTemplate.opsForValue().get("code");
        //判断输入的验证码是否正确
        if ("".equals(checkCode) || checkCode == null) {
            return "{msg:code_null}";
        }
        if (checkCode.equals(code)) {
            //判断第二次输入的密码是否正确
            if (user.getPassword().equals(rePassword)) {
                user.setRegTime(new Date());
                int index = userService.insUsers(user);
                System.out.println("注册成功");
                return "success";
            } else {
                return "notSame";
            }
        } else {
            return "code_error";
        }

    }

    //验证码生成对象
    @Autowired
    private Producer captchaProducer;

    /**
     * 获取图片验证码
     *
     * @param resp
     */
    @RequestMapping("vc")
    public void makeCode(HttpServletResponse resp) {
        //获取验证码字符串
        String capText = captchaProducer.createText();

        //通过验证码字符串生成图片数据
        BufferedImage bi = captchaProducer.createImage(capText);
        try {
            // 生成图片验证码
//            ByteArrayOutputStream outputStream = null;
            ServletOutputStream outputStream = null;
//            outputStream = new ByteArrayOutputStream();
            outputStream = resp.getOutputStream();
            ImageIO.write(bi, "jpg", outputStream);
            try {
                outputStream.flush();
            } finally {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将生成的验证码字符串存入redis
        redisTemplate.opsForValue().set("code", capText);
    }
   /* @RequestMapping("vc")
    public void makeCode(HttpServletRequest req, HttpServletResponse resp){
        *//*
     * 面板的概念：在图片上扣一张玻璃（透明的图片），在玻璃上绘制图像，画板是透明的
     *
     *//*
        //1、创建一个图片，使用构造器，创建一张空图片，而且图片默认颜色为黑色
        BufferedImage image = new BufferedImage(100,30,BufferedImage.TYPE_INT_RGB);
        //2、创建画板对象，透明的画板，使用画笔在画板上进行绘制
        Graphics2D g = image.createGraphics();
        //3、向画板绘制内容时，要先设置画笔，且需要更改画笔的颜色
        g.setColor(Color.WHITE);
        //4、使用画笔时，将画板全部填充为白色，填充一个矩形区域
        g.fillRect(0, 0, 100, 30);
        //5、更改画笔颜色
        g.setColor(Color.RED);
        //6、创建一个随机自然数的集合
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        //7、创建随机数
        Random random = new Random();
        //8、生成随机数，循环生成4个随机数
        for(int i=0;i<4;i++){
            randomList.add(random.nextInt(10));
        }
        //9、设置字体
        g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,18));
        //10、遍历集合，输出到画板上，设置一个颜色数组
        Color[] colors = new Color[]{Color.RED,Color.YELLOW,Color.PINK,Color.BLUE};

        for(int j=0;j<randomList.size();j++){
            g.setColor(colors[random.nextInt(colors.length)]);
            //让字体不整齐的显示出来
            g.drawString(randomList.get(j)+"", 20*j+15, 24+(random.nextInt(13)-6));
        }

        //画杠
        for(int k=0;k<2;k++){
            g.setColor(colors[random.nextInt(colors.length)]);
            g.drawLine(0, random.nextInt(31), 100, random.nextInt(31));
        }

        ServletOutputStream outputStream = null;
        try {
            outputStream = resp.getOutputStream();
            //二进制，将图片信息直接写入到outputStream
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        redisTemplate.opsForValue().set("code",""+randomList.get(0)+randomList.get(1)
                        +randomList.get(2)+randomList.get(3));
    }*/


    /**
     * 用户自行修改密码
     *
     * @param password 旧密码
     * @param username 用户名
     * @param newPWD   新密码
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @RequestMapping("updateUserPwd")
    @ResponseBody
    public Map updUserPassword(String password, String username, String newPWD) {
        //查找当前用户完整信息
        User user = userService.selUsersByName(username);
        String flagStr = "";
        if (user == null) {//判断用户是否存在
            flagStr = "UserIsNotExist";
        } else {//判断密码是否正确
            if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {//密码正确
                //更新用户密码
                Boolean flag = adminService.updPassword(newPWD, user.getId());
                if (flag) {//修改成功
                    flagStr = "success";
                } else {
                    flagStr = "error";
                }
            } else {//密码不正确
                flagStr = "pwdIsNotCorrect";
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", flagStr);
        return map;
    }

    /**
     * 用户自行修改信息
     *
     * @param user 待更新的用户信息
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') ")
    @RequestMapping("updateUser")
    @ResponseBody
    public Map updUser(User user) {
        Boolean flag = userService.updateUser(user);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", flag);
        return map;
    }


    /**
     * 获取当前登录用户信息
     *
     * @param authentication SpringSecurity提供的保存当前用户部分信息的对象
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    @RequestMapping("getUser")
    @ResponseBody
    public Map getUser(Authentication authentication) {
        //获取登录用户姓名
        String username = authentication.getName();

        //查找当前用户完整信息
        User user = userService.selUsersByName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        return map;
    }

    /**
     * 查询所有病人信息
     *
     * @param authentication
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_DOCTOR')")
    @RequestMapping("findAllPatientInfo")
    @ResponseBody
    public Map<String, Object> findAllPatientInfo(Authentication authentication, HttpServletRequest req) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);

        //查询所有病人信息
        List<Patientinfo> allPatientInfo = userService.findAllPatientInfo();
        map.put("patientInfos", allPatientInfo);
        return map;
    }

    /**
     * 根据条件查询ICU监控信息
     *
     * @param authentication
     * @param departmentNo   医院科室号
     * @param type           感染类型
     * @param year           年
     * @param month          月
     * @param day            日
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("ICUMonitor")
    @ResponseBody
    public Map<String, Object> findAllInICUMonitor(Authentication authentication, int departmentNo, String type, String year, String month, String day, HttpServletRequest req) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);

        //获取ICU检查信息
        ICUCheckShow icuCheckShow = userService.findICUCheckShow(departmentNo, type, year, month, day);
        //获取所有感染类型
        List<InfectionType> allInfection = userService.findAllInfection();
        //获取科室信息
        List<Department> allDepartment = userService.findAllDepartment();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("infection", allInfection);
        map.put("department", allDepartment);
        map.put("icuCheckShow", icuCheckShow);
        map.put("infectionTypes", icuCheckShow.getInfectionTypes());
        map.put("user", user);
        return map;
    }

    /**
     * 目标监测漏报查询
     *
     * @param authentication
     * @param startDate      开始日期
     * @param endDate        结束日期
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("TargetMonitor")
    @ResponseBody
    public Map<String, Object> findAllInTargetMonitor(Authentication authentication, String startDate, String endDate, HttpServletRequest req) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        List<TargetMonitor> targetMonitor = userService.findTargetMonitor(startDate, endDate);
        map.put("targetMonitor", targetMonitor);
        return map;
    }

    /**
     * 查询手术回访信息
     *
     * @param authentication
     * @param grade          手术等级
     * @param departmentNo   科室号
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER') ")
    @RequestMapping("operationFollow")
    @ResponseBody
    public Map<String, Object> findAllInOPerationFollow(Authentication authentication, int grade, int departmentNo, HttpServletRequest req) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);

        //获取科室信息
        List<Department> allDepartment = userService.findAllDepartment();

        //获取切口等级信息
        List<IncisionGrade> allIncisionGrade = userService.findAllIncisionGrade();

        //根据条件获取手术回访信息
        List<OperationFollow> operationFByGradeAndDno = userService.findOperationFByGradeAndDno(grade, departmentNo);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departments", allDepartment);
        map.put("incisionGrade", allIncisionGrade);
        map.put("operationFollows", operationFByGradeAndDno);
        map.put("user", user);
        return map;
    }

    /**
     * 查询病人手术信息
     *
     * @param grade     手术等级
     * @param patientId 病人编号
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER') ")
    @RequestMapping("operations")
    @ResponseBody
    public Map<String, Object> findAllInOPerationFollow(Authentication authentication, int grade, int patientId) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);

        //获取切口等级信息
        List<IncisionGrade> allIncisionGrade = userService.findAllIncisionGrade();

        //根据条件获取手术信息
        List<Operation> operations = userService.findOperationsByGAndPid(grade, patientId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("incisionGrade", allIncisionGrade);
        map.put("operations", operations);
        map.put("user", user);
        return map;
    }

    /**
     * 疑似爆发查询
     *
     * @param authentication
     * @param departmentNo   科室号
     * @param outbreakTypeNo 爆发类型号
     * @param liCount        例次数
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("SusOutbreak")
    @ResponseBody
    public Map<String, Object> findAllInSusOutbreak(Authentication authentication, int departmentNo, int outbreakTypeNo, int liCount, HttpServletRequest req) {

        String username = authentication.getName();
        User user = userService.selUsersByName(username);

        //获取所有科室信息
        List<Department> allDepartment = userService.findAllDepartment();

        //获取爆发类型
        List<OutbreakType> allOutbreakType = userService.findAllOutbreakType();

        //获取疑似爆发信息
        List<SusOutbreak> susOutbreak = userService.findSusOutbreak(departmentNo, outbreakTypeNo, liCount);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departments", allDepartment);
        map.put("outbreakType", allOutbreakType);
        map.put("susOutbreak", susOutbreak);
        map.put("user", user);
        return map;
    }

    /**
     * 查找报卡信息
     *
     * @param authentication
     * @param departmentNo   科室号
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findReportCard")
    @ResponseBody
    public Map findReportCard(Authentication authentication, int departmentNo, HttpServletRequest req, @RequestParam(defaultValue = "-1") int handleAction, @RequestParam(defaultValue = "0") int status,String startDate,String endDate) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);

        System.out.println(startDate+":"+endDate);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date start=null;
        Date end=null;
        if(!"".equals(startDate)||!"".equals(endDate)){
            try {
                start= ft.parse(startDate);
                end= ft.parse(endDate);
            } catch (ParseException e) {
                System.out.println("日期格式错误");
            }
            System.out.println(start);
            System.out.println(end);
        }
        //通过科室号获取报卡信息
        List<ReportCard> allReportCard;
        if (status == 1) {
            allReportCard = userService.findAllReportCardByDnoHa(departmentNo, handleAction,start,end);
        } else {
            allReportCard = userService.findAllReportCard(departmentNo,start,end);
        }
        //通过科室号获取疑似病例人数，确诊人数等统计信息
        List<ReportTable> reportTable = userService.findReportTable(departmentNo);
        List<Department> allDepartment = userService.findAllDepartment();
        Map map = new HashMap<String, Object>();
        int reportCardId = 0;
        //找出第一个病人的ID
        if (allReportCard.size() != 0) {
            reportCardId = allReportCard.get(0).getId();
        }
        //通过报卡ID获取对应病人的排除信息
        List<EliminateWarn> eliminateWarn = userService.findEliminateWarn(reportCardId);
        //通过报卡ID获取对应病人的警告信息
        List<WarnInfo> warnInfoByPid = userService.findWarnInfoByRid(reportCardId);
        //查询预警条件以及对应的信息数
        List<WarnContditionToCount> warnTypeToCount = userService.findWarnTypeToCount(departmentNo);
        map.put("warnConditions", warnTypeToCount);
        map.put("warnInfo", warnInfoByPid);
        map.put("elimiInfo", eliminateWarn);
        map.put("department", allDepartment);
        map.put("reportCard", allReportCard);
        map.put("reportTable", reportTable);
        map.put("user", user);
        return map;
    }

    /**
     * 查询警告信息
     *
     * @param authentication
     * @param reportCardId   报卡ID
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findWarnInfo")
    @ResponseBody
    public Map findWarnInfo(Authentication authentication, int reportCardId) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);

        //通过病人ID获取对应病人的排除信息
        List<EliminateWarn> eliminateWarn = userService.findEliminateWarn(reportCardId);
        //通过病人ID获取对应病人的警告信息
        List<WarnInfo> warnInfoByPid = userService.findWarnInfoByRid(reportCardId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("warnInfo", warnInfoByPid);
        map.put("elimiInfo", eliminateWarn);
        map.put("user", user);
        return map;
    }

    /**
     * 查询排除信息
     *
     * @param patientId 病人编号
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findEliminateWarn")
    @ResponseBody
    public List<EliminateWarn> findEliminateWarn(int patientId) {
        return userService.findEliminateWarn(patientId);
    }

    /**
     * 查询所有警告条件和警告类型
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findAllWarnTypesAndConditions")
    @ResponseBody
    public Map findAllWarnTypesAndConditions() {
        HashMap<String, Object> map = new HashMap<>();
        List<WarnType> allWarnTypes = userService.findAllWarnTypes();
        List<WarnCondition> allWarnconditions = userService.findAllWarnconditions();
        map.put("warnTypes", allWarnTypes);
        map.put("warnconditions", allWarnconditions);
        return map;
    }

    /**
     * 对报卡人员进行预警
     *
     * @param authentication
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("warn")
    @ResponseBody
    public Map warn(Authentication authentication, WarnInfo warnInfo) {//id:报卡ID
        String username = authentication.getName();
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", userService.warn(username, warnInfo));
        return map;
    }

    /**
     * 处理报卡,怀疑报卡
     *
     * @param authentication
     * @param id             报卡ID
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("susReportCard")
    @ResponseBody
    public Map handle(Authentication authentication, int id) {//id:报卡ID
        String username = authentication.getName();
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag", userService.sus(username, id));
        return map;
    }

    /**
     * 排除预警
     *
     * @param authentication
     * @param eliminateWarn  排除信息
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("eliminateWarn")
    @ResponseBody
    public Boolean EliminateWarn(Authentication authentication, EliminateWarn eliminateWarn) {//id:报卡ID
        String username = authentication.getName();
        return userService.insEliminateWarn(eliminateWarn, username);
    }

    /**
     * 预警处理
     *
     * @param authentication
     * @param warnInfo       预警信息
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("warn1")//预警处理
    @ResponseBody
    public Boolean Warn(Authentication authentication, WarnInfo warnInfo, HttpServletRequest req) {//id:报卡ID
        String username = authentication.getName();
        return userService.insWarnInfo(warnInfo, username);
    }

    /**
     * 疑似爆发处理
     *
     * @param authentication
     * @param susOutbreak    爆发信息
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("susOutbreak")//预警处理
    @ResponseBody
    public Boolean susOutbreak(Authentication authentication, SusOutbreak susOutbreak, HttpServletRequest req) {//id:报卡ID
        String username = authentication.getName();
        return userService.insSusOutbreak(susOutbreak, username);
    }

    /**
     * 查询报卡统计信息
     *
     * @param departmentNo 科室
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findReportTable")
    @ResponseBody
    public List<ReportTable> findReportTable(int departmentNo) {
        return userService.findReportTable(departmentNo);
    }


    /**
     * 查询病区概况
     *
     * @param authentication
     * @param req
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("bingqugaikuang")
    @ResponseBody
    public Map<String, Object> BingQuGaikuang(Authentication authentication, HttpServletRequest req) {
        String username = authentication.getName();
        System.out.println(username);
        User user = userService.selUsersByName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        //查询体征统计信息
        List<TiZhengInfo> allTiZheng = userService.findAllTiZheng();
        //查询生物统计信息
        List<ShengWuInfo> allshengwuinfo = userService.findAllshengwuinfo();
        //查询手术统计信息
        List<OperateInfo> alloperateinfo = userService.findAlloperateinfo();
        //查询科室各监测类型人数
        List<DepartmentBQ> allDepartmentBqType = userService.findAllDepartmentBqType();
        map.put("TiZheng", allTiZheng);
        map.put("ShengWu", allshengwuinfo);
        map.put("operation", alloperateinfo);
        map.put("departmentBQs", allDepartmentBqType);
        return map;
    }

    /**
     * 收集报卡
     *
     * @param authentication
     * @param diagnosisTypeNo 诊断类型编号
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("collectReportCard")
    @ResponseBody
    public Map collectReportCard(Authentication authentication, int diagnosisTypeNo, @RequestParam(defaultValue = "-1") int handleAction, @RequestParam(defaultValue = "0") int status,String startDate,String endDate) {
        String username = authentication.getName();
        User user = userService.selUsersByName(username);
        Map<String, Object> map = new LinkedHashMap<>();
        //根据科室查询所有报卡信息
        List<ReportCard> reportCards;
//        ,String upTime
//        Date date = new Date(upTime);
        System.out.println(startDate+":"+endDate);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date start=null;
        Date end=null;
        if(!"".equals(startDate)||!"".equals(endDate)){
            try {
                start= ft.parse(startDate);
                end= ft.parse(endDate);
            } catch (ParseException e) {
                System.out.println("日期格式错误");
            }
            System.out.println(start);
            System.out.println(end);
        }

        if (status == 1) {
            reportCards = userService.findAllReportCardByDTnoHa(diagnosisTypeNo, handleAction,start,end);
        } else {
            reportCards = userService.findAllReportCardByDTnoHas(diagnosisTypeNo, handleAction,start,end);
        }

        //查询所有诊断类型
        List<DiagnosisType> diagnosisTypes = userService.sellAllDiagnosisTypes();
        map.put("reportCards", reportCards);
        map.put("user", user);
        map.put("diagnosisTypes", diagnosisTypes);
        return map;
    }

    /**
     * 查询每个科室不同情况病人的人数
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findDepartmentToPatients")
    @ResponseBody
    public Map findDepartmentToPatients(int year, int month) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("departments", userService.findAllDepartment());
        map.put("list", userService.findDepartmentPatientS(year, month));
        return map;
    }

    /**
     * 查询不同年龄段人数
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findPatientsByAgeRange")
    @ResponseBody
    public Map findPatientsByAgeRange(int year) {
        HashMap<String, Object> map = new HashMap<>();
        if (year == 0) {
            year = 2021;
        }
        map.put("list", userService.findAgeInfo(year));
        return map;
    }

    /**
     * 根据科室名查询病人信息
     * @param departmentName
     * @param type
     * @param year
     * @param month
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findPatiensByDNameAndType")
    @ResponseBody
    public Map findPatiensByDNameAndType(String departmentName, String type,int year,int month) {
        List<Patientinfo> patiensByDNameAndType = userService.findPatiensByDNameAndType(departmentName, type,year,month);
        Map map = new HashMap<String, Object>();
        map.put("patients", patiensByDNameAndType);
        return map;
    }

    /**
     * 查询检查结果信息
     * @param year
     * @param month
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("findPatiensCheckInfo")
    @ResponseBody
    public Map findPatiensCheckInfo(String year,String month) {
        if("".equals(year)){
            year="0";
        }
        if("".equals(month)){
            month="0";
        }
        //正常情况
        List<PatientPTInfo> patientPT = userService.findPatientPT(year, month, 0);
        //不正常情况
        List<PatientPTInfo> patientPT1 = userService.findPatientPT(year, month, 1);
        //总情况
        List<PatientPTInfo> patientPT2 = userService.findPatientPT(year, month, -1);
//        List<PatientTarget> patientTargets = patientinfoService.selAllPatientTarget();
        Map map = new HashMap<String, Object>();
        map.put("normal", patientPT);
        map.put("abnormal", patientPT1);
        map.put("totals", patientPT2);
        return map;
    }

    @RequestMapping("/tongjiCount")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public Map TongjiCount(){
        Integer patientsCount = userService.findPatientsCount();
        Integer departmentCount = userService.findDepartmentCount();
        Integer doctorsCount = userService.findDoctorsCount();
        Map<String,Object> map = new HashMap<>();
        map.put("patientCount",patientsCount);
        map.put("doctorCount",doctorsCount);
        map.put("departmentCount",departmentCount);
        return map;
    }

}
