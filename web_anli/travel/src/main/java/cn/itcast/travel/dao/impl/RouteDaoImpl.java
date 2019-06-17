package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 此为操作route表的dao实现类
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据路分类的cid和搜索的条件来获取总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findAll(int cid, String rname) {
        //1.定义sql模板  动态sql 因为查询的条件是变化的  根据搜索的条件进行模糊查询
        String sql = "select count(*) from tab_route where 1=1 ";
        //2.定义StringBuilder
        StringBuilder sb = new StringBuilder(sql);
        //3.定义参数的集合 ，用于可变参的出入 给问号赋值
        List<Object> parma = new ArrayList<Object>();
        //4.拼接sql
        if (cid != 0) {
            //拼接cid并往集合中添加对应的参数
            sb.append(" and cid=? ");
            parma.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {  //注意rname有可能为"null" 所以要排除
            //拼接rname并往集合中添加对应的参数  注意：模糊查询要加%
            sb.append(" and rname like ? ");
            parma.add("%"+rname+"%");
        }
        //5.将StringBuilder转为String
        sql = sb.toString();
        //6.执行sql   注意：要将参数的集合转为数组也 就是可变参 ？问号的值
        return template.queryForObject(sql,Integer.class,parma.toArray());
    }

    /**
     * 根据搜索的条件分页查询线路数据的集合
     * @param cid
     * @param currentPage
     * @param rows
     * @param rname
     * @return
     */
    @Override
    public List<Route> pageQuery(int cid, int currentPage, int rows, String rname) {
        //1.定义sql模板  动态sql 因为查询的条件是变化的   根据搜索的条件进行模糊查询
        String sql = "select * from tab_route where 1=1 ";
        //2.定义StringBuilder
        StringBuilder sb = new StringBuilder(sql);
        //3.定义参数的集合 ，用于可变参的出入 给问号赋值
        List<Object> parma = new ArrayList<Object>();
        //4.拼接sql
        if (cid != 0) {
            //拼接cid并往集合中添加对应的参数
            sb.append(" and cid=? ");
            parma.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            //拼接rname并往集合中添加对应的参数  注意：模糊查询要加%
            sb.append(" and rname like ? ");
            parma.add("%"+rname+"%");
        }
        sb.append(" limit ?,? ");
        int start = (currentPage-1)*rows;  //开始的索引
        parma.add(start);
        parma.add(rows);
        //5.将StringBuilder转为String
        sql = sb.toString();
        //6.执行sql   注意：要将参数的集合转为数组也 就是可变参 ？问号的值
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),parma.toArray());
    }

    /**
     *根据id查询一个具体的route
     * @param rid
     * @return
     */
    @Override
    public Route findByRid(int rid) {
        try {
            //1.定义sql
            String sql = "select * from tab_route where rid=?";
            //2.执行sql
            return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
        } catch (DataAccessException e) {
           return null;
        }
    }
}
