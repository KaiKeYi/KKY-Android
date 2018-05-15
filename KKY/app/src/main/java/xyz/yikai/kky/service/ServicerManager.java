package xyz.yikai.kky.service;

import com.xlg.library.service.BaseServiceManager;

/**
 * @Author: Jason
 * @Time: 2018/4/26 21:21
 * @Description:接口服务管理器
 */
public class ServicerManager extends BaseServiceManager {

    public ServicerManager() {
        super();

        initServices();
    }

    @Override
    public Object getService(String name) {
        return super.getService(name);
    }

    @Override
    public void releaseAll() {
        super.releaseAll();
    }

    /**
     * 初始化所有服务
     */
    private void initServices() {

        //注册图片处理类
        //registService(ProcessPhotoService.ServiceName, new ProcessPhotoServiceImpl());
    }
}
