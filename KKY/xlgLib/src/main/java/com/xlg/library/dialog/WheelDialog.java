package com.xlg.library.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.xlg.library.R;
import com.xlg.library.base.BaseApp;
import com.xlg.library.dialog.wheel.LoopListener;
import com.xlg.library.dialog.wheel.LoopView;
import com.xlg.library.utils.CommonUtil;
import com.xlg.library.utils.UiThreadHandler;

import java.util.ArrayList;

/**
 * @category滑轮对话框
 * @author hu
 * 
 */
public class WheelDialog {

	private View mDialogView;
	private LoopView loopView;
	private View mContentView;

	private IWheelItemListener mItemListener;
	private static WheelDialog mDialog;

	private boolean isDismiss;
	private WheelType[] mType;

	private WheelDialog() {

	}

	public static WheelDialog getInstance() {
		if (null == mDialog) {
			mDialog = new WheelDialog();
		}
		return mDialog;
	}

	public void setItemListener(IWheelItemListener itemListener) {
		this.mItemListener = itemListener;
	}

	/**
	 * @Description:  传入索引位置，数据源，显示滑轮弹框
	 * @Author:  Jason
	 * @Time:  2018/3/7 09:37
	 */
	public synchronized void showDialog(int postion, ArrayList<String> list,
										WheelType ... type) {

		Activity app = CommonUtil.getCurrentActivity();
		if (null != app) {
			isDismiss = false;
			this.mType = type;
			initDialog(app, postion, list);
		}
	}

	private void initDialog(Activity app, int postion, ArrayList<String> list) {

		if (null == mDialogView) {

			LayoutInflater inflater = LayoutInflater.from(BaseApp.getAppContext());
			mDialogView = inflater.inflate(R.layout.include_wheel_dialog, null);
			mDialogView.setOnClickListener(new OnWhellClick());

			TextView okText = (TextView) mDialogView.findViewById(R.id.tv_ok);
			okText.setOnClickListener(new OnWhellClick() {
				@Override
				public void onClick(View view) {
					loopView.setListener(new LoopListener() {
						@Override
						public void onItemSelect(int item) {
							mItemListener.onWheelItemClick(
									item,mType);
						}
					});
					dismiss();
				}
			});

			mContentView = mDialogView.findViewById(R.id.lv_wheelView);
			LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			loopView = new LoopView(app);
			loopView.setNotLoop();
			//设置原始数据
			loopView.setArrayList(list);
			//设置初始位置
			loopView.setPosition(postion);
			//设置字体大小
			loopView.setTextSize(18);

			LinearLayout wheelView = (LinearLayout) mDialogView.findViewById(R.id.rootview);
			wheelView.addView(loopView);
			app.addContentView(mDialogView, params);
			showAnimaration();
		}
		app = null;
	}

	public void dismiss() {

		if (!isDismiss) {
			isDismiss = true;
			Animation animation = AnimationUtils.loadAnimation(
					BaseApp.getAppContext(), R.anim.translate_buttom);
			mContentView.startAnimation(animation);
		}

		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mDialogView.setVisibility(View.GONE);
				mDialogView = null;
				isDismiss = false;
			}
		}, 500);
		
	}

	public boolean isShowing() {
		return mDialogView != null;
	}

	public interface IWheelItemListener {
		void onWheelItemClick(int postion, WheelType ... type);
	}

	class OnWhellClick implements OnClickListener {

		public OnWhellClick() {

		}

		@Override
		public void onClick(View arg0) {

		}
	}

	public void showAnimaration() {

		Animation animation = AnimationUtils.loadAnimation(BaseApp.getAppContext(),
				R.anim.translate_top);
		mContentView.startAnimation(animation);
		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mDialogView.setVisibility(View.VISIBLE);
			}
		}, 350);
	}

	public enum WheelType {

		_WHEEL_DEFAULT_

	}
}
