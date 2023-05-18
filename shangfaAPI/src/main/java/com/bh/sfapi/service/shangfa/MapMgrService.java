package com.bh.sfapi.service.shangfa;


import com.bh.sfapi.entity.shangfa.*;
import com.bh.sfapi.entity.shangfa.dto.MapDto;
import com.bh.sfapi.entity.shangfa.firstPage.MapInfoDto;

import java.util.List;

public interface MapMgrService {


    public void save(MapDto mapMgr);

    public List<MapDto> queryDataMgrList();

    public void delete( long mapMgrId );

    List<MapDto> queryMapMgrListByModelMgrId( long modelMgrId );

    MapDto queryMapMgrById(long mapMgrId);

    List<MapDto> dimQueryMapMgr(MapMgr mapMgr);

    List<MapX> queryXAxisByMapMgrId(Long mapMgrId);



    List<MapY> queryYAxisByMapMgrId(Long mapMgrId);

    List<MapBase> queryBaseLineByMapMgrId(Long mapMgrId);

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
