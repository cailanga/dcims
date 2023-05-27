package com.pzhu.dcims;

import cn.pzhu.dcims.DcimsApplication;
import cn.pzhu.dcims.mapper.DoctorMapper;
import cn.pzhu.dcims.mapper.UserMapper;
import cn.pzhu.dcims.pojo.Operation;
import cn.pzhu.dcims.service.PatientinfoService;
import cn.pzhu.dcims.service.UserService;
import cn.pzhu.dcims.service.impl.Test1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;

@SpringBootTest(classes = DcimsApplication.class)
class DcimsApplicationTests {

    @Autowired
    UserService userService;
    @Autowired
    PatientinfoService patientinfoService;
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ApplicationContext context;
    @Test
    void contextLoads() {
        //数据初始化
        Map maps[]=new HashMap[2];
        Map map = new HashMap<>();
        Map map1 = new HashMap<>();
        maps[0]=map;
        maps[1]=map1;
        map.put(int.class,25);
        map1.put(String.class,"test");
        //方法参数类型
        Class[] clazz = new Class[maps.length];
        Object []params=new Object[maps.length];

        int i=0;
        for (Map map2 : maps) {
            clazz[i]=(Class) map2.keySet().stream().findFirst().get();
            params[i]=map2.values().stream().findFirst().get();
            i++;
        }
        Object bean = context.getBean("test1");
        Test1 test=(Test1)bean;
        try {
            Method method = test.getClass().getMethod("test", clazz);
            Object invoke = method.invoke(new Test1(), params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws ParseException {
        /*List<TargetMonitor> targetMonitor = userService.findTargetMonitor("2021-3-1", "2021-4-1");
        for(TargetMonitor targetMonitor1:targetMonitor){
            System.out.println(targetMonitor1);
        }*/
        /*List<CaseList> allCaseListById = patientinfoService.findAllCaseListById(1);
        for (CaseList cs:allCaseListById
             ) {
            System.out.println(cs);
        }*/
        /*
        System.out.println(parse);*/
        List<Operation> operations = doctorMapper.selOperationsByDno(2021100000);
        for (Operation operation : operations) {
            System.out.println(operation);
        }
    }

    @Test
    public void test1(){

        Scanner scanner=new Scanner(System.in);
        String str = scanner.next();
        String[] strs = str.split(" ");
        int M = scanner.nextInt();
        int count=0;
        for(int i=0;i<strs.length-1;i++){
            int ii=Integer.parseInt(strs[i]);
            for(int j=i+1;j<strs.length;j++){
                int jj=Integer.parseInt(strs[j]);
                if(ii+jj<=M){
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    @Test
    public void test2(){
       /* Map<String, Object> stringObjectMap = userMapper.selectById1(1);
        System.out.println(stringObjectMap);
        User user = JSON.parseObject(JSON.toJSONString(stringObjectMap), User.class);
        System.out.println(user);*/
        Integer integer = doctorMapper.selIsMultiMedical(1);
        System.out.println(integer);
    }

    @Test
    public void test3(){
//给定一个正整数n（1≤n≤9）, 输出1到n的所有排列 输入 3 输出 123, 132, 213, 231, 312, 321
        int n=0;
        String str="";
        for (int i = 0; i < n; i++) {
            str+=i;
        }
        for (int i = 0; i < str.length(); i++) {

        }
    }
    @Test
    public void test4(){
        String str="sf243hghjasdiijhgtfcgfgfyuhyuidg";
        String testStr="yu";
        int count=0;
        int index=0;
        while(index!=-1){
            index = str.indexOf(testStr,index);
            if(index!=-1){
                count++;
                index+=2;
            }
        }
        System.out.println(count);
    }

}
