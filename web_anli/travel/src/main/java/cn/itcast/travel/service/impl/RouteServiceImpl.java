package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * 操作Route表的service
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao= new RouteDaoImpl();   //route表的dao   线路表
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();  //route_img表的dao  线路的图片表
    private SellerDao sellerDao = new SellerDaoImpl();      //seller表的dao  商家表
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();  //favorite表的dao  收藏表
    /**
     * 封装pagebean对象
     */
    @Override
    public PageBean<Route> findByPage(String _cid, String _currentPage, String _rows, String rname) {
        //1.解析数据 得到整数  并且为不正确的数据设置初始值
        int cid = 0;
        //注意：_cid的值有可能为"null" 字符串，所以要排除该字符串
        if (_cid != null && _cid.length()>0 && !"null".equals(_cid)) {
            cid = Integer.parseInt(_cid);
        }
        int currentPage = 1;
        if (_currentPage != null && _currentPage.length()>0) {
            currentPage = Integer.parseInt(_currentPage);
        }
        int rows = 5;
        if (_rows != null && _rows.length()>0) {
            rows = Integer.parseInt(_rows);
        }
        //2.调用dao层获取数据 总记录数和数据的集合 根据条件模糊查询
        int totalCount = routeDao.findAll(cid,rname);
        List<Route> list = routeDao.pageQuery(cid, currentPage, rows,rname);
        //System.out.println(list);
        //3.根据总记录数和每页显示的条数  计算总页数
        int totalPage = totalCount%rows == 0 ? totalCount/rows : (totalCount/rows)+1;
        //4.封装pagebean对象  并返回给servlet
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);
        pb.setList(list);
        return pb;
    }

    /**
     * 根据rid查找具体route的详细信息
     * @param _rid
     * @return
     */
    @Override
    public Route findOne(String _rid) {
        int rid = 0;
        if (_rid != null && _rid.length()>0) {
            rid = Integer.parseInt(_rid);
        }
        //1.查询tab_route表得到route 对象的部分信息
        Route route = routeDao.findByRid(rid);
        //2.查询route_img表得到该线路的图片 一个线路有多个图片（一对多） 所以得到的图片是集合
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        //3.将得到的图片集合设置给route对象
        route.setRouteImgList(routeImgList);
        //4.查询seller表得到该线路的商家信息  一个线路有一个商家（一对多） 所以只有一个记录
        Seller seller = sellerDao.findBySid(route.getSid());
        //5.将得到的商家信息设置给route对象
        route.setSeller(seller);
        //6.查询favorite表得到该路线用户的收藏次数
        int count = favoriteDao.userCount(rid);
        //7.将得到的该线路的收藏次数设置给route对象
        route.setCount(count);
        //8.返回route对象
        return route;
    }

    /**
     * 判断该线路是否被该用户收藏
     * @param _rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String _rid, int uid) {
        //1.解析字符串_rid
        int rid = 0;
        if (_rid != null && _rid.length()>0 && !"null".equals(_rid)){
            rid = Integer.parseInt(_rid);
        }
        //2.调用dao来查找响应的记录
        Favorite favorite = favoriteDao.findByRidAndUid(rid,uid);
        return favorite != null;
    }

    /**
     * 给登录的用户添加收藏信息
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
