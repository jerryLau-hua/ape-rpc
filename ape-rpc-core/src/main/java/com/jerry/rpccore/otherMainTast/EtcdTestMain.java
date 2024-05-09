package com.jerry.rpccore.otherMainTast;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.kv.PutResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 10:26
 * @Description 测试etcd操作
 * @注释
 */
public class EtcdTestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建etcd客户端
        Client client = Client.builder().endpoints("http://8.140.192.79:12379").build();
        // 获取KV客户端
        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test".getBytes());
        ByteSequence value = ByteSequence.from("testValue".getBytes());
        // 写入数据
        PutResponse putResponse = kvClient.put(key, value).get();

//        System.out.println(prevKv.getKey().toString() + " : " + prevKv.getValue().toString());


        // 读取数据
        CompletableFuture<GetResponse> getResponseCompletableFuture = kvClient.get(key);
        GetResponse getResponse = getResponseCompletableFuture.get();
        System.out.println("获取到的数据是" + getResponse.getKvs().get(0).getValue().toString());

        // 删除数据
        DeleteResponse deleteResponse = kvClient.delete(key).get();
        long deleted = deleteResponse.getDeleted();
        System.out.println(deleted);

    }
}
