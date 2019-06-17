package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 此为分发方法的servlet
 */
public class BaseServlet extends HttpServlet {
    /*
        谁调用这个service方法 ，this就代表哪个类的对象 利用反射就可以调用对应的方法
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的uri     /user/add
        String uri = req.getRequestURI();
        //2.截取uri，得到方法名add  根据索引来截取 ，包含头不包含尾 ,从最后一个'/'开始截取
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        //3.通过反射来得到该方法 并调用  为了不使用暴力反射，将权限改为public
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将响应的数据序列化为json 并填充到输出流中 避免了每个方法都要重复写
     * @param response
     * @param obj
     */
    public void writeValue(HttpServletResponse response,Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将对象序列化成json字符串，并返回
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
