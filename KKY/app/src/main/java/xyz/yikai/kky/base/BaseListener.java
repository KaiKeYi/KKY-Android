package xyz.yikai.kky.base;

/**
 * 基类Listener.
 */
public interface BaseListener {

	/**
	 * 打开侧滑菜单.
	 */
	void onToggle();

	/**
	 * 跳转系统设置.
	 */
	void onSkipSet();

	/**
	 * 标题栏左按钮点击.
	 */
	void onTitleBarLeftClick();

	/**
	 * 标题栏右按钮点击.
	 */
	void onTitleBarRightClick();

	/**
	 * 点击事件.
	 *
	 * @param index the index
	 * @param obj   the obj
	 */
	void onClick(int index, Object... obj);
}
