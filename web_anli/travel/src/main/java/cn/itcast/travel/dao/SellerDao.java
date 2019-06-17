package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * 此为商家表的dao  和route表是一对多的关系  一个线路有一个商家
 */
public interface SellerDao {
    /**
     * 根据route对象获取到的sid来查询商家
     * @param sid
     * @return
     */
    Seller findBySid(int sid);
}
