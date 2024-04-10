package com.jerry.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/9 15:33
 * @注释 实体 🐱
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cat implements Serializable {

    private int id;
    /***
     * 名字
     */
    private String name;

    /***
     * 颜色
     */
    private String color;
}
