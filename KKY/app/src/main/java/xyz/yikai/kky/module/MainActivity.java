package xyz.yikai.kky.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import io.github.leibnik.wechatradiobar.WeChatRadioGroup;
import xyz.yikai.kky.R;
import xyz.yikai.kky.base.BaseFragment;
import xyz.yikai.kky.module.appointment.AppointmentFragment;
import xyz.yikai.kky.module.mine.MineFragment;
import xyz.yikai.kky.module.order.OrderFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private WeChatRadioGroup gradualRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        gradualRadioGroup = (WeChatRadioGroup) findViewById(R.id.radiogroup);

        List<BaseFragment> list = new ArrayList<>();

        AppointmentFragment appointmentFragment = new AppointmentFragment();
        OrderFragment orderFragment = new OrderFragment();
        MineFragment mineFragment = new MineFragment();

        list.add(appointmentFragment);
        list.add(orderFragment);
        list.add(mineFragment);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), list));
        gradualRadioGroup.setViewPager(viewPager);
    }

    class PagerAdapter extends FragmentPagerAdapter {
        List<BaseFragment> mData;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fm, List<BaseFragment> data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }
}
