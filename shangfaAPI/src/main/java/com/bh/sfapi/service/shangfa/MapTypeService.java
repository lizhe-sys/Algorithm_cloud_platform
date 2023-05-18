package com.bh.sfapi.service.shangfa;
import com.bh.sfapi.entity.shangfa.MapType;
import com.bh.sfapi.entity.shangfa.dto.MapDto;

import java.util.List;

public interface MapTypeService {

    void save(MapType mapType);

    List<MapDto> getMapByMapType(Integer mapType, long healthMgrId);

//    MapType getMapTypeByHealthMgrId(Integer mapType, long healthMgrId);

//    void updateMapType(Integer type, MapType mapType);

    MapDto getSingleMapByMapType(Integer type, Integer mapOrder, Long healthMgrId);


    Long getPagebyhealmgrId(Long healthMgrId);

    void add(MapType mapType);

    void updateMapType(MapType mapType);

    List<MapDto> getMapBypage(Long page);

    MapType getMapTypeByHealthMgrId(Long page,Long healthMgrId);

    List<MapType> getMapTypeListByHealthMgrId(Long p,Long healthMgrId);

    List<MapDto> getMapByhealthMgrId(Long healthMgrId);

    Long getTypeBypage(Long page, Long healthMgrId);

    void delete(Long healthMgrId);
}
