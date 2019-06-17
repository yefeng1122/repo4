package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * 操作收藏表favorite的dao  favorite表是user表和route表的中间表 user表和route表是多对多的关系
 */
public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 统计该条线路被用户收藏的次数 （收藏次数）
     * @param rid
     * @return
     */
    @Override
    public int userCount(int rid) {
        //1.定义sql
        String sql = "select count(*) from tab_favorite where rid = ?";
        //2.执行sql
        return template.queryForObject(sql, Integer.class, rid);
    }

    /**
     * 根据rid和uid查找是否有对应的用户收藏该线路
     * @return
     * @param rid
     * @param uid
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        try {
            //1.定义sql
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            //2.执行sql
            return template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 给登录的用户添加收藏信息
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        //1.定义sql
        String sql = "insert into tab_favorite values(?,?,?)";
        //2.执行sql
        template.update(sql,rid,new Date(),uid);
    }
}
