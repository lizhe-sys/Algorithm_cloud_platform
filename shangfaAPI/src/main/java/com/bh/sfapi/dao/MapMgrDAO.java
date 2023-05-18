package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.*;
import com.bh.sfapi.entity.shangfa.dto.MapDto;
import com.bh.sfapi.entity.shangfa.firstPage.MapInfoDto;
import com.bh.sfapi.entity.shangfa.firstPage.TypeCountDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MapMgrDAO extends BaseDAO<MapMgr, String > {

    public List<MapDto> queryDataMgrList();

    List<MapDto> queryMapMgrListByModelMgrId(long modelMgrId);

    MapDto queryMapMgrById(long mapMgrId);

    void deleteMapMgr(long mapMgrId);

    List<MapDto> dimQueryMapMgr(MapMgr mapMgr);


    // 坐标轴相关操作
    List<MapX> queryXAxisByMapMgrId(Long mapMgrId);

    List<MapY> queryYAxisByMapMgrId(Long mapMgrId);

    List<MapBase> queryBaseLineByMapMgrId(Long mapMgrId);

    MapMgr getNewAddMapMgr(Long userId);

    void saveXAxis(MapX xAxis);

    void saveYAxis(MapY yAxis);

    void saveBaseLine(MapBase mapBase);

    List<MapInfoDto> MapMgrInfo();

    List<MapDto> queryDataMapMgrList();

    List<MapDto> queryModelMapMgrList();

    List<MapDto> dimQueryDataMapMgr(MapMgr mapMgr);

    List<MapDto> dimQueryModelMapMgr(MapMgr mapMgr);

    MapDto queryModelMapMgrById(long mapMgrId);

    MapDto queryDataMapMgrById(long mapMgrId);

    List<MapDto> queryMapMgrListByDataMgrId(Long dataMgrId);

    MapMgr queryMapMgrByName(String mapName);

    void updateMapMgr(MapMgr mapMgr);

    int overviewMapCount(Long equipmentUuid);

    int getMapCountByDataMgrId(Long dataMgrId);

    List<TypeCountDto> getTypeCount();

    void updateMapX(MapX mapX);

    void updateMapY(MapY mapY);

    List<DataMgr> reDataMgr(Long id);

    List<MapDto> queryMapMgrListBydataMgrId(long dataMgrId);

    List<MapMgr> queryMapMgrBydataMgrId(Long olddataMgrId);

    void updatedatamgr(Long dataMgrId);

    void updateMapBase(MapBase mapBase);

    void delMapY(long map_y_id);

    void addMapY(MapY mapY);

    Long getMap_y_Id(MapY mapY);
}
