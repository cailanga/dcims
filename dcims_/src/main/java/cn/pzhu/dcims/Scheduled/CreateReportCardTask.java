package cn.pzhu.dcims.Scheduled;

import cn.pzhu.dcims.mapper.DoctorMapper;
import cn.pzhu.dcims.mapper.UserMapper;
import cn.pzhu.dcims.pojo.ReportCard;
import cn.pzhu.dcims.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 创建定时任务
 */
@Component
//@Slf4j
public class CreateReportCardTask implements ScheduledOfTask {
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    DoctorService doctorService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void execute() {
        System.out.println("自动监测报卡生成");
        CountDownLatch latch = new CountDownLatch(5);
        new Thread(){
            @Override
            public void run() {
                Object inspTarget = redisTemplate.opsForList().leftPop("INSPTarget");
                if(inspTarget!=null){
                    int pid = (Integer)inspTarget;
                    ReportCard reportCard = doctorService.selReportCard(pid);
                    reportCard.setWarnStatus(1);
                    reportCard.setPatientId(pid);
                    //上报时间
                    String upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    reportCard.setUpTime(upTime);
                    //系统自动提交医生编号 ： 1
                    reportCard.setDoctorNo(1);
                    reportCard.setCourseAnalysis("有感染相关");
                    reportCard.setCstatus(0);
                    //设置诊断类型
                    Integer diagnosisTypeNo = doctorMapper.selDnoByPid(pid);
                    reportCard.setDiagnosisTypeNo(diagnosisTypeNo);
                    //设置科室
                    Integer departmentNo = doctorMapper.selDepartNoByPid(pid);
                    reportCard.setDepartmentNo(departmentNo);
                    doctorMapper.insReportCard(reportCard);
                }
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Object insOperation = redisTemplate.opsForList().leftPop("INSOperation");
                if(insOperation!=null){
                    int pid = (Integer)insOperation;
                    ReportCard reportCard = doctorService.selReportCard(pid);
                    reportCard.setWarnStatus(1);
                    reportCard.setPatientId(pid);
                    //上报时间
                    String upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    reportCard.setUpTime(upTime);
                    //系统自动提交医生编号 ： 1
                    reportCard.setDoctorNo(1);
                    reportCard.setCourseAnalysis("有感染相关");
                    reportCard.setCstatus(0);
                    //设置诊断类型
                    Integer diagnosisTypeNo = doctorMapper.selDnoByPid(pid);
                    reportCard.setDiagnosisTypeNo(diagnosisTypeNo);
                    //设置科室
                    Integer departmentNo = doctorMapper.selDepartNoByPid(pid);
                    reportCard.setDepartmentNo(departmentNo);
                    doctorMapper.insReportCard(reportCard);
                }
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Object INSCheckResult = redisTemplate.opsForList().leftPop("INSCheckResult");
                if(INSCheckResult!=null){
                    int pid = (Integer)INSCheckResult;
                    ReportCard reportCard = doctorService.selReportCard(pid);
                    reportCard.setWarnStatus(1);
                    reportCard.setPatientId(pid);
                    //上报时间
                    String upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    reportCard.setUpTime(upTime);
                    //系统自动提交医生编号 ： 1
                    reportCard.setDoctorNo(1);
                    reportCard.setCourseAnalysis("有感染相关");
                    reportCard.setCstatus(0);
                    //设置诊断类型
                    Integer diagnosisTypeNo = doctorMapper.selDnoByPid(pid);
                    reportCard.setDiagnosisTypeNo(diagnosisTypeNo);
                    //设置科室
                    Integer departmentNo = doctorMapper.selDepartNoByPid(pid);
                    reportCard.setDepartmentNo(departmentNo);
                    doctorMapper.insReportCard(reportCard);
                }
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Object INSCheckResultToP = redisTemplate.opsForList().leftPop("INSCheckResultToP");
                if(INSCheckResultToP!=null){
                    int pid = (Integer)INSCheckResultToP;
                    ReportCard reportCard = doctorService.selReportCard(pid);
                    reportCard.setWarnStatus(1);
                    reportCard.setPatientId(pid);
                    //上报时间
                    String upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    reportCard.setUpTime(upTime);
                    //系统自动提交医生编号 ： 1
                    reportCard.setDoctorNo(1);
                    reportCard.setCourseAnalysis("有感染相关");
                    reportCard.setCstatus(0);
                    //设置诊断类型
                    Integer diagnosisTypeNo = doctorMapper.selDnoByPid(pid);
                    reportCard.setDiagnosisTypeNo(diagnosisTypeNo);
                    //设置科室
                    Integer departmentNo = doctorMapper.selDepartNoByPid(pid);
                    reportCard.setDepartmentNo(departmentNo);
                    doctorMapper.insReportCard(reportCard);
                }
                latch.countDown();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                Object INSCheckResultToMedical = redisTemplate.opsForList().leftPop("INSCheckResultToMedical");
                if(INSCheckResultToMedical!=null){
                    int pid = (Integer)INSCheckResultToMedical;
                    ReportCard reportCard = doctorService.selReportCard(pid);
                    reportCard.setWarnStatus(1);
                    reportCard.setPatientId(pid);
                    //上报时间
                    String upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    reportCard.setUpTime(upTime);
                    //系统自动提交医生编号 ： 1
                    reportCard.setDoctorNo(1);
                    reportCard.setCourseAnalysis("有感染相关");
                    reportCard.setCstatus(0);
                    //设置诊断类型
                    Integer diagnosisTypeNo = doctorMapper.selDnoByPid(pid);
                    reportCard.setDiagnosisTypeNo(diagnosisTypeNo);
                    //设置科室
                    Integer departmentNo = doctorMapper.selDepartNoByPid(pid);
                    reportCard.setDepartmentNo(departmentNo);
                    doctorMapper.insReportCard(reportCard);
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