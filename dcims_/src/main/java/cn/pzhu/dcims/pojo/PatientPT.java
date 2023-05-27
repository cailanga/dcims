package cn.pzhu.dcims.pojo;

import java.util.Map;

/**
 * 病人诊断情况
 * @author cailang
 * @create 2021-03-03-15:43
 */

public class PatientPT {
    //主键id
    private int patientId;
    //月份
    private String month;
    //年
    private String year;
    //当前月总天数
    private int days;
    private Map<PatientTarget, PatientPTarget[]> map;//某种情况一个月信息
    private Map<PatientTarget, Integer> ptoDays;//每种情况对应天数
    private int status;//是否查询到数据

    @Override
    public String toString() {
        return "PatientPT{" +
                "patientId=" + patientId +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", days=" + days +
                ", map=" + map +
                ", ptoDays=" + ptoDays +
                ", status=" + status +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<PatientTarget, Integer> getPtoDays() {
        return ptoDays;
    }

    public void setPtoDays(Map<PatientTarget, Integer> ptoDays) {
        this.ptoDays = ptoDays;
    }

    public Map<PatientTarget, PatientPTarget[]> getMap() {
        return map;
    }

    public void setMap(Map<PatientTarget, PatientPTarget[]> map) {
        this.map = map;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String moonth) {
        this.month = moonth;
    }

}
