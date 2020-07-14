package com.seata.test.service;
    /**
  * @author will.zjw
  * @date 2019-10-14 19:51
  */
public interface KeywordService {

    /**
     * 保存记录(表明为关键字)
     * @param name
     */
    void insertWithKeywordTableName(String name);
}
