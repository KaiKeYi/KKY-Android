package xyz.yikai.kky.base;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.xlg.library.R;
import com.xlg.library.utils.CommonUtil;
import com.xlg.library.view.ProgressLoadView;
import com.xlg.library.widget.pullview.PullListView;
import com.xlg.library.widget.pullview.PullToRefreshLayout;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:54
 * @Description:基类LinearLayout
 */
public class BaseLinearLayout extends SuperLinearlayout implements View.OnClickListener {

    private View mNetView;
    private BaseViewListener mListener;
    private ProgressLoadView loadingView;
    private android.widget.LinearLayout supTitleBar;

    public BaseViewListener getListener() {
        return mListener;
    }

    public void setBaseListener(BaseFragment fragment, boolean... isStart) {

        if (fragment instanceof BaseViewListener) {
            this.mListener = (BaseViewListener) fragment;
        }
        if (null != isStart && isStart.length > 0) {
            if (!isStart[0]) {
                return;
            }
        }
        startLoading(isStart);
    }

    public BaseLinearLayout(Context context) {
        super(context);
    }

    public void setNetState(int visiable) {

        if (null != mNetView) {
            mNetView.setVisibility(visiable);
            if (visiable == View.GONE) {
                startLoading();
            } else {
                stopLoading(false);
            }
        }
    }

    protected void setNavView(View view, String... titleContent) {

        loadingView = (ProgressLoadView) view
                .findViewById(R.id.regularprogressbar);

        supTitleBar = (android.widget.LinearLayout) view.findViewById(R.id.titleTopView);

        mNetView = view.findViewById(R.id.fl_error_item);
        if (null != mNetView) {
            mNetView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onSkipSet();
                }
            });
        }

        setTitle(view, titleContent);
    }

    /**
     * 设置标题信息
     *
     * @param view
     */
    protected void setTitle(View view, String... param) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int dp2px = CommonUtil.dp2px(70);
            android.view.ViewGroup.LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, dp2px);
            supTitleBar.setLayoutParams(params);
            supTitleBar.setPadding(0, CommonUtil.dp2px(20), 0, 0);
        }

        View backView = view.findViewById(R.id.backBtn);
        if (null != backView) {
            backView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onTitleBarLeftClick();
                }
            });
        }

        if (param == null) {
            return;
        }

        TextView title = ((TextView) view.findViewById(R.id.titileText));
        if (null != title) {
            title.setText(param[0]);
        }

        View rightBtn = view.findViewById(R.id.attach_menu_frame);
        if (null != rightBtn) {
            rightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onTitleBarRightClick();
                }
            });
        }

        TextView rightText = (TextView) view.findViewById(R.id.attach_menu_Text);
        if (null == rightText) {
            return;
        }

        if (param.length >= 2) {
            rightText.setText(param[1]);
        }
    }

    public int getTitleBarHeight(){
        return supTitleBar.getMeasuredHeight();
    }

    @Override
    public PullListView getListView() {
        return null;
    }

    @Override
    public PullToRefreshLayout getRefreshLayout() {
        return null;
    }

    @Override
    public void startLoading(boolean... isCovered) {

        if (null != loadingView) {

            loadingView.startLoading(isCovered);
        }
    }

    public void stopLoading(boolean isSucc) {

        if (null != loadingView) {

            loadingView.stopLoading(isSucc);
        }
        if (null != getRefreshLayout()) {

            getRefreshLayout().refreshFinish(isSucc);
        }
    }

    public void showFootView() {
        getListView().showFootView();
    }

    @Override
    public void onClick(View view) {

    }
}
