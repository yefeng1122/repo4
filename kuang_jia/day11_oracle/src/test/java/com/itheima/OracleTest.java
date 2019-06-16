package com.itheima;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;
import org.junit.Test;

import java.sql.*;

/**
 * 测试Oracle数据库的测试类
 */
public class OracleTest {
    @Test
    public void test1() throws Exception {
        //1.加载Oracle驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.192.10:1521:orcl", "itheima", "itheima");
        //3.获取Statement对象
        PreparedStatement preparedStatement = connection.prepareStatement("select * from emp where empno=?");
        //4.为占位符？赋值
        preparedStatement.setInt(1,7788);
        //5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //6.遍历结果集
        while (resultSet.next()){
            //获取一条记录中的ename 并输出
            String ename = resultSet.getString("ename");
            System.out.println(ename);
        }
        //7.释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    //jave调用执行过程 没有返回值
    /**
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储函数使用
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储过程使用
     */
    public void test2() throws Exception {
        //1.加载Oracle驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.192.10:1521:orcl", "itheima", "itheima");
        //3.获取Statement对象 CallableStatement 用于执行执行过程
        CallableStatement callableStatement = connection.prepareCall("{call p_yearsal(?, ?)}");
        //4.为占位符？赋值
        callableStatement.setInt(1,7788);
        //5.设置out类型数据 的数据类型
        callableStatement.registerOutParameter(2, OracleTypes.NUMBER);
        //5.执行sql  执行过程没有返回值
        callableStatement.execute();
        //6.输出out类型的参数
        System.out.println(callableStatement.getObject(2));
        //7.释放资源
        callableStatement.close();
        connection.close();
    }

    @Test
    //jave调用执行函数  有返回值
    /**
     * {?= call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储函数使用
     * {call <procedure-name>[(<arg1>,<arg2>, ...)]}   调用存储过程使用
     */
    public void test3() throws Exception {
        //1.加载Oracle驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接对象
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.192.10:1521:orcl", "itheima", "itheima");
        //3.获取Statement对象 CallableStatement 用于执行执行过程
        CallableStatement callableStatement = connection.prepareCall("{? = call f_yearsal(?)}");
        //4.为占位符？赋值  注意第一个？是返回值
        callableStatement.setInt(2,7788);
        //5.设置out类型数据 的数据类型
        callableStatement.registerOutParameter(1, OracleTypes.NUMBER);
        //5.执行sql  执行函数有返回值
        callableStatement.execute();
        //6.输出out类型的参数
        System.out.println(callableStatement.getObject(1));
        //7.释放资源
        callableStatement.close();
        connection.close();
    }
}
