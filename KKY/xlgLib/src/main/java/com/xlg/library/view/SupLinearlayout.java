package com.xlg.library.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.xlg.library.widget.pullview.PullListView;
import com.xlg.library.widget.pullview.PullToRefreshLayout;

public abstract class SupLinearlayout extends android.widget.LinearLayout{

    public SupLinearlayout(Context context) {
        super(context);
        initTitleParams();
    }

    public SupLinearlayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    void initTitleParams() {

        setFitsSystemWindows(true);
    }

    public abstract PullListView getListView();
    public abstract PullToRefreshLayout getRefreshLayout();
    public abstract void startLoading(boolean ... isCovered);
}
