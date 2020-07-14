package com.seata.test.entity;
    /**
  * @author will.zjw
  * @date 2019-04-23 11:25
  */

import java.io.Serializable;

/**
 * 订单
 */
public class Order implements Serializable {
    /**
     * The Id.
     */
    public long id;
    /**
     * The User id.
     */
    public String userId;
    /**
     * The Commodity code.
     */
    public String commodityCode;
    /**
     * The Count.
     */
    public int count;
    /**
     * The Money.
     */
    public int money;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", count=" + count +
                ", money=" + money +
                '}';
    }
}
