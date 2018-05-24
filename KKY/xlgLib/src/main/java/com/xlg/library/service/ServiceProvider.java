package com.xlg.library.service;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:58
 * @Description:
 */
public class ServiceProvider {

    private static ServiceProvider _instance = null;
    private SuperServiceManager mServiceManager = null;

    private ServiceProvider() {
    }

    public static ServiceProvider getServiceProvider() {
        if(_instance == null) {
            _instance = new ServiceProvider();
        }

        return _instance;
    }

    public void registServiceManager(SuperServiceManager sensor) {
        this.mServiceManager = sensor;
    }

    public SuperServiceManager getServiceManager() {
        return this.mServiceManager;
    }
}
