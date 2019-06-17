package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * 操作category表的dao
 */
public interface CategoryDao {
    /**
     * 查找所有的线路分类
     * @return
     */
    List<Category> findAll();
}
