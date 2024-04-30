package com.jerry.rpccore.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.jerry.rpccore.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/30 8:54
 * @注释 SPI加载器工具类
 * <p>
 * 加载系统以及用户自定义的SPI实现类
 */
@Slf4j
public class SPIloaderUtils {

    /***
     * 存储已加载的SPI实现类 接口名 -> 实现类名 -> 实现类
     */
    private static Map<String, Map<String, Class<?>>> SPI_MAP = new ConcurrentHashMap<String, Map<String, Class<?>>>();

    /***
     * 存储已实例化的SPI实现类（避免重新创建） 接口名 -> 实现类名 -> 实现类实例
     */
    private static Map<String, Object> SPI_INSTANCE_MAP = new ConcurrentHashMap<String, Object>();


    /***
     * 系统初始化时，加载系统默认的SPI实现类目录
     */
    private static final String SPI_DEFAULT_PATH = "META-INF/rpc/systemd/";


    /***
     * 用户自定义的SPI实现类目录
     */
    private static final String SPI_USER_PATH = "META-INF/rpc/customd/";

    /***
     * 扫描路径
     */
    private static final String[] SCAN_PATHS = {SPI_DEFAULT_PATH, SPI_USER_PATH};

    /***
     * 动态加载的类列表
     */
    private static final List<Class<?>> CLASS_LIST = Arrays.asList(Serializer.class);

    /***
     * 加载所有SPI实现类
     */
    public static void loadAllSPI() {
        for (Class<?> clazz : CLASS_LIST) {
            log.info("load all SPI class");
            loadSPI(clazz);
        }
    }


    /***
     *获取某个接口的实例
     * @param tClass
     * @param KEY
     * @return
     * @param <T>
     */
    public static <T> T getInstance(Class<T> tClass, String KEY) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> stringClassMap = SPI_MAP.get(tClassName);
        if (stringClassMap == null) {
            //未获取到SPI实现类
            throw new RuntimeException(" SPIloader: not found SPI implement class for " + tClassName);
        }
        if (!stringClassMap.containsKey(KEY)) {
            throw new RuntimeException(" SPIloader: tClass " + tClassName + " not found key " + KEY);
        }

        //获取要加载的实现类型
        Class<?> aClass = stringClassMap.get(KEY);
        //判断是否已经实例化过
        String name = aClass.getName();
        if (!SPI_INSTANCE_MAP.containsKey(name)) {
            //未实例化过，实例化并放入缓存
            try {
                Object instance = aClass.newInstance();
                SPI_INSTANCE_MAP.put(name, instance);
            } catch (Exception e) {
                throw new RuntimeException(" SPIloader: create instance error " + e.getMessage());
            }
        }
        return (T) SPI_INSTANCE_MAP.get(name);
    }


    /***
     * 加载SPI实现类
     * @param clazz SPI接口类
     */
    public static Map<String, Class<?>> loadSPI(Class<?> clazz) {
        log.info("load SPI implement class for " + clazz.getName());
        HashMap<String, Class<?>> keyClassMap = new HashMap<>();

        for (String scanPath : SCAN_PATHS) {
            //扫描SPI实现类
            System.out.println("scanPath + clazz.getName():"+scanPath + clazz.getName());
            List<URL> resources = ResourceUtil.getResources(scanPath + clazz.getName());
            for (URL resource : resources) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.startsWith("#")) {
                            continue;
                        }
                        String[] split = line.split(":");
                        if (split.length == 2) {
                            String key = split[0].trim();
                            String className = split[1].trim();
                            try {
                                Class<?> aClass = Class.forName(className);
                                keyClassMap.put(key, aClass);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    bufferedReader.close();
                    inputStreamReader.close();

                } catch (IOException e) {
                    log.error("load SPI implement class error " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        SPI_MAP.put(clazz.getName(), keyClassMap);
        return keyClassMap;
    }


}
