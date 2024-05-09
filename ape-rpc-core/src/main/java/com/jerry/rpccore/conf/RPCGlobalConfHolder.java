package com.jerry.rpccore.conf;

import com.jerry.rpccore.conf.regCenterConf.RegConf;
import com.jerry.rpccore.regCenter.RegCenterFactory;
import com.jerry.rpccore.regCenter.RegCenterInterface;
import com.jerry.rpccore.utils.ConfUtils;
import com.jerry.rpccore.utils.RPCCommonConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/11 16:20
 * @注释 RPC 全局配置Holder
 */

@Slf4j
public class RPCGlobalConfHolder {

    private static volatile RpcConf rpcConf;

    /***
     * 框架初始化，支持传入自定义配置
     * @param newRpcConf
     */
    public static void initConf(RpcConf newRpcConf) {
        //
        rpcConf = newRpcConf;
        log.info("rpc init,config ={}" + newRpcConf.toString());

        //注册中心配置初始化
        RegConf registryConfig = newRpcConf.getRegistryConfig();
        RegCenterInterface regCenter = RegCenterFactory.getRegCenter(registryConfig.getRegType());
        regCenter.init(registryConfig);
        log.info("rpc init,registry config ={}" + registryConfig.toString());
    }

    /***
     * 初始化
     */
    public static void initConf() {
        RpcConf newRpcConf;

        try {
            newRpcConf = ConfUtils.loadConf(RpcConf.class, RPCCommonConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            log.error("rpc init error,config load failed,use default config", e);
            //配置加载失败 使用默认值
            newRpcConf = new RpcConf();
        }

        initConf(newRpcConf);
    }

    /***
     * 获取配置
     * @return
     */
    public static RpcConf getRpcConfig() {
        if (rpcConf == null) {
            synchronized (RPCGlobalConfHolder.class) {
                if (rpcConf == null) {
                    initConf();
                }
            }
        }
        return rpcConf;
    }
}
