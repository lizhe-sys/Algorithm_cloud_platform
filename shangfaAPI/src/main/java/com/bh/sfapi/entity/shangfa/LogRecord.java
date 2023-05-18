package com.bh.sfapi.entity.shangfa;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/11 12:30
 * @desc
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogRecord {

    @ApiModelProperty(value = "logId，日志记录Id",required = false )
    private Long logId;
    @ApiModelProperty(value = "userId，用户Id",required = true )
    private Long userId;
    @ApiModelProperty(value = "moduleName",required = true )
    private String moduleName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "createTime，创建时间",required = false )
    private Date createTime;
    @ApiModelProperty(value = "operateDesc，操作描述",required = true )
    private String operateDesc;

    private String userName;

    @Override
    public String toString() {
        return "LogRecord{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", moduleName='" + moduleName + '\'' +
                ", createTime=" + createTime +
                ", operateDesc='" + operateDesc + '\'' +
                '}';
    }

}
