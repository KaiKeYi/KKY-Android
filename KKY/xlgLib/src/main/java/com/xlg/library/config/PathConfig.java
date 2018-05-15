package com.xlg.library.config;

import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.SdcardUtil;
import java.io.File;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:08
 * @Description:存储路径工具类
 */
public class PathConfig {

    public static final String BASEPATH = "/KKY/"; //根据实际工程名称修改
    public static final String IAMGE_PATH = "images";
    public static final String IAMGE_THUMB_PATH = "thumbnail";

    /**
     * 获取路径
     *
     * @return
     */
    public static File getBaseDir() {
        File cacheDir = null;
        if (SdcardUtil.isSdcardReadable() || SdcardUtil.isSdcardWritable())
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory() + BASEPATH);
        else if (null != BaseApp.getAppContext().getCacheDir()
                && BaseApp.getAppContext().getCacheDir().exists()) {
            cacheDir = new File(BaseApp.getAppContext().getCacheDir(), BASEPATH);
        } else {
            cacheDir = null;
        }
        if (null != cacheDir && !cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir;
    }

    /**
     * 图片路径
     */
    public static File getImageFile() {

        File parent = getBaseDir();
        File desc = new File(parent, IAMGE_PATH);
        if (!desc.exists())
            desc.mkdirs();
        return desc;
    }

    /**
     * 缩略图片路径
     */
    public static File getThumbImageFile() {

        File parent = getBaseDir();
        File desc = new File(parent, IAMGE_THUMB_PATH);
        if (!desc.exists())
            desc.mkdirs();
        return desc;
    }

    public static String getThmubImaePath() {

        File parent = getBaseDir();
        File desc = new File(parent, IAMGE_THUMB_PATH);
        return desc.getPath();
    }
}
