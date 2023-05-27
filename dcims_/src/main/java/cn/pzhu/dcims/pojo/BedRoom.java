package cn.pzhu.dcims.pojo;
//病房信息
public class BedRoom {
    //主键
    private int id;
    //房间号
    private String BedRoomName;
    //所属科室编号
    private String departmentNo;
    //所属科室
    private Department department;
    //床数量
    private int bedCount;

    @Override
    public String toString() {
        return "BedRoom{" +
                "id=" + id +
                ", BedRoomName='" + BedRoomName + '\'' +
                ", departmentNo='" + departmentNo + '\'' +
                ", department=" + department +
                ", bedCount=" + bedCount +
                '}';
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBedRoomName() {
        return BedRoomName;
    }

    public void setBedRoomName(String bedRoomName) {
        BedRoomName = bedRoomName;
    }

    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
