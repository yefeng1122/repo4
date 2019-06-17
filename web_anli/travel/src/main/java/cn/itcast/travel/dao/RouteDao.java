package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * 此为线路的dao
 */
public interface RouteDao {
    /**
     * 根据cid和搜索的条件查找所有的记录
     * @param cid
     * @param rname
     * @return
     */
    int findAll(int cid, String rname);

    /**
     * 根据cid和currentPage和rows和搜索的条件来获取该页记录的集合
     * @param cid
     * @param currentPage
     * @param rows
     * @param rname
     * @return
     */
    List<Route> pageQuery(int cid, int currentPage, int rows, String rname);

    /**
     * 根据id查找具体的route线路
     * @param rid
     * @return
     */
    Route findByRid(int rid);

}
