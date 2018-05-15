package com.xlg.library.utils;

import java.util.Map;

public class WebUtil {

    public static String getPath(Map<String, String> map) {

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (value == null) {//判断是否为空
                map.remove(entry);
            }else {
                builder.append(entry.getKey() + "=" + value + "&");
            }
        }
        return builder.toString().substring(0, builder.length() - 1);
    }
}
