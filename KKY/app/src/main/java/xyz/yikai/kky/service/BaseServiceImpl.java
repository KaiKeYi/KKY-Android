package xyz.yikai.kky.service;

import com.xlg.library.service.BaseService;
import xyz.yikai.kky.web.ActionType;
import xyz.yikai.kky.web.IRequestResultListener;

/**
 * @Author: Jason
 * @Time: 2018/4/25 21:21
 * @Description:接口实现基类
 */
public abstract class BaseServiceImpl implements BaseService,
        IRequestResultListener {

    protected ServicerObserver observer = new ServicerObserver();

    @Override
    public void releaseAll() {
        observer = null;
    }

    @Override
    public void onSuccess(String data, ActionType ... tag) {

    }

    @Override
    public void onFail(String code, ActionType ... tag) {

    }
}
