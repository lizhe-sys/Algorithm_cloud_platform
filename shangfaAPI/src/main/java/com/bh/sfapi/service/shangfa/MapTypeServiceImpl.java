package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.dao.MapTypeDAO;
import com.bh.sfapi.entity.shangfa.MapType;
import com.bh.sfapi.entity.shangfa.dto.MapDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MapTypeServiceImpl implements MapTypeService {

    @Autowired
    MapTypeDAO mapTyeDAO;


    @Override
    public void save(MapType mapType) {

        mapTyeDAO.saveTypeSix( mapType );
    }

    @Override
    public List<MapDto> getMapByMapType(Integer mapType, long healthMgrId) {
        List<MapDto> mapMgrs = new ArrayList<>();
        if( mapType == 1 ){
            mapMgrs = mapTyeDAO.getMapByMapTypeFour( healthMgrId );
        }
        else {
            mapMgrs = mapTyeDAO.getMapByMapTypeSix( healthMgrId );
        }
        return mapMgrs;
    }

//    @Override
//    public MapType getMapTypeByHealthMgrId(Integer mapType, long healthMgrId) {
//        MapType type = new MapType();
//        if( mapType == 1 ){
//            type = mapTyeDAO.getMapTypeFourByHealthMgrId( healthMgrId );
//        }
//        else {
//            type = mapTyeDAO.getMapTypeSixByHealthMgrId( healthMgrId );
//        }
//        return type;
//    }
        @Override
        public MapType getMapTypeByHealthMgrId(Long page, Long healthMgrId) {

            return mapTyeDAO.getMapTypeSixByHealthMgrId(page, healthMgrId );
        }

        @Override
        public List<MapType> getMapTypeListByHealthMgrId(Long page,Long healthMgrId){
//            System.out.println(58);
            List<MapType> mapTypeList=new ArrayList<MapType>();
            for(int i=1;i<=page;i++){
                mapTypeList.add(mapTyeDAO.getMapTypeSixByHealthMgrId(i,healthMgrId));

            }
            return mapTypeList;
        }

    @Override
    public List<MapDto> getMapByhealthMgrId(Long healthMgrId) {
        return mapTyeDAO.getMapByhealthMgrId(healthMgrId);
    }

    @Override
    public Long getTypeBypage(Long page, Long healthMgrId) {
        return mapTyeDAO.getTypeBypage(page,healthMgrId);
    }

    @Override
    public void delete(Long healthMgrId) {
        mapTyeDAO.deletemMap(healthMgrId);
    }

//    @Override
//    public void updateMapType(Integer type, MapType mapType) {
//        if( type == 1 ){
//            type = mapTyeDAO.updateMapTypeFour( mapType );
//        }
//        else {
//            type = mapTyeDAO.updateMapTypeSix( mapType );
//        }
//    }

    @Override
    public MapDto getSingleMapByMapType(Integer type, Integer mapOrder, Long healthMgrId) {
        MapDto mapDto =  new MapDto();
        if( type == 1 ){
            mapDto = mapTyeDAO.getSingleMapByMapTypeFour( mapOrder , healthMgrId );
        }
        else {
            mapDto = mapTyeDAO.getSingleMapByMapTypeSix( mapOrder , healthMgrId );
        }
        return mapDto;
    }

    @Override
    public Long getPagebyhealmgrId(Long healthMgrId) {
        return mapTyeDAO.getPagebyhealmgrId(healthMgrId);
    }

    @Override
    public void add(MapType mapType) {
        mapTyeDAO.add(mapType);
    }

    @Override
    public void updateMapType(MapType mapType) {
        System.out.println("ll来了");
        mapTyeDAO.updateMapType( mapType );
        System.out.println("来了哦");
    }

    @Override
    public List<MapDto> getMapBypage(Long page) {
        return mapTyeDAO.getMapBypage(page);
    }




}



