package cn.com.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString().replace("-","");
        return randomUUIDString;
    }

}
