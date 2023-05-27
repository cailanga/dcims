package cn.pzhu.dcims.pojo;

/**
 * 科室监测类型
 * @author cailang
 * @create 2021-03-28-19:05
 */
public class DepartmentBqType {
    //主键类型
    private int id;
    //科室编号
    private int departmentNo;
    //检测类型编号
    private int bqTypeNo;
    //对应人数
    private int count;

    @Override
    public String toString() {
        return "DepartmentBqType{" +
                "id=" + id +
                ", departmentNo=" + departmentNo +
                ", bqTypeNo=" + bqTypeNo +
                ", count=" + count +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(int departmentNo) {
        this.departmentNo = departmentNo;
    }

    public int getBqTypeNo() {
        return bqTypeNo;
    }

    public void setBqTypeNo(int bqTypeNo) {
        this.bqTypeNo = bqTypeNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
