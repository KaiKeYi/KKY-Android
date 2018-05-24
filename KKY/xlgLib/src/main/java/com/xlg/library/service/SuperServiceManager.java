package com.xlg.library.service;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:57
 * @Description:
 */
public interface SuperServiceManager {

    Object getService(String var1);

    void releaseAll();

    void registService(String var1, Object var2);

    void unRegistService(String var1);
}
