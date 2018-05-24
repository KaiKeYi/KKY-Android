package com.xlg.library.widget.ProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xlg.library.R;
import com.xlg.library.base.BaseApp;

public class RoundProgressBarWidthNumber extends HorizontalProgressBarWithNumber {
    /**
     * mRadius of view
     */
    private int mRadius = dp2px(30);
    private String text = "剩余电量";

    public RoundProgressBarWidthNumber(Context context) {
        this(context, null);
    }

    public RoundProgressBarWidthNumber(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWidthNumber);
        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBarWidthNumber_radius, mRadius);

        ta.recycle();


        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Cap.ROUND);

        mPaint1.setStyle(Style.STROKE);
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setStrokeCap(Cap.ROUND);
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public void setText(String txt) {
        this.text = txt;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);

        int paintWidth = Math.max(mReachedProgressBarHeight, mUnReachedProgressBarHeight);

        if (heightMode != View.MeasureSpec.EXACTLY) {

            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + mRadius * 2 + paintWidth);
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(exceptHeight, View.MeasureSpec.EXACTLY);
        }
        if (widthMode != View.MeasureSpec.EXACTLY) {
            int exceptWidth = (int) (getPaddingLeft() + getPaddingRight() + mRadius * 2 + paintWidth);
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(exceptWidth, View.MeasureSpec.EXACTLY);
        }

        super.onMeasure(heightMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        mPaint.setStyle(Style.STROKE);

        float view_width = getWidth();
        Log.d("zt", "rount width " + view_width);
        // draw unreadedbar
        mPaint.setColor(mUnReachedBarColor);
        mPaint.setStrokeWidth(mReachedProgressBarHeight);
        canvas.drawArc(new RectF(mReachedProgressBarHeight, mReachedProgressBarHeight, view_width - mReachedProgressBarHeight, view_width - mReachedProgressBarHeight), 0, 360, false, mPaint);

        //draw 外圆
        int left = mReachedProgressBarHeight;
        RectF rectF = new RectF(left, left, view_width - mReachedProgressBarHeight, view_width - mReachedProgressBarHeight);
        mPaint1.setStrokeWidth((float) (mReachedProgressBarHeight * 2));
        mPaint1.setColor(mReachedBarColor);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(rectF, 270, -sweepAngle, false, mPaint1);

        // draw text
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        float txtWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;
        canvas.drawText(text, mRadius - txtWidth / 2, (float) (mRadius * 0.9), mPaint);

        //draw progress
        mPaint.setColor(mProgressTextColor);
        mPaint.setTextSize(mProgressTextSize);
        mPaint.setTypeface(Typeface.createFromAsset(BaseApp.getAppContext().getAssets(), "font/avanti_bold.ttf"));
        String progressTxt = String.valueOf(getProgress()) + "%";
        float progressWidth = mPaint.measureText(progressTxt);
        canvas.drawText(progressTxt, mRadius - progressWidth / 2, (float) (mRadius * 1.4 + textHeight), mPaint);

        canvas.restore();

    }

}