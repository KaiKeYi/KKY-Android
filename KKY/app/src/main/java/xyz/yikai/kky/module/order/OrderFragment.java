package xyz.yikai.kky.module.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xyz.yikai.kky.R;
import xyz.yikai.kky.base.BaseFragment;
import xyz.yikai.kky.module.order.view.OrderView;

/**
 * The type Order Fragment.
 *
 * @Author: Jason
 * @Time: 2018 /4/24 15:37
 * @Description:订单
 */
public class OrderFragment extends BaseFragment {

    OrderView mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = new OrderView(view, "订单");
        mView.setBaseListener(this);
    }

}
