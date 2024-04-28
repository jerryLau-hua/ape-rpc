package com.jerry.rpccore.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2024/4/11 15:49
 * @注释 配置工具类
 */
public class ConfUtils {

    /***
     * 加载配置对象
     * @param tClass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConf(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /***
     *  加载配置对象  支持区分对象
     * @param tClass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder;
        //优先级 .yml >.properties

        //优先读取yml配置文件
        configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            //不带环境
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".yml");
        String configFilePath = configFileBuilder.toString(); // 构建完整的配置文件路径

        // 尝试加载并解析YAML文件
        try (InputStream inputStream = FileUtil.getInputStream(configFilePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> fullConfig = yaml.load(inputStream);

            if (inputStream == null || fullConfig == null) {
                //读取properties配置文件
                //配置文件名
                configFileBuilder = new StringBuilder("application");
                if (StrUtil.isNotBlank(environment)) {
                    //不带环境
                    configFileBuilder.append("-").append(environment);
                }
                configFileBuilder.append(".properties");
                Props props = new Props(configFileBuilder.toString());
//                props.autoLoad(true);
                return props.toBean(tClass, prefix);
            }

            // 读取yml配置文件
            // 过滤出以 prefix 开头的配置项
            Map<String, Object> filteredConfigMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : fullConfig.entrySet()) {
                if (entry.getKey().startsWith(prefix)) {
                    filteredConfigMap.put(entry.getKey(), entry.getValue());
                }
            }
            T configObject = tClass.getDeclaredConstructor().newInstance();
            // 遍历配置项，设置属性
            for (Map.Entry<String, Object> entry : filteredConfigMap.entrySet()) {
                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                for (Map.Entry<String, Object> stringObjectEntry : value.entrySet()) {
                    String fieldName = stringObjectEntry.getKey().substring(0, 1).toUpperCase() + stringObjectEntry.getKey().substring(1);
                    String setterName = "set" + fieldName;
                    try {
                        java.lang.reflect.Method setterMethod = tClass.getMethod(setterName, stringObjectEntry.getValue().toString().getClass());
                        setterMethod.invoke(configObject, stringObjectEntry.getValue().toString());
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to set config value for " + entry.getKey(), e);
                    }
                }
            }
            // 返回配置对象
            return configObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading config: " + configFilePath, e);
        }

    }
}
