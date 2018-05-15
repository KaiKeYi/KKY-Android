package com.xlg.library.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.xlg.library.R;

/**
 * 弹框辅助类
 *
 * Created by Jason on 2017/11/21.
 */

public class WindowUtil {

    private static Button clearLogBtn;
    private static Button debugBtn;
    private static TextView logTV;
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;
    public static Boolean isShowDebug = false;

    /**
     * 显示弹出框
     *
     * @param context
     */
    public static void showPopupWindow(final Context context) {
        if (isShown) {
            return;
        }
        isShown = true;

        mWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        mView = setUpView(context);

        clearLogBtn.setVisibility(View.GONE);
        logTV.setVisibility(View.GONE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT|Gravity.BOTTOM;
        mWindowManager.addView(mView, params);
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
            isShowDebug = false;
        }
    }

    /**
     * 隐藏/显示调试控件
     */
    public static void showHideDebugView() {
        isShowDebug = !isShowDebug;
        if (isShowDebug==true) {
            showLog();
        } else {
            hideLog();
        }
    }

    public static void showLog() {
        clearLogBtn.setVisibility(View.VISIBLE);
        logTV.setVisibility(View.VISIBLE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.updateViewLayout(mView,params);
    }

    public static void hideLog() {
        clearLogBtn.setVisibility(View.GONE);
        logTV.setVisibility(View.GONE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT|Gravity.BOTTOM;
        mWindowManager.updateViewLayout(mView,params);
    }

    /**
     * 设置调试内容
     */
    public static void setLogTv(String content) {
        if (logTV!=null) {
            logTV.setText(content);
        }
    }

    /**
     * 获取调试内容
     */
    public static String getLogTv() {
        if (logTV!=null) {
            return logTV.getText().toString();
        } else {
            return "";
        }
    }

    private static View setUpView(final Context context) {

        if (mView!=null) {
            return mView;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.window_log_view,
                null);
        debugBtn = (Button) view.findViewById(R.id.debugBtn);
        debugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示/隐藏调试视图
                WindowUtil.showHideDebugView();
            }
        });
        clearLogBtn = (Button) view.findViewById(R.id.clearLogBtn);
        clearLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logTV.setText("");
            }
        });
        logTV = (TextView) view.findViewById(R.id.logTV);// 非透明的内容区域
        logTV.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }
}
