package com.vip.file.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取文件信息DTO
 *
 * @author wgb
 * @date 2020/6/9 10:13
 */
@Data
@Accessors(chain = true)
public class AddFileDto {
    /**
     * 文件ID
     */
    private String id;
    /**
     * 目标对象ID
     */
    private String targetId;
    /**
     * 文件位置
     */
    private String filePath;
    /**
     * 原始文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * datamgrId该字段的值由前端设置数据模块的cookie值，datamgrId=datamgrId
     * 通过取得cookie的值，在./js/page/index.js文件中，赋值给file该参数的值，然后传到后台
     * datamgrId
     * @return
     */
    private Long datamgrId;

    /**
     * faultcase_id
     */
    private Long faultcaseId;

    /**
     * synthesis_id
     */
    private Long synthesisId;


    @Override
    public String toString() {
        return "AddFileDto{" +
                "id='" + id + '\'' +
                ", targetId='" + targetId + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", suffix='" + suffix + '\'' +
                ", datamgrId=" + datamgrId +
                ", faultcase_id=" + faultcaseId +
                ", synthesis_id=" + synthesisId +
                '}';
    }
}
