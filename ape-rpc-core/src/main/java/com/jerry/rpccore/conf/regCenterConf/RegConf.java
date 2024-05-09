package com.jerry.rpccore.conf.regCenterConf;

import com.jerry.rpccore.regCenter.RegType;
import lombok.Data;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 11:19
 * @discription rpc框架中的配置中心
 */

@Data
public class RegConf {
    /***
     * rpc 框架注册中心类别
     */
    private String regType= RegType.ETCD;


    /***
     * rpc框架注册中心地址
     */
    private String regAddr="http://8.140.192.79:12379";

    /***
     * rpc框架注册中心用户名
     */
    private String regUser;

    /***
     * rpc框架注册中心密码
     */
    private String regPass;

    /***
     * rpc框架注册中心超时时间
     */
    private int regTimeout=60000;
}
