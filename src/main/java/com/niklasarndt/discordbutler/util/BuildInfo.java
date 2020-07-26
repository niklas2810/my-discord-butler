package com.niklasarndt.discordbutler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Niklas on 2020/07/25
 */
public class BuildInfo {

    public static String NAME = "UNKNOWN";
    public static String DESCRIPTION = "UNKNOWN";
    public static String VERSION = "UNKNOWN";
    public static String TARGET_JDK = "UNKNOWN";
    public static String TIMESTAMP = "UNKNOWN";
    public static String URL = "UNKNOWN";


    static {
        Logger logger = LoggerFactory.getLogger(BuildInfo.class);
        try {
            Properties properties = new Properties();
            properties.load(BuildInfo.class.getClassLoader().getResourceAsStream("build.properties"));

            NAME = properties.getProperty("build.name");
            DESCRIPTION = properties.getProperty("build.description");
            VERSION = properties.getProperty("build.version");
            TARGET_JDK = properties.getProperty("build.targetJdk");
            TIMESTAMP = properties.getProperty("build.timestamp");
            URL = properties.getProperty("build.url");
        } catch (IOException e) {
            logger.error("Can not load build.properties", e);
        }
    }
}
