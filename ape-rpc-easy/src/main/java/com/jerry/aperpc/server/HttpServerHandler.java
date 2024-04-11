package com.jerry.aperpc.server;

import com.jerry.aperpc.localregcenter.LocalRegCenter;
import com.jerry.aperpc.model.RpcRequest;
import com.jerry.aperpc.model.RpcResponse;
import com.jerry.aperpc.serializer.JDKSerializer;
import com.jerry.aperpc.serializer.KryoSerializer;
import com.jerry.aperpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 16:49
 * @注释 请求处理器
 * 1.反序列化请求为对象，并从请求对象中获取参数。
 * 2.根据服务名称从本地注册器中获取到对应的服务实现类,
 * 3.通过反射机制调用方法，得到返回结果。
 * 4.对返回结果进行封装和序列化，并写入到响应中,
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest request) {
        //1-指定序列化器
        final Serializer serializer = new KryoSerializer();
        //打印请求信息日志
        System.out.println("Received request :" +"request.uri: ["+ request.uri() + "] request.method : ["+request.method()+"]");

        //2-异步进行请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();//如果请求为 nu11，直接返回
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }


            try {
                // 获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegCenter.get(rpcRequest.getServiceName());//获取实现类
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());//获取实现类的方法
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 响应
            doResponse(request, rpcResponse, serializer);

        });

    }

    /**
     * 响应
     *
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化
            byte[] serialized = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
