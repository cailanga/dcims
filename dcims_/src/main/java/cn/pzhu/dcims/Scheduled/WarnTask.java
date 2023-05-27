package cn.pzhu.dcims.Scheduled;

import cn.pzhu.dcims.mapper.*;
import cn.pzhu.dcims.pojo.Department;
import cn.pzhu.dcims.pojo.ReportCard;
import cn.pzhu.dcims.pojo.WarnConfiguration;
import cn.pzhu.dcims.pojo.WarnRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Component
public class WarnTask implements ScheduledOfTask{
    @Autowired
    WarnRecordMapper warnRecordMapper;
    @Autowired
    WarnConfigurationMapper warnConfigurationMapper;
    @Autowired
    PatientinfoMapper patientinfoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void execute() {
        System.out.println("自动预警");
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(){
            @Override
            public void run() {
                Object warnReportCard = redisTemplate.opsForList().leftPop("warnReportCard");
                if(warnReportCard!=null){
                    Integer id = (Integer) warnReportCard;
                    Integer departmentNo = warnRecordMapper.selDepartNoById(id);
                    Department department = patientinfoMapper.selDepartmentByNo(departmentNo);
                    WarnConfiguration warnConfiguration = warnConfigurationMapper.selWarnConfigurationByDno(departmentNo);
                    Integer warnCount = userMapper.selReportCardsByDnoHaCount1(departmentNo, 1, 0, 0);
                    Integer warnCount1 = warnConfiguration.getWarnCount();
                    if(warnCount>=warnCount1){
                        WarnRecord warnRecord = new WarnRecord();
                        warnRecord.setCount(warnCount);
                        warnRecord.setWarnCount(warnCount1);
                        warnRecord.setWarnContent("系统设置的"+department.getDepartmentName()+"报卡预警人数阀值为"+warnCount1+"人，而目前该科室报卡预警的人数已达"+warnCount+"人，超出预警值"+(warnCount-warnCount1)+"人,对该科室进行预警");
                        warnRecord.setWarnName(warnConfiguration.getWarnName());
                        warnRecord.setWarnTime(new Date());
                        warnRecord.setStatus(0);
                        warnRecordMapper.insWarnRecord(warnRecord);
                    }
                }
                latch.countDown();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                Object warnConfigurationChange = redisTemplate.opsForList().leftPop("warnConfigurationChange");
                if(warnConfigurationChange!=null){
                    Integer departmentNo=(Integer) warnConfigurationChange;
                    Department department = patientinfoMapper.selDepartmentByNo(departmentNo);
                    WarnConfiguration warnConfiguration = warnConfigurationMapper.selWarnConfigurationByDno(departmentNo);
                    Integer warnCount = userMapper.selReportCardsByDnoHaCount1(departmentNo, 1, 0, 0);
                    Integer warnCount1 = warnConfiguration.getWarnCount();
                    if(warnCount>=warnCount1){
                        WarnRecord warnRecord = new WarnRecord();
                        warnRecord.setCount(warnCount);
                        warnRecord.setWarnCount(warnCount1);
                        warnRecord.setWarnContent("系统设置的"+(department==null?"全部科室":department.getDepartmentName())+"报卡预警人数阀值为"+warnCount1+"人，而目前报卡预警的人数已达"+warnCount+"人，超出预警值"+(warnCount-warnCount1)+"人,对此进行预警");
                        warnRecord.setWarnName(warnConfiguration.getWarnName());
                        warnRecord.setWarnTime(new Date());
                        warnRecord.setStatus(0);
                        warnRecordMapper.insWarnRecord(warnRecord);
                    }
                }
                latch.countDown();
            }
        }.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("latch error");
        }
    }
}
