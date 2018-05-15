package com.xlg.library.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.WindowManager;
import com.xlg.library.R;
import com.xlg.library.dialog.CustomDialog;

/**
 * Dialog管理类
 */
public class DialogManager {

    private Context mContext;
    private Dialog mCurrnetDialog;
    private Handler mHanlder;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    public DialogManager(Context mContext, Handler mHanlder) {
        this.mContext = mContext;
        this.mHanlder = mHanlder;
    }

    public void CancelCurrentDialog() {
        if (mCurrnetDialog != null)
            mCurrnetDialog.dismiss();
        mCurrnetDialog = null;
    }


    /**
     * 只有一个按钮的dialog
     *
     * @param msg
     */
    public void showTitleAndMsg(String msg) {
        mCurrnetDialog = new CustomDialog.Builder(mContext).setTitle(R.string.notice).setMessage(msg).setCancelable(false)
                .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CancelCurrentDialog();
                    }
                }).create();
        try {
            mCurrnetDialog.show();
        } catch (WindowManager.BadTokenException e) {
            CancelCurrentDialog();
        }
    }

    /**
     * 两个按钮的dialog
     */
    public void showNormalDialog(String message, String positive, String negative, boolean cancelable, final onDialogClick onDialogClick) {
        mCurrnetDialog = new CustomDialog.Builder(mContext).setTitle(R.string.notice).setMessage(message).setCancelable(cancelable)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        CancelCurrentDialog();
                        if (onDialogClick != null) {
                            onDialogClick.onConfirm();
                        }

                    }
                }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        CancelCurrentDialog();
                        if (onDialogClick != null) {
                            onDialogClick.onCancle();
                        }
                    }
                }).create();
        mCurrnetDialog.show();
    }



    public interface onDialogClick {
        void onConfirm();

        void onCancle();
    }
}
