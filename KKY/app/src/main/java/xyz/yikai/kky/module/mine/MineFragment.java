package xyz.yikai.kky.module.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.yikai.kky.R;
import xyz.yikai.kky.base.BaseFragment;
import xyz.yikai.kky.module.mine.view.MineView;
import xyz.yikai.kky.module.order.view.OrderView;

/**
 * The type Mine Fragment.
 *
 * @Author: Jason
 * @Time: 2018 /4/24 15:37
 * @Description:我的
 */
public class MineFragment extends BaseFragment {

    MineView mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = new MineView(view, "我的");
        mView.setBaseListener(this);
    }
}
