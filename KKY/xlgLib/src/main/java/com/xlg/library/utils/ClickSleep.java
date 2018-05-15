package com.xlg.library.utils;

/**
 * Created by Jason on 2018/1/14.
 * 休眠类，防止按钮短时间内重复点击
 */

public class ClickSleep {
    //默认休眠时间
    public static final long DEFAULT_SLEEP_TIME = 1000;
    //线程运行标志位
    private boolean isRuning = false;
    //线程是否正在运行
    public boolean isRuning() {
        return isRuning;
    }
    //运行线程，开始休眠线程
    public void runWithTime(final long defaultSleepTime) {
        isRuning = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(defaultSleepTime, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isRuning = false;
                super.run();
            }
        }.start();
    }
}
