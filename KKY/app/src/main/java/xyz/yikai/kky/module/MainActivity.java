package xyz.yikai.kky.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xlg.library.helper.AppHelper;
import com.xlg.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import io.github.leibnik.wechatradiobar.WeChatRadioGroup;
import xyz.yikai.kky.R;
import xyz.yikai.kky.base.BaseActivity;
import xyz.yikai.kky.module.appointment.AppointmentFragment;
import xyz.yikai.kky.module.mine.MineFragment;
import xyz.yikai.kky.module.order.OrderFragment;
import xyz.yikai.kky.base.BaseFragment;

/**
 * The type Main Activity.
 *
 * @Author: Jason
 * @Time: 2018 /4/24 15:37
 * @Description:首页
 */
public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private WeChatRadioGroup gradualRadioGroup;
    private long exitTime;

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

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.getInstance().showToast(getString(R.string.press_again_to_exit));
            exitTime = System.currentTimeMillis();
        } else {
            AppHelper.getInstance().exitApp();
        }
    }
}
