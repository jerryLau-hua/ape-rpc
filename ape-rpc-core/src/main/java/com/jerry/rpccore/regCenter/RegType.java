package com.jerry.rpccore.regCenter;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 13:11
 * @Description 注册中心类型接口
 */
public interface RegType {
    String ZOOKEEPER = "zookeeper";
    String NACOS = "nacos";
    String ETCD = "etcd";
}
