package cn.pzhu.dcims.pojo;
//病床信息
public class SickBed {
    //主键
    private Integer id;
    //病床所在病房号
    private String bedRoomName;
    //病床编号
    private Integer sickBedNo;
    //病床号对应的字符串表示
    private String sickBedStr;
    //该病床住着的病人id（若为0则没人住）
    private Integer pid;


    @Override
    public String toString() {
        return "SickBed{" +
                "id=" + id +
                ", bedRoomName='" + bedRoomName + '\'' +
                ", sickBedNo=" + sickBedNo +
                ", sickBedStr='" + sickBedStr + '\'' +
                ", pid=" + pid +
                '}';
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSickBedNo() {
        return sickBedNo;
    }

    public void setSickBedNo(Integer sickBedNo) {
        this.sickBedNo = sickBedNo;
    }

    public String getSickBedStr() {
        return sickBedStr;
    }


    public String getBedRoomName() {
        return bedRoomName;
    }

    public void setBedRoomName(String bedRoomName) {
        this.bedRoomName = bedRoomName;
        this.sickBedStr=""+this.bedRoomName+"-"+this.sickBedNo;
    }
}
