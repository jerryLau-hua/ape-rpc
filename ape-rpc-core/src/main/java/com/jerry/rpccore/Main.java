package com.jerry.rpccore;

import com.jerry.rpccore.serializer.Serializer;

import java.util.ServiceLoader;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/11 16:38
 * @注释 java 内置spi机制，通过ServiceLoader加载序列化器
 */
public class Main {
    public static void main(String[] args) {

        Serializer serializer = null;
        ServiceLoader<Serializer> load = ServiceLoader.load(Serializer.class);

        for (Serializer serializer1 : load) {
            serializer = serializer1;

            System.out.println(serializer);
        }

        System.out.println(load);

    }


}
