package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * 操作UserDao的service层
 */
public class UserServiceImpl implements UserService {
    //创建UserDao对象
    private UserDao dao = new UserDaoImpl();
    /**
     * 注册用户
     * @return
     */
    @Override
    public boolean registerUser(User user) {
        //1.先根据用户名来查找用户是否存在
        String username = user.getUsername();
        User user_find = dao.findByUsername(username);
        //2.判断是否找见
        if (user_find != null) {
            //找见 ，直接返回false
            return false;
        }
        //3.没找见 添加用户 设置激活码和激活状态 用于激活
        user.setStatus("N");
        //根据电脑的硬件和操作系统得到全球惟一的string
        String code = UuidUtil.getUuid();
        user.setCode(code);
        dao.register(user);
        //4.注册完毕后，给用户发一份激活邮件  邮件的内容为激活地址的servlet  并设置code为该用户的唯一标识
        String text = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'><h2>注册成功，点击激活</h2></a>";
        MailUtils.sendMail(user.getEmail(),text,"激活邮件，无需回复");

        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean activeUser(String code) {
        //1.先判断是否有code
        if (code == null) {
            return false;
        }
        //2.在根据code查找用户
        User user = dao.findByCode(code);
        //3.判断是否有该用户
        if (user == null) {
            //没有该用户
            return false;
        }
        //4.有该用户，修改激活状态
        dao.active(user);
        return true;
    }

    /**
     * 根据用户名密码查找用户
     * @param login_user
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(User login_user) {
        return dao.findByUsernameAndPassword(login_user);
    }
}
