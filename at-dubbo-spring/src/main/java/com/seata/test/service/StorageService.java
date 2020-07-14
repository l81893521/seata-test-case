package com.seata.test.service;
    /**
  * @author will.zjw
  * @date 2019-04-19 16:24
  */
public interface StorageService {

    /**
     * 扣除库存
     * @param commodityCode 商品编号
     * @param count         购买数量
     */
    void deduct(String commodityCode, int count);

    /**
     * 增加库存
     * @param commodityCode
     * @param count
     */
    void add(String commodityCode, int count);
}
