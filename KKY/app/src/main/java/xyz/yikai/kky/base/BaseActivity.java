package xyz.yikai.kky.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xlg.library.dialog.CommonDialog;
import com.xlg.library.dialog.PopDialog;
import com.xlg.library.dialog.ShapeDialog;
import com.xlg.library.network.NetBroadCastReciver;
import com.xlg.library.utils.WindowUtil;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import xyz.yikai.kky.BuildConfig;
import xyz.yikai.kky.R;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:54
 * @Description:基类Activity
 */
public class BaseActivity extends SuperActivity implements BaseViewListener,
        NetBroadCastReciver.INetRevicerListener, SuperFragment.ITitleListener {

    /**
     * 网络监听器
     */
    private NetBroadCastReciver mNetReceiver;

    protected int width;
    protected int height;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // 窗口自适应键盘
        getWindow()
                .setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                                | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 强制设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getDisplay();

//        // 4.4及以上版本开启
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setNavigationBarTintEnabled(true);
//
//        // 自定义颜色
//        tintManager.setTintColor(Color.parseColor("#FF0000"));
    }

    private void getDisplay() {
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkNetState();

        if(BuildConfig.DEBUG){ //日志开启
//            WindowUtil.showPopupWindow(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseNetObserver();

        if(BuildConfig.DEBUG){ //日志关闭
            WindowUtil.hidePopupWindow();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        startActivityForResult(intent, -1);
    }


    private void checkNetState() {
        try {
            mNetReceiver = new NetBroadCastReciver();
            mNetReceiver.setNetRevicerListener(this, null);
            IntentFilter mFilter = new IntentFilter();
            mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mNetReceiver, mFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {

        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.base_slide_right_out); //页面返回动画
    }

    /**
     * 添加fragment
     */
    protected void onAddSubFragment(BaseFragment fragment,
                                    boolean... isArrowState) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment).addToBackStack(null);
        if (null != isArrowState && isArrowState.length > 0 && isArrowState[0]) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * 替换fragment
     */
    protected void onReplaceFragment(BaseFragment fragment,
                                     boolean... isArrowState) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment);
        if (null != isArrowState && isArrowState.length > 0 && isArrowState[0]) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    private void releaseNetObserver() {
        if (null != mNetReceiver ) {
            unregisterReceiver(mNetReceiver);
            mNetReceiver = null;
        }
    }

    @Override
    public void onNetReceiverSucc(Object data) {
    }

    @Override
    public void onNetReceiverFailure() {
    }

    @Override
    public void onToggle() {
        //YOUMENG
    }

    @Override
    public void onSkipSet() {

    }

    @Override
    public void onTitleBarLeftClick() {
        finish();
    }

    @Override
    public void onTitleBarRightClick() {
        //YOUMENG
    }

    @Override
    public void onClick(int index, Object... obj) {
        //YOUMENG
    }

    @Override
    protected void stopLoading(boolean isSucc) {
        super.stopLoading(isSucc);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (PopDialog.getInstance().isShowing()) {
                PopDialog.getInstance().dismiss();
                return false;
            }

            if (CommonDialog.getInstance().isShowing()) {
                CommonDialog.getInstance().dismiss();
                return false;
            }

            if (ShapeDialog.getInstance().isShowing()){
                ShapeDialog.getInstance().dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
