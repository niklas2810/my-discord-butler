package com.niklasarndt.discordbutler.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * Created by Niklas on 2020/07/25
 */
public class BuildInfo {

    public static String NAME;
    public static String DESCRIPTION;
    public static String VERSION;
    public static String TARGET_JDK;
    public static String TIMESTAMP;
    public static String URL;

    static {
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
            System.err.println("Can not load build.properties");
            e.printStackTrace();
        }
        //Set null fields to UNKNOWN
        for (Field field : BuildInfo.class.getDeclaredFields()) {
            try {
                if (field.getType().isAssignableFrom(String.class)
                        && field.getModifiers() == (Modifier.PUBLIC | Modifier.STATIC)) {

                    if (field.get(null) == null) { //No value assigned
                        System.out.format("%S has no value assigned to it, replacing with UNKNOWN\n", field.getName());
                        field.set(null, "UNKNOWN");
                    }
                }
            } catch (Exception e) {
                System.err.println("Can not parse BuildInfo fields");
                e.printStackTrace();
            }
        }
    }
}
