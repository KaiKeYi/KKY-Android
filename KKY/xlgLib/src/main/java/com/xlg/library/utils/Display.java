package com.xlg.library.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Display {

    //屏幕的宽度，px
    public static int SCREEN_SAMLL = 240;
    public static int SCREEN_NORMAL = 320;
    public static int SCREEN_LARGE = 480;
    public static int SCREEN_XLARGE = 720;
    public static int SCREEN_XXLARGE = 1080;
    public static int SCREEN_XXXLARGE = 1440;

    //屏幕尺寸
    public static double LARGE_SCREEN_SIZE = 6.5;

    public static float density;
    public static int densityDpi;
    public static int widthPixels;
    public static int heightPixels;
    public static Context mContext;

    public static void init(Context context) {
        mContext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        densityDpi = dm.densityDpi;
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int dip2Px(float nDip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, nDip, getMetrics());
    }

    public static int sp2Px(float nSp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, nSp, getMetrics());
    }

    public static int px2Dip(float npx) {
        return (int) npx / getMetrics().densityDpi;
    }

    public static int getScreenWidth() {
        return widthPixels;
    }

    public static int getScreenHeight() {
        return heightPixels;
    }

    public static DisplayMetrics getMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        densityDpi = dm.densityDpi;
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        return dm;
    }

    static private WindowManager getWindowManager() {
        return (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
    }

    public static double getScreenSize() {
        DisplayMetrics dm = getMetrics();
        double size = Math.sqrt((Math.pow((dm.widthPixels / dm.xdpi), 2) + Math.pow((dm.heightPixels / dm.ydpi), 2)));
        return size;
    }

    public static boolean portPriority() {
        double size = getScreenSize();
        if (size > 6.9f)
            return true;

        return size >= 4.69f && Math.min(getScreenHeight(), getScreenWidth()) >= 700;
    }

    public static boolean isLargeSize() {
        return getScreenSize() >= LARGE_SCREEN_SIZE && getScreenWidth() > (SCREEN_XXLARGE - 10);
    }

    public static float getFontScale() {
        float scale = 1.0f;
        try {
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            try {
                Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
                Object config = am.getClass().getMethod("getConfiguration").invoke(am);
                Configuration configs = (Configuration) config;
                scale = configs.fontScale;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            scale = 1.0f;
            e.printStackTrace();
        }
        return scale;
    }
}
