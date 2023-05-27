package cn.pzhu.dcims.service.impl;

import cn.pzhu.dcims.mapper.WarnRecordMapper;
import cn.pzhu.dcims.pojo.WarnRecord;
import cn.pzhu.dcims.service.WarnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WarnRecordServiceImpl implements WarnRecordService {
    @Autowired
    WarnRecordMapper warnRecordMapper;
    @Override
    public Integer insWarnRecord(WarnRecord warnRecord) {
        return warnRecordMapper.insWarnRecord(warnRecord);
    }

    @Override
    public List<WarnRecord> selAllWarnRecordByStatus(Integer status,Integer pageNum,Integer pageSize) {
        return warnRecordMapper.selAllWarnRecordByStatus(status,(pageNum-1)*pageSize,pageSize);
    }

    @Override
    public Integer findAllWarnRecordCountByStatus(Integer status) {
        return warnRecordMapper.selAllWarnRecordCountByStatus(status);
    }

    @Override
    public Boolean updWarnRecordStatus(Integer id) {
        return warnRecordMapper.updWarnRecordStatus(id)>0?true:false;
    }
}
