package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.MapType;
import com.bh.sfapi.entity.shangfa.dto.MapDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/4/8 15:23
 * @desc
 */

@Mapper
public interface MapTypeDAO extends BaseDAO< MapType , String > {

    void saveTypeFour(MapType mapType);

    void saveTypeSix(MapType mapType);

    List<MapDto> getMapByMapTypeFour(long healthMgrId);

    List<MapDto> getMapByMapTypeSix(long healthMgrId);

    MapType getMapTypeFourByHealthMgrId(long healthMgrId);

    MapType getMapTypeSixByHealthMgrId(long page,long healthMgrId);

//    Integer updateMapTypeFour(MapType mapType);



    MapDto getSingleMapByMapTypeFour(@Param("mapOrder") Integer mapOrder, @Param( "healthMgrId") Long healthMgrId);

    MapDto getSingleMapByMapTypeSix(@Param( "mapOrder") Integer mapOrder, @Param( "healthMgrId") Long healthMgrId);

    Long getPagebyhealmgrId(Long healthMgrId);

    void add(MapType mapType);

    List<MapDto> getMapBypage(Long page);

    void updateMapType(MapType mapType);

    List<MapDto> getMapByhealthMgrId(Long healthMgrId);

    Long getTypeBypage(Long page, Long healthMgrId);

    void deletemMap(Long healthMgrId);
}
