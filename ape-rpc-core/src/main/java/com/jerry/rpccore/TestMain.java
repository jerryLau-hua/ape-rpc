package com.jerry.rpccore;

import com.github.javafaker.Faker;
import com.github.javafaker.IdNumber;
import com.github.javafaker.Number;
import com.jerry.rpccore.model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/11 16:38
 * @注释 faker生成数据
 */
public class TestMain {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();


        Faker instance = Faker.instance(Locale.CHINA);

        long l = instance.number().randomNumber(1, true);
        System.out.println(l);
//        for (int i = 0; i < 10; i++) {
//            User build = User.builder()
//                    .id(i).name(instance.name().fullName())
//                    .age(instance.number().randomDigitNotZero())
//                    .address(instance.address().city() + instance.address().streetName() + instance.address().streetAddress())
//                    .phone(instance.phoneNumber().cellPhone())
//                    .build();
//            System.out.println(build);
//
//            //插入数据库
//            //....
////            userservice.save(build);
//            users.add(build);
//        }
//        //写入文件
//        saveUsersToFile(users);
    }

    /***
     * 写入csv文件
     * @param users
     */
    private static void saveUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv"))) {
            writer.write("ID,Name,Phone,Age,Address\n");
            for (User user : users) {
                writer.write(String.format("%d,%s,%s,%d,%s\n",
                        user.getId(),
                        user.getName(),
                        user.getPhone(),
                        user.getAge(),
                        user.getAddress()));
            }
            writer.flush();
            System.out.println("Users saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
