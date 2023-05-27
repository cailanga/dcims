package cn.pzhu.dcims.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 自动预警记录
 */
public class WarnRecord {
    private Integer id;
    private String warnName;
    private String warnContent;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date warnTime;
    private Integer status;
    private Integer count;//实际预警人数
    private Integer warnCount;//预警阀值人数

    @Override
    public String toString() {
        return "WarnRecord{" +
                "id=" + id +
                ", warnName='" + warnName + '\'' +
                ", warnContent='" + warnContent + '\'' +
                ", warnTime=" + warnTime +
                ", status=" + status +
                ", count=" + count +
                ", warnCount=" + warnCount +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Integer warnCount) {
        this.warnCount = warnCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public Date getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(Date warnTime) {
        this.warnTime = warnTime;
    }
}
