package xyz.yikai.kky.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.xlg.library.R;
import com.xlg.library.network.NetBroadCastReciver;
import com.xlg.library.utils.ToastUtil;

/**
 * 基类Fragment.
 */
public class BaseFragment extends Fragment implements
        BaseListener, NetBroadCastReciver.INetRevicerListener {

    /**
     * 屏幕宽度.
     */
    protected int width;
    /**
     * 屏幕高度.
     */
    protected int height;

    /**
     * 网络监听器
     */
    private NetBroadCastReciver mNetReceiver;

    /**
     * 网络是否连接.
     */
    public boolean isNetConnect=false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // 检测网络状态
        checkNetState();

        // 获取屏幕宽高
        getDisplay();
    }

    /**
     * 检测网络状态.
     */
    private void checkNetState() {
        mNetReceiver = new NetBroadCastReciver();
        mNetReceiver.setNetRevicerListener(this, null);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mNetReceiver, mFilter);
    }

    /**
     * 获取屏幕宽高.
     */
    private void getDisplay() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    /**
     * 关闭Activity.
     */
    protected void finish() {
        getActivity().finish();
    }

    @Override
    public void onToggle() {

    }

    @Override
    public void onSkipSet() {
        getActivity().startActivity(new Intent(Settings.ACTION_SETTINGS));
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        releaseNetObserver();
    }

    private void releaseNetObserver() {
        if (null != mNetReceiver && null != getActivity()) {
            getActivity().unregisterReceiver(mNetReceiver);
            mNetReceiver = null;
        }
    }

    @Override
    public void onNetReceiverSucc(Object data) {
        isNetConnect=true;
    }

    @Override
    public void onNetReceiverFailure() {}

    @Override
    public void onTitleBarLeftClick() {
        finish();
    }

    @Override
    public void onTitleBarRightClick() {}

    @Override
    public void onClick(int index, Object... obj) {}
}
