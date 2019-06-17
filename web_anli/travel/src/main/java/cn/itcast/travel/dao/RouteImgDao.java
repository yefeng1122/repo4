package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * 此为线路图片的dao  和route表是一对多的关系  一个路线可有多个图片
 */
public interface RouteImgDao {
    /**
     * 根据rid查找该线路对应的图片的集合
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);



}
