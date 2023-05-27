package cn.pzhu.dcims.pojo;


/**
 * 医生
 * @author cailang
 * @create 2021-02-01-10:13
 */
public class Doctor {
    //主键ID
    private Integer id;
    //医生编号
    private Integer doctorNo;
    //医生名字
    private String doctorName;
    //医生电话
    private String phone;
    //医生年龄
    private Integer age;
    //性别
    private String sex;
    //用户名
    private String username;

    @Override
    public String toString() {
        return "DoctorService{" +
                "id=" + id +
                ", doctorNo=" + doctorNo +
                ", doctorName='" + doctorName + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(Integer doctorNo) {
        this.doctorNo = doctorNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
