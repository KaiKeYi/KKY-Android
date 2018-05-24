package xyz.yikai.kky.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.xlg.library.widget.pullview.PullListView;
import com.xlg.library.widget.pullview.PullToRefreshLayout;

public abstract class SuperLinearlayout extends android.widget.LinearLayout {

    public SuperLinearlayout(Context context) {
        super(context);
        initTitleParams();
    }

    public SuperLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    void initTitleParams() {

        setFitsSystemWindows(true);
    }

    public abstract PullListView getListView();
    public abstract PullToRefreshLayout getRefreshLayout();
    public abstract void startLoading(boolean ... isCovered);
}
