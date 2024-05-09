package com.jerry.rpccore.regCenter;

import cn.hutool.json.JSONUtil;
import com.jerry.rpccore.conf.regCenterConf.RegConf;
import com.jerry.rpccore.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 11:31
 * @注释
 */
public class EtcdRegCenter implements RegCenterInterface {

    private Client client;//etcd client

    private KV kvClient;//etcd kv client

    private static final String ROOT_PATH = "/rpc/"; //etcd root path


    @Override
    public void init(RegConf regConf) {
        //初始化etcd client
        client = Client.builder()
                .endpoints(regConf.getRegAddr())
                .connectTimeout(Duration.ofMillis(regConf.getRegTimeout()))
                .build();
        //初始化etcd kv client
        kvClient = client.getKVClient();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) {
        //创建租约
        Lease leaseClient = client.getLeaseClient();
        try {
            //租约有效期30秒
            long id = leaseClient.grant(60).get().getID();
            //拼接etcd key
            String registerKey = ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
            //转换为字节序列
            ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);

            //关联租约
            PutOption build = PutOption.builder().withLeaseId(id).build();
            //注册服务
            kvClient.put(key, value, build).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deregister(ServiceMetaInfo serviceMetaInfo) {
        //删除etcd key
        String registerKey = ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        try {
            kvClient.delete(key).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        String discoveryKey = ROOT_PATH + serviceKey + "/";
        try {
            GetOption build = GetOption.builder().isPrefix(true).build();
            List<KeyValue> keyValues = kvClient.get(ByteSequence.from(discoveryKey, StandardCharsets.UTF_8), build).get().getKvs();
            return keyValues.stream().map(kv -> JSONUtil.toBean(kv.getValue().toString(StandardCharsets.UTF_8), ServiceMetaInfo.class)).collect(Collectors.toList());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        if (kvClient != null) {
            kvClient.close();
        }

        if (client != null) {
            client.close();
        }
    }

}
