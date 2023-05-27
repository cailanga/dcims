package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.Scheduled.ScheduledOfTask;
import cn.pzhu.dcims.Scheduled.SpringUtil;
import cn.pzhu.dcims.Scheduled.SysScheduledCron;
import cn.pzhu.dcims.mapper.SysScheduledCronMapper;
import cn.pzhu.dcims.pojo.User;
import cn.pzhu.dcims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 定时任务控制器
 *
 */
@Controller
@RequestMapping("/task")
public class SysScheduledCronController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SysScheduledCronMapper sysScheduledCronMapper;
    @Autowired
    UserService userService;

    @RequestMapping("taskList")
    @ResponseBody
    public Map taskList(Authentication authentication) {
        //获取登录用户姓名
        String username = authentication.getName();
        //查找当前用户完整信息
        User user = userService.selUsersByName(username);
        List<SysScheduledCron> list = sysScheduledCronMapper.findAll();
        HashMap<String, Object> map = new HashMap<>();
        map.put("tasks",list);
        map.put("user", user);
        return map;
    }

    //编辑Cron表达式
    @RequestMapping("updateTask")
    @ResponseBody
    public Map updateTask(SysScheduledCron sysScheduledCron) {
        boolean b = jobCronTesting(sysScheduledCron.getCronExpression());
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = false;
        if (!b){
            map.put("msg","cron表达式错误");
        }else {
            int index = sysScheduledCronMapper.updateCronExpression(sysScheduledCron);
            if(index>0){
                flag=true;
            }
        }
        map.put("flag",flag);
        return map;
    }

    /**
     * 执行定时任务
     */
    @ResponseBody
    @RequestMapping("runTaskCron")
    public Map runTaskCron(@RequestParam(value = "cronKey") String cronKey) throws ClassNotFoundException {
        ((ScheduledOfTask) context.getBean(Class.forName(cronKey))).execute();
        return null;
    }

    /**
     * 启用或禁用定时任务
     */
    @ResponseBody
    @RequestMapping("changeStatusTaskCron")
    public Map changeStatusTaskCron(
            @RequestParam(value = "cronKey") String cronKey,
            @RequestParam(value = "taskStatus") Integer taskStatus
    ) {
        int index = sysScheduledCronMapper.updateTaskStatusByCronKey(taskStatus, cronKey);
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = false;
        if(index>0){
            flag=true;
        }
        map.put("flag",flag);
        return map;
    }

    /**
     * 添加定时任务
     */
    @ResponseBody
    @RequestMapping("addTask")
    public Map changeStatusTaskCron(
           SysScheduledCron sysScheduledCron
    ) {
        HashMap<String, Object> map = new HashMap<>();
        boolean flag = false;
        Class<?> clazz;

        Object bean=null;
        try {
            clazz = Class.forName(sysScheduledCron.getCronKey());
            bean = context.getBean(clazz);
        }catch (Exception e){
            e.printStackTrace();
            map.put("flag",false);
            map.put("msg","添加失败，添加的定时任务类不存在");
            return map;
        }
        if(bean==null){
            flag=false;
            map.put("msg","添加失败，添加的定时任务类不存在");
        }else {
            int index = sysScheduledCronMapper.insertCron(sysScheduledCron);
            if(index>0){
                flag=true;
                try {
                    ((ScheduledOfTask) context.getBean(Class.forName(sysScheduledCron.getCronKey()))).execute();
                } catch (ClassNotFoundException e) {
                    map.put("msg","任务启动失败");
                    flag=false;
                    map.put("flag",flag);
                    return map;
                }
            }
        }
        map.put("flag",flag);
        return map;
    }

    public static boolean jobCronTesting(String cron){

        // cron表达式格式验证
        String str = "0 * * * * ?";
        String regMiao = "([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2})";
        String regFen = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2})";
        String regShi = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2})";
        String regRi = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2}|\\?|L|W|C)";
        String regYue = "\\s([0-9]{1,2}|[0-9]{1,2}\\-[0-9]{1,2}|\\*|[0-9]{1,2}\\/[0-9]{1,2}|[0-9]{1,2}\\,[0-9]{1,2}|JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)";
        String regZhou = "\\s([1-7]{1}|[1-7]{1}\\-[1-7]{1}|[1-7]{1}\\#[1-7]{1}|\\*|[1-7]{1}\\/[1-7]{1}|[1-7]{1}\\,[1-7]{1}|MON|TUES|WED|THUR|FRI|SAT|SUN|\\?|L|C)";
        String regNian = "(\\s([0-9]{4}|[0-9]{4}\\-[0-9]{4}|\\*|[0-9]{4}\\/[0-9]{4}|[0-9]{4}\\,[0-9]{4})){0,1}";
        String regEx = regMiao + regFen + regShi + regRi + regYue + regZhou + regNian;
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        System.out.println(rs);
        return rs;
    }

}
