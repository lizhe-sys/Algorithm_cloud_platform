package com.bh.sfapi.entity.shangfa;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author LEON
 * @since 2020-06-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Files {

    /**
     * 文件ID
     */
    private String id;
    /**
     * 目标对象ID
     */
    private String target_id;
    /**
     * 文件位置
     */
    private String file_path;
    /**
     * 原始文件名
     */
    private String file_name;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 创建时间
     */
    private Date created_time;

    /**
     * datamgrId
     */
    private Long datamgr_id;


}
