package com.xlg.library.base;

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
import com.xlg.library.receiver.NetBroadCastReciver;
import com.xlg.library.utils.ToastUtil;
import com.xlg.library.view.LinearLayout;
import com.xlg.library.view.listener.IBaseViewListener;

public class SupFragment extends Fragment implements
        IBaseViewListener, NetBroadCastReciver.INetRevicerListener {

    protected int width;
    protected int height;

    /**
     * 网络监听器
     */
    private NetBroadCastReciver mNetReceiver;

    public boolean isNetConnect=false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        checkNetState();
        getDisplay();
    }

    private void checkNetState() {
        mNetReceiver = new NetBroadCastReciver();
        mNetReceiver.setNetRevicerListener(this, null);
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mNetReceiver, mFilter);
    }

    private void getDisplay() {
        DisplayMetrics metrics = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
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
    public void onNetReceiverFailure() {
    }

    @Override
    public void onTitleBarLeftClick() {
        finish();
    }

    @Override
    public void onTitleBarRightClick() {

    }

    @Override
    public void onClick(int index, Object... obj) {

    }

    protected void finish() {
        getActivity().finish();
    }


    public void onSuccess(LinearLayout mView) {
        mView.stopLoading(true);

    }

    public void onFailure(String content,LinearLayout mView) {
        if (content!=null && !TextUtils.isEmpty(content)){
            ToastUtil.getInstance().showToast(content);
        }
        mView.stopLoading(false);
    }

    /**
     * 监听titleBar事件
     */
    public interface ITitleListener {

        public void onTitleBarLeftClick();

        public void onTitleBarRightClick();
    }
}
