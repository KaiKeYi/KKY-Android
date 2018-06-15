package xyz.yikai.kky.service;

import xyz.yikai.kky.web.ActionType;
import xyz.yikai.kky.web.IRequestResultListener;

/**
 * @Author: Jason
 * @Time: 2018/4/25 21:21
 * @Description:接口实现基类
 */
public abstract class BaseServiceImpl implements IRequestResultListener {

    protected ServicerObserver observer = new ServicerObserver();

    public void releaseAll() {
        observer = null;
    }

    @Override
    public void onSuccess(String data, ActionType ... tag) {}

    @Override
    public void onFail(String code, ActionType ... tag) {}
}
