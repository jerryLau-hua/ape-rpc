package com.jerry.rpccore.otherMainTast;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

import java.util.Date;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/5/8 15:39
 * @注释
 */
public class TimerTaskTestMain {
    public static void main(String[] args) {

        CronUtil.schedule("*/10 * * * * *", new Task() {
            @Override
            public void execute() {
                System.out.println("---触发定时---" + new Date());
            }
        });

        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }
}
