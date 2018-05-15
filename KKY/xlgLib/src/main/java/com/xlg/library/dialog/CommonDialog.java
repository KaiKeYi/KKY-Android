package com.xlg.library.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.xlg.library.R;
import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.CommonUtil;
import com.xlg.library.utils.UiThreadHandler;

public class CommonDialog {

	private View mDialogView;
	private View popView;
	private boolean isDismiss;
	private int durationTime = 200;
	private static CommonDialog dialogHelper;
	private CommonDialogType[] mType;

	public static CommonDialog getInstance() {

		if (null == dialogHelper) {
			dialogHelper = new CommonDialog();
		}
		return dialogHelper;
	}

	private CommonDialog() {
		super();
	}

	public synchronized void showLoading(boolean showCancel, String content,String ok,
										 IDialogSelectListener listener,CommonDialogType ...type) {

		Activity app = CommonUtil.getCurrentActivity();

		if (null != app) {
			isDismiss = false;
			this.onDialogSelectListener = listener;
			this.mType = type;
			showDialogItemContents(app, showCancel, content,ok);
		}
	}

	private void showDialogItemContents(Activity app, boolean showCancel,String content,String ok) {

		String title = "提示";
		String cancel = "取消";

		LayoutInflater inflater = LayoutInflater.from(BaseApp.getAppContext());
		mDialogView = inflater.inflate(R.layout.include_common_dialog, null);

		mDialogView.findViewById(R.id.rv_common_dialog)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
//						onDialogSelectListener.onSelected(false, mType);
//						dismiss();
					}
				});

		popView = mDialogView.findViewById(R.id.lay_common_dialog);
		popView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		TextView tv_title = (TextView) mDialogView.findViewById(R.id.tv_title);
		tv_title.setText(title);
		TextView tv_content = (TextView) mDialogView
				.findViewById(R.id.tv_content);
		tv_content.setText(content);
		TextView btn_cancle = (TextView) mDialogView
				.findViewById(R.id.btn_cancle);
		TextView btn_sure0 = (TextView) mDialogView.findViewById(R.id.btn_sure0);
		TextView btn_sure1 = (TextView) mDialogView.findViewById(R.id.btn_sure1);
		View view1 = mDialogView.findViewById(R.id.layout_single);
		View view2 =  mDialogView.findViewById(R.id.layout_double);
	
		if (showCancel) {
			
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.VISIBLE);
			btn_sure1.setText(ok);
			btn_sure1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onDialogSelectListener.onSelected(true,mType);
					dismiss();
				}
			});
			btn_cancle.setText(cancel);
			btn_cancle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onDialogSelectListener.onSelected(false,mType);
					dismiss();
				}
			});

		}else {

			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.GONE);
			btn_sure0.setText(ok);
			btn_sure0.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onDialogSelectListener.onSelected(true,mType);
					dismiss();
				}
			});
		}

		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

//		app.addContentView(mDialogView, params);

		Window window = app.getWindow();

		window.addContentView(mDialogView, params);

		showDialog();

	}


	public void dismiss() {
		if (mDialogView==null){
			return;
		}
		AlphaAnimation myAnimation_Alpha = new AlphaAnimation(1.0f, 0f);
		myAnimation_Alpha.setDuration(durationTime);
		mDialogView.startAnimation(myAnimation_Alpha);
		
		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				popView.setVisibility(View.GONE);
				mDialogView.setBackgroundResource(0);
				mDialogView.setVisibility(View.GONE);
				mDialogView = null;
				isDismiss = false;
			}
		}, durationTime);

	}

	public boolean isShowing() {
		return mDialogView != null;
	}

	private void showDialog() {

		AlphaAnimation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
		myAnimation_Alpha.setDuration(durationTime);
		mDialogView.startAnimation(myAnimation_Alpha);


		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mDialogView.setVisibility(View.VISIBLE);
			}
		}, durationTime);

	}

	private IDialogSelectListener onDialogSelectListener;

	public interface IDialogSelectListener {
		void onSelected(boolean select,CommonDialogType ... type);
	}

	public enum CommonDialogType {

		_DEFAULT_,
	}
}
