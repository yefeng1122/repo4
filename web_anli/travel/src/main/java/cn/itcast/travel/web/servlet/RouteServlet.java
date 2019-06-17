package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 线路表的servlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    /**
     * 分页查询线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取浏览器的参数
        String _cid = request.getParameter("cid");
        String _currentPage = request.getParameter("currentPage");
        String _rows = request.getParameter("rows");
        String rname = request.getParameter("rname");    //搜索框用来进行模糊查询
        //解决tomcat7 get请求url参数的  中文乱码的问题  先编码成字节数组，在进行编码
        //注意：要打成war包放在Linux上进行部署项目，中文必须先编码，再解码
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //2.调用service层查询数据  分页并模糊查询
        PageBean<Route> pb = routeService.findByPage(_cid,_currentPage,_rows,rname);
        //System.out.println(pb);
        //3.将pagebean的json字符串响应给浏览器
        writeValue(response,pb);
    }

    /**
     * 查询一个route线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取浏览器的参数
        String _rid = request.getParameter("rid");
        //2.调用service层查询数据该rid的详细信息
        Route route = routeService.findOne(_rid);
        //System.out.println(route);
        //3.将route的json字符串响应给浏览器
        writeValue(response,route);
    }

    /**
     * 判断当前的用户是否被该用户收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取session中的user 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        //2.判断用户是否存在  并获取uid
        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }
        //3.获取浏览器传过来的rid
        String _rid = request.getParameter("rid");
        //4.调用service层查询数据该该线路是否被该用户登录
        boolean flag = routeService.isFavorite(_rid,uid);
        //5.将结果响应给浏览器
        writeValue(response,flag);
    }

    /**
     * 给登录的用户添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取session中的user 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        //2.判断用户是否存在
        if (user == null) {
            //用户没登录  直接结束方法即可
            return;
        }
        //3.获取浏览器传过来的rid
        String _rid = request.getParameter("rid");
        //4.调用service层给登录的用户添加该收藏信息
       routeService.addFavorite(_rid,user.getUid());
    }


}
