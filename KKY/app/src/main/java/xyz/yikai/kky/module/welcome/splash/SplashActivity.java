package xyz.yikai.kky.module.welcome.splash;

import android.content.Intent;
import android.os.Bundle;

import xyz.yikai.kky.R;
import xyz.yikai.kky.base.BaseActivity;
import xyz.yikai.kky.module.MainActivity;

/**
 * @Author: jason_hzb
 * @Time: 2018/5/31 下午1:49
 * @Company：小灵狗出行
 * @Description:
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startMiaocheAPP();
    }

    /**
     * 启动秒开服务
     */
    private void startMiaocheAPP() {
        startThread();
    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
