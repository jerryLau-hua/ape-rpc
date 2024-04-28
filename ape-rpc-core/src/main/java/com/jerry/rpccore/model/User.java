package com.jerry.rpccore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/12 16:41
 * @注释
 */
@Data
@Builder
@AllArgsConstructor
@ToString
public class User {
    private int id;

    private String name;
    private int age;
    private String address;
    private String phone;


}
