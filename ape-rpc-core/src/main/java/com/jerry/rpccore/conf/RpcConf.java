package com.jerry.rpccore.conf;

import com.jerry.rpccore.serializer.SerializerKeys;
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


    /***
     * 默认序列化器
     * 默认使用jdk序列化器
     */
    private String serializer = SerializerKeys.JDK;



    /***
     * 重写mock属性的setter方法
     * @param mock
     */
    public void setMock(String mock) {
        this.mock = Boolean.valueOf(mock);
    }



}
