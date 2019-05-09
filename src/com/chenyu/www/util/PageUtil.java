package com.chenyu.www.util;

import com.chenyu.www.po.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 页码工具类
 * @author 86323
 */
public class PageUtil {

    /**
     * 根据请求计算出当前页码
     * @param request 请求
     * @return 当前页页码
     */
    public static int loadCurrentPage(HttpServletRequest request){
        String currentPage=request.getParameter("currentPage");
        //默认第一次进入，或当前页小于0，设置为首页
        if(currentPage ==null){
            currentPage="1";
        }
        //输入其他非数字默认也是第一页
        try{
            Integer.parseInt(currentPage);
        }catch (NumberFormatException e){
            currentPage="1";
            return Integer.parseInt(currentPage);
        }
        //输入小于0也是默认0
        if(Integer.parseInt(currentPage)<=0 ){
            currentPage="1";
        }
        return Integer.parseInt(currentPage);
    }

    /**
     * 根据请求计算出每页页数
     * @param request 请求
     * @return 每页页数
     */
    public static int loadPageSize(HttpServletRequest request,String normalSize){
        String pageSize=request.getParameter("pageLength");
        if(pageSize ==null){
            pageSize=normalSize;
        }
        return Integer.parseInt(pageSize);
    }


}
