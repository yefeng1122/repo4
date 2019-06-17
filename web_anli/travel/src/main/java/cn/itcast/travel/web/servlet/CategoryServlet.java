package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 此servlet为操作category表的servlet，包含了多种功能  减少了servlet的数量
 * 继承自BaseServlet，间接继承HTTPServlet，浏览器请求服务器后，找service方法提供服务
 * 为了不使用暴力反射，将方法的权限修饰符改为public
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();
    /**
     * 查找所有线路分类的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用Service层查找所有的线路分类
        List<Category> list = service.findAll();
        //2.将list序列化json后并返回到浏览器
        writeValue(response,list);
    }
}
