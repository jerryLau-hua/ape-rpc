package com.jerry.rpccore.regCenter;

import com.jerry.rpccore.utils.SPIloaderUtils;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 13:12
 * @Description 注册中心工厂，用于获取注册中心对象
 */
public class RegCenterFactory {

    // 注册中心对象
    static {
        SPIloaderUtils.loadSPI(RegCenterInterface.class);
    }

    //默认使用etcd注册中心
    private static final RegCenterInterface DEFAULT_REG_CENTER = new EtcdRegCenter();


    /***
     * 获取注册中心对象
     * @param regCenterType
     * @return
     */
    public static RegCenterInterface getRegCenter(String regCenterType) {
        if (regCenterType == null) {
            return DEFAULT_REG_CENTER;
        }
        return SPIloaderUtils.getInstance(RegCenterInterface.class, regCenterType);
    }
}
