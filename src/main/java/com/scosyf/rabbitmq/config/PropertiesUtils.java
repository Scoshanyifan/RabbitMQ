package com.scosyf.rabbitmq.config;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    
    private static Properties props;
    
    static {
        String propFile = "config.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(
                    PropertiesUtils.class.getClassLoader().getResourceAsStream(propFile), 
                    Charset.forName("utf-8"))
                    );
            logger.info("配置文件导入成功...");
        } catch (Exception e) {
            logger.error("配置文件读取失败, " + e);
        }
    }
    
    public static String getProperty(String key, String defValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isNotBlank(value)) {
            return value.trim();
        }
        return defValue;
    }
    
    public static int getPropertyInt(String key, int defValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isNotBlank(value)) {
            return Integer.parseInt(value.trim());
        }
        return defValue;
    }
    
    public static boolean getPropertyBoolean(String key, boolean defValue) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isNotBlank(value)) {
            return Boolean.parseBoolean(value.trim());
        }
        return defValue;
    }
}
