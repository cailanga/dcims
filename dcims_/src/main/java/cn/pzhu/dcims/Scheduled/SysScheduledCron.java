package cn.pzhu.dcims.Scheduled;


import org.springframework.data.annotation.Id;

public class SysScheduledCron {

    @Id
    private Integer cronId;//主键

    private String cronKey;//定时任务完整类名

    private String cronExpression;//cron表达式

    private String taskExplain;//任务描述

    private Integer taskStatus;//状态: {1:正常,2:停用}

    public Integer getCronId() {
        return cronId;
    }

    public void setCronId(Integer cronId) {
        this.cronId = cronId;
    }

    public String getCronKey() {
        return cronKey;
    }

    public void setCronKey(String cronKey) {
        this.cronKey = cronKey;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTaskExplain() {
        return taskExplain;
    }

    public void setTaskExplain(String taskExplain) {
        this.taskExplain = taskExplain;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
}
