package com.ekkosong.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 封装请求参数，对于rpc框架来说，接收的参数就是 1）接口 2）接口方法 3）方法参数
 * TODO：后续可以使用其他序列化框架替代jdk自带的序列化，如 kryo
 */
public class Invocation implements Serializable {
    private String interfaceName;
    private String methodName;
    private Class[] parameterTypes;
    private Object[] parameters;
    private String version;

    public Invocation() {
    }

    public Invocation(String interfaceName, String methodName, Class[] parameterTypes, Object[] parameters, String version) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
        this.version = version;
    }

    public Invocation(String interfaceName, String methodName, Class[] parameterTypes, Object[] parameters) {
        this(interfaceName, methodName, parameterTypes, parameters, "1.0");
    }

    @Override
    public String toString() {
        return "Invocation{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                ", version='" + version + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
