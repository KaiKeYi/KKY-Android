package com.xlg.library.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xlg.library.R;
import com.xlg.library.utils.Display;
import com.xlg.library.utils.ResUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressLint("ResourceAsColor")
public class CustomDialog extends Dialog {

    private static String TAG = CustomDialog.class.getName();
    public static final float DIALOG_KINDNOTE_WIDTH = 0.9f;


    public static final int OKANDCANCLE = 0;
    public static final int OK = 1;

    private TextView textView;
    private TextView titleView;
    private LinearLayout btnLayout;
    private LinearLayout viewLayout;
    public static int sWidth;
    private static List<Button> btns = new ArrayList<Button>();

    public CustomDialog(Context context) {
        super(context, R.style.dialog);
        sWidth = Display.getScreenWidth();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        btns.clear();
        this.init();
    }

    private void init() {
        this.setContentView(R.layout.dialog_custom);
        this.setCancelable(true);
        btnLayout = (LinearLayout) this.findViewById(R.id.btnLayout);
    }

    public void setMsg(int resId) {
        textView = (TextView) this.findViewById(R.id.dialogtext);
        if (textView != null) {
            textView.setText(resId);
        }
    }

    public void setMsg(CharSequence str) {
        textView = (TextView) this.findViewById(R.id.dialogtext);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setView(View view) {
        viewLayout = (LinearLayout) this.findViewById(R.id.viewLayout);
        viewLayout.removeAllViewsInLayout();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewLayout.addView(view, params);
    }

    public void setTitle(CharSequence title) {
        titleView = (TextView) this.findViewById(R.id.dialogtext_title);
        titleView.setText(title);
    }


    public void setTitle(int titleId) {
        titleView = (TextView) this.findViewById(R.id.dialogtext_title);
        titleView.setText(titleId);
    }

    private int btnCount;

    private Button addButton(int msgId, final OnClickListener linster) {
        final CharSequence str = this.getContext().getResources().getString(msgId);
        return addButton(str, linster);
    }


    /**
     * 创建button
     *
     * @return
     */
    private Button addButton(final CharSequence msgId, final OnClickListener linster) {

        Button btn = new Button(this.getContext());
        btn.setId(btnCount);
        btn.setTextSize(16);
        btn.setText(msgId);
        btn.setGravity(Gravity.CENTER);
        if (btnCount == 0) {
            btn.setBackgroundResource(R.drawable.dialog_gray_fillet);
            btn.setTextColor(ResUtil.getColorById(R.color.color_9f9f9f));
        } else if (btnCount == 1) {
            btn.setBackgroundResource(R.drawable.btn_green_corner_bg);
            btn.setTextColor(ResUtil.getColorById(android.R.color.white));
        }
//		if (btnCount > 0) {
//			view dividerView = new ImageView(this.getContext());
//			dividerView.setBackgroundColor(R.color.color_line);
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, LayoutParams.MATCH_PARENT);
//			params.setMargins(0, Display.Dip2Px(10), 0, Display.Dip2Px(10));
//			dividerView.setLayoutParams(params);
//			btnLayout.addView(dividerView);
//		}

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (btnCount == 0) {
            params.leftMargin = Display.dip2Px(18);
            params.rightMargin = Display.dip2Px(6);
        } else if (btnCount == 1) {
            params.leftMargin = Display.dip2Px(6);
            params.rightMargin = Display.dip2Px(18);
        }
        params.height = Display.dip2Px(42);
        params.width = ((int) (sWidth * DIALOG_KINDNOTE_WIDTH) >> 1) - Display.dip2Px(24);
        params.topMargin = Display.dip2Px(12);
        params.bottomMargin = Display.dip2Px(12);
        params.gravity = Gravity.CENTER;
        btnLayout.addView(btn, params);
        final int index = btnCount;
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                linster.onClick(CustomDialog.this, index);
                CustomDialog.this.dismiss();
            }
        });
        btnCount++;
        btns.add(btn);
        return btn;
    }

    public static class AlertParams {
        public AlertParams(Context context) {
            this.mContext = context;
        }

        private Context mContext;
        private DialogInterface mDialogInterface;
        private CharSequence mMessage;
        private CharSequence mPositiveButtonText;
        private OnClickListener mPositiveButtonListener;
        private CharSequence mTitle;
        private Drawable bitmap;
        private CharSequence mNegativeButtonText;
        private OnClickListener mNegativeButtonListener;
        private boolean mCancelable = true;
        private boolean mCanceledOnTouchOutside = false;

        private CharSequence mNeutralButtonText;
        private OnClickListener mNeutralButtonListener;

        private OnCancelListener mOnCancelListener;
        private View mView;
    }

    public static class Builder {
        private final AlertParams P;

        public Builder setBackGroundBitmap(Drawable drawable){
            P.bitmap=drawable;
            return this;
        }
        public Builder setTitle(CharSequence title) {
            P.mTitle = title;
            return this;
        }

        public Builder setTitle(int titleId) {
            P.mTitle = P.mContext.getText(titleId);
            return this;
        }

        /**
         * Constructor using a context for this builder and the
         * {@link AlertDialog} it creates.
         */
        public Builder(Context context) {
            P = new AlertParams(context);
        }

        /**
         * Set the message to display using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setMessage(int messageId) {
            P.mMessage = P.mContext.getText(messageId);
            return this;
        }

        /**
         * Set the message to display.
         *
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setMessage(CharSequence message) {
            P.mMessage = message;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog
         * is pressed.
         *
         * @param textId   The resource id of the text to display in the positive
         *                 button
         * @param listener The {@link OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setPositiveButton(int textId, final OnClickListener listener) {
            P.mPositiveButtonText = P.mContext.getText(textId);
            P.mPositiveButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the positive button of the dialog
         * is pressed.
         *
         * @param text     The text to display in the positive button
         * @param listener The {@link OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setPositiveButton(CharSequence text, final OnClickListener listener) {
            P.mPositiveButtonText = text;
            P.mPositiveButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog
         * is pressed.
         *
         * @param textId   The resource id of the text to display in the negative
         *                 button
         * @param listener The {@link OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setNegativeButton(int textId, final OnClickListener listener) {
            P.mNegativeButtonText = P.mContext.getText(textId);
            P.mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the negative button of the dialog
         * is pressed.
         *
         * @param text     The text to display in the negative button
         * @param listener The {@link OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setNegativeButton(CharSequence text, final OnClickListener listener) {
            P.mNegativeButtonText = text;
            P.mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the neutral button of the dialog is
         * pressed.
         *
         * @param textId   The resource id of the text to display in the neutral
         *                 button
         * @param listener The {@link OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setNeutralButton(int textId, final OnClickListener listener) {
            P.mNeutralButtonText = P.mContext.getText(textId);
            P.mNeutralButtonListener = listener;
            return this;
        }

        /**
         * Set a listener to be invoked when the neutral button of the dialog is
         * pressed.
         *
         * @param text     The text to display in the neutral button
         * @param listener The {@link OnClickListener} to use.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setNeutralButton(CharSequence text, final OnClickListener listener) {
            P.mNeutralButtonText = text;
            P.mNeutralButtonListener = listener;
            return this;
        }

        /**
         * Sets whether the dialog is cancelable or not default is true.
         *
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * Sets whether this dialog is canceled when touched outside the
         * window's bounds. If setting to true, the dialog is set to be
         * cancelable if not already set.
         *
         * @param canceledOnTouchOutside Whether the dialog should be canceled when touched outside
         *                               the window.
         */
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            P.mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * Sets the callback that will be called if the dialog is canceled.
         *
         * @return This Builder object to allow for chaining of calls to set
         * methods
         * @see #setCancelable(boolean)
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setView(View view) {
            P.mView = view;
            return this;
        }

        /**
         * Creates a {@link AlertDialog} with the arguments supplied to this
         * builder. It does not {@link Dialog#show()} the dialog. This allows
         * the user to do any extra processing before displaying the dialog. Use
         * {@link #show()} if you don't have any other processing to do and want
         * this to be created and displayed.
         */
        public CustomDialog create() {
            final CustomDialog dialog = new CustomDialog(P.mContext);
            dialog.setCancelable(P.mCancelable);
            dialog.setCanceledOnTouchOutside(P.mCanceledOnTouchOutside);
            dialog.setOnCancelListener(P.mOnCancelListener);
            if (P.mTitle != null) {
                dialog.setTitle(P.mTitle);
            }


            if (P.mNegativeButtonListener != null) {
                dialog.addButton(P.mNegativeButtonText, P.mNegativeButtonListener);
            }
            if (P.mPositiveButtonListener != null) {
                dialog.addButton(P.mPositiveButtonText, P.mPositiveButtonListener);
            }

            if (P.mNeutralButtonListener != null) {
                dialog.addButton(P.mNeutralButtonText, P.mNeutralButtonListener);
            }

            if (P.mPositiveButtonListener == null && P.mNegativeButtonListener == null && P.mNeutralButtonListener == null) {
                dialog.addButton(R.string.confirm, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            if (P.mView != null) {
                dialog.setView(P.mView);
                if (dialog.btnLayout != null) {
                    dialog.btnLayout.setVisibility(View.GONE);
                }
                if (dialog.titleView != null) {
                    dialog.titleView.setVisibility(View.GONE);
                }
            }
            if (btns.size() == 1) {
                Button btn = btns.get(0);
                if (btn != null) {
                    btn.setBackgroundResource(R.drawable.btn_green_corner_bg);
                    btn.setTextColor(ResUtil.getColorById(android.R.color.white));
                }
            }
            dialog.setMsg(P.mMessage);
            dialog.getWindow().setBackgroundDrawable(P.bitmap);

            dialog.getWindow().setLayout((int) (sWidth * DIALOG_KINDNOTE_WIDTH), android.view.WindowManager.LayoutParams.WRAP_CONTENT);

            return dialog;
        }

        /**
         * Creates a {@link AlertDialog} with the arguments supplied to this
         * builder and {@link Dialog#show()}'s the dialog.
         */
        public CustomDialog show() {
            CustomDialog dialog = create();
            dialog.show();
            dialog.getWindow().setLayout((int) (sWidth * DIALOG_KINDNOTE_WIDTH), android.view.WindowManager.LayoutParams.WRAP_CONTENT);
            return dialog;
        }

    }

}
