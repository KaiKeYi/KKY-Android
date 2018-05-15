package com.xlg.library.widget.pullview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.xlg.library.R;

public class PullListView extends ListView implements IPullStateListener {

    private ILoadMoreListener listener;
    private LoadMoreView moreView;
    private View footView;

    public void setLoadMoreListener(ILoadMoreListener listener) {
        this.listener = listener;
    }

    public PullListView(Context context) {
        super(context);
        setScrollListener();
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScrollListener();
    }

    public PullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setScrollListener();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0) {
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }

    private void setScrollListener() {

        setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        showMorData();
                        listener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        dispalayFootView();

    }

    public void dispalayFootView() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            footView = LayoutInflater.from(getContext()).inflate(
                    R.layout.include_pull_loadmore, null);

            moreView = (LoadMoreView) footView.findViewById(R.id.moreview);
            addFooterView(footView);
        }
    }

    public void showFootView() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            moreView.showFootView();
        }
    }

    public void hideFootView(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            moreView.setVisibility(View.GONE);
            removeFooterView(footView);
        }
    }

    private void showMorData() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            moreView.showMoreLoadingData();
        }
    }

    public void removeFootView(int adpaterCount, int count, String content) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            if (adpaterCount >= count) {

                moreView.removeFootView(content);
            } else {

                moreView.removeFootView("");
            }
        }
    }


}
