package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.pojo.WarnConfiguration;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarnConfigurationMapper {
    @Select("select * from warnConfiguration")
    List<WarnConfiguration> selAllWarnConfiguration();

    @Update("update warnConfiguration set warnCount=#{warnCount} where id=#{id}")
    Integer updateWarnConfiguration(Integer warnCount,Integer id);

    @Insert("insert into warnConfiguration values(default,#{warnName},#{warnCount},#{departmentNo})")
    Integer insWarnConfiguration(WarnConfiguration warnConfiguration);

    @Delete("delete from warnConfiguration where id=#{id}")
    Integer delWarnConfigurationById(Integer id);

    @Select("select * from warnConfiguration where departmentNo=#{departmentNo}")
    WarnConfiguration selWarnConfigurationByDno(Integer departmentNo);

    @Select("select * from warnConfiguration where id=#{id}")
    WarnConfiguration selWarnConfigurationById(Integer id);
}
