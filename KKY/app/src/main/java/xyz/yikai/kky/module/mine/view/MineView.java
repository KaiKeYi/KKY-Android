package xyz.yikai.kky.module.mine.view;

import android.view.View;

import xyz.yikai.kky.app.App;
import xyz.yikai.kky.base.BaseView;

/**
 * @Author: jason_hzb
 * @Time: 2018/5/31 下午2:06
 * @Company：小灵狗出行
 * @Description:
 */
public class MineView extends BaseView {

    public MineView(View view, String title) {
        super(App.getAppContext());

        setTitleView(view,title);
        hideBackBtn();
    }
}
