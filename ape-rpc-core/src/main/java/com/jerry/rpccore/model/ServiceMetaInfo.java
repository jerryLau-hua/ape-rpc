package com.jerry.rpccore.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;


/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 11:03
 * @Description 封装服务的注册信息
 */
@Data
public class ServiceMetaInfo {
    /***
     * 服务名
     */
    private String serviceName;

    /***
     * 服务地址
     */
    private String serviceAddress;

    /***
     * 服务主机名
     */
    private String serviceHost;

    /***
     * 服务端口号
     */
    private int servicePort;


    /***
     * 服务版本号 默认1.0版本
     */
    private String serviceVersion = "v1.0";

    /***
     * 服务分组，目前采用默认分组
     */
    private String serviceGroup = "default";

    /***
     * 获取服务注册列表中的键
     * @return
     */
    public String getServiceKey() {
        //用于扩展分组
//        return String.format("%s:%s:%s", serviceName, serviceGroup, serviceVersion);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /***
     * 获取服务注册节点的键
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /***
     * 获取服务地址
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
