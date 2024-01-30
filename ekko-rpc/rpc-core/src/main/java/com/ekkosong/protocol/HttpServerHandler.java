package com.ekkosong.protocol;

import com.ekkosong.common.Invocation;
import com.ekkosong.register.LocalRegister;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        // 处理请求：接口+方法+方法参数
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            System.out.println("server receive invocation = " + invocation);
            String interfaceName = invocation.getInterfaceName(); // 拿到接口名称后需要去寻找实现类，如果遍历所有类，性能会很差
            // 所以需要有一个注册机制，可以先使用本地注册 LocalRegister
            Class classImpl = LocalRegister.get(interfaceName, invocation.getVersion());
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());
            IOUtils.write(result, resp.getOutputStream(), StandardCharsets.UTF_8);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
