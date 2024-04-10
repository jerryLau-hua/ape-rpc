package com.jerry.aperpc.proxy;

import java.lang.reflect.Proxy;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/10 8:50
 * @注释 动态代理工厂，通过指定类创建代理对象
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> serviceClass) {

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass}
                , new ServiceProxy()
        );
    }

}
