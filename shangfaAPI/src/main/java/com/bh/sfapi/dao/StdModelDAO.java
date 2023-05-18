package com.bh.sfapi.dao;

import com.bh.sfapi.entity.shangfa.StdModel;
import com.bh.sfapi.entity.shangfa.dto.StdModelDto;
import com.bh.sfapi.entity.shangfa.firstPage.StdModelInfo;
import com.bh.sfapi.entity.shangfa.firstPage.TypeCountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface StdModelDAO extends BaseDAO<StdModel, String > {


    void addStdModelMgr(StdModel stdModel);

    StdModel queryNewStdModel(Long userId);

    void updateStdModel(StdModel stdModel);

    List<StdModel> getStdModelList();

    List<StdModel> dimQueryStdModel(StdModel stdModel);

    StdModel queryStdModelById(Long stdModelId);

    StdModelDto getPackStdModelById(long id);

    void updatePackStdModel(StdModelDto stdModel);

    StdModel queryStdModelByName(String stdModelName);

    List<StdModel> getSharedStdModelList(Long userId);

    List<StdModel> getStdModelListInfo();

    List<TypeCountDto> getTypeCount();

    List<StdModelInfo> firstPageStdModelInfo(@Param("year") int year , @Param("type") int type );

}
