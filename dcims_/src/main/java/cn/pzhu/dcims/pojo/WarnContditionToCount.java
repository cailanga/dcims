package cn.pzhu.dcims.pojo;

/**
 * 预警条件与对应人数
 * @author cailang
 * @create 2021-03-07-18:00
 */
public class WarnContditionToCount {
    //预警条件信息
    private WarnCondition warnCondition;
    //对应人数
    private int count;

    @Override
    public String toString() {
        return "WarnContditionToCount{" +
                "warnCondition=" + warnCondition +
                ", count=" + count +
                '}';
    }

    public WarnCondition getWarnCondition() {
        return warnCondition;
    }

    public void setWarnCondition(WarnCondition warnCondition) {
        this.warnCondition = warnCondition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
