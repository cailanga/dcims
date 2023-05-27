package cn.pzhu.dcims.service;

import cn.pzhu.dcims.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * @author cailang
 * @create 2021-02-02-21:05
 */
public interface PatientinfoService {
    User findUserByUsername(String username);
    Patientinfo findPatientInfoById(int id);
    List<PatientTarget> selAllPatientTarget();
    List<Patientinfo> findAllPatientinfo(int page, int size);
    List<PatientPTarget> findPatientSituationById(int id, String year, String month);
    List<CaseList> findAllCaseListById(int id);
    //查询某人某年某月体温
    String[] findTemperaturesById(int id, String year, String month);
    List<CheckList> findCheckListById(int id);
    List<CheckList> findCheckListByIdByDco(int doctorNo);
    List<CheckResultList> findCheckResultListByNo(String checkNo);
    List<MedicalAdvice> findMedicalAdviceById(int id);
    List<MedicalAdvice> findMeicalAdviceByKeyW(String id, String keyword);
    //查询病人某年某月情况
    Map<String,String> getMaxYM(int patientId, String month, String year);
    PatientPT findPatientPT(int patientId, String month, String year);

}
