package xyz.yikai.kky.base;

public interface BaseViewListener {

	void onToggle();
	
	void onSkipSet();
	
	void onTitleBarRightClick();
	
	void onTitleBarLeftClick();
	
	void onClick(int index, Object... obj);
}
