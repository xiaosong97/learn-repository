package com.ekkosong.protocol;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 这里不直接处理，而是使用一个handler是为了针对不同类型的请求使用不同的handler进行处理
        // 使用了责任链的设计模式
        System.out.println("server receive something...");
        new HttpServerHandler().handler(req, resp);
    }
}
