package com.jerry.common.service;

import com.jerry.common.model.Cat;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 15:38
 * @注释 🐱服务接口
 */

public interface CatService {
    /***
     * 获取猫咪信息
     * @param cat
     * @return
     */
    Cat getCat(Cat cat);


    /***
     * 按照id获取猫咪信息
     * @param id
     * @return
     */
    Cat getCatById(int id);

    //....other

    /**
     * 获取猫咪数量
     * @return
     */
    default int getCatCount() {
        return 100;
    }
}
