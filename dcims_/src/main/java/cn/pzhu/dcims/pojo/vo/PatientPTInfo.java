package cn.pzhu.dcims.pojo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 病人与检查信息关系
 */
public class PatientPTInfo {
    private String name;
    private int count;

    @Override
    public String toString() {
        return "PatientPTInfo{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
