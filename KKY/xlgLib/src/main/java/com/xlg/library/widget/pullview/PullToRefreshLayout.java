package com.xlg.library.widget.pullview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xlg.library.R;
import java.util.Timer;
import java.util.TimerTask;

public class PullToRefreshLayout extends RelativeLayout {
	
	public static final int INIT = 1 << 0;
	public static final int RELEASE_TO_REFRESH = 1 << 1;
	public static final int REFRESHING = 1 << 2;
	public static final int RELEASE_TO_LOAD = 1 << 3;
	public static final int LOADING = 1 << 4;
	public static final int DONE = 1 << 5;

	// 当前状态
	private int state = INIT;
	// 刷新回调接口
	private IRefreshListener mListener;
	// 刷新成功
	public static final int SUCCEED = 0;
	// 刷新失败
	public static final int FAIL = 1;
	// 按下Y坐标，上一个事件点Y坐标
	private float downY, lastY;

	public float pullDownY = 0;
	// 上拉的距离
	private float pullUpY = 0;

	// 释放刷新的距离
	private float refreshDist = 200;
	// 释放加载的距离
	private float loadmoreDist = 200;

	private MyTimer timer;

	// 回滚速度
	public float MOVE_SPEED = 128;

	// 第一次执行布局
	private boolean isLayout = false;

	// 在刷新过程中滑动操作
	private boolean isTouch = false;

	// 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
	private float radio = 12;

	// 下拉箭头的转180°动画
	private RotateAnimation rotateAnimation;
	// 均匀旋转动画
	private RotateAnimation refreshingAnimation;

	// 下拉头
	private View refreshView;
	// 下拉的箭头
	private View pullView;
	// 正在刷新的图标
	private View refreshingView;
	// 刷新结果图标
	private View refreshStateImageView;
	// 刷新结果：成功或失败
	private TextView refreshStateTextView;

	// 实现了Pullable接口的View
	private View pullableView;
	// 过滤多点触碰
	private int mEvents;
	private boolean canPullDown = true;
	private boolean canPullUp = true;

	/**
	 * 执行自动回滚的handler
	 */
	Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// 回弹速度随下拉距离moveDeltaY增大而增大
			MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2
					/ getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
			if (!isTouch) {
				// 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
				if (state == REFRESHING && pullDownY <= refreshDist) {
					pullDownY = refreshDist;
					timer.cancel();
				} else if (state == LOADING && -pullUpY <= loadmoreDist) {
					pullUpY = -loadmoreDist;
					timer.cancel();
				}

			}
			if (pullDownY > 0)
				pullDownY -= MOVE_SPEED;
			else if (pullUpY < 0)
				pullUpY += MOVE_SPEED;
			if (pullDownY < 0) {
				// 已完成回弹
				pullDownY = 0;
				pullView.clearAnimation();
				// 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
				if (state != REFRESHING && state != LOADING)
					changeState(INIT);
				timer.cancel();
				requestLayout();
			}

			// 刷新布局,会自动调用onLayout
			requestLayout();

