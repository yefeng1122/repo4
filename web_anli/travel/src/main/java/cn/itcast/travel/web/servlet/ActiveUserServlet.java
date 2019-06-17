package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 激活用户的Servlet
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String code = request.getParameter("code");
        //2.调用service层激活用户
        UserService service = new UserServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
