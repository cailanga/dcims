package cn.pzhu.dcims.controller;

import cn.pzhu.dcims.pojo.WarnRecord;
import cn.pzhu.dcims.service.WarnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("warnRecord")
public class WarnRecordController {
    @Autowired
    WarnRecordService warnRecordService;

    @RequestMapping("findWarnRecordListByStatus")
    public Map findWarnRecordListByStatus(Integer status,Integer pageNum,Integer pageSize){
        HashMap<String, Object> map = new HashMap<>();
        map.put("warnRecords",warnRecordService.selAllWarnRecordByStatus(status,pageNum,pageSize));
        map.put("total",warnRecordService.findAllWarnRecordCountByStatus(status));
        return map;
    }
    @RequestMapping("updWarnRecordListStatus")
    public Map updWarnRecordListStatus(Integer id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("flag",warnRecordService.updWarnRecordStatus(id));
        return map;
    }
}
