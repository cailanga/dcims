package cn.pzhu.dcims.Scheduled;

import cn.pzhu.dcims.mapper.SysScheduledCronMapper;

/**
 * 实现Runnable接口
 */
public interface ScheduledOfTask extends Runnable {

    /**
     * 定时任务方法
     */
    void execute();

    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
        SysScheduledCronMapper repository = SpringUtil.getBean(SysScheduledCronMapper.class);
        SysScheduledCron scheduledCron = repository.findByCronKey(this.getClass().getName());
        if (SysScheduledStatusEnum.DISABLED.getCode().equals(scheduledCron.getTaskStatus())) {
            // 任务是禁用状态
            return;
        }
        execute();
    }
}