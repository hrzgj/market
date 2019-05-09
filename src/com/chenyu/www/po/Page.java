package com.chenyu.www.po;


import java.util.List;

/**
 * 注意调用顺序，务必先set总数据数，再set页面大小，在页面大小的set中自动计算出总页数
 * @author 86323
 */
public class Page<T> {
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页的大小
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     *总数据
     */
    private int totalCount;
    /**
     * 数据集合
     */
    private List<T> list;
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        //总页数 = 数据总数%页面大小==0? 数据总数/页面大小:数据总数/页面大小+1 ;
        this.totalPage =this.totalCount%this.pageSize==0?this.totalCount/this.pageSize:totalCount/this.pageSize+1;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
