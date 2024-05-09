package com.jerry.rpccore.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.jerry.rpccore.conf.RPCGlobalConfHolder;
import com.jerry.rpccore.conf.RpcConf;
import com.jerry.rpccore.model.RpcRequest;
import com.jerry.rpccore.model.RpcResponse;
import com.jerry.rpccore.model.ServiceMetaInfo;
import com.jerry.rpccore.regCenter.RegCenterFactory;
import com.jerry.rpccore.regCenter.RegCenterInterface;
import com.jerry.rpccore.serializer.Serializer;
import com.jerry.rpccore.serializer.SerializerFactory;
import com.jerry.rpccore.utils.RPCCommonConstant;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/10 8:44
 * @注释 rpc 模块中的动态代理
 */
public class ServiceProxy implements InvocationHandler {

    /***
     * 调用代理
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceName = method.getDeclaringClass().getName();
        System.out.println("invoke current  method :" + method + "args: " + args);
        System.out.println("invoke current  method :" + method.getName() + "  args: " + args +"  parameterTypes:"+method.getParameterTypes());
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RPCGlobalConfHolder.getRpcConfig().getSerializer());
        System.out.println("serializer:["+serializer.getClass().getName()+"]");

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            // 序列化（Java 对象 => 字节数组）
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送请求
            //todo:暂时设置成硬编码，后面改成服务发现
//            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
//                    .body(bodyBytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                // 反序列化（字节数组 => Java 对象）
//                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
//                return rpcResponse.getData();
//            }
            //todo:通过服务发现获取服务地址

            //获取全局配置信息
            RpcConf rpcConfig = RPCGlobalConfHolder.getRpcConfig();
            RegCenterInterface registry = RegCenterFactory.getRegCenter(rpcConfig.getRegistryConfig().getRegType());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RPCCommonConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            //先取第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
            String serviceAddress = selectedServiceMetaInfo.getServiceAddress();
            try (HttpResponse httpResponse = HttpRequest.post(serviceAddress)
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化（字节数组 => Java 对象）
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
