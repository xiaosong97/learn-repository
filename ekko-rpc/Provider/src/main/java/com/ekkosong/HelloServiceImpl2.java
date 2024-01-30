package com.ekkosong;

public class HelloServiceImpl2 implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello2: " + name;
    }
}
