package com.jerry.rpccore.regCenter;

import com.jerry.rpccore.conf.regCenterConf.RegConf;
import com.jerry.rpccore.model.ServiceMetaInfo;

import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 11:23
 * @Description 注册中心接口
 */
public interface RegCenterInterface {
    /**
     * 初始化 注册中心
     */
    void init(RegConf regConf);

    /**
     * 注册服务(服务端调用)
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo);

    /**
     * 注销服务(服务端调用)
     */
    void deregister(ServiceMetaInfo serviceMetaInfo);

    /***
     * 服务发现(获取某服务的所有节点，消费端调用)
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);


    /**
     * 销毁服务
     */
    void destroy();
}
