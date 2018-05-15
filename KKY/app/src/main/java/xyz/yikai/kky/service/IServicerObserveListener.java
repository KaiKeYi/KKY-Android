package xyz.yikai.kky.service;

import xyz.yikai.kky.web.ActionType;

/**
 * @Author: Jason
 * @Time: 2018/4/25 15:47
 * @Description:
 */
public interface IServicerObserveListener {

    public void onSuccess(Object data, ActionType...tag);
    public void onFailure(String msg, ActionType...tag);
}
