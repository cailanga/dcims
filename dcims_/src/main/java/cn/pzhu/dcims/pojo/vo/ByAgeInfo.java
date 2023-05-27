package cn.pzhu.dcims.pojo.vo;

/**
 * 不同年龄段病人人数
 */
public class ByAgeInfo {
    private String description;//年龄段
    private Integer counts;//人数

    @Override
    public String toString() {
        return "ByAgeInfo{" +
                "description='" + description + '\'' +
                ", counts=" + counts +
                '}';
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
