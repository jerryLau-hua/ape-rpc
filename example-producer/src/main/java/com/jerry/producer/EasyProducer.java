package com.jerry.producer;

import com.jerry.common.service.CatService;
import com.jerry.producer.serviceImpl.CatServiceImpl;
import com.jerry.rpccore.conf.RPCGlobalConfHolder;
import com.jerry.rpccore.conf.RpcConf;
import com.jerry.rpccore.conf.regCenterConf.RegConf;
import com.jerry.rpccore.localregcenter.LocalRegCenter;
import com.jerry.rpccore.model.ServiceMetaInfo;
import com.jerry.rpccore.regCenter.RegCenterFactory;
import com.jerry.rpccore.regCenter.RegCenterInterface;
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
        //注册服务到本地
        LocalRegCenter.add(CatService.class.getName(), CatServiceImpl.class);

        //注册服务到注册中心
        RpcConf rpcConfig = RPCGlobalConfHolder.getRpcConfig();
        RegConf registryConfig = rpcConfig.getRegistryConfig();
        RegCenterInterface regCenter = RegCenterFactory.getRegCenter(registryConfig.getRegType());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();

        serviceMetaInfo.setServiceName(CatService.class.getName());
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());
        //todo: 这里应该是服务的版本号，暂时写死
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(Integer.valueOf(rpcConfig.getServerPort()));

        try {
            regCenter.register(serviceMetaInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //提供服务
        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        vertxHttpServer.exec(Integer.valueOf(RPCGlobalConfHolder.getRpcConfig().getServerPort()));
    }
}
