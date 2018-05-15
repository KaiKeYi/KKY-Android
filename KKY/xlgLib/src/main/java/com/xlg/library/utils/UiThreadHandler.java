package com.xlg.library.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:12
 * @Description:
 */
public class UiThreadHandler {
    private static final int LOOP = 1;
    private static final int LOOP_TIME = 2;
    private static Handler uiHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 1:
                    Runnable runnable = (Runnable)msg.obj;
                    this.run(runnable);
                    UiThreadHandler.loop(runnable, msg.arg1);
                    break;
                case 2:
                    UiThreadHandler.LoopHandler handler = (UiThreadHandler.LoopHandler)msg.obj;

                    try {
                        handler.run();
                    } catch (Exception var5) {
                        ;
                    }

                    UiThreadHandler.loop(handler, msg.arg1, --msg.arg2);
            }

        }

        public void run(Runnable runnable) {
            try {
                runnable.run();
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    };
    private static Object token = new Object();

    public UiThreadHandler() {
    }

    public static final boolean post(Runnable r) {
        return uiHandler == null?false:uiHandler.post(new UiThreadHandler.ReleaseRunnable(r));
    }

    public static final boolean postOnce(Runnable r) {
        if(uiHandler == null) {
            return false;
        } else {
            uiHandler.removeCallbacks(r, token);
            return uiHandler.postAtTime(r, token, SystemClock.uptimeMillis());
        }
    }

    public static final boolean postDelayed(Runnable r, long delayMillis) {
        return uiHandler == null?false:uiHandler.postDelayed(new UiThreadHandler.ReleaseRunnable(r), delayMillis);
    }

    public static final Handler getUiHandler() {
        return uiHandler;
    }

    public static final boolean postOnceDelayed(Runnable r, long delayMillis) {
        if(uiHandler == null) {
            return false;
        } else {
            uiHandler.removeCallbacks(r, token);
            return uiHandler.postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis);
        }
    }

    public static void loop(Runnable runnable, int delay) {
        if(uiHandler != null) {
            uiHandler.removeMessages(1);
            Message msg = Message.obtain();
            msg.what = 1;
            msg.obj = runnable;
            msg.arg1 = delay;
            uiHandler.sendMessageDelayed(msg, (long)delay);
        }
    }

    public static void stopLoop() {
        if(uiHandler != null) {
            uiHandler.removeMessages(1);
        }
    }

    public static void loop(UiThreadHandler.LoopHandler handler, int delay, int time) {
        if(uiHandler != null && handler != null) {
            uiHandler.removeMessages(2);
            if(time == 0) {
                handler.end();
            } else {
                Message msg = Message.obtain();
                msg.what = 2;
                msg.obj = handler;
                msg.arg1 = delay;
                msg.arg2 = time;
                uiHandler.sendMessageDelayed(msg, (long)delay);
            }
        }
    }

    public interface LoopHandler {
        void run();

        void end();
    }

    public static class ReleaseRunnable implements Runnable {
        private Runnable mRunnable;

        public ReleaseRunnable(Runnable runnable) {
            this.mRunnable = runnable;
        }

        public void run() {
            if(this.mRunnable != null) {
                try {
                    this.mRunnable.run();
                } catch (Throwable var2) {
                    var2.printStackTrace();
                }
            }

        }
    }
}
