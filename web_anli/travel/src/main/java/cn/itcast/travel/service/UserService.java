package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * 操作UserDao的service层
 */
public interface UserService {
    /**
     * 注册用户
     * @return
     */
    boolean registerUser(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    boolean activeUser(String code);

    /**
     * 根据用户名密码查找用户
     * @param login_user
     * @return
     */
    User findUserByUsernameAndPassword(User login_user);
}
