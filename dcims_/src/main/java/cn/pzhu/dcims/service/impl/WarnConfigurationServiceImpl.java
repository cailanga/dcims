package cn.pzhu.dcims.service.impl;

import cn.pzhu.dcims.mapper.PatientinfoMapper;
import cn.pzhu.dcims.mapper.WarnConfigurationMapper;
import cn.pzhu.dcims.pojo.Department;
import cn.pzhu.dcims.pojo.WarnConfiguration;
import cn.pzhu.dcims.service.WarnConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarnConfigurationServiceImpl implements WarnConfigurationService {
    @Autowired
    WarnConfigurationMapper warnConfigurationMapper;
    @Autowired
    PatientinfoMapper patientinfoMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public List<WarnConfiguration> findAllWarnConfiguration() {
        return warnConfigurationMapper.selAllWarnConfiguration();
    }

    @Override
    public Boolean updateWarnConfiguration(Integer warnCount, Integer id) {
        WarnConfiguration warnConfiguration = warnConfigurationMapper.selWarnConfigurationById(id);
        redisTemplate.opsForList().rightPush("warnConfigurationChange",warnConfiguration.getDepartmentNo());
        return warnConfigurationMapper.updateWarnConfiguration(warnCount,id)>0?true:false;
    }

    @Override
    public Boolean insWarnConfiguration(WarnConfiguration warnConfiguration) {
        redisTemplate.opsForList().rightPush("warnConfigurationChange",warnConfiguration.getDepartmentNo());
        return warnConfigurationMapper.insWarnConfiguration(warnConfiguration)>0?true:false;
    }

    @Override
    public Boolean delWarnConfigurationById(Integer id) {
        return warnConfigurationMapper.delWarnConfigurationById(id)>0?true:false;
    }

    @Override
    public WarnConfiguration findWarnConfigurationByDno(Integer departmentNo) {
        return warnConfigurationMapper.selWarnConfigurationByDno(departmentNo);
    }

    @Override
    public Department findDepartmentByDno(Integer departmentNo) {
        return patientinfoMapper.selDepartmentByNo(departmentNo);
    }
}
