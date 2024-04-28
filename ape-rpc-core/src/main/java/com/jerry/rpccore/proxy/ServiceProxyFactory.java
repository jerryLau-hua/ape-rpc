package com.jerry.rpccore.proxy;

import com.jerry.rpccore.conf.RPCGlobalConfHolder;

import java.lang.reflect.Proxy;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/10 8:50
 * @注释 动态代理工厂，通过指定类创建代理对象
 */
public class ServiceProxyFactory {

    /***
     * get proxy
     * @param serviceClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> serviceClass) {

        //配置管理器中是否开启Mock模式
        if (RPCGlobalConfHolder.getRpcConfig().isMock()) {
            return (T) getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass}
                , new ServiceProxy()
        );
    }


    /***
     * get mock proxy
     * @param serviceClass
     * @return
     * @param <T>
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass}
                , new MockServiceProxy()
        );
    }

}
