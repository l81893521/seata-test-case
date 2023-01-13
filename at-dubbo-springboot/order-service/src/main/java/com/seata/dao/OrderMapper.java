package com.seata.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

/**
 * @author: jiawei.zhang
 * @date: 2020/6/19
 */
@Mapper
public interface OrderMapper {

    @Insert("insert into order_tbl(user_id, commodity_code, count, money) values (#{userId}, #{commodity_code}, #{count}, #{money})")
    void save(@Param("userId") String userId, @Param("commodity_code")String commodity_code, @Param("count")int count, @Param("money")BigDecimal money);
}
