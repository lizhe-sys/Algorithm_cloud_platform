package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.HealthMgrDAO;
import com.bh.sfapi.entity.shangfa.FlyEye;
import com.bh.sfapi.entity.shangfa.HealthMgr;
import com.bh.sfapi.entity.shangfa.dto.HealthMgrDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 * @version 1.0
 * @create 2022/2/21 16:37
 * @desc
 */

@Service
@Transactional
public class HealthMgrServiceImpl implements HealthMgrService {

    @Autowired
    HealthMgrDAO healthMgrDAO;


    @Override
    public void addHealthMgr(HealthMgr healthMgr) {
        healthMgrDAO.save( healthMgr );
    }


    @Override
    public void deleteHealthMgr(String healthMgrId) {
        HealthMgr healthMgr = new HealthMgr();
        long id = Long.parseLong(healthMgrId);
        healthMgr.setHealthMgrId( id );
        healthMgr.setDeleted( 1 );
        healthMgrDAO.update( healthMgr );
    }

    @Override
    public HealthMgr getHealthMgrById(String healthMgrId) {
        HealthMgr healthMgr = healthMgrDAO.getHealthMgrById( healthMgrId );
        return healthMgr;
    }

    @Override
    public List<HealthMgrDto> getHealthMgrList() {
        return healthMgrDAO.getHealthMgrList();
    }

    @Override
    public HealthMgr getHealthMgrByModelMgrId(long modelMgrId) {
        return healthMgrDAO.getHealthMgrByModelMgrId( modelMgrId );
    }

    @Override
    public List<HealthMgrDto> dimQueryHealthMgr(HealthMgrDto healthMgrDto) {
        return healthMgrDAO.dimQueryHealthMgr( healthMgrDto );
    }

    @Override
    public void deleteHealthMgrByModelMgrId(String modelMgrId) {
        healthMgrDAO.deleteHealthMgrByModelMgrId( modelMgrId );
    }

    @Override
    public HealthMgrDto getHealthMgrDtoById(long healthMgrId) {
        return healthMgrDAO.getHealthMgrDtoById( healthMgrId );
    }

    @Override
    public HealthMgr getHealthMgrByUserId(Long userId) {
        return healthMgrDAO.getHealthMgrByUserId( userId );
    }

    @Override
    public HealthMgr getHealthMgrByName(String healthMgrName) {
        return healthMgrDAO.getHealthMgrByName( healthMgrName );
    }

    @Override
    public void changeData(Long olddataMgrId, Long dataMgrId) {
        healthMgrDAO.changeData(olddataMgrId,dataMgrId);
    }

    @Override
    public void updateHealthMgr(HealthMgr healthMgr) {
        healthMgrDAO.updateHealthMgr(healthMgr);
    }

    @Override
    public void updatemap(Long olddataMgrId, Long dataMgrId) {
        healthMgrDAO.updatemap(olddataMgrId,dataMgrId);
    }

    @Override
    public List<Long> getalldataId() {
        return healthMgrDAO.getalldataId();
            }

    @Override
    public void update(Long healthMgrId, String string,String starttime) {
        healthMgrDAO.updateheal(healthMgrId,string,starttime);
    }

    @Override
    public FlyEye find(Long healthMgrId) {
        return healthMgrDAO.find(healthMgrId);
    }

    @Override
    public void updateFlyEyeNames(FlyEye flyEye) {
        healthMgrDAO.updateFlyEyeNames(flyEye);
    }

    @Override
    public void saveFlyEyeNames(FlyEye flyEye) {
        healthMgrDAO.saveFlyEyeNames(flyEye);
    }

    @Override
    public Map<String, Object> tomap(FlyEye flyEye) {
        Map<String,Object> map=new LinkedHashMap<>();
        try {

            map = entityToMap(flyEye);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map ;
    }

    @Override
    public Long getdataMgrIdByhealthMgrId(Long healthMgrId) {
        return healthMgrDAO.getdataMgrIdByhealthMgrId(healthMgrId);
    }

    public static<T> Map<String, Object> entityToMap(T obj) throws IllegalAccessException{
        if (obj == null) {
            return null;
        }
        int i=0;
        Map<String, Object> map = new LinkedHashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (i<2){i++;continue;}
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
//    public String text(List<Double> list1){
//        PythonInterpreter pi=new PythonInterpreter();
//        pi.execfile( "C:\\Users\\user\\Desktop\\chart.py");
//        PyFunction function =pi.get("plttext",PyFunction.class);
//        List<Double> list =new ArrayList<>();
//        list=list1;
//        PyObject obj= function.__call__(new PyList(list1));
//        return (obj.toString());
//    }
//    public static String text(){
//        PythonInterpreter pi=new PythonInterpreter();
//        pi.execfile( "D:\\sys\\doc\\doc\\指标验证\\性能指标脚本\\getmapdata.py");
//        PyFunction function =pi.get("add",PyFunction.class);
//        ArrayList<Integer> list =new ArrayList<>();
//        for (int i=0;i<10;i++){
//            list.add(i);
//        }
//
//        PyObject obj= function.__call__(new PyList(list));
//        return obj.toString();
//    }
    public String text(String s){
//        Process prc;
        try{
//            System.out.println(s);
            String[] args1 = new String[]{"C:\\Users\\user\\AppData\\Local\\Programs\\Python\\Python310\\python.exe", "C:\\Users\\user\\Desktop\\getmapdata.py", s};
//            String[] args1 = new String[]{"/usr/bin/python", "/root/sf/python/getmapdata.py", s};

            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            String temp;
            while ((temp = in.readLine()) != null) {
                line=line+temp;
            }
            in.close();
            proc.waitFor();

            return line;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Process prc;
        String a = String.valueOf(3);
        List<String> list =new ArrayList<>();
        try {
            for (int i=0;i<list.size();i++) {
                String[] args1 = new String[]{"C:\\Users\\user\\AppData\\Local\\Programs\\Python\\Python310\\python.exe", "D:\\sys\\doc\\doc\\指标验证\\性能指标脚本\\getmapdata.py", a};
                prc = Runtime.getRuntime().exec(args1);// 执行py文件
                BufferedReader in = new BufferedReader(new InputStreamReader(prc.getInputStream()));
                String line = null;
                while ((line = in.readLine()) != null) {

                }

                in.close();
                prc.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

//    public static void main(String[] args) {
//        PythonInterpreter pi=new PythonInterpreter();
//        pi.execfile( "C:\\Users\\user\\Desktop\\chart.py");
//        PyFunction function =pi.get("add",PyFunction.class);
//        ArrayList<Integer> list =new ArrayList<>();
//        for (int i=0;i<10;i++){
//        list.add(i);
//        }
//        PyObject obj= function.__call__(new PyList(list));
//        System.out.println((obj.toString()));
//}
}}

