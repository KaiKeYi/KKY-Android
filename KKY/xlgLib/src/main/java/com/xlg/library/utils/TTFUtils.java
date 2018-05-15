package com.xlg.library.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:13
 * @Description:
 */
public class TTFUtils {
    public static TTFUtils instance;
    static Typeface fontFace;

    private TTFUtils() {
        instance = this;
    }

    public static TTFUtils getInstance(Context context) {
        if(instance == null) {
            instance = new TTFUtils();
            fontFace = Typeface.createFromAsset(context.getAssets(), "fonts/DS-Digital.ttf");
        }

        return instance;
    }

    public static void setTTF(Context context, TextView textView, int size) {
        textView.setTypeface(fontFace);
        textView.setTextSize((float)size);
    }
}
