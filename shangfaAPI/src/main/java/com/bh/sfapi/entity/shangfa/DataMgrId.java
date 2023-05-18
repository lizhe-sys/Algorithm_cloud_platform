package com.bh.sfapi.entity.shangfa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataMgrId {
    private Long dataMgrId;
    private Long OlddataMgrId;

    public Long getDataMgrId() {
        return dataMgrId;
    }

    public Long getOlddataMgrId() {
        return OlddataMgrId;
    }
}
