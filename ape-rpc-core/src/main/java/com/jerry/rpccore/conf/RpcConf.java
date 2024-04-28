package com.jerry.rpccore.conf;

import lombok.Data;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/11 15:45
 * @注释 rpc配置类
 */

@Data
public class RpcConf {

    /***
     * 名称
     */
    private String name = "ape-rpc";

    /***
     * 版本号
     */
    private String version = "V0.0.1";


    /***
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /***
     * 服务器端口号
     */
    private String serverPort = "8080";

    /***
     * 是否开启模拟数据模式
     * 默认不开启mock
     */
    private boolean mock = false;
}
