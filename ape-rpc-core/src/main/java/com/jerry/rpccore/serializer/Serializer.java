package com.jerry.rpccore.serializer;

import java.io.IOException;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 16:33
 * @注释
 */
public interface Serializer {
    /***
     * 序列化
     * @param object
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /***
     * 反序列化器
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[]bytes, Class<T> type) throws IOException;
}
