package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 此servlet为操作user表的servlet，包含了多种功能  减少了servlet的数量
 * 继承自BaseServlet，间接继承HTTPServlet，浏览器请求服务器后，找service方法提供服务
 * 为了不使用暴力反射，将方法的权限修饰符改为public
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //tomcat8以上get请求的url自动转码  tomcat不会  所以要设置tomcat插件的编码 <uriEncoding>utf-8</uriEncoding>
        //request.setCharacterEncoding("utf-8");  此处设置只是设置请求体的编码，不会设置url的编码，所以不会解决乱码问题
        //1.接收验证码
        String check = request.getParameter("check");
        //2.获取session中的验证码 并移除
        HttpSession session = request.getSession();
        String checkcode_session = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //3.判断验证码是否正确
        if (checkcode_session != null && !checkcode_session.equalsIgnoreCase(check)){
            //验证码不正确
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码不正确，请输入正确的验证码！");
            //将结果对象变成json字符串
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(resultInfo);
            //设置响应头并将其返回  用json格式解析
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            return;
        }
        //4.获取表单的参数并将其封装成User对象
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //System.out.println(user);
        //5.调用service层 注册用户
        //UserService service = new UserServiceImpl();
        boolean flag = service.registerUser(user);
        //6.判断用户是否注册成功
        ResultInfo resultInfo = new ResultInfo();
        if (flag){
            //注册成功
            resultInfo.setFlag(true);
        }else {
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名已存在，请选择其他的用户名！");
        }
        //7.将结果的json字符串返回给浏览器
//        ObjectMapper mapper = new ObjectMapper();
//        String s = mapper.writeValueAsString(resultInfo);
        //System.out.println(s);
        //设置响应头并将其返回  用json格式解析
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(s);
        writeValue(response,resultInfo);
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String code = request.getParameter("code");
        //2.调用service层激活用户
        //UserService service = new UserServiceImpl();
        boolean flag = service.activeUser(code);
        //3.判断是否激活成功
        response.setContentType("text/html;charset=utf-8");
        if (flag){
            //激活成功  跳转到登录页面
            response.getWriter().write("<h2>激活成功，点击<a href='/travel/login.html'>登录</a></h2>");
        }else {
            //激活失败
            response.getWriter().write("<h2>激活失败，请联系管理员！</h2>");
        }
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收验证码
        String check = request.getParameter("check");
        //2.获取session中的验证码 并移除
        HttpSession session = request.getSession();
        String checkcode_session = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //3.判断验证码是否正确
        if (checkcode_session != null && !checkcode_session.equalsIgnoreCase(check)){
            //验证码不正确
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码不正确，请输入正确的验证码！");
            //将结果对象变成json字符串
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(resultInfo);
            //设置响应头并将其返回  用json格式解析
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            //System.out.println(s);
            return;
        }
        //4.获取登录的参数 并封装成对象
        Map<String, String[]> map = request.getParameterMap();
        User login_user = new User();
        try {
            BeanUtils.populate(login_user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //5.调用service层来查询用户
        //UserService service = new UserServiceImpl();
        User user = service.findUserByUsernameAndPassword(login_user);
        ResultInfo resultInfo = new ResultInfo();
        //6.判断用户是否为空
        if (user == null) {
            //不存在  设置提示信息
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误！");
        }else {
            //存在，判断是否为激活状态
            if (user.getStatus().equals("Y")) {
                //已激活，登录成功
                resultInfo.setFlag(true);
                //将登录成功的用户存入session中 用于其他请求获取数据
                session.setAttribute("user",user);
            }else {
                //未激活提示激活
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("您还未激活,请先激活再登录！");
            }
        }
        //7.将信息响应改浏览器
//        ObjectMapper mapper = new ObjectMapper();
//        String s = mapper.writeValueAsString(resultInfo);
        //System.out.println(s);
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(s);
        writeValue(response,resultInfo);
    }

    /**
     * 在session中获取已经登录的user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getInSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取session中已经的user  因为是html不能共享数据 所以需要浏览器的请求来获取数据
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //2.将已经登录的user响应给浏览器
//        ObjectMapper mapper = new ObjectMapper();
//        String s = mapper.writeValueAsString(user);
        //System.out.println(s);
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(s);
        writeValue(response,user);
    }

    /**
     * 退出登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        HttpSession session = request.getSession();
        session.invalidate();
        //2.销毁session后 重定向到login.html
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
