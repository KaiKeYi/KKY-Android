package com.xlg.library.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.xlg.library.R;
import com.xlg.library.base.BaseApp;
import com.xlg.library.utils.CommonUtil;
import com.xlg.library.utils.UiThreadHandler;

import java.util.List;

public class PopDialog implements OnClickListener, OnItemClickListener {

	private View mDialogView;
	private View popView;
	private boolean isDismiss;

	private IPopClickListener mPopClickListener;

	private DialogType mType;
	private List<String> mItemContents;
	private ListView listView;
	
	private static PopDialog dialogHelper;
	
	
	public static PopDialog getInstance(){
		
		if (null == dialogHelper) {
			dialogHelper = new PopDialog();
		}
		return dialogHelper;
	}

	private PopDialog() {
		super();
	}

	private void showDialogItemContents(Activity app, boolean isRedFord,
			List<String> itemContents, int position, Integer... resImageArrays) {

		LayoutInflater inflater = LayoutInflater.from(BaseApp.getAppContext());
		mDialogView = inflater.inflate(R.layout.include_pop_dialog, null);
		listView = (ListView) mDialogView.findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);

		mDialogView.findViewById(R.id.rv_pop_dialog).setOnClickListener(this);

		popView = mDialogView.findViewById(R.id.lay_pop_dialog);

		TextView cancelText = (TextView) mDialogView
				.findViewById(R.id.tv_cancel_pop_dialog);
		cancelText.setOnClickListener(this);

		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		app.addContentView(mDialogView, params);
		showDialog();

		this.mItemContents = itemContents;
		PopAdapter mAdapter = new PopAdapter(app, isRedFord,
				position, resImageArrays);
		listView.setAdapter(mAdapter);
	}

	public void setPopClickListener(IPopClickListener popClickListener,
			DialogType type) {
		this.mPopClickListener = popClickListener;
		this.mType = type;
	}

	public void setPopClickListener(IPopClickListener popClickListener) {
		this.mPopClickListener = popClickListener;
	}

	/**
	 * 带有图片标志
	 * 
	 * @param isRedFord
	 * @param itemContents
	 *            文本内容列表
	 * @param itemResources
	 *            图片资源
	 * @param position
	 *            文本内容在列表中的位置
	 */
	public synchronized void showLoading(boolean isRedFord,
			List<String> itemContents, int position, Integer... itemResources) {

		Activity app = CommonUtil.getCurrentActivity();
		if (null != app) {
			isDismiss = false;
			showDialogItemContents(app, isRedFord, itemContents, position,
					itemResources);
		}
	}


	public void dismiss() {
		if (popView==null){
			return;
		}
		if (!isDismiss) {
			isDismiss = true;
			Animation animation = AnimationUtils.loadAnimation(
					BaseApp.getAppContext(), R.anim.translate_buttom);
			popView.startAnimation(animation);

		}

		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mDialogView.setVisibility(View.GONE);
				mDialogView = null;
				isDismiss = false;
			}
		}, 500);

	}

	public boolean isShowing() {
		return mDialogView != null;
	}

	private void showDialog() {

		Animation animation = AnimationUtils.loadAnimation(BaseApp.getAppContext(),
				R.anim.translate_top);
		popView.startAnimation(animation);
		UiThreadHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mDialogView.setVisibility(View.VISIBLE);
			}
		}, 350);
	}

	class PopAdapter extends BaseAdapter {

		private Context mContext;
		private TextView mItemText;
		private ImageView mItemImage;
		private boolean isRedFold;
		private int mPostion;
		private boolean isMulty;
		private Integer[] resImageArrays;

		public PopAdapter(Context context, boolean isRedFold, int position,
				Integer... resImageArrays) {
			this.mContext = context;
			this.isRedFold = isRedFold;
			this.mPostion = position;
			if (null != resImageArrays && resImageArrays.length > 0) {
				this.isMulty = true;
				this.resImageArrays = resImageArrays;
			}
		}

		@Override
		public int getCount() {

			return mItemContents == null ? 0 : mItemContents.size();
		}

		@Override
		public String getItem(int arg0) {

			return mItemContents.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {

			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {

			if (null == arg1) {

				arg1 = LayoutInflater.from(mContext).inflate(
							R.layout.include_pop_dialog_item, null);
				mItemText = (TextView) arg1
						.findViewById(R.id.tv_pop_dialog_item);

				mItemText.setTag(mPostion);
				arg1.setTag(mItemText);
			} else {
				mItemText = (TextView) arg1.getTag();
			}

			mItemText.setText(mItemContents.get(arg0));
			if (isRedFold) {
				mItemText.setTextColor(Color.parseColor("#FF4A4A"));
			}
			return arg1;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView textView = (TextView) arg1.getTag();
		int postion = (Integer) textView.getTag();
		mPopClickListener.onPopItemClick(mType, arg2, postion,mItemContents);
		dismiss();
	}

	@Override
	public void onClick(View arg0) {

		dismiss();
	}


	public interface IPopClickListener {
		/**
		 * 
		 * @param type
		 * @param position
		 *            表示对话框本身的索引
		 * @param dialogIndex
		 *            表示list item的索引
		 */
		void onPopItemClick(DialogType type, int dialogIndex, int position, List<? extends String> lists);
	}

	public enum DialogType {
		
		_DEFAULT_,
	}
}
