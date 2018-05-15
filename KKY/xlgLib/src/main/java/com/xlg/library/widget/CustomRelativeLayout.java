package com.xlg.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CustomRelativeLayout extends FrameLayout{

	public static final byte KEYBOARD_STATE_SHOW = -3;  
	public static final byte KEYBOARD_STATE_HIDE = -2;  
	public static final byte KEYBOARD_STATE_INIT = -1;  
	
	public  boolean mHasInit;//是否进行过初始化，刚进来的时候为false
	private boolean mHasKeybord; //键盘是否显示，true为显示 
	private int mHeight;  //目前布局底部距离屏幕顶部的距离
	private onKybdsChangeListener mListener;  

	public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {  
		super(context, attrs, defStyle);  
	}  

	public CustomRelativeLayout(Context context, AttributeSet attrs) {  
		super(context, attrs);  
	}  

	public CustomRelativeLayout(Context context) {  
		super(context);  
	}  
	/** 
	 * set keyboard state listener 
	 */  
	 public void setOnkbdStateListener(onKybdsChangeListener listener){  
		 mListener = listener;  
	 }  
	 @Override  
	 protected void onLayout(boolean changed, int l, int t, int r, int b) {  
		 super.onLayout(changed, l, t, r, b); 
		 if (!mHasInit) {  
			 mHasInit = true;  
			 mHeight = b;  
			 if (mListener != null) {  
				 mListener.onKeyBoardStateChange(KEYBOARD_STATE_INIT);  
			 }  
		 } else {
			 mHeight = mHeight < b ? b : mHeight;  
		 }
		 if (mHasInit && mHeight > b) {  
			 mHasKeybord = true;  
			 if (mListener != null&& Math.abs(mHeight-b)>100) {  
				 mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
			 }  
		 }  
		 if (mHasInit && mHasKeybord && Math.abs(mHeight-b)<100) {  
			 mHasKeybord = false;  
			 if (mListener != null) {  
				 mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);  
			 }  
		 }  
	 }  
	 
	public interface onKybdsChangeListener{  
		 public void onKeyBoardStateChange(int state);  
	 }  

}