package cn.itcast.travel.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 此为分页查询的pagebean实体类
 * @param <T>
 */
public class PageBean<T> implements Serializable {

    private int currentPage; //当前页码
    private int rows;         //每页的记录数
    private int totalCount;  //总记录数
    private int totalPage;   //总记录数
    private List<T> list;     //查到该页数据的类型

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
