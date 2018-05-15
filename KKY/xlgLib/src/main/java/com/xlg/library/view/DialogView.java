package com.xlg.library.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import com.xlg.library.R;
import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.UiThreadHandler;

public class DialogView extends LinearLayout {

    private View popView;
    private boolean isDismiss;
    private int durationTime = 200;

    public DialogView( boolean showCancel, String content, String ok,IDialogSelectListener listener) {
        super(BaseApp.getAppContext());
        onDialogSelectListener = listener;
        initView(showCancel, content,ok);
    }

    private void initView( boolean showCancel, String content, String ok){

        String title = "提示";
        String cancel = "取消";

        LayoutInflater.from(BaseApp.getAppContext()).inflate(R.layout.include_common_dialog, this);

        findViewById(R.id.rv_common_dialog).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        popView = findViewById(R.id.lay_common_dialog);
        popView.setOnClickListener(this);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(content);
        TextView btn_cancle = (TextView) findViewById(R.id.btn_cancle);
        TextView btn_sure0 = (TextView) findViewById(R.id.btn_sure0);
        TextView btn_sure1 = (TextView) findViewById(R.id.btn_sure1);
        View view1 = findViewById(R.id.layout_single);
        View view2 =  findViewById(R.id.layout_double);


        if (showCancel) {

            view1.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            btn_sure1.setText(ok);
            btn_sure1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDialogSelectListener.onSelected(true);
                }
            });
            btn_cancle.setText(cancel);
            btn_cancle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDialogSelectListener.onSelected(false);
                }
            });

        } else {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            btn_sure0.setText(ok);
            btn_sure0.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDialogSelectListener.onSelected(true);
                }
            });
        }
    }

    private void showDialog() {

        AlphaAnimation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
        myAnimation_Alpha.setDuration(durationTime);
        startAnimation(myAnimation_Alpha);


        UiThreadHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                setVisibility(View.VISIBLE);
            }
        }, durationTime);

    }

    private IDialogSelectListener onDialogSelectListener;

    public interface IDialogSelectListener {
        void onSelected(boolean select);
    }
}
