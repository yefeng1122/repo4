package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 操作user表的dao实现类
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
            return null;
        }
    }
    /**
     * 注册用户
     * @param user
     */
    @Override
    public void register(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    /**
     * 根据激活码查找用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        try {
            //1.定义sql
            String sql = "select * from tab_user where code = ?";
            //2.执行sql
            return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * 激活用户
     * @param user
     */
    @Override
    public void active(User user) {
        //1.定义sql
        String sql = "update tab_user set status='Y' where code = ?";
        //2.执行sql
        template.update(sql,user.getCode());
    }

    /**
     * 根据用户名密码查找用户
     * @param login_user
     * @return
     */
    @Override
    public User findByUsernameAndPassword(User login_user) {
        try {
            //1.定义sql
            String sql = "select * from tab_user where username=? && password=?";
            //2.执行sql
            return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),login_user.getUsername(),login_user.getPassword());
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
