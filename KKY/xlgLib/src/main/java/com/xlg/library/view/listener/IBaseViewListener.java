package com.xlg.library.view.listener;

public interface IBaseViewListener {

	void onToggle();
	
	void onSkipSet();
	
	void onTitleBarRightClick();
	
	void onTitleBarLeftClick();
	
	void onClick(int index, Object... obj);
}
