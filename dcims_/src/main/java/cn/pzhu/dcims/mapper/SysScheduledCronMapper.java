package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.Scheduled.SysScheduledCron;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysScheduledCronMapper {
    @Select("select * from SysScheduledCron")
    List<SysScheduledCron> findAll();
    @Select("select * from SysScheduledCron where cronKey=#{cronKey}")
    SysScheduledCron findByCronKey(String cronKey);
    //更新定时任务cron表达式
    @Update(value = "update SysScheduledCron set cronExpression=#{cronExpression},taskExplain=#{taskExplain} where cronKey=#{cronKey}")
    int updateCronExpression(SysScheduledCron sysScheduledCron);
    //更新定时任务状态
    @Update(value = "update SysScheduledCron set taskStatus=#{status} where cronKey=#{cronKey}")
    int updateTaskStatusByCronKey(Integer status, String cronKey);

    @Insert("insert into SysScheduledCron values(default,#{cronKey},#{cronExpression},#{taskExplain},#{taskStatus})")
    int insertCron(SysScheduledCron sysScheduledCron);
}
