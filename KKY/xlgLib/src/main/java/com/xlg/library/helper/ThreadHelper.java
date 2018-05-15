package com.xlg.library.helper;

/**
 * @Author: Jason
 * @Time: 2018/4/20 09:52
 * @Description:
 */
public class ThreadHelper {

    public static void excuteThread(Runnable runnable) {

        Thread thread = new Thread(runnable);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
}
