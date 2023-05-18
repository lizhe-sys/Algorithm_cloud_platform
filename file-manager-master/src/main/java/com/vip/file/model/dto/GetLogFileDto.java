package com.vip.file.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 * @version 1.0
 * @create 2022/5/3 15:28
 * @desc
 */

@Data
@Accessors(chain = true)
public class GetLogFileDto {

    /**
     * 文件ID
     */
    private String id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String suffix;

    public GetLogFileDto(String id, String fileName, String suffix) {
        this.id = id;
        this.fileName = fileName;
        this.suffix = suffix;
    }
}
