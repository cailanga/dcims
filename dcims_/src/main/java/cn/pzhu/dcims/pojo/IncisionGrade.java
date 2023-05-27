package cn.pzhu.dcims.pojo;

/**
 * 切口信息
 * @author cailang
 * @create 2021-03-04-22:16
 */
public class IncisionGrade {
    //主键id
    private int id;
    //切口等级
    private int incisionGradeNo;

    @Override
    public String toString() {
        return "IncisionGrade{" +
                "id=" + id +
                ", incisionGradeNo=" + incisionGradeNo +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncisionGradeNo() {
        return incisionGradeNo;
    }

    public void setIncisionGradeNo(int incisionGradeNo) {
        this.incisionGradeNo = incisionGradeNo;
    }
}
