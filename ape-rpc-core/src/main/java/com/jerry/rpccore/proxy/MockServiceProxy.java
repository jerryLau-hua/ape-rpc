package com.jerry.rpccore.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/12 15:42
 * @注释 MockServiceProxy 类
 * 采用JDK动态代理
 * 开启Mock服务代理
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?> returnType = method.getReturnType();
        log.info("MockServiceProxy invoke method: {}, returnType: {}", method.getName(), returnType.getName());

        return getDefaultObject(returnType);
    }


    public Object getDefaultObject(Class<?> returnType) {
        log.info("MockServiceProxy getDefaultObject returnType: {}", returnType.getName());
        if (returnType == int.class) {
            return 0;
        } else if (returnType == long.class) {
            return 0L;
        } else if (returnType == double.class) {     // double 类型默认值是 0.0
            return 0.0;
        } else if (returnType == boolean.class) {
            return false;        // boolean 类型默认值是 false
        } else if (returnType == Short.class) {
            return (short) 0;
        }
        //对象返回null
        return null;
    }
}
