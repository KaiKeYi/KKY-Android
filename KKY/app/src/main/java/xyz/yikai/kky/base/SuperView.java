package xyz.yikai.kky.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.xlg.library.widget.pullview.PullListView;
import com.xlg.library.widget.pullview.PullToRefreshLayout;

public abstract class SuperView extends android.widget.LinearLayout {

    public SuperView(Context context) {
        super(context);
        initTitleParams();
    }

    public SuperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    void initTitleParams() {

        setFitsSystemWindows(true);
    }

    public abstract PullListView getListView();
    public abstract PullToRefreshLayout getRefreshLayout();
    public abstract void startLoading(boolean ... isCovered);
}
