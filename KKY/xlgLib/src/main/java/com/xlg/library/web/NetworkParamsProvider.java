package com.xlg.library.web;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.CommonUtil;

/**
 *  手机参数
 */
public class NetworkParamsProvider {
	private static NetworkParamsProvider myParamsProvider = new NetworkParamsProvider();
	private SystemInfo systemInfo;
	private NetworkParamsProvider(){
		systemInfo = new SystemInfo();
	}
	public static NetworkParamsProvider getInstance(){
		if(myParamsProvider == null){
			myParamsProvider = new NetworkParamsProvider();
		}
		return myParamsProvider;
	}

	public SystemInfo getSystemInfo(){
		if(systemInfo == null){
			systemInfo = new SystemInfo();
		}
		return systemInfo;
	}
	public class SystemInfo {
		public String appVersion;
		public String phoneModel;
		public String versionSystem;
		public String imei;
		public String imsi;

		public SystemInfo() {
			TelephonyManager tm = (TelephonyManager) BaseApp.getAppContext()
					.getSystemService(
							BaseApp.getAppContext().TELEPHONY_SERVICE);
			appVersion = CommonUtil
					.getApplicationVersion(BaseApp.getAppContext());
			phoneModel = android.os.Build.MODEL;// 手机型号
			versionSystem = android.os.Build.VERSION.RELEASE;// 系统的版本号

			imei = tm.getDeviceId();// String
			if (TextUtils.isEmpty(imei)) {
				 imei = "";
			}
			/*
			 * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a GSM phone.
			 * 需要权限：READ_PHONE_STATE
			 */
			imsi = tm.getSubscriberId();// String
			if (TextUtils.isEmpty(imsi)) {
				imsi = "";
			}
		}
	}

}
