package com.ekkosong;

import com.ekkosong.common.Invocation;
import com.ekkosong.protocol.HttpClient;

public class Consumer {
    public static void main(String[] args) {
        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
                new Class[]{String.class}, new Object[]{"ekko"});
        invocation.setVersion("2.0");
        HttpClient httpClient = new HttpClient();
        String result = httpClient.send("localhost", 8080, invocation);
        System.out.println("result = " + result);

    }
}
