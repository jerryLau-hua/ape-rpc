package com.jerry.rpccore.model;

import com.jerry.rpccore.utils.RPCCommonConstant;
import lombok.*;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 16:43
 * @注释 rpc 请求封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RpcRequest implements Serializable {
    /***
     服务名称冰
     */
    private String serviceName;
    /***
     *方法名称
     */
    private String methodName;
    /***
     参数类型列表
     */
    private Class<?> [] parameterTypes;
    /***
     *参数列表
     */
    private Object[] args;


    /***
     * 服务版本号
     */
    private String serviceVersion= RPCCommonConstant.DEFAULT_SERVICE_VERSION;
}
