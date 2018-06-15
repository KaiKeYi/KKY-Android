package xyz.yikai.kky.module.order.view;

import android.view.View;

import xyz.yikai.kky.app.App;
import xyz.yikai.kky.base.BaseView;

/**
 * @Author: jason_hzb
 * @Time: 2018/5/31 下午1:46
 * @Company：小灵狗出行
 * @Description:
 */
public class OrderView extends BaseView {

    public OrderView(View view, String title) {
        super(App.getAppContext());

        setTitleView(view,title);
        hideBackBtn();
    }
}
