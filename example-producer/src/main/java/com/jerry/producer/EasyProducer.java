package com.jerry.producer;

import com.jerry.common.service.CatService;
import com.jerry.producer.serviceImpl.CatServiceImpl;
import com.jerry.rpccore.conf.RPCGlobalConfHolder;
import com.jerry.rpccore.localregcenter.LocalRegCenter;
import com.jerry.rpccore.server.VertxHttpServer;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 15:47
 * @注释 简单的生产者
 */
public class EasyProducer {
    public static void main(String[] args) {
        //RPC服务配置初始化
        RPCGlobalConfHolder.initConf();
        //注册服务
        LocalRegCenter.add(CatService.class.getName(), CatServiceImpl.class);

        //提供服务
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.exec(Integer.valueOf(RPCGlobalConfHolder.getRpcConfig().getServerPort()));
    }
}
