package com.chenyu.www.controller;

import com.chenyu.www.util.VerifyCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 86323
 */
@WebServlet(name = "VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode verifyCode=new VerifyCode();
        BufferedImage image=verifyCode.getImage();
        request.getSession().setAttribute("pictureText",verifyCode.getText());
        VerifyCode.output(image,response.getOutputStream());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request,response);
    }
}
