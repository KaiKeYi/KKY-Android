package com.xlg.library.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @Author: Jason
 * @Time: 2018 /5/16 10:53
 * @Description:网络监听器
 */
public class NetBroadCastReciver extends BroadcastReceiver {

	private final String runtimeException = "The method setNetRevicerListener must " +
			"Inject otherwise no effect into the program";
	
	private Object mData;
	private INetRevicerListener mNetRevicerListener;
	
	public void setNetRevicerListener(INetRevicerListener mNetRevicerListener,Object data) {
		
		this.mData = data;
		this.mNetRevicerListener = mNetRevicerListener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if (NetworkUtil.isNetworkAvailable(context)) {
			
			if (null != mNetRevicerListener) {
				mNetRevicerListener.onNetReceiverSucc(mData);
			}else {
				throw new RuntimeException(runtimeException);
			}
			
		}else {
			
			if (null != mNetRevicerListener) {
				mNetRevicerListener.onNetReceiverFailure();
			}else {
				throw new RuntimeException(runtimeException);
			}
		}
	}

	/**
	 * 网络监听成功/失败对外开放接口
	 */
	public static interface INetRevicerListener {
		
		public void onNetReceiverSucc(Object data);
		public void onNetReceiverFailure();
	}
}
