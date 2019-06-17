package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * 操作user表的dao
 */
public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 注册用户
     * @param user
     */
    void register(User user);

    /**
     * 根据激活码查找用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 激活用户
     * @param user
     */
    void active(User user);

    /**
     * 根据用户名密码查找用户
     * @param login_user
     * @return
     */
    User findByUsernameAndPassword(User login_user);
}
