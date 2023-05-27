package cn.pzhu.dcims.pojo;

/**
 * 检查结果对应的病原体对象
 * @author cailang
 * @create 2021-03-04-11:57
 */
public class CheckresultPathogen {
    //病原体编号
    private int pathogenNo;
    //病原体
    private Pathogen pathogen;
    //检查结果id
    private int checkresultId;
    //检查单编号
    private String checkNo;
    //检出日期
    private String date;
    //评语
    private String content;

    @Override
    public String toString() {
        return "CheckresultPathogen{" +
                "pathogenNo=" + pathogenNo +
                ", pathogen=" + pathogen +
                ", checkresultId=" + checkresultId +
                ", checkNo=" + checkNo +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String  checkNo) {
        this.checkNo = checkNo;
    }

    public Pathogen getPathogen() {
        return pathogen;
    }

    public void setPathogen(Pathogen pathogen) {
        this.pathogen = pathogen;
    }

    public int getPathogenNo() {
        return pathogenNo;
    }

    public void setPathogenNo(int pathogenNo) {
        this.pathogenNo = pathogenNo;
    }

    public int getCheckresultId() {
        return checkresultId;
    }

    public void setCheckresultId(int checkresultId) {
        this.checkresultId = checkresultId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
