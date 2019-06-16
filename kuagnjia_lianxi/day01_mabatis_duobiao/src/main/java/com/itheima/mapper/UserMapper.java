package com.itheima.mapper;

import com.itheima.domain.User;

import java.util.List;

/**
 * 此为user表的额mapper接口
 */
public interface UserMapper {
    //查找用户的所有订单
    public List<User> findUserAndOrders();

    //根据条件不确定进行查询
    public List<User> findByCondition(User condition);
}
