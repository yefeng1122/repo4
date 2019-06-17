package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 获取session中已经登录的用户
 */
@WebServlet("/getUserInSessionServlet")
public class GetUserInSessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取session中已经的user  因为是html不能共享数据 所以需要浏览器的请求来获取数据
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //2.将已经登录的user响应给浏览器
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(user);
        //System.out.println(s);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
