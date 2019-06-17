package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * 操作category表dao的实现类
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查询所有的路线分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        //1.定义sql
        String sql = "select * from tab_category";
        //2.执行sql
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
