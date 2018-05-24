package com.xlg.library.utils;

import android.util.Log;
import com.xlg.library.BuildConfig;
import java.util.Locale;

/**
 * @Author: Jason
 * @Time: 2018/4/24 17:05
 * @Description:日志打印工具，线上不打印
 */
public class LogUtil {

    public static final boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    private static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }

    public static void v(String mess) {
        if (isDebug()) {
            Log.v(getTag(), buildMessage(mess));
        }
    }

    public static void d(String mess) {
        if (isDebug()) {
            Log.d(getTag(), buildMessage(mess));
        }
    }

    public static void i(String mess) {
        if (isDebug()) {
            Log.i(getTag(), buildMessage(mess));
        }
    }

    public static void w(String mess) {
        if (isDebug()) {
            Log.w(getTag(), buildMessage(mess));
        }
    }

    public static void e(String mess) {
        if (isDebug()) {
            Log.e(getTag(), buildMessage(mess));
        }
    }

    //进一步优化,格式化msg的内容,String.format("Hi,%s", "飞龙");
    //buildMessage(String format, Object... args)
}
