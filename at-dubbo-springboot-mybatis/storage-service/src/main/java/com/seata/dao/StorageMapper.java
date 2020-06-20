package com.seata.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/18
 */
@Mapper
public interface StorageMapper {

    @Update("update storage_tbl set count = count - #{count} where commodity_code = #{commodity_code}")
    void decrease(@Param("commodity_code")String commodity_code, @Param("count")int count);
}
