package cn.pzhu.dcims.service;

import cn.pzhu.dcims.pojo.Department;
import cn.pzhu.dcims.pojo.WarnConfiguration;

import java.util.List;

public interface WarnConfigurationService {
    List<WarnConfiguration> findAllWarnConfiguration();
    Boolean updateWarnConfiguration(Integer warnCount,Integer id);
    Boolean insWarnConfiguration(WarnConfiguration warnConfiguration);
    Boolean delWarnConfigurationById(Integer id);
    WarnConfiguration findWarnConfigurationByDno(Integer departmentNo);
    Department findDepartmentByDno(Integer departmentNo);
}
