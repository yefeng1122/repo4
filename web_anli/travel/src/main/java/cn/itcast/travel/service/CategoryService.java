package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * 操作category表的service
 */
public interface CategoryService {
    /**
     * 查询所有线路分类
     * @return
     */
    List<Category> findAll();
}
