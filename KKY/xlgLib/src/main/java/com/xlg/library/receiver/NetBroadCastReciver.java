package com.xlg.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xlg.library.utils.CommonUtil;

/**
 * 网络监听器
 * @author hu
 *
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

		if (CommonUtil.isNetworkAvailable(context)) {
			
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
	
	public static interface INetRevicerListener{
		
		public void onNetReceiverSucc(Object data);
		public void onNetReceiverFailure();
	}

}
