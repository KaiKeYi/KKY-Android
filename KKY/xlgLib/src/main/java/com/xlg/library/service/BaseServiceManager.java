package com.xlg.library.service;

import java.util.HashMap;

/**
 * @Author: Jason
 * @Time: 2018/4/19 10:59
 * @Description:
 */
public class BaseServiceManager implements IServiceManager {
    private HashMap<String, Object> mServices = new HashMap();

    public BaseServiceManager() {
    }

    public Object getService(String name) {
        return this.mServices.get(name);
    }

    public void releaseAll() {
    }

    public void registService(String name, Object service) {
        if(!this.mServices.containsKey(name)) {
            this.mServices.put(name, service);
        }
    }

    public void unRegistService(String name) {
        this.mServices.remove(name);
    }
}
