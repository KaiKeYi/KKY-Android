package com.xlg.library.widget.pullview;

public interface IPullStateListener {

	/**
	 * @return true如果可以下拉否则返回false
	 */
	boolean canPullDown();

	/**
	 * @return true如果可以上拉否则返回false
	 */
	boolean canPullUp();

}
