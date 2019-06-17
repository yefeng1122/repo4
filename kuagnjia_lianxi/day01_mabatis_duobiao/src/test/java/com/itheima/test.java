package com.itheima;

import com.itheima.domain.Order;
import com.itheima.domain.User;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class test {
    private SqlSession sqlSession;
    @Before
    public void before() throws IOException {
        InputStream is = Resources.getResourceAsStream("sqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession(true);
    }
    @Test
    public void test1() {
        //创建代理对象  查找订单的拥有者
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orders = mapper.findOrderAndUser();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
    @Test
    public void test2() {
        //创建代理对象  查找用户所拥有的订单
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findUserAndOrders();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test3() {
        //创建代理对象  根据条件查询User 动态sql的拼接
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User condition = new User();
        condition.setUsername("zhangsan");
        //condition.setPassword("123");
        List<User> users = mapper.findByCondition(condition);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
