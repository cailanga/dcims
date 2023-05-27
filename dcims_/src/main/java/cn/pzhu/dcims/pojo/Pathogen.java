package cn.pzhu.dcims.pojo;

/**
 * 病原体
 * @author cailang
 * @create 2021-02-01-9:55
 */
public class Pathogen {
    //病原体编号
    private Integer pathogenNo;
    //病原体名称
    private String pathogenName;
    //是否多重耐药标识
    private int isMultidrug;
    //isMultidrug对应的字符串表示
    private String isMultidrugStr;

    public String getIsMultidrugStr() {
        return isMultidrugStr;
    }

    @Override
    public String toString() {
        return "Pathogen{" +
                "pathogenNo=" + pathogenNo +
                ", pathogenName='" + pathogenName + '\'' +
                ", isMultidrug=" + isMultidrug +
                '}';
    }

    public Integer getPathogenNo() {
        return pathogenNo;
    }

    public void setPathogenNo(Integer pathogenNo) {
        this.pathogenNo = pathogenNo;
    }

    public String getPathogenName() {
        return pathogenName;
    }

    public void setPathogenName(String pathogenName) {
        this.pathogenName = pathogenName;
    }

    public int getIsMultidrug() {
        return isMultidrug;
    }

    public void setIsMultidrug(int isMultidrug) {
        if(isMultidrug==0) isMultidrugStr="否";
        else isMultidrugStr="是";
        this.isMultidrug = isMultidrug;
    }
}
