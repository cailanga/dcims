package cn.pzhu.dcims.pojo;

import java.util.Map;

/**
 * 科室情况
 * @author cailang
 * @create 2021-03-07-18:49
 */
public class DepartmentToSisuation {
    //科室
    private Department department;
    private int tatol;//该科室总人数
    private Map<DiagnosisType,Integer> diagnosisTypeIntegerMap;//诊断类型对应的人数
    private int operationCount;//手术人数
    private int checkCount;//送检人数
}
