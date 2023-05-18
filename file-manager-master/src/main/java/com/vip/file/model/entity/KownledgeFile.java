package com.vip.file.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LEON
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("kownledge_files")
@ApiModel(value="知识相关文件对象", description="")
public class KownledgeFile extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "目标对象ID")
    private String targetId;

    @ApiModelProperty(value = "文件位置")
    private String filePath;

    @ApiModelProperty(value = "原始文件名")
    private String fileName;

    @ApiModelProperty(value = "文件后缀")
    private String suffix;

    @ApiModelProperty(value = "faultcaseId")
    private Long faultcaseId;

    @ApiModelProperty(value = "synthesisId")
    private Long synthesisId;

}
