package com.xlg.library.helper;

import android.app.Activity;
import android.text.TextUtils;

import com.xlg.library.cache.SharePreferences;
import com.xlg.library.database.DataBaseManager;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * @Author: Jason
 * @Time: 2018/4/24 16:43
 * @Description:
 */
public class AppHelper {

    private static AppHelper instance;

    public static AppHelper getInstance() {
        if (null == instance) {
            instance = new AppHelper();
        }
        return instance;
    }

    private ArrayList<SoftReference<Activity>> allActivitys;

    public final void addAcitivtys(Activity addActivity) {

        if (null == allActivitys) {
            allActivitys = new ArrayList<SoftReference<Activity>>();
        }
        allActivitys.add(new SoftReference<Activity>(addActivity));
    }

    /**
     *
     * @param conserveName Activity classname 数组
     */
    public final void clearActivitys(String ... conserveName) {

        if (null != allActivitys) {
            for (int i = 0; i < allActivitys.size(); i++) {

                SoftReference<Activity> app = allActivitys.get(i);
                if (null != app && null != app.get()) {

                    Activity activity = app.get();
                    boolean isFinish = true;
                    if (conserveName.length> 0){
                        for (int j=0;j<conserveName.length;j++){
                            if (TextUtils.equals(activity.getClass().getName(),conserveName[j])){//保留指定的activity
                                isFinish = false;
                                break;
                            }
                        }
                    }
                    if (isFinish){
                        activity.finish();
                    }
                }
            }
        }
    }

    /**
     * 退出app,清空数据
     */
    public void exitAppClearData() {
        SharePreferences.clear();
        DataBaseManager.getDataBaseManager().clearDefaultDB();
        clearActivitys();

        //有推送的话，该方法可能会断掉进程
        System.exit(0);
    }
    /**
     * 退出app,清空数据
     */
    public void exitApp() {

        clearActivitys();

        allActivitys.clear();
        allActivitys = null;
    }

    public void clearDatas(String ... className){

        instance = null;
        if (className!=null && className.length>0){
            clearActivitys(className);
        }else{
            clearActivitys();
        }
    }
}
