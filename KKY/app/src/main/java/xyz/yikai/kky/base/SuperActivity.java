package xyz.yikai.kky.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xlg.library.helper.AppHelper;
import com.xlg.library.service.SuperServiceManager;
import com.xlg.library.service.ServiceProvider;
import com.xlg.library.utils.SystemBarTintManager;
import com.xlg.library.view.ProgressLoadView;

import xyz.yikai.kky.R;

public class SuperActivity extends FragmentActivity {

    protected ProgressLoadView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppHelper.getInstance().addAcitivtys(this);
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            setStatusBar(this, R.color.white);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @TargetApi(19)
    public void setStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
    }


    @Override
    public Object getSystemService(String name) {
        SuperServiceManager manager = ServiceProvider.getServiceProvider()
                .getServiceManager();
        if (manager != null) {
            Object serivce = manager.getService(name);
            if (serivce != null)
                return serivce;
        }
        return super.getSystemService(name);
    }

    protected ProgressLoadView startLoading(boolean... isCovered) {

        if (null == loadingView) {
            loadingView = (ProgressLoadView) findViewById(R.id.regularprogressbar);
        }
        loadingView.startLoading(isCovered);

        return loadingView;
    }

    protected void stopLoading(boolean isSucc) {

        if (null != loadingView) {

            loadingView.stopLoading(isSucc);
        }
    }

    /**
     * 设置标题信息
     *
     * @param listener
     */
    protected void setTitle(final SuperFragment.ITitleListener listener, String... param) {

        findViewById(R.id.backBtn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                listener.onTitleBarLeftClick();
            }
        });
        if (param == null || param.length == 0) {
            return;
        }
        ((TextView) findViewById(R.id.titileText)).setText(param[0]);
        TextView rightText = (TextView) findViewById(R.id.attach_menu_Text);
        if (null == rightText) {
            return;
        }
        if (param.length >= 2) {

            rightText.setText(param[1]);
            rightText.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    listener.onTitleBarRightClick();
                }
            });
        }
    }
}
