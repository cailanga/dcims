package cn.pzhu.dcims.mapper;

import cn.pzhu.dcims.pojo.WarnRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WarnRecordMapper {
    @Insert("insert into warnRecord values(default,#{warnName},#{warnContent},#{warnTime},#{count},#{warnCount},#{status})")
    Integer insWarnRecord(WarnRecord warnRecord);

    @Select("select * from warnRecord where status=#{status} limit #{start},#{end}")
    List<WarnRecord> selAllWarnRecordByStatus(Integer status,Integer start,Integer end);

    @Select("select count(*) from warnRecord where status=#{status}")
    Integer selAllWarnRecordCountByStatus(Integer status);

    @Select("select departmentNo from reportCard where id = #{id}")
    Integer selDepartNoById(Integer id);

    @Update("update warnRecord set status=1 where id=#{id}")
    Integer updWarnRecordStatus(Integer id);
}
