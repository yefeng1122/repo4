package com.itheima.mapper;

import com.itheima.domain.Order;

import java.util.List;

/**
 * 此为order表的mapper
 */
public interface OrderMapper {
//    站在订单的角度，一个订单只有一个用户 一对一的关系
    //查找订单的拥有者
    public List<Order> findOrderAndUser();
}
