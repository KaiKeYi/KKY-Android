package com.xlg.library.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.xlg.library.R;

/**
 * 加载中Dialog
 * 
 * @author hzb
 */
public class LoadingDialog extends AlertDialog {

	private static LoadingDialog loadingDialog;

	public static LoadingDialog getInstance(Context context) {
		if (loadingDialog == null) {
			loadingDialog = new LoadingDialog(context, R.style.TransparentDialog); //设置AlertDialog背景透明
			loadingDialog.setCancelable(false);
			loadingDialog.setCanceledOnTouchOutside(false);
		}
		return loadingDialog;
	}

	public LoadingDialog(Context context, int themeResId) {
		super(context,themeResId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_loading);
	}

	@Override
	public void show() {
		super.show();

		//设置超时自动消失
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(loadingDialog.isShowing()){
					//取消加载框
					dismiss();
				}
			}
		}, 20000);//超时时间20秒
	}
}
