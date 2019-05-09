package com.chenyu.www.util;

import com.chenyu.www.po.Trade;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * @author 86323
 */
public abstract class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
            获取参数，用来识别用户想请求的方法
            然后判断是否哪一个方法，是哪一个我们就调用哪一个
         */
        String methodName = req.getParameter("method");

        if(methodName == null || methodName.trim().isEmpty()) {
            throw new RuntimeException("您没有传递method参数！无法确定您想要调用的方法！");
        }

        /*
         得到方法名，通过方法名再得到Method类的对象！
         需要得到Class，然后调用它的方法进行查询！得到Method
         我们要查询的是当前类的方法，所以我们需要得到当前类的Class
         */
        //得到当前类的class对象
        Class c = this.getClass();
        Method method=null;
        try {
            method = c.getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (method != null) {
                method.invoke(this, req, resp);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 需要从form表单接收图片时用的
     * @param request 请求
     * @return 迭代器
     */
    protected Iterator<FileItem> loadPhoto(HttpServletRequest request){
        DiskFileItemFactory factory =new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items=null;
        Trade trade=new Trade();
        try {
            //通过parseRequest解析form中的所有字段，并存入list集合中
            items=upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> itemIterator=null;
        if (items != null) {
            itemIterator=items.iterator();
        }
        return itemIterator;
    }
}
