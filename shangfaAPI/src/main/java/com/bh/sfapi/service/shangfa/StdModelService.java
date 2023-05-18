package com.bh.sfapi.service.shangfa;

import com.bh.sfapi.entity.shangfa.StdModel;
import com.bh.sfapi.entity.shangfa.dto.StdModelDto;

import java.util.List;

public interface StdModelService {

    void addStdModelMgr(StdModel stdModel);

    StdModel queryNewStdModel(Long userId);

    void updateStdModel(StdModel stdModel);

    List<StdModel> getStdModelList();

    List<StdModel> dimQueryStdModel(StdModel stdModel);

    StdModel queryStdModelById(Long stdModelId);

    StdModelDto getPackStdModelById(long id);

    StdModelDto updatePackStdModel(StdModelDto stdModel , String jupyterBackupPath );

    StdModel queryStdModelByName(String stdModelName);

    void deleteStdModel(StdModel stdModel);

    List<StdModel> getSharedStdModelList(Long userId);

    List<StdModel> getStdModelListInfo();


}
