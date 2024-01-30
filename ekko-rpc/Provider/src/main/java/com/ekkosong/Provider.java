package com.ekkosong;

import com.ekkosong.protocol.HttpServer;
import com.ekkosong.register.LocalRegister;

public class Provider {
    public static void main(String[] args) {
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class, "1.0");
        LocalRegister.register(HelloService.class.getName(), HelloServiceImpl2.class, "2.0");
        LocalRegister.printService();
        // 接收网络请求：Netty，Tomcat 等
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
