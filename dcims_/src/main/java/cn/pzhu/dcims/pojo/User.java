package cn.pzhu.dcims.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户信息
 */
public class User {
    //主键id
    private int id;
    //用户名
    private String username;
    //用户密码
    private String password;
    //用户角色标识(0：未审核，1：监测人员，2：管理员，3：医生)
    private Integer role;
    //用户角色名称
    private String roleStr;
    //用户姓名
    private String name;
    //注册时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date regTime;
    //regTime对应的字符串表示
    private String regTimeStr;
    //审核通过时间
    private String checkTime;
    //checkTime对应的字符串表示
    private String checkTimeStr;
    //审核人员id
    private Integer checkID;

    public Integer getCheckID() {
        return checkID;
    }

    public void setCheckID(Integer checkID) {
        this.checkID = checkID;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", roleStr='" + roleStr + '\'' +
                ", name='" + name + '\'' +
                ", regTime=" + regTime +
                ", regTimeStr='" + regTimeStr + '\'' +
                ", checkTime=" + checkTime +
                ", checkTimeStr='" + checkTimeStr + '\'' +
                ", checkID=" + checkID +
                '}';
    }


    public Integer getRole() {
        return role;
    }


    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTimeStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(regTime);
        this.regTime = regTime;
    }

    public String getRegTimeStr() {
        return regTimeStr;
    }


    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {

        if(checkTime!=null&&!"".equals(checkTime)){
            this.checkTime = checkTime;

        }else {
            this.checkTime="暂无";
        }

    }

    public String getCheckTimeStr() {
        return checkTimeStr;
    }


    public void setRole(Integer role) {
        if(role==0){
            this.roleStr="监测人员(未审核)";
        }else if(role==1){
            this.roleStr="监测人员(已审核)";
        }else if(role==2){
            this.roleStr="管理员";
        }else{
            this.roleStr="医生";
        }
        this.role = role;
    }

    public String getRoleStr() {
        return roleStr;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
