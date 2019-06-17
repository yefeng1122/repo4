package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 此为线路图片的dao  和route表是多对一的关系   一个线路有多张图片
 */
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据rid来查找该线路对应的图片
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        //1.定义sql
        String sql = "select * from tab_route_img where rid = ? ";
        //2.执行sql
        return template.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
