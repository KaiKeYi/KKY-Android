package com.xlg.library.widget.pullview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xlg.library.R;
import com.xlg.library.utils.UiThreadHandler;

public class LoadMoreView extends LinearLayout {

	private View mFootView;
	private TextView mNoMoreDataText;

	private View mMoreBtn;
	private LinearLayout mProgressBar;

	public LoadMoreView(Context context) {
		super(context);

	}

	public LoadMoreView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	private void initView() {

		mFootView = findViewById(R.id.lv_include_footview);
		mNoMoreDataText = (TextView) findViewById(R.id.tv_no_more_data);
		mMoreBtn = findViewById(R.id.lv_more_lay);
		// findViewById(R.id.frame_more_click).setOnClickListener(this);
		mProgressBar = (LinearLayout) findViewById(R.id.lv_more_progress);
	}

	@Override
	protected void onFinishInflate() {

		super.onFinishInflate();

		initView();
	}

	public void showFootView() {
		mFootView.setVisibility(View.VISIBLE);
		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mProgressBar.setVisibility(View.GONE);
				mNoMoreDataText.setVisibility(View.GONE);
				mMoreBtn.setVisibility(View.VISIBLE);
			}
		}, 100);
	}

	public void removeFootView(final String noMoreContent) {

		mMoreBtn.setVisibility(View.GONE);
		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mProgressBar.setVisibility(View.GONE);
				mNoMoreDataText.setVisibility(View.VISIBLE);
				mNoMoreDataText.setText(noMoreContent);
			}
		}, 200);
	}

	public void showMoreLoadingData() {

		if (mMoreBtn.getVisibility() == View.VISIBLE) {

			mMoreBtn.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			mNoMoreDataText.setVisibility(View.GONE);

		}
	}

	public void reSetMoreViewState() {

		try {
			mMoreBtn.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			mNoMoreDataText.setVisibility(View.GONE);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
