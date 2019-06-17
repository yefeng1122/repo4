package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 操作category表Service的实现类
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    /**
     * 查询所有线路分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //1.先查询缓存，缓存没有再查询数据库，提高效率，减轻服务器的压力
        Jedis jedis = JedisUtil.getJedis();
        //2.按顺序查询 ，redis中值的类型为sortedset，便于排序  将每一行数据包装成Tuple对象（包含score和value）
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //3.判断缓存中有无数据
        List<Category> list = new ArrayList<Category>();
        if (categorys == null || categorys.size() == 0) {  //一定要判断集合的大小是否为0，因为redis没获取到，集合的长度是0
            //没有数据，查询数据库
            //System.out.println("第一次访问，查询数据库。。。");
            list = dao.findAll();
            //将查询到结果返回后，并保存缓存中  按id的顺序保存
            for (Category category : list) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        }else{
            //4.有数据，返回数据  由于返回值是List集合，应该将set中的数据转换到list集合中
            for (Tuple tuple : categorys) {
                //创建category对象并为之赋值
                Category category = new Category();
                //需要将double类型强转成int
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                //将category对象添加到list集合
                list.add(category);
            }
        }
        //5.返回list集合
        return list;
    }
}
