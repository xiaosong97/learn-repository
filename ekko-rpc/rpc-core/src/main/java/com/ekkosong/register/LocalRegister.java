package com.ekkosong.register;

import java.util.HashMap;
import java.util.Map;

public class LocalRegister {
    public static final String DEFAULT_VERSION = "1.0";
    public static final String SPLIT = "-"; // interfaceName+SPLIT+version 构成map中的key
    private static Map<String, Class> map = new HashMap<>();

    public static void register(String interfaceName, Class implClass) {
        register(interfaceName, implClass, DEFAULT_VERSION);
    }

    public static void register(String interfaceName, Class implClass, String version) {
        map.put(interfaceName + SPLIT + version, implClass);
    }

    public static Class get(String interfaceName) {
        return get(interfaceName, DEFAULT_VERSION);
    }

    public static Class get(String interfaceName, String version) {
        return map.get(interfaceName + SPLIT + version);
    }

    public static void printService() {
        System.out.println("map = " + map);
    }
}
