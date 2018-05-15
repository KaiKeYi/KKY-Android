package com.xlg.library.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xlg.library.R;
import com.xlg.library.base.BaseApp;

/**
 * @自定义Toast布局
 */
public class ToastUtil {

    private View mDialogView;
    private View mContentView;

    private static ToastUtil mDialog;

    private boolean isDismiss;

    private ToastUtil() {

    }

    public static ToastUtil getInstance() {
        if (null == mDialog) {
            mDialog = new ToastUtil();
        }
        return mDialog;
    }


    /**
     * @param content 显示内容
     * @param resId   资源文件ID
     */
    public synchronized void showToast(final String content, final Integer... resId) {

        final Activity activity = CommonUtil.getCurrentActivity();
        if (null != activity) {
            UiThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    isDismiss = false;
                    initDialog(activity, content, resId);
                }
            });
        }
    }

    private void initDialog(Activity activity, String content, Integer... resId) {

        if (null == mDialogView) {

            LayoutInflater inflater = LayoutInflater.from(BaseApp.getAppContext());
            mDialogView = inflater.inflate(R.layout.include_toast_view, null);
            TextView infoText = (TextView) mDialogView.findViewById(R.id.tv_toast);
            infoText.setText(content);

//            if (null != resId && resId.length > 0) {
//                ImageView srcImage = (ImageView) mDialogView.findViewById(R.id.iv_toast);
//                srcImage.setImageResource(resId[0]);
//            }
            mContentView = mDialogView.findViewById(R.id.lv_toastView);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            activity.addContentView(mDialogView, params);

            CommonUtil.closeSoftPan(activity,infoText);
            showAnimaration();
        }
        activity = null;
    }

    public void destroyDialog() {

        AlphaAnimation myAnimation_Alpha = new AlphaAnimation(1.0f, 0f);
        myAnimation_Alpha.setDuration(300);
        mContentView.startAnimation(myAnimation_Alpha);
        mDialogView.startAnimation(myAnimation_Alpha);

        UiThreadHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mContentView.setVisibility(View.GONE);
                mDialogView.setVisibility(View.GONE);
                mDialogView = null;
                isDismiss = false;
            }
        },300);
    }

    public boolean isShowing() {
        return mDialogView != null;
    }

    public void showAnimaration() {

        AlphaAnimation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
        myAnimation_Alpha.setDuration(300);
        mContentView.startAnimation(myAnimation_Alpha);

        UiThreadHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                destroyDialog();
            }
        }, 1000);
    }
}
