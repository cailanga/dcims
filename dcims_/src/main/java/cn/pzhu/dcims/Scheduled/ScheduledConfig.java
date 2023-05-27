package cn.pzhu.dcims.Scheduled;

import cn.pzhu.dcims.mapper.SysScheduledCronMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

/**
 * 定时任务配置类
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private SysScheduledCronMapper sysScheduledCronMapper;
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        for (SysScheduledCron sysScheduledCron : sysScheduledCronMapper.findAll()) {
            Class<?> clazz;
            Object task;
            try {
                clazz = Class.forName(sysScheduledCron.getCronKey());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("sys_task_cron表数据" + sysScheduledCron.getCronKey() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(sysScheduledCron.getCronKey() + "未纳入到spring管理", e);
            }
            Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
            // 可以通过改变数据库数据进而实现动态改变执行周期
            scheduledTaskRegistrar.addTriggerTask(((Runnable) task),
                    triggerContext -> {
                        String cronExpression = sysScheduledCronMapper.findByCronKey(sysScheduledCron.getCronKey()).getCronExpression();
                        return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
                    }
            );
        }

    }
}