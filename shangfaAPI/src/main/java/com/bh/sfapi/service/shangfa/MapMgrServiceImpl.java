package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.dao.DataMgrDAO;
import com.bh.sfapi.dao.MapMgrDAO;
import com.bh.sfapi.entity.shangfa.*;
import com.bh.sfapi.entity.shangfa.dto.MapDto;
import com.bh.sfapi.entity.shangfa.firstPage.MapInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/1/12 14:12
 * @desc
 */

@Service
@Transactional
public class MapMgrServiceImpl implements MapMgrService {

    @Autowired
    MapMgrDAO mapMgrDAO;
    @Autowired
    FaultCaseService faultCaseService;

    @Autowired
    DataMgrDAO dataMgrDAO;

    @Transactional
    @Override
    public void save(MapDto mapDto) {

        MapMgr mapMgr = new MapMgr();
        mapMgr.setMapName( mapDto.getMapName() );
        mapMgr.setMapType( mapDto.getMapType() );
        mapMgr.setUseBase( mapDto.getUseBase() );
        mapMgr.setModelMgrId( mapDto.getModelMgrId() );
        mapMgr.setUserId( mapDto.getUserId() );
        mapMgr.setModelMapName( mapDto.getModelMapName() );
        mapMgr.setDataMgrId( mapDto.getDataMgrId() );
        mapMgr.setCreateTime( new Date() );

        mapMgrDAO.save( mapMgr );

        if( mapMgr.getMapType() != 1&&mapMgr.getMapType() != 3 ) return;
        MapMgr addMapMgr = mapMgrDAO.getNewAddMapMgr(mapMgr.getUserId());
        Long mapMgrId = addMapMgr.getMapMgrId();
        List<MapX> xAxis = mapDto.getXAxis();
        List<MapY> yAxis = mapDto.getYAxis();


        int xCount = xAxis == null ? 0 : 1;
        int yCount = yAxis == null ? 0 : yAxis.size();


        for (int i = 0; i < xCount; i++) {
            MapX mapX = xAxis.get(i);
            mapX.setMapMgrId( mapMgrId );
            mapMgrDAO.saveXAxis( mapX );
        }
        for (int i = 0; i < yCount; i++) {
            MapY mapY = yAxis.get(i);
            mapY.setMapMgrId( mapMgrId );
            mapMgrDAO.saveYAxis( mapY );
        }

        if( mapDto.getUseBase() == 1 ){
            List<MapBase> baseLine = mapDto.getBaseLine();
            int baseCount = baseLine == null ? 0 : baseLine.size();
            for (int i = 0; i < baseCount; i++) {
                MapBase mapBase = baseLine.get(i);
                mapBase.setMapMgrId( mapMgrId );
                mapMgrDAO.saveBaseLine( mapBase );
            }
        }
    }

    @Override
    public List<MapDto> queryDataMgrList() {
        List<MapDto> mapMgrs = mapMgrDAO.queryDataMgrList();
        return mapMgrs;
    }

    @Override
    public void delete(long mapMgrId) {

        // 删除图谱之前需要将与图谱关联的故障案例全部删除
        List<FaultCase> faultCases = faultCaseService.queryFaultCaseListByMapMgrId( mapMgrId );
        for (FaultCase faultcase: faultCases) {
            faultCaseService.deleteFaultCaseById( faultcase.getFaultCaseId() );
        }
        mapMgrDAO.deleteMapMgr( mapMgrId );

    }

    @Override
    public List<MapDto> queryMapMgrListByModelMgrId( long modelMgrId) {
        return mapMgrDAO.queryMapMgrListByModelMgrId(  modelMgrId );
    }

    @Override
    public MapDto queryMapMgrById(long mapMgrId) {
        return mapMgrDAO.queryMapMgrById( mapMgrId );
    }

    @Override
    public List<MapDto> dimQueryMapMgr(MapMgr mapMgr) {
        return mapMgrDAO.dimQueryMapMgr( mapMgr);
    }

    @Override
    public List<MapX> queryXAxisByMapMgrId(Long mapMgrId) {
        return mapMgrDAO.queryXAxisByMapMgrId( mapMgrId );
    }

    @Override
    public List<MapY> queryYAxisByMapMgrId(Long mapMgrId) {
        return mapMgrDAO.queryYAxisByMapMgrId( mapMgrId );
    }

    @Override
    public List<MapBase> queryBaseLineByMapMgrId(Long mapMgrId) {
        return mapMgrDAO.queryBaseLineByMapMgrId( mapMgrId );
    }

    @Override
    public List<MapInfoDto> MapMgrInfo() {
        return mapMgrDAO.MapMgrInfo();
    }

    @Override
    public List<MapDto> queryDataMapMgrList() {
        return mapMgrDAO.queryDataMapMgrList();
    }

    @Override
    public List<MapDto> queryModelMapMgrList() {
        return mapMgrDAO.queryModelMapMgrList();
    }

    @Override
    public List<MapDto> dimQueryDataMapMgr(MapMgr mapMgr) {
        return mapMgrDAO.dimQueryDataMapMgr( mapMgr );
    }

    @Override
    public List<MapDto> dimQueryModelMapMgr(MapMgr mapMgr) {
        return mapMgrDAO.dimQueryModelMapMgr( mapMgr );
    }

    @Override
    public MapDto queryModelMapMgrById(long mapMgrId) {
        return mapMgrDAO.queryModelMapMgrById( mapMgrId );
    }

    @Override
    public MapDto queryDataMapMgrById(long mapMgrId) {
        return mapMgrDAO.queryDataMapMgrById( mapMgrId );
    }

    @Override
    public List<MapDto> queryMapMgrListByDataMgrId(Long dataMgrId) {
        return mapMgrDAO.queryMapMgrListByDataMgrId( dataMgrId );
    }

    @Override
    public MapMgr queryMapMgrByName(String mapName) {
        return mapMgrDAO.queryMapMgrByName( mapName );
    }

    @Override
    public void updateMapMgr(MapMgr mapMgr) {
        mapMgrDAO.updateMapMgr( mapMgr );
    }

    @Override
    public int overviewMapCount(Long equipmentUuid) {
        return mapMgrDAO.overviewMapCount( equipmentUuid );
    }

    @Override
    public void updateMapX(MapX mapX) {
        mapMgrDAO.updateMapX(mapX);
    }

    @Override
    public void updateMapY(MapY mapY) {
        mapMgrDAO.updateMapY(mapY);
    }

    @Override
    public List<DataMgr> reDataMgr(Long id) {
        return mapMgrDAO.reDataMgr(id);
    }

    @Override
    public List<MapDto> queryMapMgrListBydataMgrId(long dataMgrId) {
        return mapMgrDAO.queryMapMgrListBydataMgrId(dataMgrId);
    }

    @Override
    public List<MapMgr> queryMapMgrBydataMgrId(Long dataMgrId) {
        return mapMgrDAO.queryMapMgrBydataMgrId(dataMgrId);
    }

    @Override
    public void updatedatamgr(Long dataMgrId) {
        mapMgrDAO.updatedatamgr(dataMgrId);
    }

    @Override
    public void updateMapBase(MapBase mapBase) {
        mapMgrDAO.updateMapBase(mapBase);
    }

    @Override
    public void delMapY(long map_y_id) {
        mapMgrDAO.delMapY(map_y_id);
    }

    @Override
    public void addMapY(MapY mapY) {
        mapMgrDAO.saveYAxis(mapY);
    }

    @Override
    public Long getMap_y_Id(MapY mapY) {
        return mapMgrDAO.getMap_y_Id(mapY);
    }

}
