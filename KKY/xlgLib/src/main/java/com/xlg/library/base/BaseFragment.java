package com.xlg.library.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.xlg.library.R;

public abstract class BaseFragment extends SupFragment {

    /**
     * 显示fragment
     */
    protected void onAddSubFragment(BaseFragment fragment,
                                    boolean... isArrowState) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment).addToBackStack(null);
        if (null != isArrowState && isArrowState.length > 0 && isArrowState[0]) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * 显示fragment
     */
    protected void onReplaceFragment(BaseFragment fragment,
                                     boolean... isArrowState) {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.container, fragment);
        if (null != isArrowState && isArrowState.length > 0 && isArrowState[0]) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * 回退栈顶
     **/
    protected void popBackStack() {

        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();

    }

    public void setTitle(String title){
        ((TextView) getActivity().findViewById(R.id.titileText))
                .setText(title);
    }

    /**
     * 设置标题信息
     *
     * @param listener
     */
    protected void setTitle(final ITitleListener listener, String... param) {

        ((TextView) getActivity().findViewById(R.id.titileText))
                .setText(param[0]);
        View leftImage = getActivity().findViewById(R.id.backBtn);
        leftImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                listener.onTitleBarLeftClick();
            }
        });
        TextView rightText = (TextView) getActivity().findViewById(
                R.id.attach_menu_Text);

        if (null != param && param.length >= 2) {

            rightText.setText(param[1]);
            rightText.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    listener.onTitleBarRightClick();
                }
            });
        }
    }

    public void onBackPressed(){}

}
