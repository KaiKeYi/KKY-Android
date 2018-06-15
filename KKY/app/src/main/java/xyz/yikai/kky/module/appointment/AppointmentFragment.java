package xyz.yikai.kky.module.appointment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.yikai.kky.R;
import xyz.yikai.kky.base.BaseFragment;
import xyz.yikai.kky.module.appointment.view.AppointmentView;

/**
 * The type Appointment Fragment.
 *
 * @Author: Jason
 * @Time: 2018 /4/24 15:37
 * @Description:预约
 */
public class AppointmentFragment extends BaseFragment {

    AppointmentView mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = new AppointmentView(view, "预约");
        mView.setBaseListener(this);
    }
}
