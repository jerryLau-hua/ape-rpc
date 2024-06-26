package com.jerry.consumer.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.jerry.rpccore.model.RpcRequest;
import com.jerry.rpccore.model.RpcResponse;
import com.jerry.rpccore.serializer.KryoSerializer;
import com.jerry.rpccore.serializer.Serializer;
import com.jerry.common.model.Cat;
import com.jerry.common.service.CatService;

import java.io.IOException;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 17:14
 * @注释 🐱服务的静态代理
 */

public class CatServiceProxy implements CatService {
    @Override
    public Cat getCat() {
        // 指定序列化器
        final Serializer serializer = new KryoSerializer();

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(CatService.class.getName())
                .methodName("getCat")
                .parameterTypes(new Class[]{Cat.class})
                .build();
        try {
            // 序列化（Java 对象 => 字节数组）
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化（字节数组 => Java 对象）
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return (Cat) rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Cat getCatById(int id) {
        // 指定序列化器
        final Serializer serializer = new KryoSerializer();

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(CatService.class.getName())
                .methodName("getCatById")
                .parameterTypes(new Class[]{int.class})
                .args(new Object[]{id})
                .build();
        try {
            // 序列化（Java 对象 => 字节数组）
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化（字节数组 => Java 对象）
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return (Cat) rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
