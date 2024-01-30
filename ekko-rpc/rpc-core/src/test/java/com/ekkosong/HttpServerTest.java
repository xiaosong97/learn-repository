package com.ekkosong;

import com.ekkosong.protocol.HttpServer;

public class HttpServerTest {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8081);
    }
}
