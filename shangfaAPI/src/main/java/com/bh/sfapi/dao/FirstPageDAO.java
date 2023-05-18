package com.bh.sfapi.dao;


import com.bh.sfapi.entity.shangfa.firstPage.FirstPage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FirstPageDAO extends BaseDAO< FirstPage ,  String > {

    FirstPage firstPageInfo();

    void updatefirstPage(FirstPage firstPage);

}
