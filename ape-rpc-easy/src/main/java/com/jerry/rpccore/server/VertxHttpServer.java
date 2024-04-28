package com.jerry.rpccore.server;

import io.vertx.core.Vertx;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 16:06
 * @注释
 */
public class VertxHttpServer implements HttpServer {
    @Override
    public void exec(int port) {
        //1-创建Vertx 实例
        Vertx vertx = Vertx.vertx();

        //2-创建http服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();

        //3-监听端口并处理请求
//        httpServer.requestHandler(httpServerRequest -> {
//            //处理里HTTP 请求
//            System.out.println("Received request :"
//                    + httpServerRequest.method() + " " +
//                    httpServerRequest.uri());
//
//            //发送http响应
//            httpServerRequest.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x HTTP server!");
//        });
        httpServer.requestHandler(new HttpServerHandler());

        //4-启动 HTTP 服务器并监听指定端口
        httpServer.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port:" + port);
            } else
                System.err.println("Failed to start server:" + result.cause());

        });
    }
}