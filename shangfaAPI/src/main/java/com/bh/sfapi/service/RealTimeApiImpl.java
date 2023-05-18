//package com.bh.sfapi.service;
//
//import com.bh.sfapi.dao.RealTimeDAO;
//import com.bh.sfapi.entity.RealTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @author Administrator
// * @version 1.0
// * @create 2021/12/15 16:04
// * @desc
// */
//
//@Service
//@Transactional
//public class RealTimeApiImpl implements RealTimeApi{
//
//    @Autowired
//    RealTimeDAO realTimeDAO;
//
//    @Override
//    public List<RealTime> findAll() {
//        return realTimeDAO.findAll();
//    }
//
//    @Override
//    public RealTime queryRealTimeRecord(String influxdb_database) {
//        return realTimeDAO.queryRealTimeRecord( influxdb_database );
//    }
//}