package com.jerry.consumer;

import com.jerry.aperpc.proxy.ServiceProxyFactory;
import com.jerry.common.model.Cat;
import com.jerry.common.service.CatService;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date ${DATE} ${TIME}
 * @注释
 */
public class EasyConsumer {
    public static void main(String[] args) {
        //todo: 需要获取 UserService 的实现类对象
//        CatService catService = new CatServiceProxy();
        CatService catService = ServiceProxyFactory.getProxy(CatService.class);
//        Cat cat = new Cat();
//        cat.setId(1);
//        cat.setName("tom");
//        cat.setColor("#f0f0f0");

        //调用
        Cat newCat = catService.getCatById(1);
        if (newCat != null) {
            System.out.println("消费者获取到的 cat ：" + newCat.toString());
        } else {
            System.out.println("no cat");
        }

    }

}