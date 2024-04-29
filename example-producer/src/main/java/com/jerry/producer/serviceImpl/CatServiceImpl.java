package com.jerry.producer.serviceImpl;

import com.jerry.common.model.Cat;
import com.jerry.common.service.CatService;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 15:45
 * @注释 数据提供者实现
 */
public class CatServiceImpl implements CatService {
    @Override
    public Cat getCat() {
        System.out.println("调用到了服务提供者：获取🐱"  );
        return new Cat(1,"tom","blue");
    }

    @Override
    public Cat getCatById(int id) {
        Cat tom = new Cat(id, "TOM", "#FFFFFF");
        System.out.println("调用到了服务提供者：" + tom.toString());
        return tom;
    }
}
