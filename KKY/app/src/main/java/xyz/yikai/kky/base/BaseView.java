package xyz.yikai.kky.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xlg.library.R;
import com.xlg.library.view.ProgressLoadView;
import com.xlg.library.widget.pullview.PullListView;
import com.xlg.library.widget.pullview.PullToRefreshLayout;

/**
 * 基类View.
 *
 * @Author: Jason
 * @Time: 2018 /4/19 10:54
 * @Description:基类View【1、标题栏，2、加载进度条，3、无网提示，4、加载/刷新，5、点击事件】
 */
public class BaseView extends android.widget.LinearLayout implements View.OnClickListener {

    private View mView; //继承BaseView的View
    private View mNetView; //标题栏下面的网络提示视图
    private BaseListener mListener; //点击事件回调
    private ProgressLoadView loadingView; //标题栏下面的进度条

    /**
     * 获取BaseListener.
     *
     * @return BaseListener实例
     */
    public BaseListener getListener() {
        return mListener;
    }

    /**
     * 在BaseFragment子类中设置BaseListener.
     *
     * @param fragment BaseFragment子类
     * @param isStart  是否加载进度条，默认NO
     */
    public void setBaseListener(BaseFragment fragment, boolean... isStart) {

        if (fragment instanceof BaseListener) {
            this.mListener = (BaseListener) fragment;
        }
        if (null != isStart && isStart.length > 0) {
            if (!isStart[0]) {
                return;
            }
        }
        startLoading(isStart);
    }

    /**
     * 在BaseActivity子类中设置BaseListener.
     *
     * @param activity BaseActivity子类
     * @param isStart  是否加载进度条，默认NO
     */
    public void setBaseListener(BaseActivity activity, boolean... isStart) {

        if (activity instanceof BaseListener) {
            this.mListener = (BaseListener) activity;
        }
        if (null != isStart && isStart.length > 0) {
            if (!isStart[0]) {
                return;
            }
        }
        startLoading(isStart);
    }

    /**
     * BaseView初始化.
     *
     * @param context the context
     */
    public BaseView(Context context) {
        super(context);
        setFitsSystemWindows(true);
    }

    /**
     * 设置网络状态.
     *
     * @param visiable 是否有网络
     */
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

    /**
     * 隐藏返回按钮.
     */
    public void hideBackBtn() {
        mView.findViewById(R.id.fl_back).setVisibility(View.GONE);
    }

    /**
     * 设置标题栏信息.
     *
     * @param view  BaseView子类
     * @param param 参数【0~n个】
     */
    protected void setTitleView(View view, String... param) {

        mView = view;

        loadingView = (ProgressLoadView) view
                .findViewById(R.id.regularprogressbar);
        mNetView = view.findViewById(R.id.fl_error_item);

        if (null != mNetView) {
            mNetView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onSkipSet();
                }
            });
        }

        View backView = view.findViewById(R.id.fl_back);
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

        TextView title = (TextView) view.findViewById(R.id.tv_title);
        if (null != title) {
            title.setText(param[0]);
        }

        View rightBtn = view.findViewById(R.id.fl_attach_menu);
        if (null != rightBtn) {
            rightBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onTitleBarRightClick();
                }
            });
        }

        TextView rightText = (TextView) view.findViewById(R.id.tv_attach_menu);
        if (null == rightText) {
            return;
        }

        if (param.length >= 2) {
            rightText.setText(param[1]);
        }
    }

    /**
     * 获取下拉ListView.
     *
     * @return PullListView实例
     */
    public PullListView getListView() {
        return null;
    }

    /**
     * 获取RefreshLayout.
     *
     * @return PullToRefreshLayout实例
     */
    public PullToRefreshLayout getRefreshLayout() {
        return null;
    }

    /**
     * 显示FootView.
     */
    public void showFootView() {
        getListView().showFootView();
    }

    /**
     * 开始加载进度条.
     *
     * @param isCovered 是否覆盖页面
     */
    public void startLoading(boolean... isCovered) {

        if (null != loadingView) {

            loadingView.startLoading(isCovered);
        }
    }

    /**
     * 停止加载进度条.
     *
     * @param isSucc 是否成功
     */
    public void stopLoading(boolean isSucc) {

        if (null != loadingView) {

            loadingView.stopLoading(isSucc);
        }
        if (null != getRefreshLayout()) {

            getRefreshLayout().refreshFinish(isSucc);
        }
    }

    /**
     * 点击事件.
     */
    @Override
    public void onClick(View view) {

    }
}
