package com.liuyibo.lovebill.utils.redis.sharded;

import com.liuyibo.lovebill.utils.io.PropertiesFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * 读取redis配置
 * 
 * @author zhengzhiyuan
 * @since May 20, 2016
 */
public class SharedRedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(SharedRedisConfig.class);
    private static final String DEFAULT_REDIS_PROPERTIES = "redis";

    public static String getConfigProperty(String key) {
        return PropertiesFileUtil.getInstance(DEFAULT_REDIS_PROPERTIES).get(key);
    }
}
