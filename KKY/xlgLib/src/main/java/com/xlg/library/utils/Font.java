package com.xlg.library.utils;

import android.graphics.Typeface;

import com.xlg.library.base.BaseApp;

import java.lang.reflect.Field;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:37
 * @Description:字体工具类
 */
public class Font {

    static Typeface typeface;
    public static Typeface setFont(){
        if(typeface == null) {
            try {
                typeface = Typeface.createFromAsset(BaseApp.getAppContext().getAssets(), "font/avanti.ttf");
                if(typeface == null)return null;
                Class cl = Typeface.class;
                Field aDefault = cl.getDeclaredField("SANS_SERIF");
                aDefault.setAccessible(true);
                aDefault.set(typeface, typeface);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return typeface;
    }

    static Typeface typeface_b;
    public static Typeface setFont_Bold(){
        if(typeface_b == null) {
            try {
                typeface_b = Typeface.createFromAsset(BaseApp.getAppContext().getAssets(), "font/avanti_bold.ttf");
                if(typeface_b == null)return null;
                Class cl = Typeface.class;
                Field aDefaultBold = cl.getDeclaredField("DEFAULT_BOLD");
                aDefaultBold.setAccessible(true);
                aDefaultBold.set(typeface_b, typeface_b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return typeface_b;
    }
}
