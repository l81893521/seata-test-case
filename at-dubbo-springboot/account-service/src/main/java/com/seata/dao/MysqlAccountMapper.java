package com.seata.dao;

import com.seata.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MysqlAccountMapper {

    /**
     * 获取账户
     * @param id
     * @return
     */
    Account get(@Param("id") int id);

    @Select("select id from account_tbl where id = #{id}")
    Account forUpdate1(@Param("id") int id);

    @Update("update account_tbl set money = money - #{money} where user_id = #{userId}")
    void debit1(@Param("userId")String userId, @Param("money")int money);
}
