package xyz.yikai.kky.service;

import com.xlg.library.utils.UiThreadHandler;
import xyz.yikai.kky.web.ActionType;

/**
 * @Author: Jason
 * @Time: 2018/4/25 15:52
 * @Description:
 */
public class ServicerObserver {

    private IServicerObserveListener mObserverListener;

    public void setObserverListener(IServicerObserveListener observerListener) {
        this.mObserverListener = observerListener;
    }

    public void observerSucc(final Object data, final ActionType...tag) {
        UiThreadHandler.post(new Runnable() {

            @Override
            public void run() {
                mObserverListener.onSuccess(data, tag);
            }
        });
    }

    public void observerFailure(final String msg, final ActionType...tag) {
        UiThreadHandler.post(new Runnable() {

            @Override
            public void run() {
                mObserverListener.onFailure(msg, tag);
            }
        });
    }
}