			// 没有拖拉或者回弹完成
			if (pullDownY + Math.abs(pullUpY) == 0)
				timer.cancel();
		}

	};

	public void setOnRefreshListener(IRefreshListener listener) {
		mListener = listener;
	}

	public PullToRefreshLayout(Context context) {
		super(context);
		initView(context);
	}

	public PullToRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		// mContext = context;
		timer = new MyTimer(updateHandler);
		rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
				context, R.anim.reverse_anim);
		refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
				context, R.anim.rotating_anim);
		// 添加匀速转动动画
		LinearInterpolator lir = new LinearInterpolator();
		rotateAnimation.setInterpolator(lir);
		refreshingAnimation.setInterpolator(lir);
	}

	private void hide(int time) {
		timer.schedule(time);
	}

	public void refreshFinish(boolean isSucc) {

		int refreshResult;
		if (isSucc)
			refreshResult = SUCCEED;
		else
			refreshResult = FAIL;
		refreshingView.clearAnimation();
		refreshingView.setVisibility(View.GONE);
		switch (refreshResult) {
		case SUCCEED:
			// 刷新成功
			refreshStateImageView.setVisibility(View.VISIBLE);
			refreshStateTextView.setText(R.string.refresh_succeed);

			break;
		case FAIL:
		default:
			// 刷新失败
			refreshStateImageView.setVisibility(View.VISIBLE);
			refreshStateTextView.setText(R.string.refresh_fail);

			break;
		}
		if (pullDownY > 0) {
			// 刷新结果停留300毫秒
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					changeState(DONE);
					hide(20);
				}
			}.sendEmptyMessageDelayed(0, 300);
		} else {
			changeState(DONE);
			hide(20);
		}
	}


	private void changeState(int to) {
		state = to;
		switch (state) {
		case INIT:
			// 下拉布局初始状态
			refreshStateImageView.setVisibility(View.GONE);
			refreshStateTextView.setText(R.string.pull_to_refresh);
			pullView.clearAnimation();
			pullView.setVisibility(View.VISIBLE);

			break;
		case RELEASE_TO_REFRESH:
			// 释放刷新状态
			refreshStateTextView.setText(R.string.release_to_refresh);
			pullView.startAnimation(rotateAnimation);
			break;
		case REFRESHING:
			// 正在刷新状态
			pullView.clearAnimation();
			refreshingView.setVisibility(View.VISIBLE);
			pullView.setVisibility(View.INVISIBLE);
			refreshingView.startAnimation(refreshingAnimation);
			refreshStateTextView.setText(R.string.refreshing);
			break;

		case DONE:
			break;
		}
	}

	/**
	 * 不限制上拉或下拉
	 */
	private void releasePull() {
		canPullDown = true;
		canPullUp = true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			downY = ev.getY();
			lastY = downY;
			timer.cancel();
			mEvents = 0;
			releasePull();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_OUTSIDE:
			// 过滤多点触碰
			mEvents = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mEvents == 0) {
				if (pullDownY > 0
						|| (((IPullStateListener) pullableView).canPullDown()
								&& canPullDown && state != LOADING)) {
					// 可以下拉，正在加载时不能下拉
					// 对实际滑动距离做缩小，造成用力拉的感觉
					pullDownY = pullDownY + (ev.getY() - lastY) / radio;
					if (pullDownY < 0) {
						pullDownY = 0;
						canPullDown = false;
						canPullUp = true;
					}
					if (pullDownY > getMeasuredHeight())
						pullDownY = getMeasuredHeight();
					if (state == REFRESHING) {
						// 正在刷新的时候触摸移动
						isTouch = true;
					}
				} else if (pullUpY < 0
						|| (((IPullStateListener) pullableView).canPullUp() && canPullUp && state != REFRESHING)) {
					// 可以上拉，正在刷新时不能上拉
					pullUpY = pullUpY + (ev.getY() - lastY) / radio;
					if (pullUpY > 0) {
						pullUpY = 0;
						canPullDown = true;
						canPullUp = false;
					}
					if (pullUpY < -getMeasuredHeight())
						pullUpY = -getMeasuredHeight();
					if (state == LOADING) {
						// 正在加载的时候触摸移动
						isTouch = true;
					}
				} else
					releasePull();
			} else
				mEvents = 0;
			lastY = ev.getY();
			// 根据下拉距离改变比例
			radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
					* (pullDownY + Math.abs(pullUpY))));
			if (pullDownY > 0 || pullUpY < 0)
				requestLayout();
			if (pullDownY > 0) {
				if (pullDownY <= refreshDist
						&& (state == RELEASE_TO_REFRESH || state == DONE)) {
					changeState(INIT);
				}
				if (pullDownY >= refreshDist && state == INIT) {
					changeState(RELEASE_TO_REFRESH);
				}
			}
			if ((pullDownY + Math.abs(pullUpY)) > 8) {
				ev.setAction(MotionEvent.ACTION_CANCEL);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (pullDownY > refreshDist || -pullUpY > loadmoreDist){
				isTouch = false;
			}
			if (state == RELEASE_TO_REFRESH) {
				changeState(REFRESHING);
				// 刷新操作
				if (mListener != null)
					mListener.onRefresh();
			} else if (state == RELEASE_TO_LOAD) {
				changeState(LOADING);

			}
			hide(5);
		default:
			break;
		}
		// 事件分发交给父类
		super.dispatchTouchEvent(ev);
		return true;
	}

	private class AutoRefreshAndLoadTask extends
			AsyncTask<Integer, Float, String> {

		@Override
		protected String doInBackground(Integer... params) {
			while (pullDownY < 4 / 3 * refreshDist) {
				pullDownY += MOVE_SPEED;
				publishProgress(pullDownY);
				try {
					Thread.sleep(params[0]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			changeState(REFRESHING);
			// 刷新操作
			if (mListener != null)
				mListener.onRefresh();
			hide(20);
		}

		@Override
		protected void onProgressUpdate(Float... values) {
			if (pullDownY > refreshDist)
				changeState(RELEASE_TO_REFRESH);
			requestLayout();
		}

	}

	/**
	 * 自动刷新
	 */
	public void autoRefresh() {
		AutoRefreshAndLoadTask task = new AutoRefreshAndLoadTask();
		task.execute(20);
	}

	/**
	 * 自动加载
	 */
	public void autoLoad() {
		pullUpY = -loadmoreDist;
		requestLayout();
		changeState(LOADING);
	}

	private void initView() {
		// 初始化下拉布局
		pullView = refreshView.findViewById(R.id.pull_icon);
		refreshStateTextView = (TextView) refreshView
				.findViewById(R.id.state_tv);
		refreshingView = refreshView.findViewById(R.id.refreshing_icon);
		refreshStateImageView = refreshView.findViewById(R.id.state_iv);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		if (!isLayout) {
			refreshView = getChildAt(0);
			pullableView = getChildAt(1);
			isLayout = true;
			initView();
			refreshDist = ((ViewGroup) refreshView).getChildAt(0)
					.getMeasuredHeight();
		}
		refreshView.layout(0,
				(int) (pullDownY + pullUpY) - refreshView.getMeasuredHeight(),
				refreshView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
		pullableView.layout(0, (int) (pullDownY + pullUpY),
				pullableView.getMeasuredWidth(), (int) (pullDownY + pullUpY)
						+ pullableView.getMeasuredHeight());
	}

	class MyTimer {
		private Handler handler;
		private Timer timer;
		private MyTask mTask;

		public MyTimer(Handler handler) {
			this.handler = handler;
			timer = new Timer();
		}

		public void schedule(long period) {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
			mTask = new MyTask(handler);
			timer.schedule(mTask, 0, period);
		}

		public void cancel() {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
		}

		class MyTask extends TimerTask {
			private Handler handler;

			public MyTask(Handler handler) {
				this.handler = handler;
			}

			@Override
			public void run() {
				handler.obtainMessage().sendToTarget();
			}

		}
	}

}
