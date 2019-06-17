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
 * 此为注册用户的servlet
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
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
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(resultInfo);
        //System.out.println(s);
        //设置响应头并将其返回  用json格式解析
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
