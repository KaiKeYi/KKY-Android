package xyz.yikai.kky.module.appointment.view;

import android.view.View;

import xyz.yikai.kky.app.App;
import xyz.yikai.kky.base.BaseView;

/**
 * @Author: jason_hzb
 * @Time: 2018/5/31 下午2:11
 * @Company：小灵狗出行
 * @Description:
 */
public class AppointmentView extends BaseView {

    public AppointmentView(View view, String title) {
        super(App.getAppContext());

        setNavView(view,title);
    }
}
