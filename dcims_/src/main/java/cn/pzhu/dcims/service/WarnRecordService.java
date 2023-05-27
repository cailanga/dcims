package cn.pzhu.dcims.service;

import cn.pzhu.dcims.pojo.WarnRecord;

import java.util.List;

public interface WarnRecordService {
    Integer insWarnRecord(WarnRecord warnRecord);
    List<WarnRecord> selAllWarnRecordByStatus(Integer status,Integer pageNum,Integer pageSize);
    Integer findAllWarnRecordCountByStatus(Integer status);
    Boolean updWarnRecordStatus(Integer id);
}
