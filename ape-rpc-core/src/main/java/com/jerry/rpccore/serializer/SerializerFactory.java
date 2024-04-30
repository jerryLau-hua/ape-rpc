package com.jerry.rpccore.serializer;

import com.jerry.rpccore.utils.SPIloaderUtils;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/29 16:33
 * @注释 序列化工厂 用于获取系列化器
 */
public class SerializerFactory {

    /***
     * 序列化器映射表
     */
//    private static final Map<String, Serializer> SERIALIZER_MAP = new HashMap<String, Serializer>() {{
//        put(SerializerKeys.JDK, new JDKSerializer());
//        put(SerializerKeys.KRYO, new KryoSerializer());
//        put(SerializerKeys.HESSIAN, new HessianSerializer());
//        put(SerializerKeys.JSON, new JsonSerializer());
//    }};
    static {
        SPIloaderUtils.loadSPI(Serializer.class);
    }

    /**
     * 默认序列化器 JDK序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JDKSerializer();


    /**
     * 获取序列化器
     *
     * @param key 序列化器名称
     * @return 序列化器
     */
    public static Serializer getInstance(String key) {
        if (key == null) {
            return DEFAULT_SERIALIZER;
        }
        return SPIloaderUtils.getInstance(Serializer.class, key);
    }

}
