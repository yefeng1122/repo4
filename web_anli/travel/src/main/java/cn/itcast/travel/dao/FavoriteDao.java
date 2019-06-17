package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * 操作收藏表favorite的dao  favorite表是user表和route表的中间表 user表和route表是多对多的关系
 */
public interface FavoriteDao {
    /**
     * 统计该条线路有多少用户收藏 （收藏次数）
     * @param rid
     * @return
     */
    int userCount(int rid);

    /**
     * 根据rid和uid查找收藏记录
     * @return
     * @param rid
     * @param uid
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 给登录的用户添加收藏信息
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);
}
