package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 此为商家表的dao  和route表是一对多的关系    一个route只有一个商家
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据route对象获取到的sid来查询商家的信息
     * @param sid
     * @return
     */
    @Override
    public Seller findBySid(int sid) {
        //1.定义sql
        String sql = "select * from tab_seller where sid = ?";
        //2.执行sql
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
