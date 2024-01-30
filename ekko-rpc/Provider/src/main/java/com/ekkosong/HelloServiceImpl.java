package com.ekkosong;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello: " + name;
    }
}
