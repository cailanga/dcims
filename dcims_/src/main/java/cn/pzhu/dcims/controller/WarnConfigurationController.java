package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.pojo.Department;
import cn.pzhu.dcims.pojo.User;
import cn.pzhu.dcims.pojo.WarnConfiguration;
import cn.pzhu.dcims.service.PatientinfoService;
import cn.pzhu.dcims.service.UserService;
import cn.pzhu.dcims.service.WarnConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warn")
public class WarnConfigurationController {
    @Autowired
    WarnConfigurationService warnConfigurationService;
    @Autowired
    UserService userService;

    @RequestMapping("warnList")
    public Map getAllWarnConfigurations(Authentication authentication){
        //获取登录用户姓名
        String username = authentication.getName();
        //查找当前用户完整信息
        User user = userService.selUsersByName(username);
        HashMap<String, Object> map = new HashMap<>();
        map.put("warns",warnConfigurationService.findAllWarnConfiguration());
        map.put("user", user);
        return map;
    }

    @RequestMapping("addWarn")
    public Map addWarn(WarnConfiguration warnConfiguration){
        HashMap<String, Object> map = new HashMap<>();
        WarnConfiguration warnConfigurationByDno = warnConfigurationService.findWarnConfigurationByDno(warnConfiguration.getDepartmentNo());
        if (warnConfigurationByDno!=null){
            map.put("msg","该科室预警信息已设置");
            return map;
        }
        if(warnConfiguration.getDepartmentNo()!=0){
            Department departmentByDno = warnConfigurationService.findDepartmentByDno(warnConfiguration.getDepartmentNo());
            warnConfiguration.setWarnName(departmentByDno.getDepartmentName()+"预警");

        }else {
            warnConfiguration.setWarnName("所有科室预警");
        }
        map.put("flag",warnConfigurationService.insWarnConfiguration(warnConfiguration));
        return map;
    }

    @RequestMapping("updateWarn")
    public Map updateWarn(Integer warnCount,Integer id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",warnConfigurationService.updateWarnConfiguration(warnCount,id));
        return map;
    }

    @RequestMapping("delWarn")
    public Map delWarn(Integer id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",warnConfigurationService.delWarnConfigurationById(id));
        return map;
    }

    @RequestMapping("getAllDepartments")
    public Map getAllDepartments(){
        HashMap<String, Object> map = new HashMap<>();
        List<Department> departments = userService.findAllDepartment();
        map.put("departments",departments);
        return map;
    }
}
