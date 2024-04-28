package com.jerry.rpccore.localregcenter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 16:23
 * @注释 本地注册中心
 */
public class LocalRegCenter {
    /***
     * 本地注册中心存储列表
     */
    private static Map<String, Class<?>> map = new ConcurrentHashMap<String, Class<?>>();


    /***
     * 注册
     * @param serviceName
     * @param impl
     */
    public static void add(String serviceName, Class<?> impl) {
        map.put(serviceName, impl);
    }

    /***
     * 获取
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }


    /***
     * 移除
     * @param serviceName
     */
    public static void remove(String serviceName) {
        map.remove(serviceName);
    }

}
