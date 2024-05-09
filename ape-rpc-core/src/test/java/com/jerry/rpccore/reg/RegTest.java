package com.jerry.rpccore.reg;

import com.jerry.rpccore.conf.regCenterConf.RegConf;
import com.jerry.rpccore.model.ServiceMetaInfo;
import com.jerry.rpccore.regCenter.EtcdRegCenter;
import com.jerry.rpccore.regCenter.RegCenterInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/6 14:30
 * @注释
 */
public class RegTest {
    final RegCenterInterface regCenter = new EtcdRegCenter();


    @Before
    public void init() {
        RegConf regConf = new RegConf();
        regConf.setRegAddr("http://8.140.192.79:12379");
        regCenter.init(regConf);
    }

    @Test
    public void register() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        regCenter.register(serviceMetaInfo);
        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1235);
        regCenter.register(serviceMetaInfo);
        serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("2.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        regCenter.register(serviceMetaInfo);

    }


    @Test
    public void unregister() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(1234);
        regCenter.deregister(serviceMetaInfo);
    }


    @Test
    public void destory() {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName("myService");
        serviceMetaInfo.setServiceVersion("1.0");
        String serviceKey = serviceMetaInfo.getServiceKey();
        List<ServiceMetaInfo> serviceMetaInfoList = regCenter.serviceDiscovery(serviceKey);
        Assert.assertNotNull(serviceMetaInfoList);
    }


}
