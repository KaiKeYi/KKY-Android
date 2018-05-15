package com.xlg.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.drawable.Drawable;

import com.xlg.library.base.BaseApp;

import java.io.InputStream;

/**
 * Res 工具类
 */
public class ResUtil {

    public static int getResIdentifier(String id, String type) {
        return BaseApp.getAppContext().getResources().getIdentifier(id, type, BaseApp.getAppContext().getPackageName());
    }

    public static Bitmap getBitmapById(int resId) {
        InputStream is = BaseApp.getAppContext().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, null);
    }

    public static NinePatch getNinePatchBmpByID(int resId) {
        Bitmap tmpBmp = getBitmapById(resId);
        return new NinePatch(tmpBmp, tmpBmp.getNinePatchChunk(), null);
    }

    public static Drawable getDrawableByID(int resId) {
        return BaseApp.getAppContext().getResources().getDrawable(resId);
    }

    public static String getStringById(int resId) {
        return BaseApp.getAppContext().getResources().getString(resId);
    }


    public static int getColorById(int resId) {
        return BaseApp.getAppContext().getResources().getColor(resId);
    }

    public static float getDeminVal(int redId) {
        return BaseApp.getAppContext().getResources().getDimension(redId);
    }

    public static float getDensity() {
        return BaseApp.getAppContext().getResources().getDisplayMetrics().density;
    }
}
