package com.xlg.library.helper;

import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.xlg.library.base.BaseApp;
import com.xlg.library.config.PathConfig;
import com.xlg.library.utils.AppPkgUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:02
 * @Description:崩溃日志
 */
public class CrashHelper {
    private static final String CRASH_FILE_NAME = "crash.txt";

    public CrashHelper() {
    }

    public static void init() {
        final UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                try {
                    CrashHelper.saveCrashLog2File(ex);
                } catch (Throwable var4) {

                }

                if (defaultHandler != null) {
                    defaultHandler.uncaughtException(thread, ex);
                }

            }
        });
    }

    private static File getCrashFile(String basePath) {
        try {
            File parent = Environment.getExternalStorageDirectory();
            File desc = new File(parent + File.separator + basePath, "crash.txt");
            if (!desc.exists()) {
                desc.mkdirs();
            }

            return desc;
        } catch (Throwable var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static void saveCrashLog2File(Throwable ex) {
        File desc = new File(PathConfig.getBaseDir(), "crash.txt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        StringBuffer data = new StringBuffer();
        data.append("ACTION:CRASH");
        data.append("|V:" + AppPkgUtils.getVersionName(BaseApp.getAppContext()));
        data.append("|TS:" + sdf.format(new Date()));
        data.append("|DEV:" + Build.MODEL);
        data.append("|SDK:" + Build.VERSION.RELEASE);
        data.append("|BOARD:" + Build.BOARD);
        data.append("|DAVIKM:" + memoryInfo.dalvikSharedDirty);
        data.append("|NATIVEM:" + memoryInfo.nativeSharedDirty);
        data.append("|OTHERM:" + memoryInfo.otherSharedDirty);
        data.append("|CONTENT:" + Log.getStackTraceString(ex));
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(desc);
            os.write(data.toString().replace("\n", "").getBytes());
        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }
        }
    }
}
