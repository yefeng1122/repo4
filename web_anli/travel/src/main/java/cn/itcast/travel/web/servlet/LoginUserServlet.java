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
 * 登录验证的Servlet
 */
@WebServlet("/loginUserServlet")
public class LoginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
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
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(resultInfo);
        //System.out.println(s);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
