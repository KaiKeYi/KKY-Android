package xyz.yikai.kky.web;

/**
 * @Author: Jason
 * @Time: 2018/4/24 11:43
 * @Description:公共请求的要实现的接口
 */
public interface IRequestResultListener {
	
	// 请求成功的返回数据
	void onSuccess(String data, ActionType...tag);

	// 请求失败的返回提示
	void onFail(String msg, ActionType...tag);
}
