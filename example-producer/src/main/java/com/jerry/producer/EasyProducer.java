package com.jerry.producer;

import com.jerry.aperpc.localregcenter.LocalRegCenter;
import com.jerry.aperpc.server.VertxHttpServer;
import com.jerry.common.service.CatService;
import com.jerry.producer.serviceImpl.CatServiceImpl;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 15:47
 * @注释 简单的生产者
 */
public class EasyProducer {
    public static void main(String[] args) {
        //注册服务
        LocalRegCenter.add(CatService.class.getName(), CatServiceImpl.class);

        //提供服务
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.exec(8080);
    }
}
