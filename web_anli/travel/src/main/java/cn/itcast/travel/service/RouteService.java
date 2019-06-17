package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 操作Route表的Service
 */
public interface RouteService {
    /**
     * 封装pagebean对象
     * @param _cid
     * @param _currentPage
     * @param _rows
     * @param rname
     * @return
     */
    PageBean<Route> findByPage(String _cid, String _currentPage, String _rows, String rname);

    /**
     * 根据rid查找一个具体route的详细信息
     * @param _rid
     * @return
     */
    Route findOne(String _rid);

    /**
     * 判断该线路是否被登录的用户收藏
     * @param _rid
     * @param uid
     * @return
     */
    boolean isFavorite(String _rid, int uid);

    /**
     * 给登录的用户添加收藏信息
     * @param rid
     * @param uid
     */
    void addFavorite(String rid, int uid);
}
