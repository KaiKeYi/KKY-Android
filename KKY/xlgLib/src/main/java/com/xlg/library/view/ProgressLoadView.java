package com.xlg.library.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;

import com.xlg.library.R;
import com.xlg.library.helper.ThreadHelper;
import com.xlg.library.utils.CommonUtil;
import com.xlg.library.utils.UiThreadHandler;

import java.util.Random;

/**
 * 类似网页加载进度条
 */
public class ProgressLoadView extends ProgressBar {

	private Drawable m_indicator;
	private int m_offset = 5;
	private int count = 40;
	private TextPaint m_textPaint;
	private Formatter m_formatter;
	private View coverView;

	private int progress = 0;
	private Message message;
	/**
	 * The Is can invok.
	 */
	boolean isCanInvok = true;

	/**
	 * The Randle.
	 */
	Random randle = new Random();

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			int p = msg.what;
			if (p >= count) {
				if (p >= 100) {
					setProgress(p);
					hideProgress(300);
					return;
				}

				if (!isCanInvok) {
					return;
				}

				int rand = 4 + Math.abs(randle.nextInt() % 4);
				setProgress(rand * 10);
				isCanInvok = false;
				handler.removeCallbacks(runnable);

			} else {
				setProgress(p);
			}
		}

	};

	/**
	 * Start loading.
	 *
	 * @param isCovered the is covered
	 */
	public void startLoading(boolean ... isCovered) {

		if (isCovered.length > 0 && isCovered[0]) {

			Activity acc = CommonUtil.getCurrentActivity();
			DisplayMetrics dm = new DisplayMetrics();
			acc.getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			coverView = new View(acc);
			android.view.ViewGroup.LayoutParams params = new android.view.ViewGroup.LayoutParams(
					width, height);
			coverView.setClickable(true);
			acc.addContentView(coverView,params);
			acc = null;
		}
		resetValues();
		ThreadHelper.excuteThread(runnable);
	}

	private void resetValues() {
		progress = 0;
		isCanInvok = true;
		setMax(100);
		setProgress(0);
		setVisibility(View.VISIBLE);
	}

	/**
	 * Hide progress.
	 *
	 * @param timeOut the time out
	 */
	protected void hideProgress(int timeOut) {

		AlphaAnimation myAnimation_Alpha = new AlphaAnimation(1.0f, 0f);
		myAnimation_Alpha.setDuration(timeOut);
		startAnimation(myAnimation_Alpha);

		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setVisibility(View.INVISIBLE);

				digestCoverView();
			}
		}, timeOut);
	}

	/**
	 * Stop loading.
	 *
	 * @param isOnSucc 表示是否请求成功 onSucc、onFailure
	 */
	public void stopLoading(boolean isOnSucc) {

		if (isOnSucc) {
			message = handler.obtainMessage();
			message.obj = isOnSucc;
			progress = 100;
			message.what = progress;
			handler.sendEmptyMessage(message.what);
			digestCoverView();
		} else {
			hideProgress(300);
		}
	}

	private void digestCoverView() {
		if (null != coverView) {
            coverView.setVisibility(View.GONE);
            coverView = null;
        }
	}

	/**
	 * The Runnable.
	 */
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			
			message = handler.obtainMessage();
			try {
				
				for (int i = 1; i < count; i++) {
					progress += 3;
					if (progress < 100) {
						message.what = progress;
						handler.sendEmptyMessage(message.what);
						Thread.sleep(100);
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * Instantiates a new Progress load view.
	 *
	 * @param context the context
	 */
	public ProgressLoadView(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new Progress load view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public ProgressLoadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new Progress load view.
	 *
	 * @param context  the context
	 * @param attrs    the attrs
	 * @param defStyle the def style
	 */
	public ProgressLoadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		m_textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		m_textPaint.density = getResources().getDisplayMetrics().density;

		m_textPaint.setColor(Color.WHITE);
		m_textPaint.setTextAlign(Align.CENTER);
		m_textPaint.setTextSize(10);
		m_textPaint.setFakeBoldText(true);
		m_textPaint.setDither(true);
		m_textPaint.setAntiAlias(true);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SaundProgressBar, defStyle, 0);

		if (a != null) {
			m_textPaint.setTextSize(a.getDimension(
					R.styleable.SaundProgressBar_textSize, 10));
			m_textPaint.setColor(a.getColor(
					R.styleable.SaundProgressBar_textColor, Color.WHITE));

			int alignIndex = (a.getInt(R.styleable.SaundProgressBar_textAlign,
					1));
			if (alignIndex == 0) {
				m_textPaint.setTextAlign(Align.LEFT);
			} else if (alignIndex == 1) {
				m_textPaint.setTextAlign(Align.CENTER);
			} else if (alignIndex == 2) {
				m_textPaint.setTextAlign(Align.RIGHT);
			}

			int textStyle = (a
					.getInt(R.styleable.SaundProgressBar_textStyle, 1));
			if (textStyle == 0) {
				m_textPaint.setTextSkewX(0.0f);
				m_textPaint.setFakeBoldText(false);
			} else if (textStyle == 1) {
				m_textPaint.setTextSkewX(0.0f);
				m_textPaint.setFakeBoldText(true);
			} else if (textStyle == 2) {
				m_textPaint.setTextSkewX(-0.25f);
				m_textPaint.setFakeBoldText(false);
			}

			m_indicator = a
					.getDrawable(R.styleable.SaundProgressBar_progressIndicator);

			m_offset = (int) a.getDimension(
					R.styleable.SaundProgressBar_offset, 0);

			a.recycle();
		}
	}

	/**
	 * Sets progress indicator.
	 *
	 * @param indicator the indicator
	 */
	public void setProgressIndicator(Drawable indicator) {
		m_indicator = indicator;
	}

	/**
	 * Sets text formatter.
	 *
	 * @param formatter the formatter
	 */
	public void setTextFormatter(Formatter formatter) {
		m_formatter = formatter;
	}

	/**
	 * Sets offset.
	 *
	 * @param offset the offset
	 */
	public void setOffset(int offset) {
		m_offset = offset;
	}

	/**
	 * Sets text color.
	 *
	 * @param color the color
	 */
	public void setTextColor(int color) {
		m_textPaint.setColor(color);
	}

	/**
	 * Sets text size.
	 *
	 * @param size the size
	 */
	public void setTextSize(float size) {
		m_textPaint.setTextSize(size);
	}

	/**
	 * Sets text bold.
	 *
	 * @param bold the bold
	 */
	public void setTextBold(boolean bold) {
		m_textPaint.setFakeBoldText(true);
	}

	/**
	 * Sets text align.
	 *
	 * @param align the align
	 */
	public void setTextAlign(Align align) {
		m_textPaint.setTextAlign(align);
	}

	/**
	 * Sets paint.
	 *
	 * @param paint the paint
	 */
	public void setPaint(TextPaint paint) {
		m_textPaint = paint;
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (m_indicator != null) {
			final int width = getMeasuredWidth();
			final int height = getMeasuredHeight() + getIndicatorHeight();

			setMeasuredDimension(width, height);
		}
	}

	private int getIndicatorWidth() {
		if (m_indicator == null) {
			return 0;
		}

		Rect r = m_indicator.copyBounds();
		int width = r.width();

		return width;
	}

	private int getIndicatorHeight() {
		if (m_indicator == null) {
			return 0;
		}

		Rect r = m_indicator.copyBounds();
		int height = r.height();

		return height;
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		Drawable progressDrawable = getProgressDrawable();

		if (m_indicator != null) {
			if (progressDrawable != null
					&& progressDrawable instanceof LayerDrawable) {
				LayerDrawable d = (LayerDrawable) progressDrawable;

				for (int i = 0; i < d.getNumberOfLayers(); i++) {
					d.getDrawable(i).getBounds().top = getIndicatorHeight();

					d.getDrawable(i).getBounds().bottom = d.getDrawable(i)
							.getBounds().height()
							+ getIndicatorHeight();
				}
			} else if (progressDrawable != null) {
				progressDrawable.getBounds().top = m_indicator
						.getIntrinsicHeight();
				progressDrawable.getBounds().bottom = progressDrawable
						.getBounds().height() + getIndicatorHeight();
			}
		}

		updateProgressBar();

		super.onDraw(canvas);

		if (m_indicator != null) {
			canvas.save();
			int dx = 0;

			// get the position of the progress bar's right end
			if (progressDrawable != null
					&& progressDrawable instanceof LayerDrawable) {
				LayerDrawable d = (LayerDrawable) progressDrawable;
				Drawable progressBar = d.findDrawableByLayerId(R.id.progress);
				dx = progressBar.getBounds().right;
			} else if (progressDrawable != null) {
				dx = progressDrawable.getBounds().right;
			}

			// adjust for any additional offset
			dx = dx - getIndicatorWidth() / 2 - m_offset + getPaddingLeft();

			// translate the canvas to the position where we should draw the
			// indicator
			canvas.translate(dx, 0);

			m_indicator.draw(canvas);

			canvas.drawText(
					m_formatter != null ? m_formatter.getText(getProgress())
							: Math.round(getScale(getProgress()) * 100.0f)
									+ "%", getIndicatorWidth() / 2,
					getIndicatorHeight() / 2 + 1, m_textPaint);

			// restore canvas to original
			canvas.restore();
		}
	}

	@Override
	public synchronized void setProgress(int progress) {
		super.setProgress(progress);

		invalidate();
	}

	private float getScale(int progress) {
		float scale = getMax() > 0 ? (float) progress / (float) getMax() : 0;

		return scale;
	}

	private void updateProgressBar() {
		Drawable progressDrawable = getProgressDrawable();

		if (progressDrawable != null
				&& progressDrawable instanceof LayerDrawable) {
			LayerDrawable d = (LayerDrawable) progressDrawable;

			final float scale = getScale(getProgress());

			// get the progress bar and update it's size
			Drawable progressBar = d.findDrawableByLayerId(R.id.progress);

			final int width = d.getBounds().right - d.getBounds().left;

			if (progressBar != null) {
				Rect progressBarBounds = progressBar.getBounds();
				progressBarBounds.right = progressBarBounds.left
						+ (int) (width * scale + 0.5f);
				progressBar.setBounds(progressBarBounds);
			}
		}
	}

	/**
	 * The interface Formatter.
	 */
	public interface Formatter {
		/**
		 * Gets text.
		 *
		 * @param progress the progress
		 * @return the text
		 */
		public String getText(int progress);
	}

}
