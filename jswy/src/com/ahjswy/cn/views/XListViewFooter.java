package com.ahjswy.cn.views;

import com.ahjswy.cn.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XListViewFooter extends LinearLayout {
	public static final int STATE_LOADING = 2;
	public static final int STATE_NORMAL = 0;
	public static final int STATE_READY = 1;
	private final int ROTATE_ANIM_DURATION = 180;
	private ImageView mArrowImageView;
	private View mContentView;
	private Context mContext;
	private TextView mHintView;
	private View mProgressBar;
	private Animation mRotateDownAnim;
	private Animation mRotateUpAnim;
	private int mState = 0;

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attributeset) {
		super(context, attributeset);
		initView(context);
	}

	private void initView(Context context) {
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.xlistview_footer, null);
		view.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
		mContentView = view.findViewById(R.id.xlistview_footer_content);
		mArrowImageView = ((ImageView) view.findViewById(R.id.xlistview_header_arrow));
		mProgressBar = view.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = ((TextView) view.findViewById(R.id.xlistview_footer_hint_textview));
		mRotateUpAnim = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
		addView(view);
	}

	public int getBottomMargin() {
		return ((LinearLayout.LayoutParams) this.mContentView.getLayoutParams()).bottomMargin;
	}

	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	public void loading() {
		mHintView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
	}

	public void setBottomMargin(int height) {
		if (height < 0) {
			return;
		}
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public void setState(int state) {
		mHintView.setVisibility(View.INVISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);
		if (state == STATE_READY) {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(this.mRotateDownAnim);
		} else if (state == STATE_LOADING) {
			mProgressBar.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(4);
			mProgressBar.setVisibility(0);
		} else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mRotateUpAnim);
		}

	}

	/**
	 * show footer
	 */
	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}
}
