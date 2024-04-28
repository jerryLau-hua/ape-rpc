package com.jerry.rpccore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 16:47
 * @注释 rpc 响应封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RpcResponse implements Serializable {
    /***
     *响应数据
     */
    private Object data;
    /***
     *响应数据类型(预留)
     */
    private Class<?> dataType;
    /***
     *响应信息
     *  */
    private String message;
    /***
     * 异常信息
     */
    private Exception exception;
}
